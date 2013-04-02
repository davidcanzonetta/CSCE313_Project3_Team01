package team01;

import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("serial")
public class ClientMain extends GUI {

	static int port;
	static String host;
	static long timeout;
	static Client client;

	public static void main (String[] args)
	{
		GUI.singlePlayer = Integer.parseInt(args[0]) == 1;
		host = args[1];
		port = Integer.parseInt(args[2]);

		client = new Client(host, port);

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

	private void processMove(Scanner scanner)
	{
		String type = scanner.next();
		
		if (type.equals("A"))
		{
			if (! game.approach(
				new Point(scanner.nextInt(), scanner.nextInt()),
				new Point(scanner.nextInt(), scanner.nextInt())))
			{
				client.write("ILLEGAL");
				client.write("LOSER");
				client.close();
				System.exit(-1);
			}
			if (scanner.hasNext())
			{
				if (scanner.next().equals("+"))
				{
					processMove(scanner);
				}
				else
				{
					client.write("ILLEGAL");
					client.write("LOSER");
					client.close();
					System.exit(-1);
				}
			}
		}
		else if (type.equals("W"))
		{
			if (! game.withdraw(
				new Point(scanner.nextInt(), scanner.nextInt()),
				new Point(scanner.nextInt(), scanner.nextInt())))
			{
				client.write("ILLEGAL");
				client.write("LOSER");
				client.close();
				System.exit(-1);
			}
			if (scanner.hasNext())
			{
				if (scanner.next().equals("+"))
				{
					processMove(scanner);
				}
				else
				{
					client.write("ILLEGAL");
					client.write("LOSER");
					client.close();
					System.exit(-1);
				}
			}
		}
		else if (type.equals("P"))
		{
			if (! game.paika(
				new Point(scanner.nextInt(), scanner.nextInt()),
				new Point(scanner.nextInt(), scanner.nextInt())))
			{
				client.write("ILLEGAL");
				client.write("LOSER");
				client.close();
				System.exit(-1);
			}
		}
		else if (type.equals("S"))
		{
			if (! game.sacrifice(
				new Point(scanner.nextInt(), scanner.nextInt())))
			{
				client.write("ILLEGAL");
				client.write("LOSER");
				client.close();
				System.exit(-1);
			}
		}
		else
		{
				client.write("ILLEGAL");
				client.write("LOSER");
				client.close();
				System.exit(-1);
		}
	}
}
