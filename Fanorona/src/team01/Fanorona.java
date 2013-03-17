package team01;

import java.util.*;

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
	public static int move_white(int curr_pos, int new_pos) {
		if(board.isWhite(curr_pos) && board.isEmpty(new_pos)) {
			ambiguity_check_white(curr_pos, new_pos);
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
			ambiguity_check_black(curr_pos, new_pos);
			board.setPosition(curr_pos, Board.EMPTY);
			board.setPosition(new_pos, Board.BLACK);
			return 0; 
		} else {
			return -1;
		}
	}
	//Doesn't let user choose which to capture yet, but notifies if more than one choice available
	//Also assuming that by this point it is a valid move
	public static boolean ambiguity_check_white(int curr_pos, int new_pos){
		int curr_pos_copy = curr_pos;
		boolean forward = false, backward = false, capture_1 = false, capture_2 = false;
		//Anything to import for Math.abs?
		int diff = Math.abs(curr_pos-new_pos);
		while(!forward){
			if(board.isEmpty(curr_pos))
				curr_pos = curr_pos + diff;
			else {
				if(board.isBlack(curr_pos))
					capture_1 = true;
				forward = true;
			}
		}
		curr_pos = curr_pos_copy;
		while(!backward){
			if(board.isEmpty(curr_pos))
				curr_pos = curr_pos - diff;
			else {
				if(board.isBlack(curr_pos))
					capture_2 = true;
				forward = true;
			}
		}
		if(capture_1 && capture_2)
			System.out.println("More than one piece available to be captured");
		else
			System.out.println("No more than one piece available to be captured");
		return true;
	}
	public static boolean ambiguity_check_black(int curr_pos, int new_pos){
		int curr_pos_copy = curr_pos;
		boolean forward = false, backward = false, capture_1 = false, capture_2 = false;
		//Anything to import for Math.abs?
		int diff = Math.abs(curr_pos-new_pos);
		while(!forward){
			if(board.isEmpty(curr_pos))
				curr_pos = curr_pos + diff;
			else {
				if(board.isWhite(curr_pos))
					capture_1 = true;
				forward = true;
			}
		}
		curr_pos = curr_pos_copy;
		while(!backward){
			if(board.isEmpty(curr_pos))
				curr_pos = curr_pos - diff;
			else {
				if(board.isWhite(curr_pos))
					capture_2 = true;
				forward = true;
			}
		}
		if(capture_1 && capture_2)
			System.out.println("More than one piece available to be captured");
		else
			System.out.println("No more than one piece available to be captured");
		return true;
	}
}
