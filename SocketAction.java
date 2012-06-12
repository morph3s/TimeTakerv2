import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketAction
{
  public DataInputStream inStream = null;
  protected PrintStream outStream = null;
  protected Socket socket = null;
  public boolean linkgone = false;

  public SocketAction(Socket paramSocket)
    throws IOException
  {
    this.socket = paramSocket;
    if (paramSocket != null)
    {
      this.inStream = new DataInputStream(new BufferedInputStream(paramSocket.getInputStream(), 8192));
      this.outStream = new PrintStream(new BufferedOutputStream(paramSocket.getOutputStream(), 8192), true);
    }
  }

  public SocketAction()
  {
  }

  public Socket socket()
  {
    return this.socket;
  }

  public void send(String string)
  {
    if (this.outStream == null)
      return;
    this.outStream.println(string);
    this.outStream.flush();
  }

  public void sendChars(char delimiterchar)
  {
    if (this.outStream == null)
      return;
    this.outStream.print(delimiterchar);
    this.outStream.flush();
  }

  public String receive()
    throws IOException
  {
    String str = null;
    if (this.inStream != null)
      str = this.inStream.readLine();
    return str;
  }

  public void closeConnections()
  {
    try
    {
      if (this.inStream != null)
        this.inStream.close();
      if (this.outStream != null)
        this.outStream.close();
      this.inStream = null;
      this.outStream = null;
      if (this.socket != null)
      {
        this.socket.close();
        this.socket = null;
      }
    }
    catch (Exception localException)
    {
    }
    this.linkgone = true;
  }

  public boolean isConnected()
  {
    return (this.inStream != null) && (this.outStream != null) && (this.socket != null);
  }

  protected void finalize()
  {
    if (this.socket != null)
    {
      try
      {
        this.socket.close();
      }
      catch (IOException localIOException)
      {
      }
      this.socket = null;
    }
  }

  public int available()
    throws Exception
  {
    return this.inStream.available();
  }

  public InetAddress getAddress()
    throws Exception
  {
    return this.socket.getInetAddress();
  }

  public void kill()
  {
    this.linkgone = true;
    closeConnections();
  }

  public void flush()
  {
    if (this.outStream != null)
      this.outStream.flush();
  }
}