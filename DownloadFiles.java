import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadFiles
{
	
	
	  public void downloadImage(String link) {
	    System.out.println("Downloading picture!");
	    try
	    {
	      URL imgDL = new URL(link);
	
	      ReadableByteChannel rbc = Channels.newChannel(imgDL.openStream());
	      if(ConnectToSock.playerid != 0){
	    	  FileOutputStream fos = new FileOutputStream(ConnectToSock.playerid+".jpg");
	    	  fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		      fos.close();
	      }else{
	    	  System.out.println("No player id found. Using captcha.jpg");
	      }
	      
	
	      
	    }
	    catch (MalformedURLException e) {
	      e.printStackTrace();
	    }
	    catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	}