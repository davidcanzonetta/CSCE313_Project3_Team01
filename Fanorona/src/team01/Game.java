package team01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

	public List<Point> isClickable;

	public Board board;
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
	private static final int NEED_FREE_FROM = 3;
	private static final int NEED_FREE_TO = 4;
	
	public static void main(String[] args) {
		Game game = new Game(9, 5);
		Board board = game.getBoard();
		Scanner input = new Scanner(System.in);
		
		System.out.print(board);
		
		while (true) {
			if (game.isTie()) {
				System.out.println("is tie");
				break;
			} else if (game.whiteWins()) {
				System.out.println("white wins");
				break;
			} else if (game.blackWins()) {
				System.out.println("black wins");
				break;
			}
			
			System.out.print("valid captures: ");
			List<Point> available = game.getClickable();
			for(Point p : available) {
				System.out.print(p + " ");
			}
			System.out.println();
			System.out.print("x: ");
			int x = input.nextInt();
			System.out.print("y: ");
			int y = input.nextInt();
			Point point = new Point(x, y);
			if (! game.update(point))
				System.out.println("invalid input");
			
		}
		
//		System.out.print(board);
		
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

	public boolean update(Point point)
	{
		boolean result = false;
		
		switch (state)
		{
		case NEED_CAPTURE_FROM:
			System.out.println("start at " + point);
			return updateCaptureFrom(point);
		case NEED_CAPTURE_TO:
			System.out.println("-> " + point);
			return updateCaptureTo(point);
		case NEED_CAPTURE_RESOLVE:
			System.out.println("capture " + point);
			return updateCaptureResolve(point);
		case NEED_FREE_FROM:
			System.out.println("start at " + point);
			return updateFreeFrom(point);
		case NEED_FREE_TO:
			System.out.println("-> " + point);
			return updateFreeTo(point);
		}

//		System.out.print (board);
		
		// should not be reached if end game conditions
		// are checked properly
		return result;
	}

	private boolean updateCaptureFrom(Point point)
	{
		if (! isClickable.contains(point))
		{
			// is it a sacrifice move?
			if (board.getPoint(point) != player)
			{
				return false;
			}
		}

		from = point;
		sweepNeighborsForCaptures(move, from);
		isClickable.add(from);	// for sacrifice moves
		state = NEED_CAPTURE_TO;

		return true;
	}

	private boolean updateCaptureTo(Point point)
	{
		if (! isClickable.contains(point))
		{
			return false;
		}

		to = point;
		delta = new Delta(from, to);

		// handle sacrifice moves
		if (from.equals(to))
		{
			if (player == Board.WHITE)
			{
				board.setPoint(from, Board.WHITE_GRAY);
			}
			else
			{
				board.setPoint(from, Board.BLACK_GRAY);
			}
			System.out.print(board);
			setupNextTurn();
			return true;
		}
		
		boolean isApproach = false;

		if (move.isValidApproach(from, to, delta))
		{
			if (move.isValidWithdraw(from, to, delta))
			{
				if (! isClickable.isEmpty())
				{
					isClickable.clear();
				}

				isClickable.add(to.getApproach(delta));
				isClickable.add(from.getWithdraw(delta));
				state = NEED_CAPTURE_RESOLVE;
				return true;
			}
			else
			{
				isApproach = true;
			}
		}

		capture(isApproach);
		return true;
	}

	private boolean updateCaptureResolve(Point capture)
	{
		if (! (isClickable.size() == 2 && isClickable.contains(capture)))
		{
			return false;
		}

		boolean isApproach = capture.equals(isClickable.get(0));
		capture(isApproach);
		return true;
	}

	private boolean updateFreeFrom(Point point)
	{
		if (! isClickable.contains(point))
		{
			return false;
		}

		from = point;

		isClickable.clear();

		// valid destinations for selected piece (captures not required)
		for (Delta delta : Delta.orthogonal)
		{
			Point to = from.getApproach(delta);

			if (board.isValidPoint(to)
				&& board.getPoint(to) == Board.EMPTY)
			{
				isClickable.add(to);
			}
		}
		if (board.isDiagonalPoint(from))
		{
			for (Delta delta : Delta.diagonal)
			{
				Point to = from.getApproach(delta);

				if (board.isValidPoint(to)
					&& board.getPoint(to) == Board.EMPTY)
				{
					isClickable.add(to);
				}
			}
		}

		isClickable.add(from);	// for sacrifice moves
		state = NEED_FREE_TO;
		return true;
	}

	private boolean updateFreeTo(Point point)
	{
		if (! isClickable.contains(point))
		{
			return false;
		}

		to = point;
		
		// handle sacrifice moves
		if (from.equals(to))
		{
			if (player == Board.WHITE)
			{
				board.setPoint(from, Board.WHITE_GRAY);
			}
			else
			{
				board.setPoint(from, Board.BLACK_GRAY);
			}
		}
		else
		{
			board.setPoint(from, Board.EMPTY);
			board.setPoint(to, player);
		}
		System.out.print(board);
		setupNextTurn();
		return true;
	}

	private boolean sweepBoardForCaptures(Move move)
	{
		if (! isClickable.isEmpty())
		{
			isClickable.clear();
		}

		for (Point from : board)
		{
			updateClickableSources(move, from);
		}

		return ! isClickable.isEmpty();
	}
	
	private boolean sweepNeighborsForCaptures(Move move, Point point)
	{
		if (! isClickable.isEmpty())
		{
			isClickable.clear();
		}
		
		updateClickableDestinations(move, point);
		
		return ! isClickable.isEmpty();
	}
	
	private void updateClickableSources(Move move, Point point)
	{
		if (board.getPoint(point) == player)
		{
			// check for orthogonal captures
			if (! addClickableSource(move, point, true))
			{
				// point did not have orthogonal capture
				if (board.isDiagonalPoint(point))
				{
					// check for diagonal captures
					addClickableSource(move, point, false);
				}
			}
		}
	}

	private void updateClickableDestinations(Move move, Point point)
	{		
		// add orthogonal destinations
		addClickableDestination(move, point, true);

		// try to add diagonal destinations
		if (board.isDiagonalPoint(point))
		{
			addClickableDestination(move, point, false);
		}
	}
	
	private boolean addClickableSource(Move move, Point from, boolean isOrtho)
	{
		List<Delta> deltas;
		
		if (isOrtho)
		{
			deltas = Delta.orthogonal;
		}
		else
		{
			deltas = Delta.diagonal;
		}
		
		for (Delta delta : deltas)
		{
			Point to = from.getApproach(delta);
			
			if (move.isValidMove(from, to, delta))
			{
				isClickable.add(from);
				return true;
			}
		}
		
		return false;
	}
	
	private void addClickableDestination(Move move, Point from, boolean isOrtho)
	{
		List<Delta> deltas;
		
		if (isOrtho)
		{
			deltas = Delta.orthogonal;
		}
		else
		{
			deltas = Delta.diagonal;
		}
		
		for (Delta delta : deltas)
		{
			Point to = from.getApproach(delta);
			
			if (move.isValidMove(from, to, delta))
			{
				isClickable.add(to);
			}
		}
	}

	private void capture(boolean isApproach)
	{
		move.capture(from, to, delta, isApproach);
		from = to;

		System.out.print(board);
		if (sweepNeighborsForCaptures(move, from))
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
		player = player ^ 1;
		moves = moves + 1;
		setupNextMove();
	}

	private void setupNextMove()
	{
		if (player == 0)
			System.out.println("*** WHITE ***");
		else
			System.out.println("*** BLACK ***");
		
		move = new Move(board);

		// remove sacrifice pieces
		for (Point point : board)
		{
			if (board.getPoint(point) == Board.WHITE_GRAY && player == Board.WHITE)
			{
				board.setPoint(point, Board.EMPTY);
			}
			else if (board.getPoint(point) == Board.BLACK_GRAY && player == Board.BLACK)
			{
				board.setPoint(point, Board.EMPTY);
			}
		}
		
		if (sweepBoardForCaptures(move))
		{
			state = NEED_CAPTURE_FROM;
		}
		else
		{
			for (Point from : board)
			{
				if (board.getPoint(from) == player)
				{
					isClickable.add(from);
				}
			}
			state = NEED_FREE_FROM;
		}
		
		if (player == 1 && !(this.isTie() || this.whiteWins() || this.blackWins()))
		{
			aiPlayer();
		}
	}
	
	void aiPlayer()
	{
		if (state == NEED_CAPTURE_FROM || state == NEED_FREE_FROM)
		{
			Point point = new Point(isClickable.get(0));
			update(point);
		}
		
		while (state == NEED_CAPTURE_TO || state == NEED_FREE_TO)
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
