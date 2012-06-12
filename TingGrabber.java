
public class TingGrabber extends Thread {
	int x,y, itemid;
	String lineToSend;
	public static boolean tooLong = false;
	
	
	public TingGrabber(int x, int y, int itemid){
		this.x = x;
		this.y = y;
		this.itemid = itemid;
		
		
		
		//!," + this.playerid + " ,272,94,0.0,0.0 ,5850699,0,272,94,
		 //ConnectToSock.socket.send("!," + this.playerid + " ,272,94,0.0,0.0 ,5850699,0,272,94,");
		this.lineToSend = "!," + ConnectToSock.playerid +","+ x + "," + y +",0.0,0.0,6121212,0,"+x+","+y;
	}
	
	@Override
	public void run(){
		System.out.println("Trying to pick up LIKE CRAZY with this line: " + lineToSend);
		
		
		// Start timer to count time. If timelimit is exceeded it will shut down TingGrabber
		new CountTime(10).start();
		
		
		while(RecieveFromSock.itemCondition && !tooLong){
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			ConnectToSock.socket.send(lineToSend);
		}
		
		
		if(tooLong){
			System.out.println("Tried to long - Stopping TingGrab");
			RecieveFromSock.itemCondition = false;
			RecieveFromSock.grabCords = false;
			
			
			tooLong = false;
		}else{
			System.out.println("Moved to item - Picking it up!");
			for(int a=0; a<10; a++){
				ConnectToSock.socket.send("F,"+itemid);
			}
		}
		
		
		
		
	}
}
