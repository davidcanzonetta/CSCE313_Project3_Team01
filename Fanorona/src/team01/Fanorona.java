package team01;

import java.util.*

public class Fanorona {

	/**
	 * @param args
	 */
	static int moves = 0; //Keep track of number of game moves
	static Board board = new Board();
	
	public static void main(String[] args) 
	{
		//Implements move functionality, not visual yet
		//TODO: Print the board
		boolean valid_move = false, quit = false;
		int curr_pos, new_pos, error_check = 0, in;
		Scanner input=new Scanner(System.in);
		while(!quit)
		{
			while(!valid_move)
			{
				System.out.print("Enter the current numerical position of the white piece to move: ");
				curr_pos = input.nextInt();
				System.out.print("Enter the numerical position to move to: ");
				new_pos = input.nextInt();
				error_check = move_white(curr_pos, new_pos);
				if(error_check == 0)
				{
					System.out.println("Valid move");
					moves++;
					valid_move = true;
				}
				else
					System.out.println("Not a valid move");
			}
			valid_move = false;
			while(!valid_move)
			{
				System.out.print("Enter the current numerical position of the black piece to move: ");
				curr_pos = input.nextInt();
				System.out.print("Enter the numerical position to move to: ");
				new_pos = input.nextInt();
				error_check = move_black(curr_pos, new_pos);
				if(error_check == 0)
				{
					System.out.println("Valid move");
					moves++;
					valid_move = true;
				}
				else
					System.out.println("Not a valid move");
			}
			System.out.print("Enter 1 to keep moving: ");
			in = input.nextInt();
			if(in != 1)
				quit = true;
			//quit stays false if moves has not exceeded 50
			quit = max_moves();
		}
	}
	//Returns FALSE if maximum number of moves has been exceeded
	public static boolean max_moves()
	{
		if(moves >= 50)
			return false;
		else
			return true;
	}
	
	//Function to move white pieces, returns -1 if invalid move etc.
	public static int move_white(int curr_pos, int new_pos) {
		if(board.isWhite(curr_pos) && board.isEmpty(new_pos)) {
			board.setPosition(curr_pos, Board.EMPTY);
			board.setPosition(new_pos, Board.WHITE);
			return 0; 
		} else {
			return -1;
		}
	}
	
	//Function to move black pieces, returns -1 if invalid move etc.
	public static int move_black(int curr_pos, int new_pos) {
		if(board.isBlack(curr_pos) && board.isEmpty(new_pos)) {
			board.setPosition(curr_pos, Board.EMPTY);
			board.setPosition(new_pos, Board.BLACK);
			return 0; 
		} else {
			return -1;
		}
	}

}
