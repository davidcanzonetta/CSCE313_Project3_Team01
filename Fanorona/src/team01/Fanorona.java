package team01;

import java.util.*;

public class Fanorona {

	/**
	 * @param args
	 */
	static int moves = 0; //Keep track of number of game moves
	static Board board = new Board(9, 5);
	
	public static void main(String[] args) 
	{
		//Prints board
		int curr_pos_x, curr_pos_y, new_pos_x, new_pos_y;
		int curr_player = Board.WHITE;
		
		Scanner input = new Scanner(System.in);
		
		while (true)
		{
			// TODO: sweep board for captures
			
			Point from, to;
			
			// get move from coordinates
			System.out.println("Player: " + (curr_player == Board.WHITE ? "white" : "black"));
			System.out.print(board);
			
			System.out.print("Enter X position of piece to move: ");
			curr_pos_x = input.nextInt();
			System.out.print("Enter Y position of piece to move: ");
			curr_pos_y = input.nextInt();
			
			from = new Point(curr_pos_x, curr_pos_y);
			
			// TODO: check that position from has captures?
			
			if (! board.isValidPosition(from))
			{
				System.out.println("Position " + from + " is not valid!");
				System.out.println("Try again");
				continue;
			}
			
			if (board.getPosition(from) != curr_player)
			{
				System.out.println("Position " + from + " is not " + (curr_player == Board.WHITE ? "white" : "black"));
				System.out.println("Try again");
				continue;
			}

			// TODO: handle case where there are no captures available
			Move move = new Move(board);
			
			while (true)
			{
				// get move to coordinates
				System.out.print("Enter X position to move " + from + " to: ");
				new_pos_x = input.nextInt();
				System.out.print("Enter Y position to move " + from + " to: ");
				new_pos_y = input.nextInt();
				
				to = new Point(new_pos_x, new_pos_y);
				
				if (! move.isValidMove(from, to))
				{
					System.out.println(from + " -> " + to + " is not a valid move!");
					System.out.println("Try again");
					continue;
				}
				
				++moves;
				
				// TODO: check for withdraw/approach ambiguity
				if (! move.capture(from, to, move.hasCapture(from, to, true)))
				{
					// no more captures
					break;
				}
				
				System.out.println(board);
				from = to;
			}
			
			if (! max_moves() || board.numBlack() == 0 || board.numWhite() == 0)
			{
				// game over
				break;
			}
			
			// other player's turn now
			curr_player ^= 1;
		}
		
		input.close();
	}
	
	//Returns FALSE if maximum number of moves has been exceeded
	public static boolean max_moves()
	{
		if(moves >= 50)
		{
			System.out.println("Maximum moves exceeded");
			return false;
		}
		else
			return true;
	}
//	public static boolean move_function(Move move, Point from, Point to)
//	{
//			// TODO: handle case where approach and withdraw are possible
//			done = m.capture(from, to, m.hasCapture(from, to, true));
//		//if move_done == false, no more captures
//		System.out.println("Valid move");
//		moves++;
//	}
//	//Function to move white pieces, returns -1 if invalid move etc.
//	public static int move_white(int curr_pos_x, int curr_pos_y, int new_pos_x, int new_pos_y) {
////		if(board.isWhite(curr_pos_x, curr_pos_y) && board.isEmpty(new_pos_x, new_pos_y)) {
////			board.setPosition(curr_pos_x, curr_pos_y, Board.EMPTY);
////			board.setPosition(new_pos_x, new_pos_y, Board.WHITE);
////			return 0; 
////		} else {
////			return -1;
////		}
//		
//		return 0;
//	}
//	
//	//Function to move black pieces, returns -1 if invalid move etc.
//	public static int move_black(int curr_pos_x, int curr_pos_y, int new_pos_x, int new_pos_y) {
////		if(board.isBlack(curr_pos_x, curr_pos_y) && board.isEmpty(new_pos_x, new_pos_y)) {
////			board.setPosition(curr_pos_x, curr_pos_y, Board.EMPTY);
////			board.setPosition(new_pos_x, new_pos_y, Board.BLACK);
////			return 0; 
////		} else {
////			return -1;
////		}
//		
//		return 0;
//	}
}
