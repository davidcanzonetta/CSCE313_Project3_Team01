package team01;

import java.util.ArrayList;
import java.util.List;

public class Delta {

	private int dx;
	private int dy;

	public static final int MIN_DELTA = -1;
	public static final int MAX_DELTA = 1;
	
	public static List<Delta> orthogonal = new ArrayList<Delta>();
	public static List<Delta> diagonal = new ArrayList<Delta>();

	public Delta(int dx, int dy) {
		super();
		this.dx = dx;
		this.dy = dy;
	}

	public Delta(Point from, Point to)
	{
		super();
		this.dx = to.getX() - from.getX();
		this.dy = to.getY() - from.getY();
	}

	boolean isDiagonal() {
		return Math.abs(dx) + Math.abs(dy) == 2;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dx;
		result = prime * result + dy;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Delta other = (Delta) obj;
		if (dx != other.dx)
			return false;
		if (dy != other.dy)
			return false;
		return true;
	}

	static {
		// initialize valid delta lists at startup
		for (int dy = Delta.MIN_DELTA; dy <= Delta.MAX_DELTA; dy++)
		{
			for (int dx = Delta.MIN_DELTA; dx <= Delta.MAX_DELTA; dx++)
			{
				Delta delta = new Delta(dx, dy);
				
				if (delta.isDiagonal())
				{
					diagonal.add(delta);
				}
				else
				{
					// exclude (0, 0)
					if (dx != 0 || dy != 0)
					{
						orthogonal.add(delta);
					}
				}
			}
		}
	}

}
