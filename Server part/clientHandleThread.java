import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.DeathByCaptcha.AccessDeniedException;
import com.DeathByCaptcha.Captcha;
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.SocketClient;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


public class clientHandleThread extends Thread {

	Socket clientSock = null;
	String ip;
	PrintStream out;
	BufferedReader in;
	public clientHandleThread(Socket clientSock){
		if(clientSock!=null){
			this.clientSock = clientSock;
			try {
				in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
				out = new PrintStream(new BufferedOutputStream(clientSock.getOutputStream()), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			return;
		}
		
	}
			
	public void run(){
		if(this.clientSock!=null){
			System.out.println("New connection!");
			
			ip = this.clientSock.getInetAddress().toString();
			
			
			System.out.println("IP: " + ip);
			handleConnection();
		}else{
			return;
		}
	}
	
	public void handleConnection(){
		
		try {
			
			
			String readLine;
			
			while((readLine = in.readLine()) != null){
				if(readLine.substring(0, 4).equals("auth")){
					authRespond(readLine);
					break;
				}
				else if(readLine.substring(0, 5).equals("bauth")){
					authLogin(readLine);
					break;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// Login but also handles captcha respond
	public void authRespond(String auth){
		System.out.println("Auth respond: " + auth);
		
		// Fetches the username and password AND captcha url from string: etc: auth;usr=wqreqre;pass=weqrwe;link=www.captcha123123.com/jpg.com
		String details[] = auth.split(";");
		String usernameTemp[] = details[1].split("=");
	    String username = usernameTemp[1];
		String passwordTemp[] = details[2].split("=");
		String password = passwordTemp[1];
		String linkTemp[] = details[3].split("=");
		String link = linkTemp[1];
		
		if(authenticateUser(username, password)){
			System.out.println("Logged in - Checking credits");
			
			if(checkCredits(username, password)){
				System.out.println("Credits available!");
				
				
				
				// Downloads the captcha image-
				downloadImage(link);
				
				
				// Sends the downloaded image to DeathByDecaptcha services
				String decodedCaptcha = decodeCaptcha();
				if(decodedCaptcha!=null){
					// Sends the capctcha back solved as string
					out.println("Captcha=" + decodedCaptcha);
					out.close();
					try {
						in.close();
						clientSock.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				System.out.println("Error! Not enough credits!");
				//out.println("0 credits available");
			}	
		}else{
			System.out.println("Unable to authenticate user");
		}
		
		
		
	}
	
	
	// Just plain regular login.
	
	public void authLogin(String readLine){
		System.out.println("Auth login: " + readLine);
		
		// Fetches the username and password from string: etc: bauth;usr=rqwer;pass=ewr
		String details[] = readLine.split(";");
		String usernameTemp[] = details[1].split("=");
	    String username = usernameTemp[1];
		String passwordTemp[] = details[2].split("=");
		String password = passwordTemp[1];
		
		System.out.println("Username: " + username + " Password: " + password);
		
		// Sending username and password to method which authenticates user in database
		if(authenticateUser(username, password)){
			System.out.println("User: " + username + " succesfully logged in!");
			int credits = getCredits(username, password);
			if(credits==0){
				out.println("err=No credits left");
			}else{
				out.println("credits=" + getCredits(username, password));
			}
			
			
			
		}else{
			System.out.println("Unable to authenticate user");
		}
		out.close();
		try {
			in.close();
			clientSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public boolean authenticateUser(String username, String password){
		
		// Checking username and password in database!
		try {
			java.sql.PreparedStatement loginStatement = null;
			java.sql.PreparedStatement updateIpStatement = null;
			
			String loginStatementString = "select * from accounts where username='"+username+"' and password='"+password+"'";
			String updateIpStatementString = "update accounts set lastip='"+( clientSock.getInetAddress().toString().substring(1) )+"' where username='"+username+"' and password='"+password+"'";
			
			loginStatement = Main.conn.prepareStatement(loginStatementString);
			updateIpStatement = Main.conn.prepareStatement(updateIpStatementString);
			
			loginStatement.executeQuery();
			updateIpStatement.executeUpdate();
			ResultSet rs = loginStatement.getResultSet();
			if(rs.next()){
				out.println("Succesfully logged in!");
				return true;
			}else{
				out.println("Unable to authenticate user");
				return false;
			}
		    
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	private int getCredits(String username, String password){
		
		try {
			java.sql.PreparedStatement creditCheckStatement = null;
			String creditCheckStatementString = "select credits from accounts where username='"+username+"' and password='"+password+"'";
		
			creditCheckStatement = Main.conn.prepareStatement(creditCheckStatementString);
		
			creditCheckStatement.executeQuery();
			ResultSet rs = creditCheckStatement.getResultSet();
			if(rs.next()){
				int credits = rs.getInt("credits");
				return credits;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean checkCredits(String username, String password){
	
		try {
			
			// Get credits
			java.sql.PreparedStatement creditCheckStatement = null;
			java.sql.PreparedStatement creditUpdateStatement = null;
			
			String creditCheckStatementString = "select credits from accounts where username='"+username+"' and password='"+password+"'";
			
			
			creditCheckStatement = Main.conn.prepareStatement(creditCheckStatementString);
			creditCheckStatement.executeQuery();
			ResultSet rs = creditCheckStatement.getResultSet();
			
			//Check if credits level is over 0
			if(rs.next()){
				int credit = rs.getInt("credits");
				if(credit > 0){
					String creditUpdateStatementString = "update accounts set credits="+(credit-1)+" where username='"+username+"' and password='"+password+"'";
					creditUpdateStatement = Main.conn.prepareStatement(creditUpdateStatementString);
					creditUpdateStatement.executeUpdate();
					out.println((credit-1) + " credits available");
					return true;
				}
			}
			
			return false;
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	
	
	public void downloadImage(String link) {
	    System.out.println("Downloading picture!");
	    try{
	      URL imgDL = new URL(link);
	
	      ReadableByteChannel rbc = Channels.newChannel(imgDL.openStream());
	      
	      
	      FileOutputStream fos = new FileOutputStream("captcha.jpg");
	      fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		  fos.close();
	    }catch (MalformedURLException e) {
	      e.printStackTrace();
	    }catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	public String decodeCaptcha(){
		
		// DeathByDecaptcha user details
		
		// Register a new account at deathbydecaptcha and replace user credentials here with your username and password
		
		String DBDusername = "MASKED" ;
		String DBDpassword = "MASKED";
		
		Client client = new SocketClient(DBDusername, DBDpassword);
	    try {
	    	
	    	Captcha captcha = client.decode("captcha.jpg", 70);
	    	if (captcha != null) {
		    	String capth = captcha.text;
		    	System.out.println("Solved the captcha: " + captcha.text);
		    	return capth;
		    }
	    }catch (AccessDeniedException e) {
	      System.out.println("Access denied. Check balance or/and login information...");
	    }catch (IOException e) {
	      e.printStackTrace();
	      System.out.println("IOException. Retrying");
	      client.close();
	      
	    }catch (com.DeathByCaptcha.Exception e) {
	      e.printStackTrace();
	    }catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    return null;
	  
	}
	
	
}
