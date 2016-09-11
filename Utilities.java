/***********************************************************************************
* Author: Michael Friedman
* Created: 9/9/16
*
* Description: This is a library of utility constants and methods for other classes
* in this project.
***********************************************************************************/

public class Utilities {

	// Constants

	// Integer labels to identify types of Cublets
	public static final int CUBLET_EDGE 	= 0;
	public static final int CUBLET_CORNER 	= 1;
	public static final int CUBLET_CENTER	= 2;
	public static final int CUBLET_CORE 	= 3;


	// Methods

	/**
	 * Returns the number of values in the given array that equal the query value
	 */
	private static int numValuesEqualTo(int query, int[] values) {
		int numEqual = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == query) numEqual++;
		}
		return numEqual;
	}
	
	/**
	 * Returns the type of Cublet whose solved position is (x, y, z)
	 */
	public static int typeOfCublet(int x, int y, int z) {
		// The type of cublet depends entirely on how many coordinates have value 1
		int numValuesEqualTo1 = numValuesEqualTo(1, new int[] { x, y, z });
		if 		(numValuesEqualTo1 == 3) 	return CUBLET_CORE;
		else if (numValuesEqualTo1 == 2) 	return CUBLET_CENTER;
		else if (numValuesEqualTo1 == 1) 	return CUBLET_EDGE;
		else 								return CUBLET_CORNER;
	}	
}