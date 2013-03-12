package team01;

public class Fanorona {

	/**
	 * @param args
	 */
	int moves = 0; //Keep track of number of game moves
	
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

}
