/***********************************************************************************
* Author: Michael Friedman
* Created: 9/5/16
*
*
* Description: This is the superclass for a Coordinate. It is a wrapper for a value,
* intended to provide strict typing for x, y, and z coordinates separately
***********************************************************************************/

public class Coordinate {

	// Instance variables

	private int value;

	
	// Constructors

	public Coordinate(int value) {
		this.value = value;
	}


	// Methods

	/**
	 * Returns the value of this Coordinate
	 */
	public int value() {
		return value;
	}
	
}