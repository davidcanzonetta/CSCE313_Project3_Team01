package team01;

import java.util.List;
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
		singlePlayer = Integer.parseInt(args[0]) == 1;
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

		game = new Game(width, height, !singlePlayer, player);
		Scanner input = new Scanner(System.in);
		
		while (true)
		{
			if (game.isTie())
			{
//				System.out.println("******* IS TIE ********");
				break;
			}
			else if (game.whiteWins())
			{
//				System.out.println("***** WHITE WINS ******");
				break;
			}
			else if (game.blackWins())
			{
//				System.out.println("***** BLACK WINS ******");
				break;
			}
			
			if (game.currentPlayer() == player) {

				System.out.print(">>> x: ");
				int x = input.nextInt();
				System.out.print(">>> y: ");
				int y = input.nextInt();
				System.out.println();
				
				Point point = new Point(x, y);
				
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
