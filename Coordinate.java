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

	/**
	 * Construct a Coordinate with the given integer value
	 */
	public Coordinate(int value) {
		this.value = value;
	}

	/**
	 * Construct a new Coordinate with the same value as the Coordinate given
	 */
	public Coordinate(Coordinate coordinate) {
		this.value = coordinate.value();
	}
	

	// Methods

	/**
	 * Returns the value of this Coordinate
	 */
	public int value() {
		return value;
	}

	/**
	 * Returns wheter this Coordinate's value is the same as that Coordinate's
	 * value
	 */
	public boolean equals(Coordinate that) {
		return this.value() == that.value();
	}
	
	
}