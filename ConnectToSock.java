import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ConnectToSock extends Thread
{
	  public static SocketAction socket;
	  private static String host = null;
	  private static int port = 0;
	  private static String loginstring = null;
	  private static String username = null;
	  private static String password = null;
	  
	  public static int playerid = 0;
	  public static int playerid2 = 0;
	
	  public static SendToSock sendtosock;
	  public static RecieveFromSock recvfromsock;
	  
	  public static boolean useProxy = false;
	  
	  
	  private static Login ll = new Login();
	  
	  public ConnectToSock(String host, int port, String username, String password, boolean useProxy) {
		  ConnectToSock.host = host;
		  this.port = port;
		  this.password = password;
		  this.username = username;
		  this.useProxy = useProxy;
	  }
	
	  
	  @Override
	  public void run(){
		  startConnecting();
	  }
	  
	  
	  public static boolean startConnecting(){
		  Runtime.getRuntime().addShutdownHook( new Thread() { 
			    @Override 
			    public void run() { 
			        shutdownAndCleanup(false); 
			    }
			});
		 
		 
	
		
	
	    
		ConnectToSock.loginstring = ConnectToSock.ll.getGuid(username, password, useProxy);
		if(ConnectToSock.loginstring == null){
			JOptionPane.showMessageDialog(null, "Kunne ikke logge ind. Tjek at brugernavn og kodeord er rigtigt og, at du ikke er banned");
			return false;
		}
		
		
		
		
		if(!useProxy){
			RequestSites rs = new RequestSites();
			rs.startRequestingBeforeChallengestring();
			
		}
				
		
		
		TimeTakerRenewedUI.guidLabel.setText(ConnectToSock.loginstring);
	    makeSocket();
		if (!login()) {
			JOptionPane.showMessageDialog(null, "Kunne ikke forbinde til serveren af ukendte årsager. Genstart programmet og prøv igen. Kontakt en admin for yderligere hjælp.");
			return false;
		}
		sendtosock = new SendToSock(ConnectToSock.playerid, ConnectToSock.playerid2);
	    recvfromsock = new RecieveFromSock();
	   
	    
	    sendtosock.start();
	    recvfromsock.start();
	    
	    
	    
	    if ((sendtosock.isAlive()) && (recvfromsock.isAlive())){
	    	TimeTakerRenewedUI.statusLabel.setText("Logged in !");
	    }else{
	    	TimeTakerRenewedUI.statusLabel.setText("Failed to login");
	    }
	    JOptionPane.showMessageDialog(null, "Du er nu logget ind korrekt!");
	    return true;
	  }
	
	  private static boolean login()
	  {
	    if (ConnectToSock.socket == null) {
	     
	      return false;
	    }
	
	    try{
	    	Thread.sleep(1000);
	    }
	    catch (InterruptedException e1) {
	    	e1.printStackTrace();
	    }
	    try{
		      String str1 = ConnectToSock.socket.receive();
		      int i = StrangeBase.parseBase(str1, 17) & 0x3FFF;
		      String base = StrangeBase.makeBase(i * i, 13);
		      ConnectToSock.socket.send(base + ",-1," + ConnectToSock.loginstring + '\000');
		      ConnectToSock.socket.flush();
		      str1 = null;
		      str1 = ConnectToSock.socket.receive();
		      
		      if (str1.charAt(0) == '§'){
		    	  ConnectToSock.socket.kill();
	    	 
		    	  return false;
		      }
		      jTokenizer jk = new jTokenizer(str1, ",");
		      ConnectToSock.playerid = jk.nextInt();
		      ConnectToSock.playerid2 = jk.nextInt();
		      
		      // Sending list rooms - Maybe needed for auth
		      ConnectToSock.socket.send("a");
		      return true;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return false;
	  }
	  
	
	  
	  public static void shutdownAndCleanup(boolean restart){
		  // Application closed. Cleaning up code.
		  
		  //If restart = true then restart else just close
		  
		  if(recvfromsock!=null){
			  recvfromsock.interrupt();
			  recvfromsock = null;
		  }
		  
		  if(sendtosock!=null){
			  sendtosock.interrupt();
			  sendtosock = null;
		  }
		  
		  
		  if(ConnectToSock.socket != null){
			  ConnectToSock.socket.flush();
			  ConnectToSock.socket.closeConnections();
			  ConnectToSock.socket.kill();
			  ConnectToSock.socket = null;
			  
		  }
		  
		  if(restart){
			  try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  TimeTakerRenewedUI.statusLabel.setText("Reconnecting..");
			  TimeTakerRenewedUI.guidLabel.setText("Reconnecting..");
			  loginstring = ConnectToSock.ll.getGuid(username, password, useProxy);
			  startConnecting();
			  
		  }
		  
		  
	  }
	  
	  
	  
	  
	  private static void makeSocket(){
		  try{
			 
			  ConnectToSock.socket = new SocketAction(new Socket(ConnectToSock.host, ConnectToSock.port));
			  
		  }catch (UnknownHostException e) {
			  e.printStackTrace();
		  }catch (IOException e) {
			  e.printStackTrace();
		  }
	  }
}