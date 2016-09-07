import junit.framework.*;

public class TestCoordinates extends TestCase {

	// XCoordinate tests

	public void testXCoordinateValue() {
		int value = 1;
		XCoordinate x = new XCoordinate(value);
		assertEquals(value, x.value());
	}

	// YCoordinate tests

	public void testYCoordinateValue() {
		int value = 1;
		YCoordinate y = new YCoordinate(value);
		assertEquals(value, y.value());
	}

	// ZCoordinate tests

	public void testZCoordinateValue() {
		int value = 1;
		ZCoordinate z = new ZCoordinate(value);
		assertEquals(value, z.value());
	}

}