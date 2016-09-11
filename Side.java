/***********************************************************************************
* Author: Michael Friedman
* Created: 9/8/16
*
* Description: This class is a library with operations that can be done on a single 
* side of a Rubik's Cube. The side is identified by the Coordinate of the plane in 
* which the side lives.
***********************************************************************************/

public final class Side {

	// Constants
	private static final boolean BUILD_SIDE_ARRAY = true;
	private static final boolean APPLY_SIDE_ARRAY = false;

	// Helper methods

	/**
	 * Permutes the given array so to represent a clockwise turn on those Cublets
	 */
	private static void permuteClockwise(Cublet[] cublets) {
		int N = cublets.length;

		// Store the last two cublets temporarily
		Cublet last 		= cublets[N - 1];
		Cublet secondToLast = cublets[N - 2];

		// Shift each Cublet over in the array by 2 forward
		for (int i = N - 3; i >= 0; i--) {
			cublets[i + 2] = cublets[i];
		}

		// Put the original last two cublets in the front
		cublets[1] = last;
		cublets[0] = secondToLast;
	}

	/**
	 * Helper class to hold corners ordered c1 - c4 of the sides B, F, L, or R
	 *
	 * The corners are identified as follows: from the perspective of any of the 4
	 * sides with U on top:
	 *
	 * c1 	- 	c2
	 * -	-	-
	 * c4 	- 	c3
	 */
	private static class CornerContainer {
		private Corner c1, c2, c3, c4;

		private CornerContainer(Corner c1, Corner c2, Corner c3, Corner c4) {
			this.c1 = c1;
			this.c2 = c2;
			this.c3 = c3;
			this.c4 = c4;
		}
	}

	/**
	 * Orients the Corners and Edges, given the corners c1 - c4 of the sides B, F,
	 * L, or R and returns a CornerContainer of the oriented Corners
	 * 
	 * Corners c1 and c3 twist clockwise, corners c2 and c4 twist counter-clockwise
	 */
	private static CornerContainer orientCorners(Corner c1, Corner c2, Corner c3, Corner c4) {
		c1 = c1.twistClockwise();
		c3 = c3.twistClockwise();

		c2 = c2.twistCounterClockwise();
		c4 = c4.twistCounterClockwise();

		return new CornerContainer(c1, c2, c3, c4);
	}

	/**
	 * Orients the Corners and Edges of the given array appropriately when making
	 * a clockwise turn on the specified side.
	 *
	 * Assumes the corners are in their original locations (before the turn)
	 */
	private static void orientClockwise(Cublet[] cublets, XCoordinate side) {
		// Orient corners
		if (side.equals(Cube.B)) {
			// Array starts with bottom-right corner
			Corner c3 = (Corner) cublets[0];
			Corner c4 = (Corner) cublets[2];
			Corner c1 = (Corner) cublets[4];
			Corner c2 = (Corner) cublets[6];
			CornerContainer container = orientCorners(c1, c2, c3, c4);
			cublets[0] = container.c3;
			cublets[2] = container.c4;
			cublets[4] = container.c1;
			cublets[6] = container.c2;
		} else if (side.equals(Cube.F)) {
			// Array starts with bottom-left corner
			Corner c4 = (Corner) cublets[0];
			Corner c1 = (Corner) cublets[2];
			Corner c2 = (Corner) cublets[4];
			Corner c3 = (Corner) cublets[6];
			CornerContainer container = orientCorners(c1, c2, c3, c4);
			cublets[0] = container.c4;
			cublets[2] = container.c1;
			cublets[4] = container.c2;
			cublets[6] = container.c3;
		}

		// Flip all edges on B/F turns
		for (int i = 1; i < cublets.length; i += 2) {
			Edge e = (Edge) cublets[i];
			cublets[i] = e.flip();
		}
	}

