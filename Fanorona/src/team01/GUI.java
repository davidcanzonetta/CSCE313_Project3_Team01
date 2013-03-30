package team01;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	private List<Pair> gridLines = new ArrayList<Pair>();

	private final Game game;
	
	private int width;
	private int height;

	private int spacing;
	private int radius;
	private int diameter;

	private int minX;
	private int minY;
	private int maxX;
	private int maxY;

	private int winWidth;
	private int winHeight;
	
	private DrawCanvas canvas;
	
	public GUI() {
		game = new Game(9, 5, false);
		width = game.getBoard().getWidth();
		height = game.getBoard().getHeight();
		
		spacing = 80;				// spacing between board positions
		radius = spacing / 2 - 8;	// 8 px between game pieces
		diameter = 2 * radius;
		
		minX = spacing;
		minY = spacing;
		maxX = width * spacing;
		maxY = height * spacing;
		
		winWidth = maxX + spacing;
		winHeight = maxY + spacing;
		
		initializeGridLines();
		
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(winWidth, winHeight));
		
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();

				int x = (mouseX + radius) / spacing;
				int y = height - ((mouseY + radius) / spacing) + 1;

				Point point = new Point(x, y);
				if (game.getBoard().isValidPoint(point))
				{
					if (! game.update(point))
					{
						System.out.println("!!! INVALID MOVE: " + point);
						System.out.println();
					}
				}
				
				repaint();
			}
		});
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setTitle("Fanorona");
		setVisible(true);
	}
	
	class DrawCanvas extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintChildren(g);
//			setBackground(Color.BLUE);		// TODO: doesn't work
			drawGridLines(g);
			drawGamePieces(g);
		}
	}
	
	private void drawGamePieces(Graphics g)
	{
		Board board = game.getBoard();

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
					g.setColor(Color.WHITE);
					g.fillOval(x, y, diameter, diameter);
					break;
				case Board.BLACK:
					g.setColor(Color.BLACK);
					g.fillOval(x, y, diameter, diameter);
					break;
				case Board.WHITE_GRAY:
				case Board.BLACK_GRAY:
					g.setColor(Color.GRAY);
					g.fillOval(x, y, diameter, diameter);
					break;
			}

			if (game.getClickable().contains(point))
			{
				g.setColor(Color.GREEN);
				g.drawOval(x, y, diameter, diameter);
			}
		}
	}

	private void drawGridLines(Graphics g)
	{
		g.setColor(new Color(221, 218, 236));
		g.fillRect(0, 0, winWidth, winHeight);
		g.setColor(Color.BLACK);
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
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				new GUI();
			}
		});
	}
}
