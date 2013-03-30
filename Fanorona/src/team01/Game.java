package team01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

	List<Point> isClickable;	// cache of valid moves
	Board board;
	Move move;
	
	Point from;
	Point to;
	Delta delta;

	private boolean hasAiPlayer;

	private int player;
	private int state;
	private int moves;
	private int maxMoves;

	// game states (game manager is a finite state machine)
	private static final int NEED_CAPTURE_START = 0;
	private static final int NEED_CAPTURE_DEST = 1;
	private static final int NEED_CAPTURE_RESOLVE = 2;
	private static final int NEED_PAIKA_START = 3;
	private static final int NEED_PAIKA_DEST = 4;
	private static List<List<Delta>> deltaList = new ArrayList<List<Delta>>();
	
	static {
		deltaList.add(Delta.orthogonal);
		deltaList.add(Delta.diagonal);
	}
	
	public static void main(String[] args) {
		System.out.println("** If game piece can capture enter its position twice to **");
		System.out.println("** sacrifice it.  If the game piece does not have a      **");
		System.out.println("** capture it only needs to be entered once, a sacrifice **");
		System.out.println("** move is done automatically.  Only capture/paika moves **");
		System.out.println("** listed.                                               **");
		System.out.println();
		
		Game game = new Game(9, 5, false);
		Scanner input = new Scanner(System.in);

		while (true)
		{
			if (game.isTie())
			{
				System.out.println("******* IS TIE ********");
				break;
			}
			else if (game.whiteWins())
			{
				System.out.println("***** WHITE WINS ******");
				break;
			}
			else if (game.blackWins())
			{
				System.out.println("***** BLACK WINS ******");
				break;
			}
			
			System.out.print("*** legal moves: ");
			List<Point> available = game.getClickable();
			for(Point pt : available) {
				System.out.printf("(%d, %d) ", pt.getX(), pt.getY());
			}
			System.out.println();
			
			System.out.print(">>> x: ");
			int x = input.nextInt();
			System.out.print(">>> y: ");
			int y = input.nextInt();
			System.out.println();
			
			Point point = new Point(x, y);
			
			if (! game.update(point))
			{
				System.out.println("!!! INVALID INPUT");
				System.out.println();
			}
		}
		
		System.out.println();
		System.out.print(game.getBoard());
		input.close();
	}

	public Game(int width, int height, boolean hasAiPlayer)
	{
		moves = 0;
		maxMoves = 10 * width;
		player = Board.WHITE;
		this.hasAiPlayer = hasAiPlayer;

		isClickable = new ArrayList<Point>();
		board = new Board(width, height);
		setupNextMove();
	}

	public Game(Game game)
	{

	}

	public List<Point> getClickable()
	{
		return isClickable;
	}

	public Board getBoard()
	{
		return board;
	}

