package team01;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {

	@Test
	public void testConstructor() {
		Board board = new Board(9, 5);	// test the default board
		
		assertTrue (board.isEmpty(4, 2));
		assertEquals (board.numBlack(), 22);
		assertEquals (board.numWhite(), 22);
		assertEquals (board.numEmpty(), 1);
		
		System.out.println (board);
	}

	@Test
	public void testSetPosition() {
		Board board = new Board(9, 5);
		int x = 3;	// arbitrary board position
		int y = 1;
		
		assertTrue (board.isBlack(x, y));
		board.setPosition(x, y, Board.EMPTY);
		assertTrue (board.isEmpty(x, y));
		
		System.out.println(board);
	}
	
	@Test
	public void testMove() {
		Board board = new Board(9, 5);
		Move move = new Move(board);
		
		int player = Board.WHITE;
		
		for (int y1 = 0; y1 < board.getHeight(); y1++)
		{
			for (int x1 = 0; x1 < board.getWidth(); x1++)
			{
				if (board.getPosition(x1, y1) == player)
				{
					for (int dx = -1; dx <= 1; dx++)
					{
						for (int dy = -1; dy <= 1; dy++)
						{
							if (move.isValidMove(x1, y1, x1+dx, y1+dy))
							{
								System.out.printf("(%d,%d) -> (%d,%d)\n", x1, y1, x1+dx, y1+dy);
							}
						}
					}
				}
			}
		}
		System.out.println();
	}
}
