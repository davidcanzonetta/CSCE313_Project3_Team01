package team01;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientMain {

	static int width;
	static int height;
	static int player;
	static boolean singlePlayer;
	static long timeout;
	static Client client;
	static Game game;
	
	public static void main (String[] args)
	{
		singlePlayer = Integer.parseInt(args[0]) == 1;
		String host = args[1];
		int port = Integer.parseInt(args[2]);

		client = new Client(host, port);
		String info = client.read();
		processInfo(info);
		client.write("READY");
		game = new Game(width, height, !singlePlayer, player ^ 1);
		
		if (! client.read().equals("BEGIN"))
		{
			System.out.println("fail");
			System.exit(-1);
		}
		// TODO: game play
		

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
					client.write(game.moveLog);
					System.out.println("*** " + client.read());
					game.moveLog = "";
				}
			} else {
				String message = client.read();
				client.write("OK");
				Scanner scanner = new Scanner(message);
				processMove(scanner);
				scanner.close();
				game.moveLog = "";
			}
		}
		input.close();
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

			width = scanner.nextInt();
			height = scanner.nextInt();
			player = scanner.next().equals("W") ? Board.WHITE : Board.BLACK;
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

	private static void processMove(Scanner scanner)
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
