import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login
{
  
	
  public String getGuid(String username, String password, boolean useProxy)
  {
    CookieManager cm = new CookieManager();
    CookieHandler.setDefault(cm);
    String mGuid = null;
    try {
      username = URLEncoder.encode(username);
      password = URLEncoder.encode(password);
      
      URLConnection url;
      if(useProxy){
    	  // Login using proxy !
    	  /*
    	   * 	
    	   */
    	  
    	  String ip = TimeTakerRenewedUI.proxyIP.getText();
    	  int port = Integer.parseInt(TimeTakerRenewedUI.proxyPort.getText());
    	  Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
    	  
    	  System.out.println("Logging into server and grabbing GUID with proxy");
    	  url = new URL("http://www.n.dk/community/login/login.asp?username=" + username + "&pdummy=" + "Kode" + "&password=" + password).openConnection(proxy);
    	  System.out.println("Done!");
    	  
      }else{
    	  // Login normal
    	  System.out.println("Logging into server and grabbing GUID without proxy");
    	  url = new URL("http://www.n.dk/community/login/login.asp?username=" + username + "&pdummy=" + "Kode" + "&password=" + password).openConnection();
      }
      
      
      BufferedReader loginResponse = new BufferedReader(new InputStreamReader(url.getInputStream()));

      Pattern pattern = Pattern.compile("guid=[a-zA-Z0-9-]+");
      String inline;
      while ((inline = loginResponse.readLine()) != null)
      {
        
        Matcher matcher = pattern.matcher(inline);
        if (matcher.find()) {
          mGuid = matcher.group();
          mGuid = mGuid.substring(5);
          

          return mGuid;
        }

      }
      loginResponse.close();
      

    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return mGuid;
  }
}