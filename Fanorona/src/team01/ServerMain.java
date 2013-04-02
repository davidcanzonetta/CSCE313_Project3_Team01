package team01;

public class ServerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI.singlePlayer = Integer.parseInt(args[0]) == 1;
		GUI.player = args[1].equals("W") ? Board.WHITE : Board.BLACK;
		GUI.width = Integer.parseInt(args[2]);
		GUI.height = Integer.parseInt(args[3]);
		long time = Long.parseLong(args[4]);
		int port = Integer.parseInt(args[5]);

		Server server = new Server(port);
		server.write("WELCOME");
		server.write("INFO " + GUI.width + " " + GUI.height + " " + args[1] + " " + time);
		if (! server.read().equals("READY"))
		{
			server.write("ILLEGAL");
			System.exit(-1);
		}
		server.write("BEGIN");

		// TODO: start the GUI
	}

}
