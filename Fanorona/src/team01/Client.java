package team01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket clientSocket;
	private InputStream sockInput;
	private OutputStream sockOutput;

	public Client(String host, int port) {
		try {
			clientSocket = new Socket(host, port);
		} catch (UnknownHostException e) {
			System.out.println("don't know about host: " + host);
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("couldn't get I/O for the connection to " + host);
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
		} catch (IOException e) {
			System.out.println("cannot close socket");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
