/***********************************************************************************
* Author: Michael Friedman
* Created: 9/13/16
*
* Description: DistanceHeuristic is an interface for an object that defines a
* measure of how close a cube is to being solved. (See API for details)
***********************************************************************************/

public interface DistanceHeuristic {

	/**
	 * Returns the distance from the given cube to a solved cube
	 */
	public int distance(Cube cube);

}