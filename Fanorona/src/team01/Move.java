package team01;

import java.util.*;

public class Move {
	
	public Move (Board b)
	{
		board = b;
		path = new ArrayList<Point>();
	}
	
	// check move from (x1, y1) to (x2, y2).
	// this code should not have side effects!
	public boolean isValidMove(int x1, int y1, int x2, int y2)
	{
		// position (x2, y2) is in range?
		if (! board.isValidPosition(x2, y2))
		{
			return false;
		}
		
		// position (x2, y2) is empty?
		if (! board.isEmpty(x2, y2))
		{
			return false;
		}
		
		// position (x2, y2) already in path?
		if (findInPath(x2, y2))
		{
			return false;
		}
		
		// use delta x and delta y to check direction
		int dx = x2 - x1;
		int dy = y2 - y1;
		
		// check delta ranges
		if (! isValidDelta(dx, dy))
		{
			return false;
		}
		
		// direction of current move different than last move?
		if (! isNewDirection(x1, y1, dx, dy))
		{
			return false;
		}
		
		// handle diagonal moves
		if (Math.abs(dx) + Math.abs(dy) == 2)
		{
			// current position (x1, y1) can move diagonally?
			if (! board.isDiagonalPosition(x1, y1))
			{
				return false;
			}
		}
		
		// TODO: possibly check for approach or withdraw captures separately
		
		int player = board.getPosition(x1, y1);
		
		// move will capture other player's piece by approach or withdrawal?
		if (! (hasCapture(player, x2 + dx, y2 + dy)
			|| hasCapture(player, x1 - dx, y1 - dy)))
		{
			return false;
		}
		
		return true;
	}
	
	private boolean hasCapture(int player, int x, int y)
	{
		if (board.isValidPosition(x, y))
		{
			int otherplayer = board.getPosition(x, y);
			
			if (otherplayer != Board.EMPTY && otherplayer != player)
			{
				return true;
			}
		}
		
		// not a capture
		return false;
	}
	
	// check if position (x, y) is in path
	private boolean findInPath(int x, int y)
	{
		for (Point point : path)
		{
			if (point.x == x && point.y == y)
			{
				return true;
			}
		}
		
		// (x, y) not in path
		return false;
	}
	
	// add position (x, y) to path
	private void addToPath(int x, int y)
	{
		path.add(new Point(x, y));
	}
	
	private boolean isValidDelta(int dx, int dy)
	{
		// delta values in [-1, 1] ?
		return (-1 <= dx && dx <= 1)
			&& (-1 <= dy && dy <= 1);
	}
	
	private boolean isNewDirection(int x, int y, int dx, int dy)
	{
		int last = path.size() - 1;
		
		// check direction of last move in the path
		if (last >= 0)
		{
			Point point = path.get(last);
			int lastdx = x - point.x;
			int lastdy = y - point.y;
			return (dx != lastdx || dy != lastdy);
		}
		
		// no moves to check
		return true;
	}
	
	private class Point {
		public Point (int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int x;
		public int y;
	}
	
	private List<Point> path;
	private Board board;
}
