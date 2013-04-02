package team01;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	private List<Pair> gridLines = new ArrayList<Pair>();

	protected final Game game;
	
	public static int mode;
	public static boolean singlePlayer;
	public static int player;
	public static int width;
	public static int height;
	public static long timeout;

	protected int spacing;
	protected int radius;
	protected int diameter;
	protected int minX;
	protected int minY;
	protected int maxX;
	protected int maxY;
	protected int winWidth;
	protected int winHeight;
	protected DrawCanvas canvas;
	
	public GUI() {
		setBackground(new Color(47, 79, 79));
		
		game = new Game(width, height, singlePlayer, player);
		spacing = 80;				// 80 px between board positions
		radius = spacing / 2 - 8;	// 8 px between game pieces
		diameter = 2 * radius;
		minX = spacing;
		minY = spacing;
		maxX = width * spacing;
		maxY = height * spacing;
		winWidth = maxX + spacing;
		winHeight = maxY + spacing;
		
		initializeGridLines();
	}
	
	class DrawCanvas extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintChildren(g);
			drawGridLines(g);
			drawGamePieces(g);
		}
	}

	private void drawGamePieces(Graphics g)
	{
		Board board = game.getBoard();

		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));
		
		for (Point point : board)
		{
			int type = board.getPoint(point);
			int x = point.getX() * spacing;
			int y = winHeight - (point.getY() * spacing);

			x -= radius;
			y -= radius;
			
			switch (type)
			{
				case Board.WHITE:
					g2d.setColor(new Color(240, 255, 255));
					g2d.fillOval(x, y, diameter, diameter);
					break;
				case Board.BLACK:
					g2d.setColor(Color.BLACK);
					g2d.fillOval(x, y, diameter, diameter);
					break;
				case Board.WHITE_GRAY:
				case Board.BLACK_GRAY:
					g2d.setColor(Color.GRAY);
					g2d.fillOval(x, y, diameter, diameter);
					break;
			}

			if (game.getClickable().contains(point))
			{
				g.setColor(Color.MAGENTA);
				g.drawOval(x, y, diameter, diameter);
			}
		}
	}

	private void drawGridLines(Graphics g)
	{
		g.setColor(new Color(250, 158, 114));
		for (Pair pair : gridLines)
		{
			Point src = pair.getSrc();
			Point dest = pair.getDest();
			g.drawLine(src.getX(), src.getY(), dest.getX(), dest.getY());
		}
	}

	private void initializeGridLines()
	{
		// horizontal lines
		for (int i = 1; i <= height; i++)
		{
			int y = i * spacing;

			gridLines.add(new Pair(minX, y, maxX, y));
		}

		// vertical lines
		for (int i = 1; i <= width; i++)
		{
			int x = i * spacing;

			gridLines.add(new Pair(x, minY, x, maxY));
		}

		// diagonal lines (upper left to lower right)
		for (int i = 1; i < width; i += 2)
		{
			int x1 = i * spacing;

			int x2 = x1;
			int y2 = minY;

			while (x2 < maxX && y2 < maxY)
			{
				x2 += spacing;
				y2 += spacing;
			}

			gridLines.add(new Pair(x1, minY, x2, y2));
		}
		for (int i = 3; i < height; i += 2)
		{
			int y1 = i * spacing;

			int x2 = minX;
			int y2 = y1;

			while (x2 < maxX && y2 < maxY)
			{
				x2 += spacing;
				y2 += spacing;
			}

			gridLines.add(new Pair(minX, y1, x2, y2));
		}

		// diagonal lines (upper right to lower left)
		for (int i = 3; i < width; i += 2)
		{
			int x1 = i * spacing;

			int x2 = x1;
			int y2 = minY;

			while (x2 > minX && y2 < maxY)
			{
				x2 -= spacing;
				y2 += spacing;
			}

			gridLines.add(new Pair(x1, minY, x2, y2));
		}

		for (int i = 1; i < height; i += 2)
		{
			int y1 = i * spacing;

			int x2 = maxX;
			int y2 = y1;

			while (x2 > minX && y2 < maxY)
			{
				x2 -= spacing;
				y2 += spacing;
			}

			gridLines.add(new Pair(maxX, y1, x2, y2));
		}
	}
	
	public static void main(String[] args) {

		if (args.length < 5) {
			System.out.println("invalid command arguments");
			System.exit(-1);
		}
		
		GUI.mode = Integer.parseInt(args[0]);
		GUI.singlePlayer = args[1].equals("A");
		GUI.player = args[2].equals("W") ? Board.WHITE : Board.BLACK;
		GUI.width = Integer.parseInt(args[3]);
		GUI.height = Integer.parseInt(args[4]);
		GUI.timeout = Long.parseLong(args[5]);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				new GUI();
			}
		});
	}
}
