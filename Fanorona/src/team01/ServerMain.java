package team01;

public class ServerMain {

	static long time;
	static int port;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI.singlePlayer = Integer.parseInt(args[0]) == 1;
		GUI.player = args[1].equals("W") ? Board.WHITE : Board.BLACK;
		GUI.width = Integer.parseInt(args[2]);
		GUI.height = Integer.parseInt(args[3]);
		time = Long.parseLong(args[4]);
		port = Integer.parseInt(args[5]);
		String otherPlayer = GUI.player == Board.WHITE ? "B" : "W";
		
		Server server = new Server(port);
		server.write("WELCOMEINFO " + GUI.width + " " + GUI.height + " " + otherPlayer + " " + time);
		if (! server.read().equals("READY"))
		{
			server.write("ILLEGAL");
			System.exit(-1);
		}
		server.write("BEGIN");

		// TODO: start the GUI
		
		// USED FOR DEBUGGING PURPOSES ONLY
		System.out.println("SUCCESS!!!");
		
		server.close();
	}

}
