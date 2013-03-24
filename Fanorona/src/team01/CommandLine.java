package team01;

import java.util.*;

public class CommandLine {

	/**
	 * @param args
	 */
	static int moves = 0; //Keep track of number of game moves
	
	public static void main(String[] args) 
	{
		Board board = new Board(9, 5);
		Scanner input = new Scanner(System.in);
		int player = Board.WHITE;
		
		while (true)
		{
			// TODO: sweep board for available captures

			int xFrom, yFrom;
			
			// get move from coordinates
			System.out.println("Player: " + playerIdToString(player));
			System.out.print(board);
			
			System.out.print("Enter X position of piece to move: ");
			xFrom = input.nextInt();
			System.out.print("Enter Y position of piece to move: ");
			yFrom = input.nextInt();
			
			Point from = new Point(xFrom, yFrom);
			
			// TODO: check that position from has captures?
			
			if (! board.isValidPosition(from))
			{
				System.out.println("Position " + from + " is not valid!");
				System.out.println("Try again");
				continue;
			}
			
			if (board.getPosition(from) != player)
			{
				System.out.println("Position " + from + " is not " + playerIdToString(player));
				System.out.println("Try again");
				continue;
			}

			// TODO: handle case where there are no captures available
			Move move = new Move(board);
			
			while (true)
			{
				int xTo, yTo;
				int ans;
				
				// get move to coordinates
				System.out.print("Enter X position to move " + from + " to: ");
				xTo = input.nextInt();
				System.out.print("Enter Y position to move " + from + " to: ");
				yTo = input.nextInt();
				
				Point to = new Point(xTo, yTo);
				
				if (! move.isValidMove(from, to))
				{
					System.out.println(from + " -> " + to + " is not a valid move!");
					System.out.println("Try again");
					continue;
				}
				
				++moves;
				
				// checks for withdraw/approach ambiguity
				if(move.hasCapture(from, to, true) && move.hasCapture(from, to, false))
				{
					System.out.println("Ambiguous Capture!");
					System.out.print("Select 1 for approach or 2 for withdraw: ");
					ans = input.nextInt();
					while(ans != 1 && ans != 2){
						ans = input.nextInt();
						System.out.print("Select 1 for approach or 2 for withdraw: ");
					}
					if(ans == 1)
					{
						move.capture(from, to, true);
					}
					else
					{
						move.capture(from, to, false);
					}
					break;
				}
				else if (! move.capture(from, to, move.hasCapture(from, to, true)))
				{
					// no more captures
					break;
				}
				
				System.out.println(board);
				from = to;
			}
			
			if (! maxMoves() || board.numBlack() == 0 || board.numWhite() == 0)
			{
				// game over
				break;
			}
			
			// other player's turn now
			player ^= 1;
		}
		
		input.close();
	}
	
	private static String playerIdToString(int player)
	{
		if (player == Board.WHITE)
		{
			return "white";
		}
		
		return "black";
	}
	
	//Returns FALSE if maximum number of moves has been exceeded
	private static boolean maxMoves()
	{
		if(moves >= 50)
		{
			System.out.println("Maximum moves exceeded");
			return false;
		}
		else
			return true;
	}

}
