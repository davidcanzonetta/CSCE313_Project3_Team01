package team01;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {

	@Test
	public void test() {
		Board board = new Board();
		assertTrue (board.isEmpty(22));
		System.out.println (board);
	}

}
