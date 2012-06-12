import java.io.File;
import n7.security.client.Base64;

public class ChallengeHandler
{
  public String doit(String challengeString, String challengeHandlerString)
  {
    String encrypted = challengeString;
    String handlerClass = challengeHandlerString;

    Base64 decoder = null;
    try
    {
      decoder = (Base64)Class.forName("n7.security.client." + handlerClass).newInstance();
    }
    catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    

    
    String pdecrypted = Protocol.decodeString(encrypted);
    

    String decrypted = decoder.decode(pdecrypted);

   

    String filename = challengeHandlerString + ".class";
    File f = new File("n7/security/client/" + filename);
    f.delete();
    return decrypted;
  }
}