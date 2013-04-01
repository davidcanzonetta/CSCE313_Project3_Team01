package team01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.swing.SwingUtilities;

public class Server extends Thread {

	private ServerSocket serverSocket;
	private Socket clientSocket;
	private InputStream sockInput;
	private OutputStream sockOutput;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("could not listen on port: " + port);
			System.exit(-1);
		}

		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.out.println("accept failed");
			System.exit(-1);
		}

		try {
			sockInput = clientSocket.getInputStream();
			sockOutput = clientSocket.getOutputStream();
		} catch (IOException e) {
			System.out.println("cannot get socket streams");
			System.exit(-1);
		}
	}

	public void write(String msg) {
		try {
			byte[] buf = msg.getBytes();
			sockOutput.write(buf, 0, buf.length);
		} catch (IOException e) {
			System.out.println("cannot write socket");
			System.exit(-1);
		}
	}

	public String read() {
		byte[] buf = new byte[1024];
		int len = 0;

		try {
			len = sockInput.read(buf, 0, buf.length);
		} catch (IOException e) {
			System.out.println("cannot read socket");
			System.exit(-1);
		}

		return new String(buf, 0, len);
	}

	public void close() {
		try {
			sockInput.close();
			sockOutput.close();
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("cannot close socket");
		}
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		int port = 4343; 
		Server server = new Server(port);
		*/
	}

}
