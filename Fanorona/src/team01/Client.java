package team01;
import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) {
		//Declare Socket
		Socket c_socket = null;
		//Declare Streams
		InputStream c_sockInput = null;
		OutputStream c_sockOutput = null;
		//Initialize socket/ streams
		try { 
			c_socket = new Socket("Aserver", 4001);
			c_sockInput= c_socket.getInputStream();
			c_sockOutput = c_socket.getOutputStream();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: Aserver."); 
			System.exit(1);
		} catch(IOException e){
			System.err.println("Couldn't get I/O for the connection to: Aserver."); 
			System.exit(1);
		}
		//If everything has been initialized correctly 
		 if (c_socket != null && c_sockInput != null && c_sockOutput != null) {
	            try {
	            	//Do stuff
	            	 //Close everything
	            	c_sockInput.close();
	            	c_sockOutput.close();
            		c_socket.close();
	            	
	            } catch (UnknownHostException e) {
	                System.err.println("Trying to connect to unknown host: " + e);
	            } catch (IOException e) {
	                System.err.println("IOException:  " + e);
	            }
		 }
		 
		
		
		
	}
}
