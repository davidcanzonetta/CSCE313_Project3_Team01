package team01;

import java.util.*;

public class Board {

	static final int BOARD_SIZE = 9 * 5;
	
	// board states
	static final int EMPTY = 0;
	static final int WHITE = 1;
	static final int BLACK = 2;
	
	private List <Integer> board;
	
	public Board() {
		board = new ArrayList <Integer>(BOARD_SIZE);
	}
	
	boolean isEmpty(int pos) {
		return board.get(pos) == EMPTY;
	}
	
	boolean isWhite(int pos) {
		return board.get(pos) == WHITE;
	}
	
	boolean isBlack(int pos) {
		return board.get(pos) == BLACK;
	}
	
	void setPosition(int pos, int state) {
		board.set(pos, state);
	}
}
