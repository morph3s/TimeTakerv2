
public class CountTime extends Thread {
	
	private int seconds;
	
	public CountTime(int seconds){
		this.seconds = seconds;
	}
	
	
	@Override
	public void run(){
		TakeTime();
	}
	
	public void TakeTime(){
		long time = System.currentTimeMillis() + (seconds*1000);
		
		while(System.currentTimeMillis() < time && RecieveFromSock.itemCondition){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!RecieveFromSock.itemCondition){
			//Item is picked up
			this.interrupt();
			
			
		}else{
			//Item wasnt picked up. Determine that it was taking too long
			TingGrabber.tooLong = true;
		}
		
		
		
				
		
		
		
	}
	
}
