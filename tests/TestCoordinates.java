import org.junit.*;
import static org.junit.Assert.*;

public class TestCoordinates {

	// XCoordinate tests

	@Test
	public void testXCoordinateValue() {
		int value = 1;
		XCoordinate x = new XCoordinate(value);
		assertEquals(value, x.value());
	}

	@Test
	public void testXCoordinateCopy() {
		int value = 1;
		XCoordinate x1 = new XCoordinate(value);
		XCoordinate x2 = new XCoordinate(x1);
		assertTrue(x1.equals(x2));
	}

	// YCoordinate tests

	@Test
	public void testYCoordinateValue() {
		int value = 1;
		YCoordinate y = new YCoordinate(value);
		assertEquals(value, y.value());
	}

	@Test
	public void testYCoordinateCopy() {
		int value = 1;
		YCoordinate y1 = new YCoordinate(value);
		YCoordinate y2 = new YCoordinate(y1);
		assertTrue(y1.equals(y2));
	}

	// ZCoordinate tests

	@Test
	public void testZCoordinateValue() {
		int value = 1;
		ZCoordinate z = new ZCoordinate(value);
		assertEquals(value, z.value());
	}

	@Test
	public void testZCoordinateCopy() {
		int value = 1;
		ZCoordinate z1 = new ZCoordinate(value);
		ZCoordinate z2 = new ZCoordinate(z1);
		assertTrue(z1.equals(z2));
	}

}