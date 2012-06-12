import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class loginToServer {
	String username = null; 
	String password = null; 
	private String creditsLeft;
	private int cause = 0;
	
	public String servip = null; // This has to be set with the server ip who is hosting the login menu
	public int servport = 0; // This has to be set with the port the authentication app is running on
	
	
	public loginToServer(String username, String password){
		
		this.username = username;
		this.password = password;
	}
	
	public boolean start(){
		
		try {
			Socket s = new Socket(servip, servport);
			if(s.isConnected()){
				System.out.println("Connected!");
				DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				PrintStream out = new PrintStream(new BufferedOutputStream(s.getOutputStream()), true);
				
				System.out.println("Sending auth details..");
				out.println("bauth;usr="+this.username+";pass="+this.password);
				
				
				
				
				if(in.readLine().equals("Succesfully logged in!")){
					String credit[] = in.readLine().split("=");
					creditsLeft = credit[1];
					if(creditsLeft.equals("No credits left")){
						cause = 2;
						return false;
					}
					out.close();
					in.close();
					s.close();
					return true;
				}else{
					cause = 1;
					return false;
				}
				
				
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	public int getCause(){
		return this.cause;
	}
	
	public String getCreditsLeft(){
		return creditsLeft;
	}
}
