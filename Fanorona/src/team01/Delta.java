package team01;

public class Delta {

	public int dx;
	public int dy;
	
	public Delta (Point from, Point to)
	{
		dx = to.x - from.x;
		dy = to.y - from.y;
	}
	
	static Delta getDelta (Point from, Point to)
	{
		return new Delta(from, to);
	}
	
	// TODO: should override Object.equals?
	boolean equals(Delta delta)
	{
		return (dx == delta.dx)
			&& (dy == delta.dy);
	}
	
	// dx, dy in [MIN_DELTA, MAX_DELTA] ?
	boolean isValid()
	{
		return (Util.inRange(dx, MIN_DELTA, MAX_DELTA))
			&& (Util.inRange(dy, MIN_DELTA, MAX_DELTA));
	}
	
	// direction is diagonal?
	boolean isDiagonal()
	{
		return Math.abs(dx) + Math.abs(dy) == 2;
	}
	
	public static final int MIN_DELTA = -1;
	public static final int MAX_DELTA = 1;
}
