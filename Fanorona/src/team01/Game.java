package team01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

//	private static List<Delta> diagonal = new ArrayList<Delta>();
//	private static List<Delta> orthogonal = new ArrayList<Delta>();
	
	private List<Point> isClickable;
	private Board board;
	private int player;

	// TODO: remove Scanner in favor of more general input source
	private Scanner input;

	public Game()
	{
		isClickable = new ArrayList<Point>();
		board = new Board(9, 5);
		player = Board.WHITE;
		
		// TODO: remove Scanner
		input = new Scanner(System.in);
	}
	
	public static void main(String[] args)
	{
		// TODO: should initialize delta lists elsewhere
//		setupDeltas();
		
		Game game = new Game();
		game.play();
	}
	
	public void play()
	{
		final int MAX_MOVES = board.getWidth() * 10;
		
		int moves;

		for (moves = 0; moves < MAX_MOVES; moves++)
		{
			Move move = new Move(board);
		
			if (player == Board.WHITE)
			{
				System.out.println("WHITE make your move!");
			}
			else
			{
				System.out.println("BLACK make your move!");
			}
			
			if (sweepBoardForCaptures(move))
			{
				
				System.out.print("move from? ");
				Point from = getUserInput();
				
				// move the selected piece around the board
				while (sweepNeighborsForCaptures(move, from))
				{
					System.out.print("move to? ");
					Point to = getUserInput();

					boolean isApproach = false;
					boolean isAmbiguous = false;
					Delta delta = new Delta(from, to);
					
					if (move.isValidApproach(from, to, delta))
					{
						if (move.isValidWithdraw(from, to, delta))
						{
							isAmbiguous = true;
						}
						else
						{
							isApproach = true;
						}
					}
					
					if (isAmbiguous)
					{
						if (! isClickable.isEmpty())
							isClickable.clear();
						
						isClickable.add(to.getApproach(delta));
						isClickable.add(from.getWithdraw(delta));
						
						System.out.print("capture? ");
						Point capture = getUserInput();
						isApproach = capture.equals(isClickable.get(0));
					}
					
					move.capture(from, to, delta, isApproach);
					
					from = to;
				}
			}
			else // board has no captures
			{
				for (Point from : board)
				{
					if (board.getPoint(from) == player)
					{
						isClickable.add(from);
					}
				}
				
				System.out.print("free move: ");
				Point from = getUserInput();
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
				
				System.out.print("move to: ");
				Point to = getUserInput();
				board.setPoint(from, Board.EMPTY);
				board.setPoint(to, player);
			}
			
			if (gameOver())
			{
				System.out.println("game over!");
				System.out.println(board);
				break;
			}
			//else if ( aiPlayer )
			//{
			//AI player updates board here 
			//}
			else
			{
				player = player ^ 1;
			}
		}
		
		// TODO: remove Scanner in favor of more general input source
		input.close();
	}
	public Board getBoard()
	{
		return board;
	}
	private boolean gameOver()
	{
		return board.getBlack() == 0 || board.getWhite() == 0;
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
	
	private Point getUserInput()
	{
		int x = -1;
		int y = -1;
		
		Point point;
		
		// TODO: remove Scanner in favor of more general input source
		while (true)
		{
			for (Point p : isClickable)
			{
				System.out.print(p + " ");
			}
			System.out.println();
			
			System.out.print(board);
			System.out.print("enter x: ");
			x = input.nextInt();
			System.out.print("enter y: ");
			y = input.nextInt();
			
			point = new Point(x, y);
			
			if (isClickable.contains(point))
			{
				break;
			}
			
			System.out.print("invalid input! choose one of ");
		}
		
		return point;
	}
	
//	private static void setupDeltas() {
//		// initialize valid delta lists at startup
//		for (int dy = Delta.MIN_DELTA; dy <= Delta.MAX_DELTA; dy++)
//		{
//			for (int dx = Delta.MIN_DELTA; dx <= Delta.MAX_DELTA; dx++)
//			{
//				Delta delta = new Delta(dx, dy);
//				
//				if (delta.isDiagonal())
//				{
//					diagonal.add(delta);
//				}
//				else
//				{
//					// exclude (0, 0)
//					if (dx != 0 || dy != 0)
//					{
//						orthogonal.add(delta);
//					}
//				}
//			}
//		}
//	}

}
