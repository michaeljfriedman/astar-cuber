import org.junit.*;
import static org.junit.Assert.*;

public class TestCube {

	private static final int NUM_SIDES = 3;

	private Cublet[][][] cublets;		// cublets at positions (x, y, z)
	private Cube cube;					// a solved Cube

	@Before
	public void setUp() {
		// Manually make all the Cublets of a solved cube
		cublets = new Cublet[NUM_SIDES][NUM_SIDES][NUM_SIDES];
		int orientation = 0;
		for (int x = 0; x < NUM_SIDES; x++) {
			for (int y = 0; y < NUM_SIDES; y++) {
				for (int z = 0; z < NUM_SIDES; z++) {
					int type = Utilities.typeOfCublet(x, y, z);
					switch (type) {
						case Utilities.CUBLET_EDGE:
							cublets[x][y][z] = new Edge(new XCoordinate(x), 
								new YCoordinate(y), new ZCoordinate(z), orientation);
							break;
						case Utilities.CUBLET_CORNER:
							cublets[x][y][z] = new Corner(new XCoordinate(x), 
								new YCoordinate(y), new ZCoordinate(z), orientation);
							break;
						case Utilities.CUBLET_CENTER:
							cublets[x][y][z] = new Center(new XCoordinate(x), 
								new YCoordinate(y), new ZCoordinate(z));
							break;
						case Utilities.CUBLET_CORE:
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

	@Test
	public void testConstructionFromFile() {
		// Test that the cube made in setUp() matches all cublets in the 3D array
		for (int x = 0; x < NUM_SIDES; x++) {
			for (int y = 0; y < NUM_SIDES; y++) {
				for (int z = 0; z < NUM_SIDES; z++) {
					XCoordinate cx = new XCoordinate(x);
					YCoordinate cy = new YCoordinate(y);
					ZCoordinate cz = new ZCoordinate(z);
					int type = Utilities.typeOfCublet(x, y, z);
					Cublet testCublet;
					switch (type) {
						case Utilities.CUBLET_EDGE:
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
						case Utilities.CUBLET_CORNER:
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

	@Test
	public void testConstructionFromArray() {
		Cube testCube = new Cube(cublets);
		assertTrue(cube.equals(testCube));
	}

	@Test
	public void testCopy() {
		Cube copy = new Cube(cube);
		assertTrue(cube.equals(copy));
	}

	@Test
	public void testConstructionOfSolvedCube() {
		// Test that the constructor for a solved cube matches the cube made in
		// setUp()
		Cube solved = new Cube();
		assertTrue(cube.equals(solved));
	}


	// Solve checking tests

	@Test
	public void testSolvedAfterConstructingSolved() {
		// construct a cube with the "solved cube" constructor and test that it
		// passes isSolved()
		Cube solved = new Cube();
		assertTrue(solved.isSolved());
	}

	@Test
	public void testUnsolvedAfterConstructingSolvedByU() {
		// construct an unsolved cube and test that it fails isSolved()
		Cube cubeAfterUi = new Cube("tests/cube_after_Ui.txt");
		assertFalse(cubeAfterUi.isSolved());
	}

	@Test
	public void testUnsolvedAfterConstructingSolvedByRU() {
		// construct an unsolved cube and test that it fails isSolved()
		Cube cubeAfterUiRi = new Cube("tests/cube_after_UiRi.txt");
		assertFalse(cubeAfterUiRi.isSolved());
	}


	// Equality checking

	@Test
	public void testSolvedCubesAreEqual() {
		// construct a solved cube with the "solved cube" constructor and test that
		// it equals the one made in setUp()
		Cube solved = new Cube();
		assertTrue(solved.equals(cube));
	}
}