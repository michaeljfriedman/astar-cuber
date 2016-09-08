/***********************************************************************************
* Author: Michael Friedman
* Created: 9/5/16
*
*
* Description: This class represents an edge on the cube. Adopts the Cublet's
* convention that an orientation of 0 = oriented. Adds the convention that an
* orientation of 1 = disoriented/"flipped"
***********************************************************************************/

public class Edge extends Cublet {

	// Additional constants

	// The number of colors on an edge
	private static final int NUM_COLORS = 2;


	// Constructors

	/**
	 * Construct an Edge given its solved position (x, y, z) and its orientation
	 */
	public Edge(XCoordinate solvedX, YCoordinate solvedY, ZCoordinate solvedZ,
		int orientation) {
		super(solvedX, solvedY, solvedZ, orientation);
	}

	/**
	 * Construct a copy of the given Edge
	 */
	public Edge(Edge edge) {
		super(edge);
	}
	


	// Additional methods

	/**
	 * Returns a new Edge representing this edge with a flipped orientation
	 */
	public Edge flip() {
		int orientationFlipped = (orientation + 1) % NUM_COLORS;
		return new Edge(solvedX, solvedY, solvedZ, orientationFlipped);
	}
}