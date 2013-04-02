package team01;

import javax.swing.SwingUtilities;

public class Local {

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
				new GUI();
			}
		});
	}

}
