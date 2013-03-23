package team01;

public class Point {

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public Point (int x_, int y_)
	{
		x = x_;
		y = y_;
	}
	
	Point getApproach (Delta delta)
	{
		return getApproach (delta.dx, delta.dy);
	}
	
	Point getWithdraw (Delta delta)
	{
		return getWithdraw (delta.dx, delta.dy);
	}
	
	Point getApproach (int dx, int dy)
	{
		return new Point (x + dx, y + dy);
	}
	
	Point getWithdraw (int dx, int dy)
	{
		return new Point (x - dx, y - dy);
	}
	
	public int x;
	public int y;
	
	// not intended to override Object.equals
	public boolean equals(Point pt)
	{
		return (x == pt.x) && (y == pt.y);
	}
}
