package team01;
import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) {
		Socket clientSocket = null; 
		try { 
			clientSocket = serverSocket.accept(); 
		} catch (IOException e) {
			System.err.println("Accept failed."); 
			System.exit(1); 
		}
		
		ServerSocket serverSocket = null;
		try { 
			serverSocket = new ServerSocket(4001);
		} catch (IOException e) {
			System.out.println("Could not listen on port: 4001"); 
			System.exit(-1); 
		}
	}
};
