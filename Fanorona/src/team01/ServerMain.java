package team01;

import java.util.Scanner;

public class ServerMain {

	static int width;
	static int height;
	static int player;
	static boolean singlePlayer;
	static int port;
	static long time;
	static long timeout;
	static Server server;
	static Game game;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		singlePlayer = Integer.parseInt(args[0]) != 1;
		player = args[1].equals("W") ? Board.WHITE : Board.BLACK;
		width = Integer.parseInt(args[2]);
		height = Integer.parseInt(args[3]);
		time = Long.parseLong(args[4]);
		port = Integer.parseInt(args[5]);
		String otherPlayer = GUI.player == Board.WHITE ? "B" : "W";
		
		server = new Server(port);
		server.write("WELCOMEINFO " + width + " " + height + " " + otherPlayer + " " + time);
		if (! server.read().equals("READY"))
		{
			server.write("ILLEGAL");
			System.exit(-1);
		}
		server.write("BEGIN");

		game = new Game(width, height, singlePlayer, player);
		Scanner input = new Scanner(System.in);
		
		while (true)
		{
			if (game.isTie())
			{
//				System.out.println("******* IS TIE ********");
//				server.write("TIE");
				break;
			}
			else if (game.whiteWins())
			{
//				System.out.println("***** WHITE WINS ******");
//				if(player == 0)
//					server.write("WINNER");
//				else
//					server.write("LOSER");
//				If the server is white, and white wins, would the server write WINNER or LOSER?
//				It's writing what the client will read, so should it output WINNER and write LOSER?
//				break;
			}
			else if (game.blackWins())
			{
//				System.out.println("***** BLACK WINS ******");
//				if(player == 1)
//					server.write("WINNER");
//				else
//					server.write("LOSER");
//				break;
			}
			
/*
			Another possible solution?
			
			if(server.read() == "TIE")
			{
				System.out.println("TIE");
				break;
			}
			else if(server.read() == "LOSER")
			{
				System.out.println("LOSER");
			}

*/
			
			if (game.currentPlayer() == player) {
				boolean time_check = true;
				
				long start = System.currentTimeMillis();
				long end = start + time;
//				Countdown c = new Countdown(time);
				System.out.print(">>> x: ");
				int x = input.nextInt();
				System.out.print(">>> y: ");
				int y = input.nextInt();
				System.out.println();
				
				if(System.currentTimeMillis() > end)
				{
//					System.out.println("\nTIME\nLOSER);
					server.write("TIME");
					server.write("LOSER");
					server.close();
					System.exit(-1);
				}
				
				Point point = new Point(x, y);
//				c.terminate();			
				if (! game.update(point))
				{
					System.out.println("!!! INVALID INPUT");
					System.out.println();
				}
				if (game.currentPlayer() != player)
				{
					server.write(game.moveLog);
					System.out.println("*** " + server.read());
					game.moveLog = "";
				}
			} else {
				String message = server.read();
				server.write("OK");
				Scanner scanner = new Scanner(message);
				processMove(scanner);
				scanner.close();
				game.moveLog = "";
			}
		}
		input.close();
		server.close();
	}

	private static void processMove(Scanner scanner)
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
