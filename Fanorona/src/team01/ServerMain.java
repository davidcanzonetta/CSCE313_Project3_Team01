package team01;

import java.util.Scanner;

@SuppressWarnings("serial")
public class ServerMain extends GUI {

	static long time;
	static int port;
	static Server server;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI.singlePlayer = Integer.parseInt(args[0]) == 1;
		GUI.player = args[1].equals("W") ? Board.WHITE : Board.BLACK;
		GUI.width = Integer.parseInt(args[2]);
		GUI.height = Integer.parseInt(args[3]);
		time = Long.parseLong(args[4]);
		port = Integer.parseInt(args[5]);
		String otherPlayer = GUI.player == Board.WHITE ? "B" : "W";
		
		server = new Server(port);
		server.write("WELCOMEINFO " + GUI.width + " " + GUI.height + " " + otherPlayer + " " + time);
		if (! server.read().equals("READY"))
		{
			server.write("ILLEGAL");
			System.exit(-1);
		}
		server.write("BEGIN");

		// TODO: start the GUI
		
		// USED FOR DEBUGGING PURPOSES ONLY
		System.out.println("SUCCESS!!!");
		
		server.close();
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
				server.write("ILLEGAL");
				server.write("LOSER");
				server.close();
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
					server.write("ILLEGAL");
					server.write("LOSER");
					server.close();
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
				server.write("ILLEGAL");
				server.write("LOSER");
				server.close();
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
					server.write("ILLEGAL");
					server.write("LOSER");
					server.close();
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
				server.write("ILLEGAL");
				server.write("LOSER");
				server.close();
				System.exit(-1);
			}
		}
		else if (type.equals("S"))
		{
			if (! game.sacrifice(
				new Point(scanner.nextInt(), scanner.nextInt())))
			{
				server.write("ILLEGAL");
				server.write("LOSER");
				server.close();
				System.exit(-1);
			}
		}
		else
		{
				server.write("ILLEGAL");
				server.write("LOSER");
				server.close();
				System.exit(-1);
		}
	}
}
