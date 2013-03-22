package team01;

public class Board {

	// board states
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public static final int EMPTY = 2;
	
	public Board (int w, int h)
	{
		width = w;
		height = h;
		nWhite = nBlack = (height * width - 1) / 2;
		grid = new int [height][width];
		
		// initialize grid
		newGame ();
	}
	
	public Board (Board board)
	{
		width = board.width;
		height = board.height;
		nWhite = board.nWhite;
		nBlack = board.nBlack;
		
		grid = new int [height][width];
		
		// deep copy of grid
		for (int i = 0; i < height; i++)
		{
			for (int k = 0; k < width; k++)
			{
				grid[i][k] = board.grid[i][k];
			}
		}
	}
	
	@Override public String toString()
	{
		StringBuilder str = new StringBuilder();
		
		for (int i = 0; i < height; i++)
		{
			for (int k = 0; k < width; k++)
			{
				int x = grid[i][k];
				
				if (x == WHITE)
				{
					str.append("o ");
				}
				else if (x == BLACK)
				{
					str.append("x ");
				}
				else // if (x == EMPTY)
				{
					str.append("_ ");
				}
			}
			str.append('\n');
		}
		
		return str.toString();
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	
	public int getPosition(int x, int y)
	{
		return grid[y][x];
	}
	
	public void setPosition(int x, int y, int val)
	{
//		if (isValidPosition(x, y))
		{
			grid[y][x] = val;
		}
	}
	
	public boolean isValidPosition(int x, int y)
	{
		// x in [0, width) and y in [0, height) ?
		return 0 <= x && x < width
			&& 0 <= y && y < height;
	}
	
	public boolean isDiagonalPosition(int x, int y)
	{
		// position has diagonal if x+y is even
		return isEven(x + y);
	}
	
	public boolean isEmpty(int x, int y)
	{
		return getPosition(x, y) == EMPTY;
	}
	
	public boolean isWhite(int x, int y)
	{
		return getPosition(x, y) == WHITE;
	}
	
	public boolean isBlack(int x, int y)
	{
		return getPosition(x, y) == BLACK;
	}
	
	public int numWhite()
	{
		return nWhite;
	}
	
	public int numBlack()
	{
		return nBlack;
	}
	
	public int numEmpty()
	{
		return width * height - nWhite - nBlack;
	}
	
	private void newGame()
	{
		int middle = height / 2;	// middle row
		int center = width / 2;	// center column
		
		// top 2 rows
		for (int i = 0; i < middle; i++)
		{
			for (int k = 0; k < width; k++)
			{
				grid[i][k] = BLACK;
			}
		}
		
		// left half of middle row
		for (int k = 0; k < center; k++)
		{
			grid[middle][k] = isEven(k) ? BLACK : WHITE;
		}
		
		// middle center position
		grid[middle][center] = EMPTY;
		
		// right half of middle row
		for (int k = center+1; k < width; k++)
		{
			grid[middle][k] = isEven(k) ? WHITE : BLACK;
		}
		
		// bottom 2 rows
		for (int i = middle+1; i < height; i++)
		{
			for (int k = 0; k < width; k++)
			{
				grid[i][k] = WHITE;
			}
		}
	}
	
	/*
	public static void main (String[] args)
	{
		Board board1 = new Board (3, 3);
		Board board2 = new Board (board1);
		
		board2.setPosition(0, 1, EMPTY);
		System.out.println (board1);
		System.out.println (board2);
	}
	*/
	
	private boolean isEven(int n)
	{
		return (n & 1) == 0;
	}
	
	private int width;
	private int height;
	private int [][] grid;
	
	private int nWhite;
	private int nBlack;
}
