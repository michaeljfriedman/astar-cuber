/***********************************************************************************
* Author: Michael Friedman
* Created: 9/13/16
*
* Description: This is a Rubik's Cube solver object that uses the A* algorithm. It
* aims to solve the Rubik's Cube using the smallest number of turns. (By convention,
* only quarter turns are considered single turns.) The algorithm used is based on
* a measurement of "distance" of the current cube from a solved cube, and has been
* implemented so that the distance heuristic is defined using a separate interface.
* Thus, this solver program can be easily adapted to use any distane heuristic that
* implements that interface (see DistanceHeuristic).
***********************************************************************************/

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class CubeSolver {

	// Instance variables

	/**
	 * The NeighborNode holding the current state of the Cube being solved
	 */
	private NeighborNode neighborNode;

	/**
	 * The DistanceHeuristic providing the definition of distance on cubes for this
	 * solver
	 */
	private DistanceHeuristic distanceHeuristic;
	
	/**
	 * A min-priority-queue of NeighborNodes that uses a DistanceHeuristic to
	 * compare priority of the Cubes contained in the NeighborNodes
	 */
	private MinPQ<NeighborNode> neighborsPQ;

	/**
	 * The string representation of the algorithm to solve the cube, as a 
	 * comma-separated list of turns
	 */
	private String solveAlgorithm;


	// Helper classes

	/**
	 * A container with a reference to a neighbor Cube and the turn made to get to
	 * that neighbor from the preceding Cube
	 */
	private class NeighborContainer {
		private Cube neighbor;
		private String turn;

		private NeighborContainer(Cube neighbor, String turn) {
			this.neighbor = neighbor;
			this.turn = turn;
		}
	}
	

	/**
	 * A container with a reference to the current Cube and the neighbor that
	 * preceded it
	 */
	private class NeighborNode implements Comparable<NeighborNode> {
		private NeighborContainer neighborContainer;
		private NeighborNode prev;

		private NeighborNode(NeighborContainer neighborContainer, NeighborNode prev) {
			this.neighborContainer = neighborContainer;
			this.prev = prev;
		}

		/**
		 * Using the DistanceHeuristic of the CubeSolver (outer class) as a
		 * definition of distance, returns greater than 0 if the cube represented by
		 * this NeighborNode has greater distance than that of the other
		 * NeighborNode, less than 0 if it has smaller distance, and 0 if they have
		 * the same distance
		 */
		public int compareTo(NeighborNode that) {
			Cube thisCube = this.neighborContainer.neighbor;
			Cube thatCube = that.neighborContainer.neighbor;
			int cmp = distanceHeuristic.distance(thisCube) - distanceHeuristic.distance(thatCube);
			if 		(cmp > 0) return +1;
			else if (cmp < 0) return -1;
			else 			  return  0;
		}
		
	}


	// Constructors

	/**
	 * Initializes a CubeSolver, and solves the given Cube using the given
	 * DistanceHeuristic for the A* algorithm. Throws an error if the Cube is
	 * invalid or not solvable.
	 */
	public CubeSolver(Cube cube, DistanceHeuristic distanceHeuristic) {
		if (cube == null || distanceHeuristic == null)
			throw new NullPointerException("Arguments are null");

		this.distanceHeuristic 	= distanceHeuristic;
		this.neighborNode		= new NeighborNode(new NeighborContainer(new Cube(cube), null), null);
		this.neighborsPQ 		= new MinPQ<NeighborNode>();

		// Solve the cube
		Cube workingCube = neighborNode.neighborContainer.neighbor;
		while (!workingCube.isSolved()) {
		    for (NeighborContainer neighborContainer : neighbors(workingCube)) {
		    	neighborsPQ.insert(new NeighborNode(neighborContainer, neighborNode));
		    }
		    neighborNode = neighborsPQ.delMin();
		    workingCube = neighborNode.neighborContainer.neighbor;
		}

		// Retrace the algorithm
		Stack<String> algorithm = new Stack<String>();
		while (neighborNode != null) {
			algorithm.push(neighborNode.neighborContainer.turn);
			neighborNode = neighborNode.prev;
		}

		StringBuilder s = new StringBuilder();
		for (String turn : algorithm) {
			s.append(turn + ",");
		}
		s.deleteCharAt(s.length() - 1);	// remove lingering comma
		this.solveAlgorithm = s.toString();
	}


	// Helper methods

	/**
	 * Returns the NeighborContainers for all the neighbors of the provided Cube -
	 * that is, all the Cubes that can be reached from the one provided by making a
	 * single turn
	 */
	private Iterable<NeighborContainer> neighbors(Cube cube) {
		Stack<NeighborContainer> neighbors = new Stack<NeighborContainer>();
		XCoordinate[] sidesX = { Cube.B, Cube.F };
		YCoordinate[] sidesY = { Cube.L, Cube.R };
		ZCoordinate[] sidesZ = { Cube.D, Cube.U };

		int sidesPerAxis = sidesX.length;
		for (int i = 0; i < sidesPerAxis; i++) {
			// Clockwise turns
			Cube cwX = cube.turnClockwise(sidesX[i]);
			Cube cwY = cube.turnClockwise(sidesY[i]);
			Cube cwZ = cube.turnClockwise(sidesZ[i]);
			neighbors.push(new NeighborContainer(cwX, Cube.clockwiseTurnToString(sidesX[i])));
			neighbors.push(new NeighborContainer(cwY, Cube.clockwiseTurnToString(sidesY[i])));
			neighbors.push(new NeighborContainer(cwZ, Cube.clockwiseTurnToString(sidesZ[i])));

			// Counter-clockwise turns
			Cube ccwX = cube.turnCounterClockwise(sidesX[i]);
			Cube ccwY = cube.turnCounterClockwise(sidesY[i]);
			Cube ccwZ = cube.turnCounterClockwise(sidesZ[i]);
			neighbors.push(new NeighborContainer(ccwX, Cube.counterClockwiseTurnToString(sidesX[i])));
			neighbors.push(new NeighborContainer(ccwY, Cube.counterClockwiseTurnToString(sidesY[i])));
			neighbors.push(new NeighborContainer(ccwZ, Cube.counterClockwiseTurnToString(sidesZ[i])));
		}

		return neighbors;
	}
	

	// Getters

	/**
	 * Returns the algorithm for solving the cube as a string of moves in standard
	 * cube notation
	 */
	public String getSolveAlgorithm() {
		return solveAlgorithm;
	}
	


	// Client

	/**
	 * A client program that solves the cube from the filename and the name of the
	 * distanceHeuristic passed as arguments
	 */
	public static void main(String[] args) {
		String filename = args[0];
		String distanceHeuristicName = args[1];

		// TODO: Is there a better way to initialize a DistanceHeuristic from arg?
		DistanceHeuristic distanceHeuristic = null;
		try {
			distanceHeuristic = (DistanceHeuristic) Class.forName(distanceHeuristicName)
				.getConstructor()
				.newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}
		Cube cube = new Cube(filename);
		System.out.println("Solving cube.....");
		CubeSolver cubeSolver = new CubeSolver(cube, distanceHeuristic);
		System.out.print("Solution: ");
		System.out.println(cubeSolver.getSolveAlgorithm());
	}
}