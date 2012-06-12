import java.io.FileOutputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class decodeChallengeString extends Thread{
	
	@Override
	public void run(){
	
		if(RecieveFromSock.endurl != null && RecieveFromSock.filename != null){
			try{
				/*
				CookieManager cm = new CookieManager();
			    CookieHandler.setDefault(cm);
			*/
			    URL baseDL = new URL(RecieveFromSock.endurl);
			    ReadableByteChannel rbc = Channels.newChannel(baseDL.openStream());
			
			    FileOutputStream fos = new FileOutputStream("n7/security/client/" + RecieveFromSock.filename + ".class");
			
			    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
			    
			    baseDL = null;
			    rbc.close();
			    fos.close();
			}catch (MalformedURLException e){
			      e.printStackTrace();
			}catch (IOException e) {
			      e.printStackTrace();
			}
		}
		
		decodeChStr();
	}
	
	public void decodeChStr(){

		ChallengeHandler ch = new ChallengeHandler();
		RecieveFromSock.decoded = ch.doit(RecieveFromSock.challengeString, RecieveFromSock.challengeHandlerString);
		if(RecieveFromSock.decoded!=null){
			RecieveFromSock.send_command("ChallengeHandlerResponse", RecieveFromSock.decoded);
			
			RecieveFromSock.decodedBackup = RecieveFromSock.decoded;
			RecieveFromSock.decoded = null;
			
			System.out.println("Sent challenge String");
			
			if(!ConnectToSock.useProxy){
				RequestSites rs = new RequestSites();
				rs.startRequestingAfterChallengestring();
			}
			
		}else{
			
		}
		
		RecieveFromSock.challengeHandlerString = null;
		RecieveFromSock.challengeString = null;
		RecieveFromSock.challengeHandlerStringtemp = null;
	}
	
}
