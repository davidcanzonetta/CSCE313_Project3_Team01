package team01;
import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) {
		//Sockets
		Socket clientSocket = null;
		ServerSocket serverSocket = null;
		//Streams
		InputStream sockInput = null;
		OutputStream sockOutput = null;
		//Initialize Sockets/ Streams
		try { 
			serverSocket = new ServerSocket(4001);
		} catch (IOException e) {
			System.out.println("Could not listen on port: 4001"); 
			System.exit(-1); 
		}
		try { 
			clientSocket = serverSocket.accept(); 
			sockInput = clientSocket.getInputStream();
			sockOutput = clientSocket.getOutputStream();
		} catch (IOException e) {
			System.err.println("Accept failed."); 
			System.exit(1); 
		}
		//If everything has beenn initialized correctly
		 if (clientSocket != null && serverSocket != null && sockOutput != null  && sockInput != null) {
	            try {
	            	//Do stuff
	            	byte[]buf = new byte[1024];
	            	//buf <- certain data
	            	sockOutput.write(buf, 0, buf.length); //Writing data
	            	int bytes_read = sockInput.read(buf, 0, buf.length);//Reading data, waits forever for input
	           
	            	//Close everything
	            	sockInput.close();
	            	sockOutput.close();
	            	clientSocket.close();
	            	serverSocket.close();
	            	
	            } catch (UnknownHostException e) {
	                System.err.println("Trying to connect to unknown host: " + e);
	            } catch (IOException e) {
	                System.err.println("IOException:  " + e);
	            }
	            
		 }
		
			
		 
	
		
		
	}
};
