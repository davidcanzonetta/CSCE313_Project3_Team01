package team01;

public class Fanorona {

	/**
	 * @param args
	 */
	static int moves = 0; //Keep track of number of game moves
	static int whitePieces = 22;
	static int blackPieces = 22;
	
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

}
