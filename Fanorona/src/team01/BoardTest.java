package team01;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {

	@Test
	public void testConstructor() {
		Board board = new Board();	// test the default board
		
		assertTrue (board.isEmpty(22));
		assertEquals (board.numBlack(), 22);
		assertEquals (board.numWhite(), 22);
		assertEquals (board.numEmpty(), 1);
		
		System.out.println (board);
	}

	@Test
	public void testSetPosition() {
		Board board = new Board();
		int pos = 6;	// arbitrary board position
		
		assertTrue (board.isBlack(pos));
		board.setPosition(pos, Board.EMPTY);
		assertTrue (board.isEmpty(pos));
		
		System.out.println(board);
	}
}
