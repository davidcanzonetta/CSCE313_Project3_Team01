package team01;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board implements Iterable<Point> {

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
	
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		
		str.append("  ");
		for (int i = 0; i < width; i++)
		{
			str.append(i + " ");
		}
		str.setCharAt(str.length()-1, '\n');
		
		for (int i = 0; i < height; i++)
		{
			str.append(i + " ");
			for (int k = 0; k < width; k++)
			{
				int x = grid[i][k];
				
				if (x == WHITE)
				{
					str.append("O-");
				}
				else if (x == BLACK)
				{
					str.append("X-");
				}
				else // if (x == EMPTY)
				{
					str.append("_-");
				}
			}
			str.setCharAt(str.length()-1, '\n');
			str.append("  ");
			if (i < height - 1)
			{
				for (int k = 0; k < width; k++)
				{
					if (Util.isEven(i + k))
					{
						str.append("|\\");
					}
					else
					{
						str.append("|/");
					}
				}
			}
			str.setCharAt(str.length()-1, '\n');
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
	
	public int getPosition(Point pt)
	{
		return grid[pt.y][pt.x];
	}
	
	public void setPosition(Point pt, int val)
	{
		int oldVal = getPosition(pt);

		if (oldVal == WHITE)
			nWhite--;
		else if (oldVal == BLACK)
			nBlack--;
		
		if (val == WHITE)
			nWhite++;
		else if (val == BLACK)
			nBlack++;
		
		grid[pt.y][pt.x] = val;
	}
	
	// x in [0, width) and y in [0, height) ?
	public boolean isValidPosition(Point pt)
	{
		return (Util.inRange(pt.x, 0, width-1))
			&& (Util.inRange(pt.y, 0, height-1));
	}
	
	// position has diagonal if x+y is even
	public boolean isDiagonalPosition(Point pt)
	{
		return Util.isEven(pt.x + pt.y);
	}
	
	public boolean isEmpty(Point pt)
	{
		return getPosition(pt) == EMPTY;
	}
	
	public boolean isWhite(Point pt)
	{
		return getPosition(pt) == WHITE;
	}
	
	public boolean isBlack(Point pt)
	{
		return getPosition(pt) == BLACK;
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
		return (width * height) - nWhite - nBlack;
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
			grid[middle][k] = Util.isEven(k) ? BLACK : WHITE;
		}
		
		// middle center position
		grid[middle][center] = EMPTY;
		
		// right half of middle row
		for (int k = center+1; k < width; k++)
		{
			grid[middle][k] = Util.isEven(k) ? WHITE : BLACK;
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
	
	private int [][] grid;
	private int width;
	private int height;
	private int nWhite;
	private int nBlack;

	@Override
	public Iterator<Point> iterator() {
		// TODO Auto-generated method stub
		return new Itr();
	}
	
	private class Itr implements Iterator<Point> {

		int x = 0;
		int y = 0;
		
		@Override
		public boolean hasNext() {
			return (x < width)
				|| (y < height);
		}

		@Override
		public Point next() {
			if (x >= width && y >= height)
			{
				throw new NoSuchElementException();
			}
			
			x = x % width;
			y = y % height;
			
			Point point = new Point(x, y);
			
			if (++x == width)
			{
				++y;
			}
			
			return point;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		
	}
}
