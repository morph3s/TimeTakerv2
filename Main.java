import javax.swing.JOptionPane;

/*
 *  Author: Morph3s
 *  Created: 20/3/2012
 *  Project name: TimeTaker Renewed
 *  Version: 3.3
 */


public class Main extends Thread {
	 
	
    private final String host = "176.9.65.83";  // The ip address of www.n.dk where netstationen is hosted
    private final int port = 499;               // The port number of where www.n.dk runs the netstationen application. Don't change unless you're running a private server
    private static String username;
    private static String password;
    private boolean useProxy = false;
    
    ConnectToSock cn = null;
    Protocol pr = new Protocol();
	
	
	public static boolean shouldRestart = false;
	

	public Main(String username, String password, boolean useProxy){
		Main.username = username;
		Main.password = password;
		this.useProxy = useProxy;
	}
	
	
	public void start(){
		
		
		/*
		this.loginstring = this.ll.getGuid(username, password);
		TimeTakerRenewedUI.guidLabel.setText(this.loginstring);
		*/
		
		
		StrangeBase.startchar = 'a';
		
		if ((this.host != null) && (this.port > 0)) {
		
		   this.cn = new ConnectToSock(host, port, username, password, useProxy);
		   
		   if (this.cn != null){
			   
			   cn.start();
			   while(true){
			   
				   
				   // Wait here until you should restart!
				   while(!shouldRestart){
					   // Check every 500 ms. 500 ms delay makes performance much better
					   try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   }
	
				   // Assume that shouldRestart became true:
				   
				   
				   try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   
			   }
			   
		   }else{
			   
		   }
		}else {
		    
		}
		
	}
	
	public void kills(){
		cn.shutdownAndCleanup(false);
		cn.interrupt();
		this.interrupt();
	}
		
		
	
}
