package team01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

	private List<Point> isClickable;
	private Board board;
	private Move move;
	
	Point from;
	Point to;
	Delta delta;

	private int player;
	private int state;
	private int moves;
	private int maxMoves;

	private static final int NEED_CAPTURE_FROM = 0;
	private static final int NEED_CAPTURE_TO = 1;
	private static final int NEED_CAPTURE_RESOLVE = 2;
	private static final int NEED_PAIKA_FROM = 3;
	private static final int NEED_PAIKA_TO = 4;
	private static List<List<Delta>> deltaList = new ArrayList<List<Delta>>();
	
	static {
		deltaList.add(Delta.orthogonal);
		deltaList.add(Delta.diagonal);
	}
	
	public static void main(String[] args) {
		System.out.println("** If game piece can capture enter its position twice to **");
		System.out.println("** sacrifice it.  If the game piece does not have a      **");
		System.out.println("** capture it only needs to be entered once, a sacrifice **");
		System.out.println("** move is done automatically.                           **");
		
		Game game = new Game(9, 5);
		Scanner input = new Scanner(System.in);

		while (true)
		{
			if (game.isTie())
			{
				System.out.println("**** is tie");
				break;
			}
			else if (game.whiteWins())
			{
				System.out.println("**** white wins");
				break;
			}
			else if (game.blackWins())
			{
				System.out.println("**** black wins");
				break;
			}
			
			System.out.print("** legal captures: ");
			List<Point> available = game.getClickable();
			for(Point pt : available) {
				System.out.print(pt + " ");
			}
			System.out.println();
			
			System.out.print(">> x: ");
			int x = input.nextInt();
			System.out.print(">> y: ");
			int y = input.nextInt();
			
			Point point = new Point(x, y);
			
			if (! game.update(point))
			{
				System.out.println("invalid input");
			}
		}
		
		input.close();
	}

	public Game(int width, int height)
	{
		moves = 0;
		maxMoves = 10 * width;
		player = Board.WHITE;

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

	private boolean update(Point point)
	{
		switch (state)
		{
		case NEED_CAPTURE_FROM:
			if (! isClickable.contains(point))
			{
				// check for sacrifice move
				if (board.getPoint(point) == player)
				{
					// add sacrifice to board
					board.setPoint(point, (player == Board.WHITE) ? Board.WHITE_GRAY : Board.BLACK_GRAY);
					System.out.println(board);
					setupNextTurn();
					return true;
				}
				// invalid point
				return false;
			}
			// get capture points
			from = point;
			getCaptureToPoints(move, from);
			state = NEED_CAPTURE_TO;
			return true;
		case NEED_CAPTURE_TO:
			if (! isClickable.contains(point))
			{
				// check for sacrifice move
				if (from.equals(point))
				{
					// add sacrifice to board
					board.setPoint(point, (player == Board.WHITE) ? Board.WHITE_GRAY : Board.BLACK_GRAY);
					System.out.println(board);
					setupNextTurn();
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
		case NEED_PAIKA_FROM:
			if (! isClickable.contains(point))
			{
				// invalid point
				return false;
			}
			// get valid paika moves
			from = point;
			getPaikaToPoints(from);
			state = NEED_PAIKA_TO;
			return true;
		case NEED_PAIKA_TO:
			if (! isClickable.contains(point))
			{
				// check for sacrifice move
				if (from.equals(point))
				{
					// add sacrifice to board
					board.setPoint(point, (player == Board.WHITE) ? Board.WHITE_GRAY : Board.BLACK_GRAY);
					System.out.println(board);
					setupNextTurn();
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

	private void capture(boolean isApproach)
	{
		move.capture(from, to, delta, isApproach);
		from = to;
		
		System.out.println(board);

		if (getCaptureToPoints(move, from))
		{
			state = NEED_CAPTURE_TO;
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
		deleteSacrifices();
		move = new Move(board);

		if (getCaptureFromPoints(move))
		{
			state = NEED_CAPTURE_FROM;
		}
		else
		{
			getPaikaFromPoints();
			state = NEED_PAIKA_FROM;
		}

		if (player == Board.WHITE)
		{
			System.out.println("******** WHITE ********");
			System.out.println(board);
		}
		else
		{
			System.out.println("******** BLACK ********");
		}

		if (player == 1 && !(isTie() || whiteWins() || blackWins()))
		{
			aiPlayer();
		}
	}

	private boolean getCaptureFromPoints(Move move)
	{
		isClickable.clear();

		for (Point src : board)
		{
			if (board.getPoint(src) == player)
			{
				getCaptureFromPointsInnerLoop(move, src);
			}
		}

		return ! isClickable.isEmpty();
	}

	private void getCaptureFromPointsInnerLoop(Move move, Point src)
	{
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

	private boolean getCaptureToPoints(Move move, Point src)
	{
		isClickable.clear();

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

	private void getPaikaFromPoints()
	{
		isClickable.clear();

		for (Point src : board)
		{
			if (board.getPoint(src) == player)
			{
				isClickable.add(src);
			}
		}
	}

	private void getPaikaToPoints(Point src)
	{
		isClickable.clear();

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

	private void deleteSacrifices()
	{
		for (Point point : board)
		{
			int type = board.getPoint(point);

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
		if (state == NEED_CAPTURE_FROM || state == NEED_PAIKA_FROM)
		{
			Point point = new Point(isClickable.get(0));
			update(point);
		}
		
		while (state == NEED_CAPTURE_TO || state == NEED_PAIKA_TO)
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