//	public List<Point> getPath()
//	{
//		return move.getPath();
//	}

	public boolean isTie()
	{
		return moves == maxMoves;
	}

	public boolean whiteWins()
	{
		return board.getWhite() > 0 && board.getBlack() == 0;
	}

	public boolean blackWins()
	{
		return board.getWhite() == 0 && board.getBlack() > 0;
	}

	// return true for successful update, false otherwise
	public boolean update(Point point)
	{
		switch (state)
		{
		case NEED_CAPTURE_START:
			if (! isClickable.contains(point))
			{
				// check for sacrifice move
				if (board.getPoint(point) == player)
				{
					// add sacrifice to board
					sacrifice(point);
					return true;
				}
				// invalid point
				return false;
			}
			// get capture points
			from = point;
			getCaptureDestPoints(move, from);
			state = NEED_CAPTURE_DEST;
			return true;
		case NEED_CAPTURE_DEST:
			if (! isClickable.contains(point))
			{
				// check for sacrifice move
				if (from.equals(point))
				{
					// add sacrifice to board
					sacrifice(point);
					return true;
				}
				// invalid point
				return false;
			}
			// update the board
			to = point;
			delta = new Delta(from, to);
			if (move.isValidApproach(from, to, delta))
			{
				if (move.isValidWithdraw(from, to, delta))
				{
					// approach or withdraw?
					isClickable.clear();
					isClickable.add(to.getApproach(delta));
					isClickable.add(from.getWithdraw(delta));
					state = NEED_CAPTURE_RESOLVE;
					return true;
				}
				else
				{
					// approach
					capture(true);
					return true;
				}
			}
			// withdraw
			capture(false);
			return true;
		case NEED_CAPTURE_RESOLVE:
			if (! isClickable.contains(point))
			{
				// invalid point
				return false;
			}
			// resolve approach or withdraw capture
			capture(point.equals(isClickable.get(0)));
			return true;
		case NEED_PAIKA_START:
			if (! isClickable.contains(point))
			{
				// invalid point
				return false;
			}
			// get valid paika moves
			from = point;
			getPaikaDestPoints(from);
			state = NEED_PAIKA_DEST;
			return true;
		case NEED_PAIKA_DEST:
			if (! isClickable.contains(point))
			{
				// check for sacrifice move
				if (from.equals(point))
				{
					// add sacrifice to board
					sacrifice(point);
					return true;
				}
				// invalid point
				return false;
			}
			// paika move
			to = point;
			board.setPoint(from, Board.EMPTY);
			board.setPoint(to, player);
			System.out.println(board);
			setupNextTurn();
			return true;
		}

		return false;
	}

	// capture pieces and update game state
	private void capture(boolean isApproach)
	{
		move.capture(from, to, delta, isApproach);
		from = to;
		
		System.out.println(board);

		if (getCaptureDestPoints(move, from))
		{
			state = NEED_CAPTURE_DEST;
		}
		else
		{
			setupNextTurn();
		}
	}

	private void setupNextTurn()
	{
		player ^= 1;
		moves += 1;
		setupNextMove();
	}

	private void setupNextMove()
	{
		// gray pieces removed at each turn
		deleteSacrifices();
		move = new Move(board);

		// available captures?
		if (getCaptureStartPoints(move))
		{
			state = NEED_CAPTURE_START;
		}
		else
		{
			getPaikaStartPoints();
			state = NEED_PAIKA_START;
		}

		// check for end game condition
		if (!(isTie() || whiteWins() || blackWins()))
		{
			if (player == Board.WHITE)
			{
				System.out.println("******** WHITE ********");
			}
			else
			{
				System.out.println("******** BLACK ********");
			}
			System.out.println();
			System.out.println(board);
	
			// ai player's turn
			if (hasAiPlayer)
			{
				if (player == 1)
				{
					aiPlayer();
				}
			}
		}
	}

	// get valid capture move starting points
	private boolean getCaptureStartPoints(Move move)
	{
		isClickable.clear();

		for (Point src : board)
		{
			if (board.getPoint(src) == player)
			{
				getCaptureStartPointsInnerLoop(move, src);
			}
		}

		return ! isClickable.isEmpty();
	}

	// prevents valid starting point from be added to isClickable multiple times
	private void getCaptureStartPointsInnerLoop(Move move, Point src)
	{
		// prevents unnecessary checks of diagonal moves
		int nLists = board.isDiagonalPoint(src) ? 2 : 1;

		for (int i = 0; i < nLists; i++)
		{
			for (Delta delta : deltaList.get(i))
			{
				Point dst = src.getApproach(delta);

				if (move.isValidMove(src, dst, delta))
				{
					isClickable.add(src);
					return;
				}
			}
		}
	}

	// get valid capture move destinations
	private boolean getCaptureDestPoints(Move move, Point src)
	{
		isClickable.clear();

		// prevents unnecessary checks of diagonal moves
		int nLists = board.isDiagonalPoint(src) ? 2 : 1;

		for (int i = 0; i < nLists; i++)
		{
			for (Delta delta : deltaList.get(i))
			{
				Point dst = src.getApproach(delta);

				if (move.isValidMove(src, dst, delta))
				{
					isClickable.add(dst);
				}
			}
		}
		
		return ! isClickable.isEmpty();
	}

	// get valid paika move starting points
	private void getPaikaStartPoints()
	{
		isClickable.clear();

		for (Point src : board)
		{
			if (board.getPoint(src) == player)
			{
				getPaikaStartPointsInnerLoop(move, src);
			}
		}
	}

	// prevents valid paika move points from being added multiple times
	private void getPaikaStartPointsInnerLoop(Move move, Point src)
	{
		// prevents unnecessary checks of diagonal moves
		int nLists = board.isDiagonalPoint(src) ? 2 : 1;

		for (int i = 0; i < nLists; i++)
		{
			for (Delta delta : deltaList.get(i))
			{
				Point dst = src.getApproach(delta);

				if (board.isValidPoint(dst) && board.getPoint(dst) == Board.EMPTY)
				{
					isClickable.add(src);
					return;
				}
			}
		}
	}
	
	// get valid paika move destinations
	private void getPaikaDestPoints(Point src)
	{
		isClickable.clear();

		// prevents unnecessary checks of diagonal moves
		int nLists = board.isDiagonalPoint(src) ? 2 : 1;

		for (int i = 0; i < nLists; i++)
		{
			for (Delta delta : deltaList.get(i))
			{
				Point dst = src.getApproach(delta);

				if (board.isValidPoint(dst)
				&& board.getPoint(dst) == Board.EMPTY)
				{
					isClickable.add(dst);
				}
			}
		}
	}

	// add sacrifice piece for current player
	private void sacrifice(Point point)
	{
		int type;
		if (player == Board.WHITE)
		{
			type = Board.WHITE_GRAY;
		}
		else
		{
			type = Board.BLACK_GRAY;
		}
		board.setPoint(point, type);
		System.out.println(board);
		setupNextTurn();
	}

	// delete sacrificed pieces at the turn
	private void deleteSacrifices()
	{
		for (Point point : board)
		{
			int type = board.getPoint(point);

			// two types of gray pieces
			// removed type must match current player
			if ((type == Board.WHITE_GRAY && player == Board.WHITE)
			|| (type == Board.BLACK_GRAY && player == Board.BLACK))
			{
				board.setPoint(point, Board.EMPTY);
				return;
			}
		}
	}

	void aiPlayer()
	{
		if (state == NEED_CAPTURE_START || state == NEED_PAIKA_START)
		{
			Point point = new Point(isClickable.get(0));
			update(point);
		}
		
		while (state == NEED_CAPTURE_DEST || state == NEED_PAIKA_DEST)
		{
			Point point = new Point(isClickable.get(0));
			update(point);
			
			if (state == NEED_CAPTURE_RESOLVE)
			{
				point = new Point(isClickable.get(0));
				update(point);
			}
		}
	}

}
