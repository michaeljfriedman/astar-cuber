/***********************************************************************************
* Author: Michael Friedman
* Created: 9/13/16
*
* Description: This is a command-line program for simulating turns on the Rubik's
* Cube. Enter a move to make, and optionally print out the full state of the cube:
* the orientations and positions of every cublet.
***********************************************************************************/

import java.util.Scanner;

public class CubeSimulator {

	private static final String EXIT = "exit";
	private static final String YES  = "y";
	private static final String NO 	 = "n";
	private static final String F 	 = "F";
	private static final String B    = "B";
	private static final String L    = "L";
	private static final String R 	 = "R";
	private static final String U 	 = "U";
	private static final String D 	 = "D";

	private static void testForExit(String cmd) {
		if (cmd.equals(EXIT)) System.exit(0);
	}

	private static boolean isYesOrNo(String cmd) {
		if (cmd.equals(YES) || cmd.equals(NO)) return true;
		return false;
	}

	private static boolean isValidTurn(String cmd) {
		if (cmd.equals(F) || cmd.equals(B) || cmd.equals(L) || cmd.equals(R) || cmd.equals(U) || cmd.equals(D))
			return true;
		return false;
	}

	public static void main(String[] args) {
		// Initialization
		System.out.println("Initializing solved cube.....");
		Cube cube = new Cube();
		System.out.println("Done!\n");

		// Instructions
		System.out.println("You can enter any of the following moves: F, B, R, L, U, D.");
		System.out.println("You can also enter 'exit' at any time to exit.");

		// User interaction
		Scanner input = new Scanner(System.in);
		String cmd = "";
		while (true) {
			// Get next turn
			while (!isValidTurn(cmd)) {
				System.out.println("Enter a move:");
				cmd = input.next();
				testForExit(cmd);
			}

			// Parse and apply turn
			String turn = cmd;
			switch (turn) {
				case F:
					cube = cube.turnClockwise(Cube.F);
					break;
				case B:
					cube = cube.turnClockwise(Cube.B);
					break;
				case R:
					cube = cube.turnClockwise(Cube.R);
					break;
				case L:
					cube = cube.turnClockwise(Cube.L);
					break;
				case U:
					cube = cube.turnClockwise(Cube.U);
					break;
				case D:
					cube = cube.turnClockwise(Cube.D);
					break;
				default:
					throw new RuntimeException("The turn was not valid. (This error should never be thrown).");
			}

			// Ask if user wants to print out the state of the cube
			while (!isYesOrNo(cmd)) {
				System.out.println("Print out the cube? (y/n)");
				cmd = input.next();
				testForExit(cmd);
			}

			if (cmd.equals(YES)) System.out.println(cube);
		}
	}
}