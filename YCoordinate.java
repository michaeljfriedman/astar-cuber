/***********************************************************************************
* Author: Michael Friedman
* Created: 9/5/16
*
*
* Description: This object represents a y coordinate.
***********************************************************************************/

public class YCoordinate implements Coordinate {

	// Instance variables
	private int value;

	// Constructors
	public YCoordinate(int value) {
		this.value = value;
	}

	// Methods
	public int value() {
		return value;
	}

}