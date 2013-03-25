package team01;
/*
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoveTest {

	private Board board;
	private Move move;
	
	@Before
	public void setUp() throws Exception {
		board = new Board(9, 5);
		move = new Move(board);
		
		board.setPosition(new Point(5, 1), Board.EMPTY);
		board.setPosition(new Point(3, 2), Board.EMPTY);
		board.setPosition(new Point(4, 2), Board.WHITE);
		board.setPosition(new Point(5, 3), Board.EMPTY);
		board.setPosition(new Point(5, 4), Board.EMPTY);
	}

	@Test
	public void availableMovesWhite() {
		assertEquals(board.numEmpty(), 4);
		
		System.out.println("available moves white:");
		System.out.println(board);
		
		int count = 0;
		
		for (Point from : board)
		{
			if (board.getPosition(from) == Board.WHITE)
			{
				// TODO: possibly add a delta iterator?
				for (int dx = Delta.MIN_DELTA; dx <= Delta.MAX_DELTA; dx++)
				{
					for (int dy = Delta.MIN_DELTA; dy <= Delta.MAX_DELTA; dy++)
					{
						Point to = from.getApproach(dx, dy);

						if (move.isValidMove(from, to))
						{
							++ count;
							System.out.printf("%s -> %s\n", from, to);
						}
					}
				}
			}
		}
		
		assertEquals(count, 6);
		
		System.out.println();
	}

	@Test
	public void availableMovesBlack() {
		assertEquals(board.numEmpty(), 4);
		
		System.out.println("available moves black:");
		System.out.println(board);
		
		int count = 0;
		
		for (Point from : board)
		{
			if (board.getPosition(from) == Board.BLACK)
			{
				// TODO: possibly add a delta iterator?
				for (int dx = Delta.MIN_DELTA; dx <= Delta.MAX_DELTA; dx++)
				{
					for (int dy = Delta.MIN_DELTA; dy <= Delta.MAX_DELTA; dy++)
					{
						Point to = from.getApproach(dx, dy);

						if (move.isValidMove(from, to))
						{
							++ count;
							System.out.printf("%s -> %s\n", from, to);
						}
					}
				}
			}
		}
		
		assertEquals(count, 4);
		
		System.out.println();
	}
	
	@Test
	public void multiMoveWhite() {
		assertEquals(board.numEmpty(), 4);
		
		System.out.println("before multi move:");
		System.out.println(board);
		
		Point from = new Point(6, 2);
		Point to = new Point(5, 3);
		
		assertTrue(move.isValidMove(from, to));
		assertTrue(move.capture(from, to, false));
		assertEquals(board.numEmpty(), 6);
		
		System.out.println("after move 1:");
		System.out.println(board);
		
		from = to;
		to = new Point(5, 4);
		
		assertTrue(move.isValidMove(from, to));
		assertFalse(move.capture(from, to, false));
		assertEquals(board.numEmpty(), 7);
		
		System.out.println("after move 2:");
		System.out.println(board);
		
		assertEquals(board.numBlack() + board.numWhite() + board.numEmpty(), 45);
	}
	
}
*/
