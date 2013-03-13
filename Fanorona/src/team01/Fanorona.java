package team01;

public class Fanorona {

	/**
	 * @param args
	 */
	static int moves = 0; //Keep track of number of game moves
	static int whitePieces = 22;
	static int blackPieces = 22;
	static Board board = new Board();
	
	public static void main(String[] args) 
	{
			
		
	}
	//Returns FALSE if maximum number of moves has been exceeded
	public static boolean max_moves()
	{
		if(moves >= 50)
			return false;
		else
			return true;
	}
	//Return number of remaining white pieces
	public static int rem_white()
	{
		return whitePieces;
	}
	//Return number of remaining black pieces
	public static int rem_black()
	{
		return blackPieces;
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
