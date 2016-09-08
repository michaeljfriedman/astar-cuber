import junit.framework.*;

public class TestCube extends TestCase {

	private static final int NUM_SIDES = 3;

	// Integer labels to identify types of Cublets
	private static final int CUBLET_EDGE 	= 0;
	private static final int CUBLET_CORNER 	= 1;
	private static final int CUBLET_CENTER	= 2;
	private static final int CUBLET_CORE 	= 3;

	private Cublet[][][] cublets;		// cublets at positions (x, y, z)
	private Cube cube;					// a solved Cube

	/**
	 * Helper function for setUp()
	 * Returns the number of values in the given array that equal the query value
	 */
	private int numValuesEqualTo(int query, int[] values) {
		int numEqual = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == query) numEqual++;
		}
		return numEqual;
	}
	
	/**
	 * Helper funtion for setUp()
	 * Returns the type of Cublet that should be initialized at location (x, y, z)
	 */
	private int typeOfCublet(int x, int y, int z) {
		// The type of cublet depends entirely on how many coordinates have value 1
		int numValuesEqualTo1 = numValuesEqualTo(1, new int[] { x, y, z });
		if 		(numValuesEqualTo1 == 3) 	return CUBLET_CORE;
		else if (numValuesEqualTo1 == 2) 	return CUBLET_CENTER;
		else if (numValuesEqualTo1 == 1) 	return CUBLET_EDGE;
		else 								return CUBLET_CORNER;
	}

	@Override
	protected void setUp() {
		// Manually make all the Cublets of a solved cube
		cublets = new Cublet[NUM_SIDES][NUM_SIDES][NUM_SIDES];
		int orientation = 0;
		for (int x = 0; x < NUM_SIDES; x++) {
			for (int y = 0; y < NUM_SIDES; y++) {
				for (int z = 0; z < NUM_SIDES; z++) {
					int type = typeOfCublet(x, y, z);
					switch (type) {
						case CUBLET_EDGE:
							cublets[x][y][z] = new Edge(new XCoordinate(x), 
								new YCoordinate(y), new ZCoordinate(z), orientation);
							break;
						case CUBLET_CORNER:
							cublets[x][y][z] = new Corner(new XCoordinate(x), 
								new YCoordinate(y), new ZCoordinate(z), orientation);
							break;
						case CUBLET_CENTER:
							cublets[x][y][z] = new Center(new XCoordinate(x), 
								new YCoordinate(y), new ZCoordinate(z));
							break;
						case CUBLET_CORE:
							cublets[x][y][z] = new Cublet(new XCoordinate(x),
								new YCoordinate(y), new ZCoordinate(z), orientation);
							break;
						default:
							throw new RuntimeException("Error making cublets: this error will never be thrown.");
					}
				}
			}
		}

		// Construct a solved Cube from a file
		cube = new Cube("tests/cube_solved.txt");
	}


	// Construction tests

	public void testConstructionFromFile() {
		// Test that the cube made in setUp() matches all cublets in the 3D array
		for (int x = 0; x < NUM_SIDES; x++) {
			for (int y = 0; y < NUM_SIDES; y++) {
				for (int z = 0; z < NUM_SIDES; z++) {
					XCoordinate cx = new XCoordinate(x);
					YCoordinate cy = new YCoordinate(y);
					ZCoordinate cz = new ZCoordinate(z);
					int type = typeOfCublet(x, y, z);
					Cublet testCublet;
					switch (type) {
						case CUBLET_EDGE:
							if (cx.equals(Cube.EDGE)) {
								testCublet = cube.getEdge(cy, cz);
							}
							else if (cy.equals(Cube.EDGE)) {
								testCublet = cube.getEdge(cx, cz);
							}
							else {
								testCublet = cube.getEdge(cx, cy);
							}
							break;
						case CUBLET_CORNER:
							testCublet = cube.getCorner(cx, cy, cz);
							break;
						default:
							testCublet = cube.getCublet(cx, cy, cz);
							break;
					}
					assertTrue(cublets[x][y][z].equals(testCublet));
				}
			}
		}
	}

	public void testCopy() {
		Cube copy = new Cube(cube);
		assertTrue(cube.equals(copy));
	}

	public void testConstructionOfSolvedCube() {
		// Test that the constructor for a solved cube matches the cube made in
		// setUp()
		Cube solved = new Cube();
		assertTrue(cube.equals(solved));
	}


	// Solve checking tests

	public void testSolvedAfterConstructingSolved() {
		// construct a cube with the "solved cube" constructor and test that it
		// passes isSolved()
		Cube solved = new Cube();
		assertTrue(solved.isSolved());
	}

	public void testUnsolvedAfterConstructingSolvedByU() {
		// construct an unsolved cube and test that it fails isSolved()
		Cube cubeAfterUi = new Cube("tests/cube_after_Ui.txt");
		assertFalse(cubeAfterUi.isSolved());
	}

	public void testUnsolvedAfterConstructingSolvedByRU() {
		// construct an unsolved cube and test that it fails isSolved()
		Cube cubeAfterUiRi = new Cube("tests/cube_after_UiRi.txt");
		assertFalse(cubeAfterUiRi.isSolved());
	}


	// Equality checking

	public void testSolvedCubesAreEqual() {
		// construct a solved cube with the "solved cube" constructor and test that
		// it equals the one made in setUp()
		Cube solved = new Cube();
		assertTrue(solved.equals(cube));
	}
}