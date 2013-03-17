package team01;

import java.util.*;

public class LegalMove {

	// There are 8 sets (1 for each of the 8 possible directions). If a board position
	// belongs to a given set we know it is legal to move a game piece in that direction.
	// For example, if we want to know if board position 0 can move left then we would
	// call LegalMove.hasNeighbor(0, LegalMove.LEFT)

	private static List<Set<Integer>> neighbor;

	public static final int UP_LEFT = 0;
	public static final int UP = 1;
	public static final int UP_RIGHT = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final int DOWN_LEFT = 5;
	public static final int DOWN = 6;
	public static final int DOWN_RIGHT = 7;
	
	static {
		neighbor = new ArrayList <Set<Integer>> (8);
		for (int i = 0; i < 8; i++) {
			neighbor.add(i, new HashSet<Integer>());
		}

		// TODO: initialize the sets
	}


	public static boolean hasNeighbor (int pos, int dir)
	{
		return neighbor.get(dir).contains(pos);
	}
	
}
