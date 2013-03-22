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
		//TODO: Implement Move class
		boolean valid_move = false, quit = false;
		int curr_pos_x, curr_pos_y, new_pos_x, new_pos_y, error_check = 0, in;
		Scanner input=new Scanner(System.in);
		while(!quit)
		{
			while(!valid_move)
			{
				System.out.println("White's turn\n------------\n");
				System.out.print(board);
				System.out.print("\nEnter X position of piece to move: ");
				curr_pos_x = input.nextInt();
				System.out.print("Enter Y position of piece to move: ");
				curr_pos_y = input.nextInt();
				System.out.print("Enter new X pos: ");
				new_pos_x = input.nextInt();
				System.out.print("Enter new Y pos: ");
				new_pos_y = input.nextInt();
				error_check = move_white(curr_pos_x, curr_pos_y, new_pos_x, new_pos_y);
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
				System.out.println("Black's turn\n------------");
				System.out.print(board);
				System.out.println("\n0 1 2 3 4 5 6 7 8 <- X values");
				System.out.print("\nEnter X position of piece to move: ");
				curr_pos_x = input.nextInt();
				System.out.print("Enter Y position of piece to move: ");
				curr_pos_y = input.nextInt();
				System.out.print("Enter new X pos: ");
				new_pos_x = input.nextInt();
				System.out.print("Enter new Y pos: ");
				new_pos_y = input.nextInt();
				error_check = move_black(curr_pos_x, curr_pos_y, new_pos_x, new_pos_y);
				if(error_check == 0)
				{
					System.out.println("Valid move");
					moves++;
					valid_move = true;
				}
				else
					System.out.println("Not a valid move");
			}
			quit = max_moves();
			System.out.print("Enter 1 to keep moving: ");
			in = input.nextInt();
			if(in != 1)
				quit = true;
			//quit stays false if moves has not exceeded 50
		}
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
	
	//Function to move white pieces, returns -1 if invalid move etc.
	public static int move_white(int curr_pos_x, int curr_pos_y, int new_pos_x, int new_pos_y) {
		if(board.isWhite(curr_pos_x, curr_pos_y) && board.isEmpty(new_pos_x, new_pos_y)) {
			board.setPosition(curr_pos_x, curr_pos_y, Board.EMPTY);
			board.setPosition(new_pos_x, new_pos_y, Board.WHITE);
			return 0; 
		} else {
			return -1;
		}
	}
	
	//Function to move black pieces, returns -1 if invalid move etc.
	public static int move_black(int curr_pos_x, int curr_pos_y, int new_pos_x, int new_pos_y) {
		if(board.isBlack(curr_pos_x, curr_pos_y) && board.isEmpty(new_pos_x, new_pos_y)) {
			board.setPosition(curr_pos_x, curr_pos_y, Board.EMPTY);
			board.setPosition(new_pos_x, new_pos_y, Board.BLACK);
			return 0; 
		} else {
			return -1;
		}
	}
}
