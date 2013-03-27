package team01;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	private Board board;
	private Move move;
	
	@Before
	public void setUp() throws Exception {
		board = new Board(9, 5);
		move = new Move(board);
	}
	
	@Test public void testSetPoint() {
		Board copy = new Board(board);
		
		assertEquals(board.getEmpty(), 1);
		assertEquals(copy.getEmpty(), 1);
		
		System.out.println(board);
		
		copy.setPoint(new Point(1, 1), Board.EMPTY);
		
		System.out.println(copy);
		assertEquals(copy.getEmpty(), 2);
	}
/*
	@Test
	public void availableMovesNewBoardTest() {
		int player = Board.WHITE;
		
		assertEquals(board.numWhite(), 22);
		assertEquals(board.numBlack(), 22);
		assertEquals(board.numEmpty(), 1);
		
		board.setPosition(new Point(2, 2), Board.EMPTY);
		board.setPosition(new Point(3, 2), Board.EMPTY);

		assertEquals(board.numWhite(), 21);
		assertEquals(board.numBlack(), 21);
		assertEquals(board.numEmpty(), 3);
		
		System.out.println("available moves:");
		System.out.println(board);
		
		int count = 0;
		
		for (Point from : board)
		{
			if (board.getPosition(from) == player)
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
		
		assertEquals(count, 8);
		
		System.out.println();
	}
	
	@Test
	public void captureApproachUpTest() {
		// make sure moves/captures update the board properly
		assertEquals(board.numWhite(), 22);
		assertEquals(board.numBlack(), 22);
		assertEquals(board.numEmpty(), 1);
		
		Point from = new Point(4, 3);
		Point to = new Point(4, 2);

		System.out.println("before:");
		System.out.println(board);
		
		assertTrue(move.isValidMove(from, to));
		assertFalse(move.capture(from, to, true));
		
		assertEquals(board.numWhite(), 22);
		assertEquals(board.numBlack(), 20);
		assertEquals(board.numEmpty(), 3);

		System.out.println("after:");
		System.out.println(board);
	}
	
	@Test
	public void captureApproachDiagonalTest() {
		// make sure moves/captures update the board properly
		assertEquals(board.numWhite(), 22);
		assertEquals(board.numBlack(), 22);
		assertEquals(board.numEmpty(), 1);
		
		Point from = new Point(3, 3);
		Point to = new Point(4, 2);

		System.out.println("before:");
		System.out.println(board);
		
		assertTrue(move.isValidMove(from, to));
		assertFalse(move.capture(from, to, true));
		
		assertEquals(board.numWhite(), 22);
		assertEquals(board.numBlack(), 20);
		assertEquals(board.numEmpty(), 3);

		System.out.println("after:");
		System.out.println(board);
	}

	@Test
	public void captureApproachSideTest() {
		// make sure moves/captures update the board properly
		assertEquals(board.numWhite(), 22);
		assertEquals(board.numBlack(), 22);
		assertEquals(board.numEmpty(), 1);
		
		Point from = new Point(3, 2);
		Point to = new Point(4, 2);

		System.out.println("before:");
		System.out.println(board);
		
		assertTrue(move.isValidMove(from, to));
		assertFalse(move.capture(from, to, true));
		
		assertEquals(board.numWhite(), 22);
		assertEquals(board.numBlack(), 21);
		assertEquals(board.numEmpty(), 2);

		System.out.println("after:");
		System.out.println(board);
	}
	*/
//	@Test
//	public void captureWithdrawSideTest() {
//		// make sure moves/captures update the board properly
//		assertEquals(board.numWhite(), 22);
//		assertEquals(board.numBlack(), 22);
//		assertEquals(board.numEmpty(), 1);
//		
//		Point from = new Point(3, 2);
//		Point to = new Point(4, 2);
//
//		System.out.println("before:");
//		System.out.println(board);
//		
//		assertTrue(move.isValidMove(from, to));
//		assertFalse(move.capture(from, to, false));
//		
//		assertEquals(board.numWhite(), 22);
//		assertEquals(board.numBlack(), 21);
//		assertEquals(board.numEmpty(), 2);
//
//		System.out.println("after:");
//		System.out.println(board);
//	}

}