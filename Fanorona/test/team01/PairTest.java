package team01;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PairTest {

private List<Pair> list;
	
	@Before
	public void setUp() throws Exception {
		list = new ArrayList<Pair>();
	}

	@Test
	public void testList() {
		for (int x = 1; x <= 4; x++) {
			for (int y = 1; y <= 4; y++) {
				list.add(new Pair(new Point(x, y), new Point(x+1, y-1)));
			}
		}
		
		assertEquals(list.size(), 16);
		
//		for (Pair pair : list) {
//			Point from = pair.from;
//			Point to = pair.to;
//			System.out.printf("(%d, %d) -> (%d, %d)\n", from.x, from.y, to.x, to.y);
//		}
	}

}
