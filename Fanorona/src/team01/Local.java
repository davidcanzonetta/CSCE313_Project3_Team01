package team01;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Local extends GUI {

	public Local() {
		super();
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(winWidth, winHeight));
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (game.isTie() || game.whiteWins() || game.blackWins())
				{
					game.reset();
					setTitle("Fanorona");
					repaint();
					return;
				}
				
				int mouseX = e.getX();
				int mouseY = e.getY();

				int x = (mouseX + radius) / spacing;
				int y = game.getBoard().getHeight() - ((mouseY + radius) / spacing) + 1;

				Point point = new Point(x, y);
				if (game.getBoard().isValidPoint(point))
				{
					if (! game.update(point))
					{
						System.out.println("!!! INVALID MOVE: " + point);
						System.out.println();
					}
				}
				
				if (game.isTie())
				{
					System.out.println("Tie");
					setTitle("Tie");
				}
				else if (game.whiteWins())
				{
					System.out.println("White wins");
					setTitle("White wins");
				}
				else if (game.blackWins())
				{
					System.out.println("Black wins");
					setTitle("Black wins");
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 4) {
			System.out.println("invalid command arguments");
			System.exit(-1);
		}

		// command line options
		// singlePlayer: 1 or 2
		// player: W or B
		// width: 3-13
		// height: 3-13
		GUI.singlePlayer = Integer.parseInt(args[0]) == 1;
		GUI.player = args[1].equals("W") ? Board.WHITE : Board.BLACK;
		GUI.width = Integer.parseInt(args[2]);
		GUI.height = Integer.parseInt(args[3]);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				new Local();
			}
		});
	}

}
