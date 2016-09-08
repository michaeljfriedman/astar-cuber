/***********************************************************************************
* Author: Michael Friedman
* Created: 9/5/16
*
*
* Description: This class represents a corner on the cube. Adopts the Cublet's
* convention that an orientation of 0 = oriented. Adds the convention that
* orientation 1 = twisted clockwise, and 2 = counter-clockwise.
***********************************************************************************/

public class Corner extends Cublet {

	// Additional constants

	// The number of colors on a corner
	private static final int NUM_COLORS = 3;


	// Constructors

	/**
	 * Construct a Corner given its solved position (x, y, z) and its orientation
	 */
	public Corner(XCoordinate solvedX, YCoordinate solvedY, ZCoordinate solvedZ,
		int orientation) {
		super(solvedX, solvedY, solvedZ, orientation);
	}

	/**
	 * Construct a copy of the given Corner
	 */
	public Corner(Corner corner) {
		super(corner);
	}
	


	// Additional methods

	/**
	  * Returns a new Corner representing this corner twisted clockwise
	  */
	public Corner twistClockwise() {
		int orientationClockwise = (orientation + 1) % NUM_COLORS;
		return new Corner(solvedX, solvedY, solvedZ, orientationClockwise);
	}

	/**
	 * Returns a new Corner representing this corner twiseted counter-clockwise
	 */
	public Corner twistCounterClockwise() {
		int orientationCounterClockwise = (orientation - 1) % NUM_COLORS;
		return new Corner(solvedX, solvedY, solvedZ, orientationCounterClockwise);
	}

}