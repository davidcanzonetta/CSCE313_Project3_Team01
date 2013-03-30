package team01;

public class Pair {

	private Point src;
	private Point dest;

	public Pair(Point src, Point dest) {
		super();
		this.src = src;
		this.dest = dest;
	}

	// constructor
	public Pair(int x1, int y1, int x2, int y2) {
		super();
		this.src = new Point(x1, y1);
		this.dest = new Point(x2, y2);
	}

	public Point getSrc() {
		return src;
	}

	public Point getDest() {
		return dest;
	}

}
