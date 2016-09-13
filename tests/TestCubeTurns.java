import org.junit.*;
import static org.junit.Assert.*;

public class TestCubeTurns {

	private Cube solved;

	@Before
	public void setUp() {
		// Create a solved cube as a starting point for turns
		solved = new Cube();
	}

	//---------------------------------
	// Single clockwise turns
	//---------------------------------

	// Construct a cube in the state just after a each turn, make the turn on the
	// test cube, and compare the two cubes
	@Test
	public void testB() {
		Cube cubeAfterB = new Cube("tests/cube_after_B.txt");
		Cube testCube = solved.turnClockwise(Cube.B);
		assertTrue("The test cube after a B should equal the preset cube after a B",
			testCube.equals(cubeAfterB));
	}

	@Test
	public void testF() {
		Cube cubeAfterF = new Cube("tests/cube_after_F.txt");
		Cube testCube = solved.turnClockwise(Cube.F);
		assertTrue("The test cube after an F should equal the preset cube after an F",
			testCube.equals(cubeAfterF));
	}

	@Test
	public void testL() {
		Cube cubeAfterL = new Cube("tests/cube_after_L.txt");
		Cube testCube = solved.turnClockwise(Cube.L);
		assertTrue("The test cube after an L should equal the preset cube after an L",
			testCube.equals(cubeAfterL));
	}

	@Test
	public void testR() {
		Cube cubeAfterR = new Cube("tests/cube_after_R.txt");
		Cube testCube = solved.turnClockwise(Cube.R);
		assertTrue("The test cube after an R should equal the preset cube after an R",
			testCube.equals(cubeAfterR));
	}

	@Test
	public void testD() {
		Cube cubeAfterD = new Cube("tests/cube_after_D.txt");
		Cube testCube = solved.turnClockwise(Cube.D);
		assertTrue("The test cube after a D should equal the preset cube after a D",
			testCube.equals(cubeAfterD));
	}

	@Test
	public void testU() {
		Cube cubeAfterU = new Cube("tests/cube_after_U.txt");
		Cube testCube = solved.turnClockwise(Cube.U);
		assertTrue("The test cube after a U should equal the preset cube after a U",
			testCube.equals(cubeAfterU));
	}


	//-----------------------------------------
	// Double clockwise turns between two axes
	//-----------------------------------------

	// x/y axes
	@Test
	public void testXthenY() {
		Cube cubeAfterFL = new Cube("tests/cube_after_FL.txt");
		Cube testCube = solved.turnClockwise(Cube.F).turnClockwise(Cube.L);
		assertTrue("The test cube after F, L should equal the preset cube",
			testCube.equals(cubeAfterFL));
	}

	@Ignore("Test not implemented")
	public void testYthenX() {}

	// y/z axes

	@Test
	public void testYthenZ() {
		Cube cubeAfterRU = new Cube("tests/cube_after_RU.txt");
		Cube testCube = solved.turnClockwise(Cube.R).turnClockwise(Cube.U);
		assertTrue("The test cube after R, U should equal the preset cube",
			testCube.equals(cubeAfterRU));
	}

	@Ignore("Test not implemented")
	public void testZthenY() {}

	// x/z axes

	@Test
	public void testXthenZ() {
		Cube cubeAfterBD = new Cube("tests/cube_after_BD.txt");
		Cube testCube = solved.turnClockwise(Cube.B).turnClockwise(Cube.D);
		assertTrue("The test cube after B, D should equal the preset cube",
			testCube.equals(cubeAfterBD));
	}

	@Ignore("Test not implemented")
	public void testZthenX() {}


	//-----------------------------------------
	// Equality of cubes making the same moves
	//-----------------------------------------

	@Test
	public void testEqualAfterSomeMoves() {
		// make some turns on the solved cubes and test that they are still equal
		Cube solved2 = new Cube();
		Cube testTurns = solved
			.turnClockwise(Cube.B)
			.turnClockwise(Cube.L)
			.turnClockwise(Cube.D)
			.turnClockwise(Cube.F)
			.turnClockwise(Cube.R)
			.turnClockwise(Cube.U);
		Cube testTurns2 = solved2
			.turnClockwise(Cube.B)
			.turnClockwise(Cube.L)
			.turnClockwise(Cube.D)
			.turnClockwise(Cube.F)
			.turnClockwise(Cube.R)
			.turnClockwise(Cube.U);
		assertTrue("Two cubes should be equal after making the same moves",
			testTurns.equals(testTurns2));
	}


	//---------------------------------
	// Check solved after making moves
	//---------------------------------

	@Test
	public void testNearlySolvedCubesBecomeSolved1() {
		Cube cubeAfterRU = new Cube("tests/cube_after_RU.txt");
		Cube testCube = cubeAfterRU
			.turnClockwise(Cube.U).turnClockwise(Cube.U).turnClockwise(Cube.U)
			.turnClockwise(Cube.R).turnClockwise(Cube.R).turnClockwise(Cube.R);
		assertTrue("After moves U', R' the test cube should be solved",
			testCube.isSolved());
	}

	@Test
	public void testMakingMovesAndUndoingThemSolvesTheCube() {
		// construct a solved cube with the "solved cube" constructor, make some
		// moves, make the reverse moves, and check that it passes isSolved()
		Cube testTurns = solved
			.turnClockwise(Cube.B)
			.turnClockwise(Cube.L)
			.turnClockwise(Cube.D)
			.turnClockwise(Cube.D).turnClockwise(Cube.D).turnClockwise(Cube.D)
			.turnClockwise(Cube.L).turnClockwise(Cube.L).turnClockwise(Cube.L)
			.turnClockwise(Cube.B).turnClockwise(Cube.B).turnClockwise(Cube.B);
		assertTrue("Cube should be solved after making some moves and undoing them",
			testTurns.isSolved());
	}

	@Test
	public void testSolvedCubeBecomesUnsolved() {
		// construct a solved cube with the "solved cube" constructor, make some
		// moves, and test that it fails isSolved()
		Cube unsolved = solved
			.turnClockwise(Cube.B)
			.turnClockwise(Cube.L)
			.turnClockwise(Cube.D)
			.turnClockwise(Cube.F)
			.turnClockwise(Cube.R)
			.turnClockwise(Cube.U);
		assertFalse("Cube should be unsolved after some moves", unsolved.isSolved());
	}

	@Test
	public void testUnsolvedCubeBecomesMoreUnsolved() {
		// construct an unsolved cube from a file, make some arbitrary moves, and test
		// that it fails isSolved()
		Cube cubeAfterRU = new Cube("tests/cube_after_RU.txt");
		Cube testCube = cubeAfterRU
			.turnClockwise(Cube.B)
			.turnClockwise(Cube.B)
			.turnClockwise(Cube.L)
			.turnClockwise(Cube.D)
			.turnClockwise(Cube.R)
			.turnClockwise(Cube.F)
			.turnClockwise(Cube.U);
		assertFalse("Test cube should still be unsolved after R, U, B, B, L, D, R, F, U",
			testCube.isSolved());
	}

}