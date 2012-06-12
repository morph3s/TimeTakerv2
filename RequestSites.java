import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 *  Intended for test purpose. 
 *  Making the connection more reliable when connecting to all URLS as normal client does
 *  All connection are made AFTER! login and BEFORE! challengestring is sent back
 *  
 *  
 *  Urls to connect: 
 *  http://www.n.dk
 *  - /community/hh/frHH.asp
 *  - /community/hh/frRoom.asp?id=[roomid]
 *  - /javascript/by.asp?url=http3A//www.n.dk/community/hh/frRoom.asp&queryStr=%3Fid%3D45
 *  - /frameset.asp?right=http3A//www.n.dk/community/hh/frRoom.asp&urlLeft=undefined&urlForum=undefined&queryStr=%3Fid%3D45
 *  - 
 *  
 *  
 *  
 *  All these are made AFTER! challengestring is sent back
 *  Urls to connect: 
 *  http://www.n.dk
 *   - /community/login/frTop.asp
 *   - /community/hh/frRoom.asp?id=45
 *   - /community/navigation/frNavigation.asp
 *   - /community/banner/frBanner.asp
 *   - /community/navigation/SessionLoader.aspx
 *   - /redefault.asp
 */
public class RequestSites {
	
	public void startRequestingBeforeChallengestring(){
		/*try {
			
			 * URLConnection url = new URL("http://www.n.dk/community/hh/frHH.asp").openConnection();
			 
			URLConnection url1 = new URL("http://www.n.dk/community/hh/frRoom.asp?id=45").openConnection();
			URLConnection url2 = new URL("http://www.n.dk/javascript/by.asp?url=http3A//www.n.dk/community/hh/frRoom.asp&queryStr=%3Fid%3D45").openConnection();
			URLConnection url3 = new URL("http://www.n.dk/frameset.asp?right=http3A//www.n.dk/community/hh/frRoom.asp&urlLeft=undefined&urlForum=undefined&queryStr=%3Fid%3D45").openConnection();
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	
	public void startRequestingAfterChallengestring(){
		System.out.println("Requesting sites");
		/*try {
			
			URLConnection url = new URL("http://www.n.dk/community/login/frTop.asp").openConnection();
			URLConnection url1 = new URL("http://www.n.dk/community/hh/frRoom.asp?id=45").openConnection();
			URLConnection url2 = new URL("http://www.n.dk/community/navigation/frNavigation.asp").openConnection();
			URLConnection url3 = new URL("http://www.n.dk/community/banner/frBanner.asp").openConnection();
			URLConnection url4 = new URL("http://www.n.dk/community/navigation/SessionLoader.aspx").openConnection();
			URLConnection url5 = new URL("http://www.n.dk/redefault.asp").openConnection();
			URLConnection url6 = new URL("http://www.n.dk/").openConnection();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
	}
}
