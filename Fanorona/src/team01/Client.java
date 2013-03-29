package team01;
import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) {
		Socket c_socket = null;
		try { 
			c_socket = new Socket("Aserver", 4001);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: Aserver."); 
			System.exit(1);
		} catch(IOException e){
			System.err.println("Couldn't get I/O for the connection to: Aserver."); 
			System.exit(1);
		}
	}
}
