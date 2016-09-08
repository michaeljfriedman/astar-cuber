import junit.framework.*;

public class TestCublets extends TestCase {

	private XCoordinate cubletX;
	private YCoordinate cubletY;
	private ZCoordinate cubletZ;
	private Cublet cublet;

	private XCoordinate cornerX;
	private YCoordinate cornerY;
	private ZCoordinate cornerZ;
	private Corner corner;

	private XCoordinate edgeX;
	private YCoordinate edgeY;
	private ZCoordinate edgeZ;
	private Edge edge;

	private XCoordinate centerX;
	private YCoordinate centerY;
	private ZCoordinate centerZ;
	private Center center;

	@Override
	protected void setUp() {
		cubletX = new XCoordinate(0);
		cubletY = new YCoordinate(0);
		cubletZ = new ZCoordinate(0);
		cublet 	= new Cublet(cubletX, cubletY, cubletZ, Cublet.ORIENTED);

		cornerX = new XCoordinate(0);
		cornerY = new YCoordinate(0);
		cornerZ = new ZCoordinate(0);
		corner 	= new Corner(cornerX, cornerY, cornerZ, Cublet.ORIENTED);

		edgeX 	= new XCoordinate(0);
		edgeY 	= new YCoordinate(1);
		edgeZ 	= new ZCoordinate(0);
		edge 	= new Edge(edgeX, edgeY, edgeZ, Cublet.ORIENTED);

		centerX	= new XCoordinate(0);
		centerY = new YCoordinate(1);
		centerZ = new ZCoordinate(1);
		center 	= new Center(centerX, centerY, centerZ);
	}
	

	// Cublet tests

	public void testCubletPermutation() {
		assertTrue(cublet.isPermuted(cubletX, cubletY, cubletZ));
	}

	public void testCubletOrientation() {
		assertTrue(cublet.isOriented());
	}

	public void testCubletCopy() {
		Cublet copy = new Cublet(cublet);
		assertTrue(cublet.equals(copy));
	}


	// Corner tests

	public void testCornerPermutation() {
		assertTrue(corner.isPermuted(cornerX, cornerY, cornerZ));
	}

	public void testCornerOrientation() {
		assertTrue(corner.isOriented());
	}

	public void testCornerTwists() {
		// Test that twisting clockwise 3 times restores orientation
		Corner twisted1 = corner.twistClockwise();
		assertFalse(twisted1.isOriented());

		Corner twisted2 = twisted1.twistClockwise();
		assertFalse(twisted2.isOriented());
		
		Corner twisted3 = twisted2.twistClockwise();
		assertTrue(twisted3.isOriented());

		// Test that twisting counter-clockwise 3 times restores orientation
		twisted1 = corner.twistCounterClockwise();
		assertFalse(twisted1.isOriented());

		twisted2 = twisted1.twistCounterClockwise();
		assertFalse(twisted2.isOriented());

		twisted3 = twisted2.twistCounterClockwise();
		assertTrue(twisted3.isOriented());

		// Test that clockwise and counter-clockwise twists are inverses
		assertTrue(corner
			.twistClockwise()
			.twistCounterClockwise()
			.isOriented()
		);

		assertTrue(corner
			.twistCounterClockwise()
			.twistClockwise()
			.isOriented()
		);

		assertTrue(corner
			.twistClockwise()
			.twistClockwise()
			.twistCounterClockwise()
			.twistCounterClockwise()
			.isOriented()
		);


		assertTrue(corner
			.twistCounterClockwise()
			.twistCounterClockwise()
			.twistClockwise()
			.twistClockwise()
			.isOriented()
		);
	}

	public void testCornerCopy() {
		Corner copy = new Corner(corner);
		assertTrue(corner.equals(copy));
	}


	// Edge tests

	public void testEdgePermutation() {
		assertTrue(edge.isPermuted(edgeX, edgeY, edgeZ));
	}

	public void testEdgeOrientation() {
		assertTrue(edge.isOriented());
	}

	public void testEdgeFlip() {
		// Test that flipping once unorients, and flipping twice reorients
		Edge flipped = edge.flip();
		assertFalse(flipped.isOriented());

		Edge restored = flipped.flip();
		assertTrue(restored.isOriented());
	}

	public void testEdgeCopy() {
		Edge copy = new Edge(edge);
		assertTrue(edge.equals(copy));
	}


	// Center tests

	public void testCenterPermutation() {
		assertTrue(center.isPermuted(centerX, centerY, centerZ));
	}

	public void testCenterOrientation() {
		assertTrue(center.isOriented());
	}

	public void testCenterCopy() {
		Center copy = new Center(center);
		assertTrue(center.equals(copy));
	}
}