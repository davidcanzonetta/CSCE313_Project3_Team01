package team01;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board implements Iterable<Point> {

	private int[][] grid;
	private int width;
	private int height;
	private int white;
	private int black;

	// board states
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public static final int EMPTY = 2;

	public Board(int width, int height) {
		super();

		this.width = width;
		this.height = height;

		int total = height * width;
		this.white = this.black = (total - 1) / 2;

		this.grid = new int[height + 1][];
		for (int i = 1; i < grid.length; i++)
		{
			this.grid[i] = new int[width + 1];
		}
		
		setupGrid();
	}

	public Board(Board board) {
		this.width = board.width;
		this.height = board.height;
		this.white = board.white;
		this.black = board.black;
		
		this.grid = new int[board.grid.length][];
		
		// deep copy of grid
		for (int i = 1; i < board.grid.length; i++)
		{
			this.grid[i] = Arrays.copyOf(board.grid[i], board.grid[i].length);
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getWhite() {
		return white;
	}

	public int getBlack() {
		return black;
	}

	public int getEmpty() {
		int total = width * height;
		return total - white - black;
	}

	public int getPoint(Point p) {
		return grid[p.getY()][p.getX()];
	}

	public void setPoint(Point p, int value) {
		int oldVal = getPoint(p);

		if (oldVal == WHITE)
			white--;
		else if (oldVal == BLACK)
			black--;

		if (value == WHITE)
			white++;
		else if (value == BLACK)
			black++;
		
		grid[p.getY()][p.getX()] = value;
	}

	public boolean isValidPoint(Point p) {
		return 1 <= p.getX() && p.getX() <= width
			&& 1 <= p.getY() && p.getY() <= height;
	}

	public boolean isDiagonalPoint(Point p) {
		int sum = p.getX() + p.getY();
		return (sum % 2) == 0;
	}

	@Override
	public String toString() {
		String result = "";
		
		for (int i = 1; i <= height; i++)
		{
			StringBuilder row = new StringBuilder();

			for (int k = 1; k <= width; k++)
			{
				int x = grid[i][k];
				
				if (x == WHITE)
				{
					row.append("O-");
				}
				else if (x == BLACK)
				{
					row.append("X-");
				}
				else // if (x == EMPTY)
				{
					row.append("_-");
				}
			}

			row.setCharAt(row.length() - 1, '\n');
			result = row.toString() + result;
		}
		
		return result;
	}

	@Override
	public Iterator<Point> iterator() {
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
			
			Point point = new Point(x + 1, y + 1);
			
			if (++x == width)
			{
				++y;
			}
			
			return point;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private void setupGrid() {
		int middle = height / 2 + 1;	// middle row
		int center = width / 2 + 1;	// center column
		
		// lower 2 rows
		for (int i = 1; i < middle; i++)
		{
			for (int k = 1; k <= width; k++)
			{
				grid[i][k] = WHITE;
			}
		}
		
		// left half of middle row
		for (int k = 1; k < center; k++)
		{
			grid[middle][k] = (k & 1) == 1 ? BLACK : WHITE;
		}
		
		// middle center position
		grid[middle][center] = EMPTY;
		
		// right half of middle row
		for (int k = center + 1; k <= width; k++)
		{
			grid[middle][k] = (k & 1) == 1 ? WHITE : BLACK;
		}
		
		// upper 2 rows
		for (int i = middle+1; i <= height; i++)
		{
			for (int k = 1; k <= width; k++)
			{
				grid[i][k] = BLACK;
			}
		}
	}

}
