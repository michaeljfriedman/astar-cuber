/***********************************************************************************
* Author: Michael Friedman
* Created: 9/5/16
*
*
* Description: This is the superclass for all cublets, which are the pieces that
make up the cube.
***********************************************************************************/

public class Cublet {

	// Constants
	public static final int ORIENTED = 0;

	// Instance variables

	// Solved position (x, y, z)
	protected XCoordinate solvedX;
	protected YCoordinate solvedY;
	protected ZCoordinate solvedZ;

	// Orientation: 0 = oriented, other values are varying levels of disoriented,
	// depending on the implementation in the subclass
	protected int orientation;


	// Constructors

	/**
	  * Construct a Cublet given its solved position (x, y, z) and its orientation
	  */
	public Cublet(XCoordinate solvedX, YCoordinate solvedY, ZCoordinate solvedZ,
		int orientation) {

		this.solvedX = solvedX;
		this.solvedY = solvedY;
		this.solvedZ = solvedZ;
		this.orientation = orientation;
	}


	// Methods

	/**
	 * Returns true if this Cublet is in its correct position, false if not
	 */
	public boolean isPermuted(XCoordinate currentX, YCoordinate currentY,
		ZCoordinate currentZ) {

		return 	solvedX.value() == currentX.value()
				&& solvedY.value() == currentY.value()
				&& solvedZ.value() == currentZ.value();
	}

	/**
	 * Returns true if this Cublet is correctly oriented, false if not
	 */
	public boolean isOriented() {
		return orientation == ORIENTED;
	}

	/**
	 * Returns a String representation of this Cublet
	 */
	public String toString() {
		String position = String.format(
			"(%d, %d, %d)",
			solvedX.value(),
			solvedY.value(),
			solvedZ.value()
		);

		return 	"Position: " + position + "\n"
				+ "Orientation: " + orientation;
	}

}