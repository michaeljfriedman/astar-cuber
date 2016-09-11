/***********************************************************************************
* Author: Michael Friedman
* Created: 9/6/16
*
*
* Description: This class represents a center on the cube. Since it only has one
* possible orientation, no orientation is required to be passed in.
***********************************************************************************/

public class Center extends Cublet {

	/**
	 * Construct a Center given its solved position (x, y, z)
	 */
	public Center(XCoordinate solvedX, YCoordinate solvedY, ZCoordinate solvedZ) {
		super(solvedX, solvedY, solvedZ, Cublet.ORIENTED);
	}

	/**
	 * Construct a copy of the given Center
	 */
	public Center(Center center) {
		super(center);
	}
	
}