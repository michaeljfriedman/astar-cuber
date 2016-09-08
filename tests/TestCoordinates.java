import junit.framework.*;

public class TestCoordinates extends TestCase {

	// XCoordinate tests

	public void testXCoordinateValue() {
		int value = 1;
		XCoordinate x = new XCoordinate(value);
		assertEquals(value, x.value());
	}

	public void testXCoordinateCopy() {
		int value = 1;
		XCoordinate x1 = new XCoordinate(value);
		XCoordinate x2 = new XCoordinate(x1);
		assertTrue(x1.equals(x2));
	}

	// YCoordinate tests

	public void testYCoordinateValue() {
		int value = 1;
		YCoordinate y = new YCoordinate(value);
		assertEquals(value, y.value());
	}

	public void testYCoordinateCopy() {
		int value = 1;
		YCoordinate y1 = new YCoordinate(value);
		YCoordinate y2 = new YCoordinate(y1);
		assertTrue(y1.equals(y2));
	}

	// ZCoordinate tests

	public void testZCoordinateValue() {
		int value = 1;
		ZCoordinate z = new ZCoordinate(value);
		assertEquals(value, z.value());
	}

	public void testZCoordinateCopy() {
		int value = 1;
		ZCoordinate z1 = new ZCoordinate(value);
		ZCoordinate z2 = new ZCoordinate(z1);
		assertTrue(z1.equals(z2));
	}

}