/***********************************************************************************
* Author: Michael Friedman
* Created: 9/5/16
*
*
* Description: This object represents an x coordinate.
***********************************************************************************/

public class XCoordinate implements Coordinate {

	// Instance variables
	private int value;

	// Constructors
	public XCoordinate(int value) {
		this.value = value;
	}

	// Methods
	public int value() {
		return value;
	}

}