	/**
	 * Orients the Corners and Edges of the given array appropriately when making
	 * a clockwise turn on the specified side.
	 *
	 * Assumes the corners are in their original locations (before the turn)
	 */
	private static void orientClockwise(Cublet[] cublets, YCoordinate side) {
		// Only corners get oriented for L/R turns
		if (side.equals(Cube.L)) {
			// Array starts with bottom-left corner
			Corner c4 = (Corner) cublets[0];
			Corner c1 = (Corner) cublets[2];
			Corner c2 = (Corner) cublets[4];
			Corner c3 = (Corner) cublets[6];
			CornerContainer container = orientCorners(c1, c2, c3, c4);
			cublets[0] = container.c4;
			cublets[2] = container.c1;
			cublets[4] = container.c2;
			cublets[6] = container.c3;
		} else if (side.equals(Cube.R)) {
			// Array starts with bottom-right corner
			Corner c3 = (Corner) cublets[0];
			Corner c4 = (Corner) cublets[2];
			Corner c1 = (Corner) cublets[4];
			Corner c2 = (Corner) cublets[6];
			CornerContainer container = orientCorners(c1, c2, c3, c4);
			cublets[0] = container.c3;
			cublets[2] = container.c4;
			cublets[4] = container.c1;
			cublets[6] = container.c2;
		}
	}

	/**
	 * Orients the Corners and Edges of the given array appropriately when making
	 * a clockwise turn on the specified side.
	 *
	 * Assumes the corners are in their original locations (before the turn)
	 */
	private static void orientClockwise(Cublet[] cublets, ZCoordinate side) {
		// No orientations change with D/U moves
		return;
	}

	/**
	 * Returns a 3D cublet array constructed from the given cube
	 */
	private static Cublet[][][] buildCubletArray(Cube cube) {
		Cublet[][][] cublets = new Cublet[Cube.GRID_SIZE][Cube.GRID_SIZE][Cube.GRID_SIZE];
		for (int x = 0; x < Cube.GRID_SIZE; x++) {
			for (int y = 0; y < Cube.GRID_SIZE; y++) {
				for (int z = 0; z < Cube.GRID_SIZE; z++) {
					XCoordinate cx = new XCoordinate(x);
					YCoordinate cy = new YCoordinate(y);
					ZCoordinate cz = new ZCoordinate(z);
					int type = Utilities.typeOfCublet(x, y, z);
					switch (type) {
						case Utilities.CUBLET_EDGE:
							if (x == Cube.EDGE.value())
								cublets[x][y][z] = cube.getEdge(cy, cz);
							else if (y == Cube.EDGE.value())
								cublets[x][y][z] = cube.getEdge(cx, cz);
							else
								cublets[x][y][z] = cube.getEdge(cx, cy); 
							break;
						case Utilities.CUBLET_CORNER:
							cublets[x][y][z] = cube.getCorner(cx, cy, cz);
							break;
						case Utilities.CUBLET_CENTER:
							cublets[x][y][z] = new Center((Center) cube.getCublet(cx, cy, cz));
							break;
						case Utilities.CUBLET_CORE:
							cublets[x][y][z] = cube.getCublet(cx, cy, cz);
							break;
						default:
							throw new RuntimeException("Error while building cublet array: this error will never be thrown.");
					}
				}
			}
		}

		return cublets;
	}

	/**
	 * Either adds the Cublet at position (x, y, z) to the clockwise-ordered list
	 * of Cublets on a side, or replaces the Cublet at position (x, y, z) with
	 * the corresponding one on the list, depending on the boolean isBuildingSide.
	 */
	private static void addOrReplaceCublet(int x, int y, int z, int i, Cublet[] sideCublets,
		Cublet[][][] allCublets, boolean isBuildingSide) {
		if 	 (isBuildingSide) sideCublets[i] 	  = allCublets[x][y][z];
		else 		 		  allCublets[x][y][z] = sideCublets[i];
	}
	

