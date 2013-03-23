package team01;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PointTest {

	private Point point1;
	private Point point2;
	private Point point3;
	
	@Before
	public void setUp() throws Exception {
		point1 = new Point(1, 2);
		point2 = new Point(1, 2);
		point3 = new Point(0, 0);
	}

	@Test
	public void pointEqualsTest() {
		assertTrue(point1.equals(point1));
		assertTrue(point1.equals(point2));
		assertTrue(point2.equals(point1));
		assertFalse(point1.equals(point3));
	}

}
