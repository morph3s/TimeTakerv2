
public class SendToSock extends Thread
{
  
  Protocol pr = new Protocol();
  public static int playerid = 0;
  private static int playerid2 = 0;
  StrangeBase strbas = new StrangeBase();

  public SendToSock(int playerid, int playerid2) {
	  
	  this.playerid = playerid;
	  this.playerid2 = playerid2;
  }
  	  @Override
	  public void run(){
		  try{
			  if(TimeTakerRenewedUI.botType.equals("randommessage")){
				  randomMessage();
			  }else if(TimeTakerRenewedUI.botType.equals("goto")){
				  gotoMessage();
			  }else{
				 gotoMessage();
			  }
			  
		  }catch(InterruptedException e){
			  Thread.currentThread().interrupt();
		  }
		  
	  }
	  
  	  
  	  
  	  public synchronized void gotoMessage() throws InterruptedException{
  		  send_chatline("/goto " + TimeTakerRenewedUI.startRoomint.getText());
  		  
		  
		  Thread.sleep(2000);
	    		
		  ConnectToSock.socket.send("T,-1," + this.playerid * 3);
		       	
		  int vvv = 0;
		  while (ConnectToSock.socket.isConnected()){
			  vvv++;  
			  int sleeptime = (int) (8*Math.random()+1);
			  sleeptime *= 60000;
			  Thread.sleep(sleeptime);
			  if(vvv==1){
				  ConnectToSock.socket.send("T,-1," + this.playerid * 3);
			  }
			  send_chatline("/goto 0");
			 

		    }
  	  }
  	  
  	  public synchronized void randomMessage() throws InterruptedException{
  		send_chatline("/goto " + TimeTakerRenewedUI.startRoomint.getText());
  		
			
		   
		  Thread.sleep(3000);
	    		
		  ConnectToSock.socket.send("T,-1," + this.playerid * 3);
		       	
		  int sleeptime = (int) (8*Math.random()+1);
		  sleeptime *= 60000;
		  Thread.sleep(sleeptime);
		
		  ConnectToSock.socket.send("T,-1," + this.playerid * 3);
		  
		  while (ConnectToSock.socket.isConnected()){
			  
			  Thread.sleep(60000*2);
			  String messageS = TimeTakerRenewedUI.messages.get((int)(TimeTakerRenewedUI.messages.size()*Math.random())*1);
			  send_chatline(messageS);
			  
		    }
  	  }
  	  
  	  

	  
	  
	  
	  
	  public static synchronized void send_chatline(String paramString){
		  
		  ConnectToSock.socket.send(Protocol.string_chatline(SendToSock.playerid, paramString));
	  }
	
	  public synchronized void send_alive(){
		  ConnectToSock.socket.send(Protocol.string_alive());
	  }
	
	  public synchronized void send_command(String paramString1, String paramString2){
		  ConnectToSock.socket.send(Protocol.string_command(paramString1, paramString2));
	  }
}