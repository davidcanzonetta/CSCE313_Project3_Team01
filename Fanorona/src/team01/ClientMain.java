package team01;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientMain {

	static int port;
	static String host;
	static long timeout;

	public static void main (String[] args)
	{
		GUI.singlePlayer = Integer.parseInt(args[0]) == 1;
		host = args[1];
		port = Integer.parseInt(args[2]);

		Client client = new Client(host, port);

		String info = client.read();
		processInfo(info);
		client.write("READY");

		// TODO: start the GUI
		
		// USED FOR DEBUGGING PURPOSES ONLY
		System.out.println(GUI.width + " " + GUI.height + " " + timeout);
		
		client.close();
	}

	private static void processInfo(String info)
	{
		Scanner scanner = new Scanner(info);

		try
		{
			String header = scanner.next();

			if (! header.equals("WELCOMEINFO"))
			{
				System.exit(-1);
			}

			GUI.width = scanner.nextInt();
			GUI.height = scanner.nextInt();
			GUI.player = scanner.next().equals("W") ? Board.WHITE : Board.BLACK;
			timeout = scanner.nextLong();
		}
		catch (InputMismatchException e)
		{
			System.exit(-1);
		}
		finally
		{
			scanner.close();
		}
	}

}
