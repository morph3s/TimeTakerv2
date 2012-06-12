import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.UnknownHostException;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;



public class RecieveFromSock extends Thread{
 

	private String captchaText;
	public static String filename;
	public static String endurl;
	public static String challengeString = null;
	public static String challengeHandlerString = null;
	public static String challengeHandlerStringtemp = null;
	public static String decoded = null;
	public static String decodedBackup = null;
	
	
	
	
	// For tinggrabber
	public static boolean itemCondition = false;   // Is there an item on the ground ?
	public static boolean grabCords = false;	   // Is it the correct time to grab cordinates of the items location ? 
	int x, y, itemid;							   // x = x cord, y = y cord itemid = itemid on dropped item
	
	
	
	Protocol pr = new Protocol();
	SBUtil sb = new SBUtil();
	DownloadFiles dl = new DownloadFiles();
	decodeChallengeString de;
	
	@Override
	public void run(){
		try{
			ConnectToSock.socket.socket().setTcpNoDelay(true);
			//Thread.sleep(2000);
			startReceiving();
		}catch (SocketException e) {
			e.printStackTrace();
		}catch (InterruptedException e){
			Thread.currentThread().interrupt();
	        
		}
		
		
		
	}
	
	
	public synchronized void startReceiving() throws InterruptedException{
		String response = null;
		
		while(ConnectToSock.socket.isConnected()){
			try {
				response = ConnectToSock.socket.receive();
				//System.out.println(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(response==null){
				
				
				// Assume it returns null when disconnected
				// Try to reconnect.
				System.out.println("Server returned null . Restarting!");
				
				//d
				Main.shouldRestart = true;
				this.interrupt();
				
				
				
			}
			if(response!=null){
				
				
				
				
				
				jTokenizer js = new jTokenizer(response, ",");
		        String cmd = js.nextToken();
		        if (cmd.equals("3")){
		        	handleCommand(js);
		        	
		        }else if(cmd.equals("U")){
		        	System.out.println("SOMETHING SHOED");
		        	if(itemCondition==true){
		        		System.out.println("Already trying to grab sumthin");
		        		if(js.nextInt()==itemid){
		        			System.out.println("user picked it up");
		        			itemCondition = false;
		        		}
		        		
		        	}else{
		        		
		        		//Item Picked up
			        	//Item dropped  -- V,itemid,name,?,?,?,?,?,?,xakse,yakse,?
			        	// Start thread that is trying to grab item
			        	int itemid = js.nextInt();
			        	js.nextToken();
			        	
			        	//Pickup = 2 Drop = -1
			        	int thing = js.nextInt();
			        	if(thing==-1){
			        		System.out.println("real drop");
			        		grabCords = true;
			        		
			        	}else{
			        		System.out.println("Not a real drop");
			        		
			        	}
			        	
		        	}
		        	
		        	
		        	
		        }else if(cmd.equals("x")){
		        	//User exits
		        	//Delete user from tableModel --- NOT CODED YET ---
		        	
		        
		        	
		        	
		        	
		        	
				}else if(cmd.equals("%")){
		        	// People whisper to you
		        	int from = js.nextInt();
		        	int me = js.nextInt();
		        	String besked = js.nextToken();
		        	besked = URLDecoder.decode(besked);
		        	TimeTakerRenewedUI.chatlog.append(from + " hvisker til dig: " + besked + "\n");
		        	
		        	
		        	
		        	
		        	
		        }else if(cmd.equals("v")){
		        // User goes to other room
				}else if(cmd.equals("V")){
		        	if(grabCords){
		        		System.out.println("SOMETHING Dropped get cords");
		        		itemid = js.nextInt();
			        	js.nextToken();
		        		js.nextToken();
			        	js.nextToken();
			        	js.nextToken();
			        	js.nextToken();
			        	js.nextToken();
			        	js.nextToken();
			        	x = js.nextInt();
			        	y = js.nextInt();
			        	itemCondition = true;
			        	grabCords = false;
			        	// Already trying to move to one item
			        	
			        	
			        	// Already trying to grab one item
			        	
			        	
			        	new TingGrabber(x,y, itemid).start();
		        	}else{
		        		System.out.println("Shieeet");
		        	}
		        	
		        	
		        	
		        }else if(cmd.equals("o")){
		        	//user joins room
		        	int usrid = js.nextInt();
		        	js.nextToken();  // Skips unwanted part
		        	String joinedUsrNm = js.nextToken();
		        	int pos = TimeTakerRenewedUI.jList1.getModel().getSize();
		        	TimeTakerRenewedUI.usersInRoomModel.add(pos, joinedUsrNm +"  -  "+ usrid);
		        	
		        	
		        }else if(cmd.equals("!")){
		        	//some user moves 
		        	//Check if it is myself
		        	
			        	if(js.nextInt()==ConnectToSock.playerid){
			        		if(js.nextInt()==x && js.nextInt()==y){
			        			System.out.println("CORRECT SPOT: Picking up!");
			        			//ConnectToSock.socket.send("F,"+itemid);
			        			itemCondition = false;
			        			x = 0;
			        			y = 0;
			        		}
			        		//itemCondition = true;
			        	}else{
			        		//it is not myself
			        		
			        		
			        	}
		        	
		        	
		        	
		        }else if(cmd.equals(" ")){
		        	//Chat message
		        	int msgfromid = js.nextInt();
		        	String besked = js.nextToken();
		        	besked = URLDecoder.decode(besked);
		        	besked = ReplaceHTMLChars.decodeHtml(besked);
		        	
		        	
		        	TimeTakerRenewedUI.chatlog.append(msgfromid + " siger: " + besked + "\n");
		        	TimeTakerRenewedUI.chatlog.setCaretPosition(TimeTakerRenewedUI.chatlog.getDocument().getLength());
		        	
		        	
		        }else if(cmd.equals("C")){
		        	js.skipToken();
		        	js.skipToken();
		        	String token = js.nextToken();
		        	if(token.equals("_challengeString")){
		        		if(js.hasMoreTokens()){
			        		String nes = js.nextToken();
			        		
			        		if(nes!=null){
			        			handleChallengeString(nes);
			        		}	
		        		}
		        	}else if(token.equals("_challengeHandlerString")){
		        		if(js.hasMoreTokens()){
		        			String nes = js.nextToken();
		        			
		        			if(nes!=null){
			        			handleChallengeHandlerString(nes);
			        		}
		        		}else{
		        			if(decodedBackup!=null){
		        				System.out.println("Backup sent");
		        				send_command("ChallengeHandlerResponse", decodedBackup);
		        				
		        			}
		  
		        		}
		        	}
		        }
			}
			response = null;
		}
	}
	
