/***********************************************************************************
* Author: Michael Friedman
* Created: 9/5/16
*
*
* Description: This object represents a z coordinate.
***********************************************************************************/

public class ZCoordinate implements Coordinate {

	// Instance variables
	private int value;

	// Constructors
	public ZCoordinate(int value) {
		this.value = value;
	}

	// Methods
	public int value() {
		return value;
	}

}