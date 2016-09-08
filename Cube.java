/***********************************************************************************
* Author: Michael Friedman
* Created: 9/7/16
*
* Description: This object is a model for the Rubik's Cube. It supports operations
* for retrieving cublets, turning sides, and determining whether or not the cube is
* solved.
***********************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;

public class Cube {

	// Constants

	private static final int NUM_SIDES = 3;		// number of sides on the cube

	// Integer labels to identify types of Cublets
	private static final int CUBLET_EDGE 	= 0;
	private static final int CUBLET_CORNER 	= 1;
	private static final int CUBLET_CENTER	= 2;
	private static final int CUBLET_CORE 	= 3;

	public static final XCoordinate B = new XCoordinate(0);		// back side
	public static final XCoordinate F = new XCoordinate(2);		// front side
	public static final YCoordinate L = new YCoordinate(0);		// left side
	public static final YCoordinate R = new YCoordinate(2);		// right side
	public static final ZCoordinate D = new ZCoordinate(0);		// down side
	public static final ZCoordinate U = new ZCoordinate(2);		// up side

	// The default value of the unspecified Coordinate of an edge's location on
	// the cube
	public static final Coordinate EDGE = new Coordinate(1);


	// Instance variables

	private Cublet[][][] cublets;	// cublets at locations (x, y, z)


	// Constructors

	/**
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
	

	/**
	 * Construct a Cube from a file. All 26 cublets are read in, one line per cublet:
	 *
	 * x0 y0 z0 x y z orientation
	 *
	 * (x0, y0, z0) is the solved position, and (x, y, z) is the current position.
	 * x, y, and z are allowed to take on values in [0, 2]
	 * orientation takes { 0, 1, 2 } for corners, { 0, 1 } for edges, or just { 0 }
	 * for centers.
	 *
	 * Lines beginning with hash symbols (#) and blank lines are ignored.
	 *
	 * All 26 positions must be read in and the cube must be solvable, or the
	 * construction will fail.
	 */
	public Cube(String filename) {
		cublets = new Cublet[NUM_SIDES][NUM_SIDES][NUM_SIDES];

		String comment = "#";
		In input = new In(filename);
		while (!input.isEmpty()) {
			String next = input.readString();
			if (next.equals(comment)) {
				input.readLine();
				continue;
			}

			// Parse the line and initialize the Cublet
			int x0 	= Integer.parseInt(next);
			int y0 	= Integer.parseInt(input.readString());
			int z0 	= Integer.parseInt(input.readString());
			int x 	= Integer.parseInt(input.readString());
			int y 	= Integer.parseInt(input.readString());
			int z 	= Integer.parseInt(input.readString());
			int orientation = Integer.parseInt(input.readString());
			int type = typeOfCublet(x, y, z);
			switch (type) {
				case CUBLET_EDGE:
					cublets[x][y][z] = new Edge(new XCoordinate(x0), 
						new YCoordinate(y0), new ZCoordinate(z0), orientation);
					break;
				case CUBLET_CORNER:
					cublets[x][y][z] = new Corner(new XCoordinate(x0), 
						new YCoordinate(y0), new ZCoordinate(z0), orientation);
					break;
				case CUBLET_CENTER:
					cublets[x][y][z] = new Center(new XCoordinate(x0), 
						new YCoordinate(y0), new ZCoordinate(z0));
					break;
				case CUBLET_CORE:
					cublets[x][y][z] = new Cublet(new XCoordinate(x0),
						new YCoordinate(y0), new ZCoordinate(z0), orientation);
					break;
				default:
					throw new RuntimeException("Error in Cube construction by file: this error will never be thrown.");
			}
		}

		// Add the core as a blank Cublet
		int core = 1;
		cublets[core][core][core] = new Cublet(new XCoordinate(core),
			new YCoordinate(core), new ZCoordinate(core), Cublet.ORIENTED);

		// Validate that every position was filled with a Cublet
		for (int x = 0; x < NUM_SIDES; x++) {
			for (int y = 0; y < NUM_SIDES; y++) {
				for (int z = 0; z < NUM_SIDES; z++) {
					if (cublets[x][y][z] == null)
						throw new IllegalArgumentException("File is not a complete Rubik's Cube.");
				}
			}
		}

		// TODO: Validate that this Cube is solvable
	}

	/**
	 * Construct a copy of the given Cube
	 */
	public Cube(Cube cube) {
		this.cublets = new Cublet[NUM_SIDES][NUM_SIDES][NUM_SIDES];
		for (int x = 0; x < NUM_SIDES; x++) {
			for (int y = 0; y < NUM_SIDES; y++) {
				for (int z = 0; z < NUM_SIDES; z++) {
					this.cublets[x][y][z] = new Cublet(cube.cublets[x][y][z]);
				}
			}
		}
	}

	/**
	 * Construct a solved Cube
	 */
	public Cube() {
		this("tests/cube_solved.txt");
	}


	// Getters

	/**
	 * Returns the Cublet at position (x, y, z)
	 */
	public Cublet getCublet(XCoordinate x, YCoordinate y, ZCoordinate z) {
		return new Cublet(cublets[x.value()][y.value()][z.value()]);
	}

	/**
	 * Returns the Corner at position (x, y, z)
	 */
	public Corner getCorner(XCoordinate x, YCoordinate y, ZCoordinate z) {
		return new Corner((Corner) cublets[x.value()][y.value()][z.value()]);
	}

	/**
	 * Returns the Edge identified by its two sides - e.g. BL (x, y), RU (y z),
	 * FD (x, z), etc.
	 */
	public Edge getEdge(XCoordinate x, YCoordinate y) {
		return new Edge((Edge) cublets[x.value()][y.value()][EDGE.value()]);
	}

	/**
	 * Returns the Edge identified by its two sides - e.g. BL (x, y), RU (y z),
	 * FD (x, z), etc.
	 */
	public Edge getEdge(YCoordinate y, ZCoordinate z) {
		return new Edge((Edge) cublets[EDGE.value()][y.value()][z.value()]);
	}

	/**
	 * Returns the Edge identified by its two sides - e.g. BL (x, y), RU (y z),
	 * FD (x, z), etc.
	 */
	public Edge getEdge(XCoordinate x, ZCoordinate z) {
		return new Edge((Edge) cublets[x.value()][EDGE.value()][z.value()]);
	}
	

	// Methods

	/**
	 * Returns whether this Cube is solved
	 */
	public boolean isSolved() {
		for (int x = 0; x < NUM_SIDES; x++) {
			for (int y = 0; y < NUM_SIDES; y++) {
				for (int z = 0; z < NUM_SIDES; z++) {
					XCoordinate cx = new XCoordinate(x);
					YCoordinate cy = new YCoordinate(y);
					ZCoordinate cz = new ZCoordinate(z);
					Cublet cublet = cublets[x][y][z];
					if (!(cublet.isPermuted(cx, cy, cz) && cublet.isOriented()))
						return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns a new Cube that is the result of turning the side in the given plane
	 * once clockwise
	 */
	public Cube turnClockwise(Coordinate plane) {
		return null;
	}

	/**
	 * Return whether this Cube is the same as that Cube
	 */
	public boolean equals(Cube that) {
		for (int x = 0; x < NUM_SIDES; x++) {
			for (int y = 0; y < NUM_SIDES; y++) {
				for (int z = 0; z < NUM_SIDES; z++) {
					if (!this.cublets[x][y][z].equals(that.cublets[x][y][z]))
						return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns a string representation of this cube
	 */
	public String toString() {
		// Make mappings from array indices to cube notation letters for x, y, and
		// z coordinates
		RedBlackBST<Integer, String> mapX = new RedBlackBST<Integer, String>(); 
		RedBlackBST<Integer, String> mapY = new RedBlackBST<Integer, String>(); 
		RedBlackBST<Integer, String> mapZ = new RedBlackBST<Integer, String>();
		mapX.put(B.value(), "B");
		mapX.put(F.value(), "F");
		mapY.put(L.value(), "L");
		mapY.put(R.value(), "R");
		mapZ.put(D.value(), "D");
		mapZ.put(U.value(), "U");

		StringBuilder s = new StringBuilder();
		for (int x = 0; x < NUM_SIDES; x++) {
			for (int y = 0; y < NUM_SIDES; y++) {
				for (int z = 0; z < NUM_SIDES; z++) {
					// Construct the cube notation for the next cublet using its
					// coordinates
					String cubletLabel = "";
					int type = typeOfCublet(x, y, z);
					switch (type) {
						case CUBLET_EDGE:
							if (x == EDGE.value())
								cubletLabel = mapY.get(y) + mapZ.get(z);
							else if (y == EDGE.value())
								cubletLabel = mapX.get(x) + mapZ.get(z);
							else
								cubletLabel = mapX.get(x) + mapY.get(y);
							break;
						case CUBLET_CORNER:
							cubletLabel = mapX.get(x) + mapY.get(y) + mapZ.get(z);
							break;
						case CUBLET_CENTER:
							cubletLabel = "Center";
							break;
						case CUBLET_CORE:
							cubletLabel = "Core";
							break;
						default:
							throw new RuntimeException("Something went wrong determining the type of cublet.");
					}

					// Combine the cublet's label with the cublet's string
					s.append(cubletLabel + "\n" + cublets[x][y][z].toString() + "\n\n");
				}
			}
		}

		return s.toString();
	}
	
}