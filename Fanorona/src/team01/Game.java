package team01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

	public List<Point> isClickable;

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
	private static final int NEED_FREE_FROM = 3;
	private static final int NEED_FREE_TO = 4;
	
	public static void main(String[] args) {
		Game game = new Game(9, 5);
		Board board = game.getBoard();
		Scanner input = new Scanner(System.in);
		
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
			
			System.out.print(board);
			System.out.print("legal points: ");
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
		System.out.println(board);
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
		this.moves = game.moves;
		this.maxMoves = game.maxMoves;
		this.player = game.player ^ 1;
		
		this.isClickable = new ArrayList<Point>();
		this.board = new Board(game.board);
		setupNextMove();
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
		switch (state)
		{
		case NEED_CAPTURE_FROM:
			System.out.println("attempting capture from " + point);
			return updateCaptureFrom(point);
		case NEED_CAPTURE_TO:
			System.out.println("attempting capture to " + point);
			return updateCaptureTo(point);
		case NEED_CAPTURE_RESOLVE:
			System.out.println("resolving " + point);
			return updateCaptureResolve(point);
		case NEED_FREE_FROM:
			System.out.println("moving from " + point);
			return updateFreeFrom(point);
		case NEED_FREE_TO:
			System.out.println("moving to " + point);
			return updateFreeTo(point);
		}

		// should not be reached if end game conditions
		// are checked properly
		return false;
	}

	private boolean updateCaptureFrom(Point point)
	{
		if (! isClickable.contains(point))
		{
			return false;
		}

		from = point;
		sweepNeighborsForCaptures(move, from);
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
		board.setPoint(from, Board.EMPTY);
		board.setPoint(to, player);
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
			System.out.println("*****player: WHITE*****");
		else
			System.out.println("*****player: BLACK*****");
		
		move = new Move(board);

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
	}
}