	/**
	 * Iterates over the the side cublets in clockwise order on the side indicated
	 * by the given Coordinate. Either iterates over the earlier of the two
	 * non-constant axes (x/y, y/z, or x/z) or the latter, depending on the boolean
	 * earlierAxisFirst. Either builds or applies the the list of side Cublets, 
	 * depending on the boolean isBuildingSide.
	 */
	private static void iterateSideArray(boolean isBuildingSide, Cublet[] sideCublets, 
		Cublet[][][] allCublets, boolean earlierAxisFirst, XCoordinate side) {	
		int x 	 = side.value();	// the constant x axis
		int i 	 = 0;				// index of next cublet
		int y 	 = 0;				// y coordinate of next cublet
		int z 	 = 0;				// z coordinate of next cublet
		if (earlierAxisFirst) {
			for (y = 0; y < Cube.GRID_SIZE; y++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			y = Cube.GRID_SIZE - 1; // TODO: Come up with a better way to iterate through the Cublets so you don't have to reset these values after each loop

			for (z++; z < Cube.GRID_SIZE; z++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			z = Cube.GRID_SIZE - 1;

			for (y--; y >= 0; y--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			y = 0;

			for (z--; z > 0; z--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			z = 0;
		}

		else {
			for (z = 0; z < Cube.GRID_SIZE; z++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			z = Cube.GRID_SIZE - 1;

			for (y++; y < Cube.GRID_SIZE; y++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			y = Cube.GRID_SIZE - 1;

			for (z--; z >= 0; z--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			z = 0;

			for (y--; y > 0; y--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			y = 0;
		}	
	}

	private static void iterateSideArray(boolean isBuildingSide, Cublet[] sideCublets, 
		Cublet[][][] allCublets, boolean earlierAxisFirst, YCoordinate side) {	
		int y 	 = side.value();	// the constant y axis
		int i 	 = 0;				// index of next cublet
		int x 	 = 0;				// x coordinate of next cublet
		int z 	 = 0;				// z coordinate of next cublet
		if (earlierAxisFirst) {
			for (x = 0; x < Cube.GRID_SIZE; x++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			x = Cube.GRID_SIZE - 1;

			for (z++; z < Cube.GRID_SIZE; z++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			z = Cube.GRID_SIZE - 1;

			for (x--; x >= 0; x--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			x = 0;

			for (z--; z > 0; z--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);;
			}
			z = 0;
		}

		else {
			for (z = 0; z < Cube.GRID_SIZE; z++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			z = Cube.GRID_SIZE - 1;

			for (x++; x < Cube.GRID_SIZE; x++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			x = Cube.GRID_SIZE - 1;

			for (z--; z >= 0; z--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			z = 0;

			for (x--; x > 0; x--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			x = 0;
		}	
	}

	private static void iterateSideArray(boolean isBuildingSide, Cublet[] sideCublets, 
		Cublet[][][] allCublets, boolean earlierAxisFirst, ZCoordinate side) {	
		int z 	 = side.value();	// the constant z axis
		int i 	 = 0;				// index of next cublet
		int x 	 = 0;				// x coordinate of next cublet
		int y 	 = 0;				// y coordinate of next cublet
		if (earlierAxisFirst) {
			for (x = 0; x < Cube.GRID_SIZE; x++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			x = Cube.GRID_SIZE - 1;

			for (y++; y < Cube.GRID_SIZE; y++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			y = Cube.GRID_SIZE - 1;

			for (x--; x >= 0; x--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			x = 0;

			for (y--; y > 0; y--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			y = 0;
		}

		else {
			for (y = 0; y < Cube.GRID_SIZE; y++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			y = Cube.GRID_SIZE - 1;

			for (x++; x < Cube.GRID_SIZE; x++) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			x = Cube.GRID_SIZE - 1;

			for (y--; y >= 0; y--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			y = 0;

			for (x--; x > 0; x--) {
				addOrReplaceCublet(x, y, z, i++, sideCublets, allCublets, isBuildingSide);
			}
			x = 0;
		}	
	}
	
	/**
	 * Returns the 1D array of Cublets of the side represented by the given
	 * Coordinate, in clockwise order
	 */
	private static Cublet[] buildSideArray(Cublet[][][] allCublets, XCoordinate side) {
		Cublet[] sideCublets = new Cublet[(Cube.GRID_SIZE * Cube.GRID_SIZE) - 1];
		boolean earlierAxisFirst = (side.equals(Cube.B)) ? true : false;
		iterateSideArray(BUILD_SIDE_ARRAY, sideCublets, allCublets, earlierAxisFirst, side);
		return sideCublets;
	}

	private static Cublet[] buildSideArray(Cublet[][][] allCublets, YCoordinate side) {
		Cublet[] sideCublets = new Cublet[(Cube.GRID_SIZE * Cube.GRID_SIZE) - 1];
		boolean earlierAxisFirst = (side.equals(Cube.L)) ? false : true;
		iterateSideArray(BUILD_SIDE_ARRAY, sideCublets, allCublets, earlierAxisFirst, side);
		return sideCublets;
	}

	private static Cublet[] buildSideArray(Cublet[][][] allCublets, ZCoordinate side) {
		Cublet[] sideCublets = new Cublet[(Cube.GRID_SIZE * Cube.GRID_SIZE) - 1];
		boolean earlierAxisFirst = (side.equals(Cube.D)) ? true : false;
		iterateSideArray(BUILD_SIDE_ARRAY, sideCublets, allCublets, earlierAxisFirst, side);
		return sideCublets;
	}

	/**
	 * Replaces the Cublets of the 3D array on one side with those of the given array
	 * of Cublets
	 */
	private static void applySideArray(Cublet[] sideCublets, Cublet[][][] allCublets, XCoordinate side) {
		boolean earlierAxisFirst = (side.equals(Cube.B)) ? true : false;
		iterateSideArray(APPLY_SIDE_ARRAY, sideCublets, allCublets, earlierAxisFirst, side);
	}

	private static void applySideArray(Cublet[] sideCublets, Cublet[][][] allCublets, YCoordinate side) {
		boolean earlierAxisFirst = (side.equals(Cube.L)) ? false : true;
		iterateSideArray(APPLY_SIDE_ARRAY, sideCublets, allCublets, earlierAxisFirst, side);
	}
	
	private static void applySideArray(Cublet[] sideCublets, Cublet[][][] allCublets, ZCoordinate side) {
		boolean earlierAxisFirst = (side.equals(Cube.D)) ? true : false;
		iterateSideArray(APPLY_SIDE_ARRAY, sideCublets, allCublets, earlierAxisFirst, side);
	}
	
	

	// Static methods

	/**
	 * Returns a new Cube with the given side turned clockwise
	 */
	public static Cube turnClockwise(XCoordinate side, Cube cube) {
		Cublet[][][] allCublets = buildCubletArray(cube);
		Cublet[] sideCublets 	= buildSideArray(allCublets, side);

		// Turn the side using the list of Cublets
		orientClockwise(sideCublets, side);
		permuteClockwise(sideCublets);

		applySideArray(sideCublets, allCublets, side);
		return new Cube(allCublets);
	}

	/**
	 * Returns a new Cube with the given side turned clockwise
	 */
	public static Cube turnClockwise(YCoordinate side, Cube cube) {
		Cublet[][][] allCublets = buildCubletArray(cube);
		Cublet[] sideCublets 	= buildSideArray(allCublets, side);

		// Turn the side using the list of Cublets
		orientClockwise(sideCublets, side);
		permuteClockwise(sideCublets);

		applySideArray(sideCublets, allCublets, side);
		return new Cube(allCublets);
	}

	/**
	 * Returns a new Cube with the given side turned clockwise
	 */
	public static Cube turnClockwise(ZCoordinate side, Cube cube) {
		Cublet[][][] allCublets = buildCubletArray(cube);
		Cublet[] sideCublets 	= buildSideArray(allCublets, side);

		// Turn the side using the list of Cublets
		orientClockwise(sideCublets, side);
		permuteClockwise(sideCublets);

		applySideArray(sideCublets, allCublets, side);
		return new Cube(allCublets);	
	}

}