	public void handleChallengeString(String paramchallengeString){
		try{
			if(paramchallengeString!=null){
					
					RecieveFromSock.challengeString = paramchallengeString;
					
					
			}else{
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleChallengeHandlerString(String paramChallengeHandlerString){
		try{
			if(paramChallengeHandlerString!=null){
				RecieveFromSock.challengeHandlerStringtemp = paramChallengeHandlerString;
				jTokenizer j = new jTokenizer(RecieveFromSock.challengeHandlerStringtemp, ".");
				j.skipToken();
				j.skipToken();
				j.skipToken();
				RecieveFromSock.challengeHandlerString = j.nextToken();
				
				
			}else{
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void handleCommand(jTokenizer js){
		if(js.hasMoreTokens()){
			String command = js.nextToken();
			if(command.equals("hour")){
				handleHour(js);
			}else if(command.equals("load")){
				handleLoad(js);
			}
		}
		
	}
	
	
	public void handleLoad(jTokenizer js){
		if(this.challengeString !=null && this.challengeHandlerString != null){
			
			/*try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			getDynamicBaseImplementation();
		}
	}
	
	public void handleHour(jTokenizer js){
			
	        String linkCode = Protocol.decodeString(js.nextToken());
	        jTokenizer hourToke = new jTokenizer(linkCode, ":");
	        int hdh = hourToke.nextInt();
	        int hdnum = hourToke.nextInt();
	        int i1 = hourToke.nextInt();
	        String str7 = hourToke.nextToken();
	        long captchastarttime = System.currentTimeMillis();
	        try {
	          
	          String linkToImage = "http://hhsrv.n.dk:" + i1 + "/plugin:" + str7 + ".jpg";
	          
	          
	          
	          captchaText = decodeCaptcha(linkToImage);
	
	          if (captchaText != null) {
	            
	            
	            String upperCaseCaptcha = captchaText.toUpperCase();
	            
	
	            String tosend = Protocol.string_command("time", hdnum + Protocol.encodeString(",") + upperCaseCaptcha);
	
	            ConnectToSock.socket.send(tosend);
	            
	            int hours = Integer.parseInt(TimeTakerRenewedUI.hoursonlineText.getText());
	            hours++;
	            TimeTakerRenewedUI.hoursonlineText.setText("" +hours);
	           
	          }else{
	        	  //DIALOG BOKS
	        	  
	        	  JOptionPane.showMessageDialog(null, "Du bliver nu automatisk logget ud fordi du ikke har flere credits på din konto. Kontakt nhhacks for, at fylde op igen");
	        	  
	        	        
	        	 
	        	  
	          }
	        }
		
	        catch (java.lang.Exception e) {
	        	e.printStackTrace();
          		
	        }
		
	}
	
	
	
	public void getDynamicBaseImplementation(){
		
		//dl.downloadBase64("http://hhsrv.n.dk/chat/n7/security/client/" + this.challengeHandlerString + ".class", this.challengeHandlerString);
		endurl = "http://hhsrv.n.dk/chat/n7/security/client/" + RecieveFromSock.challengeHandlerString + ".class";
		filename = RecieveFromSock.challengeHandlerString;
		
		de = new decodeChallengeString();
		de.start();
				
		
	}
	
	
	
	public static void send_command(String paramString1, String paramString2){
		ConnectToSock.socket.send(Protocol.string_command(paramString1, paramString2));
	}
	
	
	public String decodeCaptcha(String link){
		
		try {
			
			// Getting username and password for server login
			String servip = null; // Set to where it should connect to authenticate. Run authentication system on local system or delete this part
			int servport = 0;  // Set to where it should connect to authenticate. Run authentication system on local system or delete this part
			Socket s = new Socket(servip, servport);
			String usr = TimeTakerRenewedUI.srvUsername;
			String pass = TimeTakerRenewedUI.srvPassword;
			
			String srvRequest = "auth;usr="+usr+";pass="+pass+";link="+link;
			DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
			PrintStream out = new PrintStream(new BufferedOutputStream(s.getOutputStream()), true);
			
			out.println(srvRequest);
			String response=in.readLine();
			
			
			response=in.readLine();
			
			String credits[] = response.split(" ");
			TimeTakerRenewedUI.creditsLeft.setText(credits[0]);
			if(Integer.parseInt(TimeTakerRenewedUI.creditsLeft.getText()) == 0){
				return null;
			}
			
			
			response=in.readLine();
			
			
			String tempCap[] = response.split("=");
			String captchaSolved = tempCap[1];
			
			out.close();
			in.close();
			s.close();
			return captchaSolved;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}