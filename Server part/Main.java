import java.sql.Connection;
import java.sql.DriverManager;
import java.net.*;
import java.io.*;


public class Main {
	public static Connection conn = null;
	ServerSocket serverSocket = null;
	
	
	public static void main(String args[]){
		Main m = new Main();
		m.start();
	}
	
	
	public void start(){
		
		
		//This part is for connecting to the mysql. For this to work you'll have to run a mysql database on the specified machine which runs this. 
		// Or you could simply delete this part.
		try{
			String username = "MASKED";
			String password = "MASKED";
			String url = "jdbc:mysql://192.168.0.100/MASKED";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connection established");
			
			
			
			
			initializeSocketServer();
			
			
			
			
			
		}catch(Exception e){
			System.out.println("Can't connect to database");
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try{
					conn.close();
					System.out.println("Closed succesfully!");
				}catch(Exception e){
					
				}
			}
		}
	}
	
	
	public void initializeSocketServer(){
		
		try {
			serverSocket = new ServerSocket(5300);
			
			

			while(true){
				System.out.println("Listening for connections...");
				new clientHandleThread(serverSocket.accept()).start();
				
			}
			
			
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
