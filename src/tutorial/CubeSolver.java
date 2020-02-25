package tutorial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Point3D;
import javafx.scene.control.Button;

public class CubeSolver {
	
	
	/*
	 * F = F
	 * R = R
	 * U = U
	 * B = B
	 * L = L
	 * D = D
	 * 
	 * F' = G
	 * R' = T
	 * U' = I
	 * B' = N
	 * L' = K
	 * D' = S
	 * 
	 * Steps (according to return strings):
	 * 1 - Initial Cross, putting all cross pieces in their positions
	 * 2 - Proper Cross, orienting all cross pieces correctly.
	 * 3 - Corners of the First Layer, inserting all pieces in orientation.
	 */
		
	static ArrayList<Button> buttonArray;
	static int[] edgeStickers = {1,3,5,7,10,12,14,16,19,21,23,25,28,30,32,34,37,39,41,43,46,48,50,52};
	static int[] cornerStickers = {0,2,6,8,9,11,15,17,18,20,24,26,27,29,33,35,36,38,42,44,45,47,51,53};
	static int[][] edges = {{1,46},{3,28},{5,37},{7,10},{19,16},{21,34},{23,43},{25,52},{12,32},{14,39},{48,41},{50,30}};
	static int[][] corners = {{6,9,29},{8,36,11},{2,45,38},{0,27,47},{18,35,15},{20,17,42},{26,44,51},{24,53,33}};
		
	static ArrayList<String> allMoves = new ArrayList<String>();
	
	static String[] solvedCrossEdges = {"UF","UR","UB","UL"};
	static String[] flippedCrossEdges = {"FU","RU","BU","LU"};
	static String[] edgeLetters = {"FD", "FL", "FR", "FU", "CLD", "CRD", "CLU", "CRU", "BD", "BL", "BR", "BU"};
	static String[] cornerLetters = {"FRU", "BRU", "BLU", "FLU", "FRD", "BRD", "BLD", "FLD"};
	
	static final int RED     = 0;
	static final int GREEN   = 1;
	static final int BLUE    = 2;
	static final int YELLOW  = 3;
	static final int ORANGE  = 4;
	static final int WHITE   = 5;
    static final int GRAY    = 6;
    
    private static final int F_FACE = 0;
    private static final int R_FACE = 1;
    private static final int U_FACE = 2;
    private static final int B_FACE = 3;
    private static final int L_FACE = 4;
    private static final int D_FACE = 5;
    
    static int[] FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
    			 CLD, CD, CRD, CL, CR, CLU, CU, CRU,
    			 BLD, BD, BRD, BL, B, BR, BLU, BU, BRU;
	
	
	public static ArrayList<String> deriveSolution(ArrayList<Button> buttonArray) {

		F = Arrays.copyOf(UserInterface.F, 6); B = Arrays.copyOf(UserInterface.B, 6);
		CL = Arrays.copyOf(UserInterface.CL, 6); CR = Arrays.copyOf(UserInterface.CR, 6);
		CU = Arrays.copyOf(UserInterface.CU, 6); CD = Arrays.copyOf(UserInterface.CD, 6);
		
		FLD = Arrays.copyOf(UserInterface.FLD, 6); FD = Arrays.copyOf(UserInterface.FD, 6);
		FRD = Arrays.copyOf(UserInterface.FRD, 6); FL = Arrays.copyOf(UserInterface.FL, 6);
		FR = Arrays.copyOf(UserInterface.FR, 6); FLU = Arrays.copyOf(UserInterface.FLU, 6); 
		FU = Arrays.copyOf(UserInterface.FU, 6); FRU = Arrays.copyOf(UserInterface.FRU, 6);
		CLD = Arrays.copyOf(UserInterface.CLD, 6); CRD = Arrays.copyOf(UserInterface.CRD, 6); 
		CLU = Arrays.copyOf(UserInterface.CLU, 6); CRU = Arrays.copyOf(UserInterface.CRU, 6);
		BLD = Arrays.copyOf(UserInterface.BLD, 6); BD = Arrays.copyOf(UserInterface.BD, 6); 
		BRD = Arrays.copyOf(UserInterface.BRD, 6); BL = Arrays.copyOf(UserInterface.BL, 6);
		BR = Arrays.copyOf(UserInterface.BR, 6); BLU = Arrays.copyOf(UserInterface.BLU, 6); 
		BU = Arrays.copyOf(UserInterface.BU, 6); BRU = Arrays.copyOf(UserInterface.BRU, 6);
				
		CubeSolver.buttonArray = buttonArray;
		String crossEdgeLoc = findCrossEdge(1); //Green, Red, Blue, Orange
		//System.out.println(crossEdgeLoc);
		String movesToSolve = solveCrossEdge(crossEdgeLoc, "FU");
		//System.out.println("Moves to solve White-Green edge: "+movesToSolve);
		applyMovesLogically(movesToSolve);
		allMoves.add("1"+movesToSolve);
		
		crossEdgeLoc = findCrossEdge(0); //Green, Red, Blue, Orange 1,0,2,4
		//System.out.println(crossEdgeLoc);
		movesToSolve = solveCrossEdge(crossEdgeLoc, "CRU");
		//System.out.println("Moves to solve White-Red edge: "+movesToSolve);
		applyMovesLogically(movesToSolve);
		allMoves.add("1"+movesToSolve);
		
		crossEdgeLoc = findCrossEdge(2); //Green, Red, Blue, Orange 1,0,2,4
		//System.out.println(crossEdgeLoc);
		movesToSolve = solveCrossEdge(crossEdgeLoc, "BU");
		//System.out.println("Moves to solve White-Blue edge: "+movesToSolve);
		applyMovesLogically(movesToSolve);
		allMoves.add("1"+movesToSolve);
		
		crossEdgeLoc = findCrossEdge(4); //Green, Red, Blue, Orange 1,0,2,4
		//System.out.println(crossEdgeLoc);
		movesToSolve = solveCrossEdge(crossEdgeLoc, "CLU");
		//System.out.println("Moves to solve White-Orange edge: "+movesToSolve);
		applyMovesLogically(movesToSolve);
		allMoves.add("1"+movesToSolve);
		
		//At this point all croSs pieces should be in their place, but they might be flipped. If they're flipped, we need to check and fix
		//them one by one by taking them out of their slot and sledging them back in. The below function will check this for each edge.
		
		orientCrossEdges();
		
		processFirstLayerCorners();

		applyMovesLogically("ZZ");
		
		processSecondLayerEdges();
		
		solveYellowCross();
		
		solveYellowCorners();
		
		permuteCorners();
		
		permuteEdges();
		
		for (int i = 0; i < allMoves.size(); i++) {
			System.out.println(i+") "+allMoves.get(i));
		}
		//allMoves.add(movesToSolve);
		
		return allMoves;
	}
	
	private static void permuteEdges() {
		switch (FU[0]) {
		case GREEN: 
			if (CRU[1] == ORANGE) { 
				allMoves.add("D*");
				allMoves.add("E*");
			} else {
				uPerm('F');
			} break;
		case BLUE: 
			if (BU[3] == GREEN) {
				allMoves.add("DRIRURURITIRR");
				allMoves.add("EURIRURURITIRRU");
				applyMovesLogically("RIRURURITIRR");
				applyMovesLogically("URIRURURITIRRI");
			} else {
				if (CRU[1] == ORANGE) {
					uPerm('R');
				} else {
					uPerm('L');
				}
			} break;
		case ORANGE: 
			if (BU[3] == BLUE) {
				uPerm('B');
			} else if (CLU[4] == RED) {
				uPerm('L');
			} else {
				allMoves.add("DRIRURURITIRR");
				allMoves.add("EIRIRURURITIRRU");
				applyMovesLogically("RIRURURITIRR");
				applyMovesLogically("IRIRURURITIRRU");
			} break;
		case RED: 
			if (BU[3] == BLUE) {
				uPerm('B');
			} else if (CRU[1] == ORANGE) {
				uPerm('R');
			} else {
				allMoves.add("DRIRURURITIRR");
				allMoves.add("EUURRURUTITITUTUU");
				applyMovesLogically("RIRURURITIRR");
				applyMovesLogically("UURRURUTITITUTUU");
			} break;
		}
	}
	
	
	
	private static void permuteCorners() {
		boolean[] headlightArray = {false, false, false, false};
		int hlCount = 0;
		
		if (FRU[0] == FLU[0]) { //headlights on F
			headlightArray[0] = true;
			hlCount++;
		}
		if (FRU[1] == BRU[1]) { //headlights on R
			headlightArray[1] = true;
			hlCount++;
		}
		if (BRU[3] == BLU[3]) { //headlights on B
			headlightArray[2] = true;
			hlCount++;
		}
		if (BLU[4] == FLU[4]) { //headlights on L
			headlightArray[3] = true;
			hlCount++;
		}
		
		if (hlCount == 0) { //E perm, 1/21 prob:
			allMoves.add("BTFTBBRGTBBRR");
			applyMovesLogically("TFTBBRGTBBRR");
			applyMovesLogically("UTFTBBRGTBBRR");
			switch (FRU[0]) {
			case GREEN: allMoves.add("CUTFTBBRGTBBRR"); break;
			case ORANGE: allMoves.add("CUTFTBBRGTBBRRI"); applyMovesLogically("I"); break;
			case BLUE: allMoves.add("CUTFTBBRGTBBRRUU"); applyMovesLogically("UU");  break;
			case RED: allMoves.add("CUTFTBBRGTBBRRU"); applyMovesLogically("U"); break;
			}
		} else if (hlCount == 1) { //Most likely case:
			String pre = "*";
			if (headlightArray[0]) {
				pre = "UU";
			} else if (headlightArray[1]) {
				pre = "I";
			} else if (headlightArray[3]) {
				pre = "U";
			} 
			allMoves.add("B"+pre);
			applyMovesLogically(pre+"TFTBBRGTBBRR");
			switch (FRU[0]) {
			case GREEN: allMoves.add("CTFTBBRGTBBRR"); break;
			case ORANGE: allMoves.add("CTFTBBRGTBBRRI"); applyMovesLogically("I"); break;
			case BLUE: allMoves.add("CTFTBBRGTBBRRUU"); applyMovesLogically("UU"); break;
			case RED: allMoves.add("CTFTBBRGTBBRRU"); applyMovesLogically("U"); break;
			}
		} else { //CLL already solved
			allMoves.add("B*");
			switch (FRU[0]) {
			case GREEN: allMoves.add("C*"); break;
			case ORANGE: allMoves.add("CI"); applyMovesLogically("I"); break;
			case BLUE: allMoves.add("CUU"); applyMovesLogically("UU"); break;
			case RED: allMoves.add("CU"); applyMovesLogically("U"); break;
			}
			
		}
		
		
	}
	
	
	private static void uPerm(char solved) {
		String pre = "";
		String und = "";
		switch (solved) {
		case 'F': pre = "UU"; und = "UU"; break;
		case 'R': pre = "I"; und = "U"; break;
		case 'L': pre = "U"; und = "I"; break;
		}
		
		allMoves.add("D*");
		
		if (FU[0] == FRU[1]) {
			allMoves.add("E"+pre+"RIRURURITIRR"+und);
			applyMovesLogically(pre+"RIRURURITIRR"+und);
		} else {
			allMoves.add("E"+pre+"RRURUTITITUT"+und);
			applyMovesLogically(pre+"RRURUTITITUT"+und);
		}
		
		
	}
	
	private static void solveYellowCorners() {
		int[][] topCorners = {BLU, BRU, FRU, FLU};
		int count = 0;
		
		for (int i = 0; i < 4; i++) {
			if (topCorners[i][2] == YELLOW) {
				count++;
			}
		}
		
		if (count == 0) {
			if (FLU[4] == YELLOW) {
				allMoves.add("8RUTURUUT");
				applyMovesLogically("RUTURUUT");
				if (FLU[2] == YELLOW) {
					allMoves.add("9RUTURUUT");
					applyMovesLogically("RUTURUUT");
				} else if (FRU[2] == YELLOW) {
					allMoves.add("9URUTURUUT");
					applyMovesLogically("URUTURUUT");
				} else {
					allMoves.add("9IRUTURUUT");
					applyMovesLogically("IRUTURUUT");
				}
				
			} else if (FRU[0] == YELLOW) {
				allMoves.add("8URUTURUUT");
				applyMovesLogically("URUTURUUT");
				if (FLU[2] == YELLOW) {
					allMoves.add("9RUTURUUT");
					applyMovesLogically("RUTURUUT");
				} else {
					allMoves.add("9IRUTURUUT");
					applyMovesLogically("IRUTURUUT");
				}
			} else {
				allMoves.add("8UURUTURUUT");
				applyMovesLogically("UURUTURUUT");
				allMoves.add("9IRUTURUUT");
				applyMovesLogically("IRUTURUUT");
			}
			allMoves.add("A*");
		} else if (count == 1) {
			if (FLU[2] == YELLOW) {
				pureSune("", false);
			} else if (FRU[2] == YELLOW) {
				pureSune("U", false);
			} else if (BRU[2] == YELLOW) {
				pureSune("UU", false);
			} else {
				pureSune("I", false);
			}
		} else if (count == 2) {
			if (FLU[0] == YELLOW) {
				allMoves.add("8RUTURUUT");
				applyMovesLogically("RUTURUUT");
				if (FLU[2] == YELLOW) {
					pureSune("", true);
				} else if (FRU[2] == YELLOW) {
					pureSune("U", true);
				} else if (BRU[2] == YELLOW) {
					pureSune("UU", true);
				} else {
					pureSune("I", true);
				}
			} else if (FRU[1] == YELLOW) {
				allMoves.add("8URUTURUUT");
				applyMovesLogically("URUTURUUT");
				if (FLU[2] == YELLOW) {
					pureSune("", true);
				} else if (FRU[2] == YELLOW) {
					pureSune("U", true);
				} else if (BRU[2] == YELLOW) {
					pureSune("UU", true);
				} else {
					pureSune("I", true);
				}
			} else if (BRU[3] == YELLOW) {
				allMoves.add("8UURUTURUUT");
				applyMovesLogically("UURUTURUUT");
				if (FLU[2] == YELLOW) {
					pureSune("", true);
				} else if (FRU[2] == YELLOW) {
					pureSune("U", true);
				} else if (BRU[2] == YELLOW) {
					pureSune("UU", true);
				} else {
					pureSune("I", true);
				}
			} else {
				allMoves.add("8IRUTURUUT");
				applyMovesLogically("IRUTURUUT");
				if (FLU[2] == YELLOW) {
					pureSune("", true);
				} else if (FRU[2] == YELLOW) {
					pureSune("U", true);
				} else if (BRU[2] == YELLOW) {
					pureSune("UU", true);
				} else {
					pureSune("I", true);
				}
			}
		} else {
			allMoves.add("8*");
			allMoves.add("9*");
			allMoves.add("A*");
		}
		
		
		
	}
	
	private static void pureSune(String pre, boolean var) {
		if (!var) {
			if (FRU[0] == YELLOW) {
				allMoves.add("8"+pre+"RUTURUUT");
				applyMovesLogically(pre+"RUTURUUT");
				allMoves.add("9*");
				allMoves.add("A*");
			} else {
				allMoves.add("8"+pre+"RUTURUUT");
				applyMovesLogically(pre+"RUTURUUT");
				allMoves.add("9UURUTURUUT");
				applyMovesLogically("UURUTURUUT");
				allMoves.add("A*");
			}
		} else {
			if (FRU[0] == YELLOW) {
				allMoves.add("9"+pre+"RUTURUUT");
				applyMovesLogically(pre+"RUTURUUT");
				allMoves.add("A*");
			} else {
				allMoves.add("9"+pre+"RUTURUUT");
				applyMovesLogically(pre+"RUTURUUT");
				allMoves.add("AUURUTURUUT");
				applyMovesLogically("UURUTURUUT");
			}
		}
	}
	
	private static void solveYellowCross() {
		
		int[][] topEdges = {BU, CRU, FU, CLU};
		boolean[] edgeOrientations = {false, false, false, false};
		
		for (int i = 0; i < 4; i++) {
			if (topEdges[i][2] == YELLOW) {
				edgeOrientations[i] = true;
			}
		}
		
		if (edgeOrientations[0]) { //If UB yellow:
			if (edgeOrientations[1]) { //If UR yellow:
				if (edgeOrientations[2]) { //If UF yellow:
					allMoves.add("7*"); //Cross already solved.
				} else {
					allMoves.add("7IFURITG"); //U' to adj edge case
					applyMovesLogically("IFURITG");
					return;
				}
			} else if (edgeOrientations[2]) { //If UF yellow:
				allMoves.add("7UFRUTIG"); //U to opp edge case
				applyMovesLogically("UFRUTIG");
				return;
			} else {
				allMoves.add("7FURITG"); //Pure adj edge case
				applyMovesLogically("FURITG");
				return;
			}
		} else if (edgeOrientations[1]) { //If UR yellow:
			if (edgeOrientations[2]) { //If UF yellow:
				allMoves.add("7UUFURITG"); //U2 to adj edge case
				applyMovesLogically("UUFURITG");
				return;
			} else {
				allMoves.add("7FRUTIG"); //Pure opp edge case
				applyMovesLogically("FRUTIG");
				return;
			}
		} else if (edgeOrientations[2]) {
			allMoves.add("7UFURITG"); //U to adj edge case
			applyMovesLogically("UFURITG");
			return;
		} else {
			allMoves.add("7FRUTIGUUUFURITG"); //dot case
			applyMovesLogically("FRUTIGUUUFURITG");
			return;
		}
	}

	private static void processFirstLayerCorners() {
		String movesToSolve;
		String cornerFLloc;
		
		for (int i = 0; i < 4; i++) {
			cornerFLloc = findFLCorner(i);
			System.out.println("found corner "+i+", it's in "+cornerFLloc);
			movesToSolve = bringCornerDown(cornerFLloc);
			if (movesToSolve.equals("SOLVED")) {
				applyMovesLogically("Y");
				allMoves.add("3*");	
				allMoves.add("4*");
			} else if (movesToSolve.equals("NULL")) {
				allMoves.add("3*");	
				allMoves.add("4"+insertCorner());
			} else {
				applyMovesLogically(movesToSolve);
				allMoves.add("3"+movesToSolve);
				allMoves.add("4"+insertCorner());
			}
			//applyMovesLogically("Y");
		}
		//System.out.println("B: "+FRD[3]+", L: "+FRD[4]+", D: "+FRD[5]);
		//applyMovesLogically("DD");
		//System.out.println("R: "+FRD[1]+", F: "+FRD[0]+", D: "+FRD[5]);
		//System.out.println("movesToSolve: "+insertCorner());
	}

	private static void processSecondLayerEdges() {
		String destination = "";
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0: destination = "FR";
			case 1: destination = "BR";
			case 2: destination = "BL";
			case 3: destination = "FL";
			}
			String edgeLoc = findSLedge(i);
			System.out.println(edgeLoc+" is the location for edge #"+i);
			String movesToSolve = positionEdge(edgeLoc);
			if (movesToSolve.equals("SOLVED")) {
				applyMovesLogically("Y");
				allMoves.add("5*");	
				allMoves.add("6*");
			} else if (movesToSolve.equals("NULL")) {
				allMoves.add("5*");	
				allMoves.add("6"+insertSLedge());
			} else {
				applyMovesLogically(movesToSolve);
				allMoves.add("5"+movesToSolve);
				allMoves.add("6"+insertSLedge());
			}
		}
		
		
	}
	
	private static String findSLedge(int e) {
		int[][] slEdges = {FL, FR, FU, CLU, CRU, BL, BR, BU};
		String[] slEdgeLetters = {"FL", "FR", "FU", "LU", "RU", "BL", "BR", "BU"};
		int count = 0;
		int col1 = 0; 
		int col2 = 0;
		
		switch (e) {
		case 0: col1 = 1; col2 = 4; break; //Green-Orange Edge
		case 1: col1 = 4; col2 = 2; break; //Orange-Blue Edge
		case 2: col1 = 2; col2 = 0; break; //Blue-Red Edge
		case 3: col1 = 0; col2 = 1; break; //Red-Green Edge
		}
		
		final int colour1 = col1;
		final int colour2 = col2;
		
		for (int[] edge : slEdges) {
			if(Arrays.stream(edge).anyMatch(i -> i == colour1)) {
				System.out.println("Edge "+slEdgeLetters[count]+" matches edge on "+colour1);
				if(Arrays.stream(edge).anyMatch(i -> i == colour2)) {
					System.out.println("Edge "+count+" matches edge on "+colour2);
					return slEdgeLetters[count];
				} 
			}
			count++;
		}
		
		return "NA";
	}
	
	private static String positionEdge(String slEdgeLoc) {
		
		if (slEdgeLoc.toCharArray()[1] == 'U') {
			switch (slEdgeLoc.toCharArray()[0]) {
			case 'F': return ("NULL");
			case 'B': return ("UU");
			case 'R': return ("U");
			case 'L': return ("I");
			}
		} else if (slEdgeLoc.toCharArray()[0] == 'F') {
			switch (slEdgeLoc.toCharArray()[1]) {
			case 'R': return checkFlipped();
			case 'L': return ("QRUTIGIFIY");
			}
		} else if (slEdgeLoc.toCharArray()[0] == 'B') {
			switch (slEdgeLoc.toCharArray()[1]) {
			case 'R': return ("YRUTIGIFIQ");
			case 'L': return ("YYRUTIGIFIQQ");
			}
		}
		
		
		
		return "";
	}
	
	private static String checkFlipped() {
		if (FR[0] == F[0]) {
			return "SOLVED";
		} else {
			return "URUTIGIFUU";
		}
	}

	private static String insertSLedge() {
		if (FU[0] == F[0]) {
			applyMovesLogically("URUTIGIFY");
			return "URUTIGIF";
		} else {
			applyMovesLogically("YIIKILUFUG");
			return "YIIKILUFUGQ";
		}
	}

	private static String findCrossEdge(int col) {
		int[][] allEdges = {FD, FL, FR, FU, CLD, CRD, CLU, CRU, BD, BL, BR, BU};
		int count = 0;
		for (int[] edge : allEdges) {
			if(Arrays.stream(edge).anyMatch(i -> i == WHITE)) {
				if(Arrays.stream(edge).anyMatch(i -> i == col)) {
					System.out.println("Got White-Green");
					return edgeLetters[count];
				} 
			}
			count++;
		}
		
		
		return "NA";
	}
	
	private static String solveCrossEdge(String crossEdgeLoc, String destination) {
		if (destination.equals("FU")) {
			switch (crossEdgeLoc) {
			case "FD": return "FF";
			case "CRD": return "SFF";
			case "CLD": return "DFF";
			case "BD": return "DDFF";
			case "FR": return "G";
			case "FL": return "F";
			case "BR": return "RRGRR";
			case "BL": return "LLFLL";
			case "CRU": return "RRSFF";
			case "CLU": return "LLDFF";
			case "BU": return "BBDDFF";			
			default: return "*";
			}
		} else if (destination.equals("CRU")) {
			switch (crossEdgeLoc) {
			case "FD": return "DRR";
			case "CRD": return "RR";
			case "CLD": return "DDRR";
			case "BD": return "SRR";
			case "FR": return "R";
			case "FL": return "FFRFF";
			case "BR": return "T";
			case "BL": return "BBTBB";
			case "FU": return "FFDRR";
			case "CLU": return "LLDDRR";
			case "BU": return "BBSRR";			
			default: return "*";
			}
		} else if (destination.equals("BU")) {
			switch (crossEdgeLoc) {
			case "FD": return "DDBB";
			case "CRD": return "DBB";
			case "CLD": return "SBB";
			case "BD": return "BB";
			case "FR": return "RRBRR";
			case "FL": return "LLNLL";
			case "BR": return "B";
			case "BL": return "N";
			case "FU": return "FFDDBB";
			case "CRU": return "RRDBB";
			case "CLU": return "LLSBB";			
			default: return "*";
			}
		} else if (destination.equals("CLU")) {
			switch (crossEdgeLoc) {
			case "FD": return "SLL";
			case "CRD": return "DDLL";
			case "CLD": return "LL";
			case "BD": return "DLL";
			case "FR": return "FFKFF";
			case "FL": return "K";
			case "BR": return "BBLBB";
			case "BL": return "L";
			case "FU": return "FFSLL";
			case "CRU": return "RRDDLL";
			case "BU": return "BBDLL";			
			default: return "*";
			}
		}
		return "";
	}
	
	private static String findFLCorner(int cornerNum) {
		int[][] allCorners = {FRU, BRU, BLU, FLU, FRD, BRD, BLD, FLD};		
		int[] cornerToSolve = {WHITE, GRAY, GRAY};
		switch (cornerNum) {
			case 0: cornerToSolve[1] = RED; cornerToSolve[2] = GREEN; break;
			case 1: cornerToSolve[1] = BLUE; cornerToSolve[2] = RED; break;
			case 2: cornerToSolve[1] = ORANGE; cornerToSolve[2] = BLUE; break;
			case 3: cornerToSolve[1] = GREEN; cornerToSolve[2] = ORANGE; break;
		}
		System.out.println("with input "+cornerNum+", cornerToSolve: "+cornerToSolve[0]+", "+cornerToSolve[1]+", "+cornerToSolve[2]);
		int count = 0;
		
		for (int[] corner : allCorners) {
			if(Arrays.stream(corner).anyMatch(i -> i == WHITE)) {
				if(Arrays.stream(corner).anyMatch(i -> i == cornerToSolve[1])) {
					if(Arrays.stream(corner).anyMatch(i -> i == cornerToSolve[2])) {
						return cornerLetters[count];
					}
				} 
			}
			count++;
		}
		
		return("N/A");


		
	}
	
	private static String bringCornerDown(String flCornerLoc) {
		if (flCornerLoc.toCharArray()[2] == 'U') {
			switch (flCornerLoc) {
			case "FRU": if (FRU[2] == WHITE) {return "SOLVED";} else {return "TSRD";}
			case "FLU": return "QTSRDYD";
			case "BLU": return "QQTSRDYYDD";
			case "BRU": return "YTSRDQS";
			}
		} else {
			switch (flCornerLoc) {
			case "FRD": return "NULL";
			case "FLD": return "D";
			case "BLD": return "DD";
			case "BRD": return "S";
			}
		}
		return "NULL";
	}
	
	private static String insertCorner() {
		System.out.println("R: "+FRD[1]+", F: "+FRD[0]+", D: "+FRD[5]);
		if (FRD[1] == WHITE) {
			applyMovesLogically("TSRDY");
			return "TSRD";
		} else if (FRD[0] == WHITE) {
			applyMovesLogically("STDRY");
			return "STDR";
		} else {
			applyMovesLogically("TDDRDTSRDY");
			return "TDDRDTSRD";
		}
	}
	
	private static void applyMovesLogically(String moves) {
		char[] movesLeft = moves.toCharArray();
		for (char move : movesLeft) {
			switch (move) {
				case 'F': makeFmove(false); break;
				case 'R': makeRmove(false); break;
				case 'U': makeUmove(false); break;
				case 'B': makeBmove(false); break;
				case 'L': makeLmove(false); break;
				case 'D': makeDmove(false); break;
				case 'G': makeFmove(true); break;
				case 'T': makeRmove(true); break;
				case 'I': makeUmove(true); break;
				case 'N': makeBmove(true); break;
				case 'K': makeLmove(true); break;
				case 'S': makeDmove(true); break;
				case 'Y': makeYrotation(false); break;
				case 'Q': makeYrotation(true); break;
				case 'Z': makeZrotation(); break;
			}
			
		  /*switch (move) {
			case 'F': makeFmove(false); UserInterface.makeFmove(false); break;
			case 'R': makeRmove(false); UserInterface.makeRmove(false); break;
			case 'U': makeUmove(false); UserInterface.makeUmove(false); break;
			case 'B': makeBmove(false); UserInterface.makeBmove(false); break;
			case 'L': makeLmove(false); UserInterface.makeLmove(false); break;
			case 'D': makeDmove(false); UserInterface.makeDmove(false); break;
			case 'G': makeFmove(true); UserInterface.makeFmove(true); break;
			case 'T': makeRmove(true); UserInterface.makeRmove(true); break;
			case 'I': makeUmove(true); UserInterface.makeUmove(true); break;
			case 'N': makeBmove(true); UserInterface.makeBmove(true); break;
			case 'K': makeLmove(true); UserInterface.makeLmove(true); break;
			case 'S': makeDmove(true); UserInterface.makeDmove(true); break;
			case 'Y': makeYrotation(false); UserInterface.makeYrotation(false); break;
			case 'Q': makeYrotation(true); UserInterface.makeYrotation(true); break;
			case 'Z': makeZrotation(); UserInterface.makeZrotation(false); break;
			}*/
			 
		}
		
	}

	private static void orientCrossEdges() {
		if (FU[2] == WHITE) {
			System.out.println("Nothing to do, edge is oriented well.");
			allMoves.add("2"+"*");
		} else {
			System.out.println("Edge needs to be flipped.");
			allMoves.add("2"+"FFDRGT");
			applyMovesLogically("FFDRGT");
		}
		
		if (CRU[2] == WHITE) {
			System.out.println("Nothing to do, edge is oriented well.");
			allMoves.add("2"+"*");
		} else {
			System.out.println("Edge needs to be flipped.");
			allMoves.add("2"+"RRDBTN");
			applyMovesLogically("RRDBTN");
		}
		
		if (BU[2] == WHITE) {
			System.out.println("Nothing to do, edge is oriented well.");
			allMoves.add("2"+"*");
		} else {
			System.out.println("Edge needs to be flipped.");
			allMoves.add("2"+"BBDLNK");
			applyMovesLogically("BBDLNK");
		}
		
		if (CLU[2] == WHITE) {
			System.out.println("Nothing to do, edge is oriented well.");
			allMoves.add("2"+"*");
		} else {
			System.out.println("Edge needs to be flipped.");
			allMoves.add("2"+"LLDFKG");
			applyMovesLogically("LLDFKG");
		}
	}

	private static void cycleColours(int[] list, int one, int two, int three) {
		int temp = list[three];
		list[three] = list[two];
		list[two] = list[one];
		list[one] = temp;
	}
	
	private static void makeRmove(boolean prime) {
		if (prime) {
			int[] temp1 = CRU; int[] temp2 = BRU;
			CRU = BR; BRU = BRD; BR = CRD; BRD = FRD; CRD = FR; FRD = FRU; FR = temp1; FRU = temp2;
			cycleColours(FR, B_FACE, U_FACE, F_FACE);
			cycleColours(FRU, B_FACE, U_FACE, F_FACE);
			cycleColours(CRU, D_FACE, B_FACE, U_FACE);
			cycleColours(BRU, D_FACE, B_FACE, U_FACE);
			cycleColours(BR, F_FACE, D_FACE, B_FACE);
			cycleColours(BRD, F_FACE, D_FACE, B_FACE);
			cycleColours(CRD, U_FACE, F_FACE, D_FACE);
			cycleColours(FRD, U_FACE, F_FACE, D_FACE);
		} else {
			int[] temp1 = FRD; int[] temp2 = FR;		
			FR = CRD; FRD = BRD; CRD = BR; BRD = BRU; BR = CRU; BRU = FRU; CRU = temp2; FRU = temp1;
			cycleColours(FR, D_FACE, F_FACE, U_FACE);
			cycleColours(FRU, D_FACE, F_FACE, U_FACE);
			cycleColours(CRU, F_FACE, U_FACE, B_FACE);
			cycleColours(BRU, F_FACE, U_FACE, B_FACE);
			cycleColours(BR, U_FACE, B_FACE, D_FACE);
			cycleColours(BRD, U_FACE, B_FACE, D_FACE);
			cycleColours(CRD, F_FACE, B_FACE, D_FACE);
			cycleColours(FRD, F_FACE, B_FACE, D_FACE);
		}
	}
	
	private static void makeFmove(boolean prime) {
		if (prime) {
			int[] temp1 = FLU; int[] temp2 = FU;
			FLU = FRU; FU = FR; FRU = FRD; FR = FD; FRD = FLD; FD = FL; FLD = temp1; FL = temp2;
			cycleColours(FRU, D_FACE, R_FACE, U_FACE);
			cycleColours(FR, D_FACE, R_FACE, U_FACE);
			cycleColours(FRD, L_FACE, D_FACE, R_FACE);
			cycleColours(FD, L_FACE, D_FACE, R_FACE);
			cycleColours(FLD, U_FACE, L_FACE, D_FACE);
			cycleColours(FL, U_FACE, L_FACE, D_FACE);
			cycleColours(FLU, R_FACE, U_FACE, L_FACE);
			cycleColours(FU, R_FACE, U_FACE, L_FACE);			
		} else {
			int[] temp1 = FU; int[] temp2 = FLU;
			FU = FL; FLU = FLD; FL = FD; FLD = FRD; FD = FR; FRD = FRU; FR = temp1; FRU = temp2;
			cycleColours(FRU, L_FACE, U_FACE, R_FACE);
			cycleColours(FR, L_FACE, U_FACE, R_FACE);
			cycleColours(FRD, U_FACE, R_FACE, D_FACE);
			cycleColours(FD, U_FACE, R_FACE, D_FACE);
			cycleColours(FLD, R_FACE, D_FACE, L_FACE);
			cycleColours(FL, R_FACE, D_FACE, L_FACE);
			cycleColours(FLU, D_FACE, L_FACE, U_FACE);
			cycleColours(FU, D_FACE, L_FACE, U_FACE);
		}		
	}

	private static void makeBmove(boolean prime) {
		if (prime) {
			int[] temp1 = BLD; int[] temp2 = BD;
			BLD = BRD; BD = BR; BRD = BRU; BR = BU; BRU = BLU; BU = BL; BLU = temp1; BL = temp2;
			cycleColours(BLU, D_FACE, L_FACE, U_FACE);
			cycleColours(BU, D_FACE, L_FACE, U_FACE);
			cycleColours(BRU, L_FACE, U_FACE, R_FACE);
			cycleColours(BR, L_FACE, U_FACE, R_FACE);
			cycleColours(BRD, U_FACE, R_FACE, D_FACE);
			cycleColours(BD, U_FACE, R_FACE, D_FACE);
			cycleColours(BLD, R_FACE, D_FACE, L_FACE);
			cycleColours(BL, R_FACE, D_FACE, L_FACE);
		} else {
			int[] temp1 = BLU; int[] temp2 = BU;
			BLU = BRU; BU = BR; BRU = BRD; BR = BD; BRD = BLD; BD = BL; BLD = temp1; BL = temp2;
			cycleColours(BLU, R_FACE, U_FACE, L_FACE);
			cycleColours(BL, R_FACE, U_FACE, L_FACE);
			cycleColours(BLD, U_FACE, L_FACE, D_FACE);
			cycleColours(BD, U_FACE, L_FACE, D_FACE);
			cycleColours(BRD, L_FACE, D_FACE, R_FACE);
			cycleColours(BR, L_FACE, D_FACE, R_FACE);
			cycleColours(BRU, D_FACE, R_FACE, U_FACE);
			cycleColours(BU, D_FACE, R_FACE, U_FACE);
		}		
	}

	private static void makeUmove(boolean prime) {
		if (prime) {
			int[] temp1 = FRU; int[] temp2 = FU;
			FRU = FLU; FU = CLU; FLU = BLU; CLU = BU; BLU = BRU; BU = CRU; BRU = temp1; CRU = temp2; 
			cycleColours(FU, L_FACE, F_FACE, R_FACE);
			cycleColours(FRU, L_FACE, F_FACE, R_FACE);
			cycleColours(CRU, F_FACE, R_FACE, B_FACE);
			cycleColours(BRU, F_FACE, R_FACE, B_FACE);
			cycleColours(BU, R_FACE,  B_FACE, L_FACE);
			cycleColours(BLU, R_FACE, B_FACE, L_FACE);
			cycleColours(CLU, B_FACE, L_FACE, F_FACE);
			cycleColours(FLU, B_FACE, L_FACE, F_FACE);
		} else {
			int[] temp1 = FRU; int[] temp2 = CRU;		
			FRU = BRU; CRU = BU; BRU = BLU; BU = CLU; BLU = FLU; CLU = FU; FLU = temp1; FU = temp2;
			cycleColours(FU, B_FACE, R_FACE, F_FACE);
			cycleColours(FRU, B_FACE, R_FACE, F_FACE);
			cycleColours(CRU, L_FACE, B_FACE, R_FACE);
			cycleColours(BRU, L_FACE, B_FACE, R_FACE);
			cycleColours(BU, F_FACE, L_FACE, B_FACE);
			cycleColours(BLU, F_FACE, L_FACE, B_FACE);
			cycleColours(CLU, R_FACE, F_FACE, L_FACE);
			cycleColours(FLU, R_FACE, F_FACE, L_FACE);	
		}		
	}

	private static void makeDmove(boolean prime) {
		if (prime) {
			int[] temp1 = CLD; int[] temp2 = FLD;
			CLD = FD; FLD = FRD; FD = CRD; FRD = BRD; CRD = BD; BRD = BLD; BD = temp1; BLD = temp2;  
			cycleColours(FLD, R_FACE, F_FACE, L_FACE);
			cycleColours(CLD, R_FACE, F_FACE, L_FACE);
			cycleColours(BLD, F_FACE, L_FACE, B_FACE); 
			cycleColours(BD, F_FACE, L_FACE, B_FACE);
			cycleColours(BRD, L_FACE, B_FACE, R_FACE);
			cycleColours(CRD, L_FACE, B_FACE, R_FACE);
			cycleColours(FRD, B_FACE, R_FACE, F_FACE);
			cycleColours(FD, B_FACE, R_FACE, F_FACE);
		} else {
			int[] temp1 = CLD; int[] temp2 = BLD;
			CLD = BD; BLD = BRD; BD = CRD; BRD = FRD; CRD = FD; FRD = FLD; FD = temp1; FLD = temp2;
			cycleColours(CLD, R_FACE, B_FACE, L_FACE);
			cycleColours(BLD, R_FACE, B_FACE, L_FACE);
			cycleColours(BD, F_FACE, R_FACE, B_FACE);
			cycleColours(BRD, F_FACE, R_FACE, B_FACE);
			cycleColours(CRD, L_FACE, F_FACE, R_FACE);
			cycleColours(FRD, L_FACE, F_FACE, R_FACE);
			cycleColours(FD, B_FACE, L_FACE, F_FACE);
			cycleColours(FLD, B_FACE, L_FACE, F_FACE);
		}
	}

	private static void makeLmove(boolean prime) {
		if (prime) {
			int[] temp1 = CLD; int[] temp2 = BLD;
			CLD = BL; BLD = BLU; BL = CLU; BLU = FLU; CLU = FL; FLU = FLD; FL = temp1; FLD = temp2;
			cycleColours(FLD, B_FACE, D_FACE, F_FACE);
			cycleColours(FL, B_FACE, D_FACE, F_FACE);
			cycleColours(FLU, D_FACE, F_FACE, U_FACE);
			cycleColours(CLU, D_FACE, F_FACE, U_FACE);
			cycleColours(BLU, F_FACE, U_FACE, B_FACE);
			cycleColours(BL, F_FACE, U_FACE, B_FACE);
			cycleColours(BLD, U_FACE, B_FACE, D_FACE);
			cycleColours(CLD, U_FACE, B_FACE, D_FACE);
		} else {
			int[] temp1 = FLD; int[] temp2 = FL;
			FLD = FLU; FL = CLU; FLU = BLU; CLU = BL; BLU = BLD; BL = CLD; BLD = temp1; CLD = temp2;
			cycleColours(FLD, U_FACE, F_FACE, D_FACE);
			cycleColours(FL, B_FACE, U_FACE, F_FACE);
			cycleColours(FLU, B_FACE, U_FACE, F_FACE);
			cycleColours(CLU, D_FACE, B_FACE, U_FACE);
			cycleColours(BLU, D_FACE, B_FACE, U_FACE);
			cycleColours(BL, F_FACE, D_FACE, B_FACE);
			cycleColours(BLD, F_FACE, D_FACE, B_FACE);
			cycleColours(CLD, U_FACE, F_FACE, D_FACE);
		}
	}

	private static void makeYrotation(boolean prime) {
		if (prime) {
			int[] temp1 = FRU; int[] temp2 = FU;
			FRU = FLU; FU = CLU; FLU = BLU; CLU = BU; BLU = BRU; BU = CRU; BRU = temp1; CRU = temp2; 
			cycleColours(FU, L_FACE, F_FACE, R_FACE);
			cycleColours(FRU, L_FACE, F_FACE, R_FACE);
			cycleColours(CRU, F_FACE, R_FACE, B_FACE);
			cycleColours(BRU, F_FACE, R_FACE, B_FACE);
			cycleColours(BU, R_FACE,  B_FACE, L_FACE);
			cycleColours(BLU, R_FACE, B_FACE, L_FACE);
			cycleColours(CLU, B_FACE, L_FACE, F_FACE);
			cycleColours(FLU, B_FACE, L_FACE, F_FACE);
			temp1 = CLD; temp2 = BLD;
			CLD = BD; BLD = BRD; BD = CRD; BRD = FRD; CRD = FD; FRD = FLD; FD = temp1; FLD = temp2;
			cycleColours(CLD, R_FACE, B_FACE, L_FACE);
			cycleColours(BLD, R_FACE, B_FACE, L_FACE);
			cycleColours(BD, F_FACE, R_FACE, B_FACE);
			cycleColours(BRD, F_FACE, R_FACE, B_FACE);
			cycleColours(CRD, L_FACE, F_FACE, R_FACE);
			cycleColours(FRD, L_FACE, F_FACE, R_FACE);
			cycleColours(FD, B_FACE, L_FACE, F_FACE);
			cycleColours(FLD, B_FACE, L_FACE, F_FACE);
			temp1 = F; temp2 = FR;
			F = CL; CL = B; B = CR; CR = temp1;
			FR = FL; FL = BL; BL = BR; BR = temp2;
			cycleColours(F, B_FACE, L_FACE, F_FACE);
			cycleColours(FR, L_FACE, F_FACE, R_FACE);
			cycleColours(CR, L_FACE, F_FACE, R_FACE);
			cycleColours(BR, F_FACE, R_FACE, B_FACE);
			cycleColours(B, F_FACE, R_FACE, B_FACE);
			cycleColours(BL, R_FACE, B_FACE, L_FACE);
			cycleColours(CL, R_FACE, B_FACE, L_FACE);
			cycleColours(FL, B_FACE, L_FACE, F_FACE);
		} else {
			int[] temp1 = FRU; int[] temp2 = CRU;		
			FRU = BRU; CRU = BU; BRU = BLU; BU = CLU; BLU = FLU; CLU = FU; FLU = temp1; FU = temp2;
			cycleColours(FU, B_FACE, R_FACE, F_FACE);
			cycleColours(FRU, B_FACE, R_FACE, F_FACE);
			cycleColours(CRU, L_FACE, B_FACE, R_FACE);
			cycleColours(BRU, L_FACE, B_FACE, R_FACE);
			cycleColours(BU, F_FACE, L_FACE, B_FACE);
			cycleColours(BLU, F_FACE, L_FACE, B_FACE);
			cycleColours(CLU, R_FACE, F_FACE, L_FACE);
			cycleColours(FLU, R_FACE, F_FACE, L_FACE);	
			temp1 = CLD; temp2 = FLD;
			CLD = FD; FLD = FRD; FD = CRD; FRD = BRD; CRD = BD; BRD = BLD; BD = temp1; BLD = temp2; 
			cycleColours(FLD, R_FACE, F_FACE, L_FACE);
			cycleColours(CLD, R_FACE, F_FACE, L_FACE);
			cycleColours(BLD, F_FACE, L_FACE, B_FACE); 
			cycleColours(BD, F_FACE, L_FACE, B_FACE);
			cycleColours(BRD, L_FACE, B_FACE, R_FACE);
			cycleColours(CRD, L_FACE, B_FACE, R_FACE);
			cycleColours(FRD, B_FACE, R_FACE, F_FACE);
			cycleColours(FD, B_FACE, R_FACE, F_FACE);
			temp1 = F; temp2 = FR;
			F = CR; CR = B; B = CL; CL = temp1;
			FR = BR; BR = BL; BL = FL; FL = temp2;
			cycleColours(FL, R_FACE, F_FACE, L_FACE);
			cycleColours(F, R_FACE, F_FACE, L_FACE);
			cycleColours(FR, B_FACE, R_FACE, F_FACE);
			cycleColours(CR, B_FACE, R_FACE, F_FACE);
			cycleColours(BR, L_FACE, B_FACE, R_FACE);
			cycleColours(B, L_FACE, B_FACE, R_FACE);
			cycleColours(BL, F_FACE, L_FACE, B_FACE);
			cycleColours(CL, F_FACE, L_FACE, B_FACE);
		}
	
	}

	private static void makeZrotation() {
		int[] temp1 = FU; int[] temp2 = FLU;
		FU = FL; FLU = FLD; FL = FD; FLD = FRD; FD = FR; FRD = FRU; FR = temp1; FRU = temp2;
		cycleColours(FRU, L_FACE, U_FACE, R_FACE);
		cycleColours(FR, L_FACE, U_FACE, R_FACE);
		cycleColours(FRD, U_FACE, R_FACE, D_FACE);
		cycleColours(FD, U_FACE, R_FACE, D_FACE);
		cycleColours(FLD, R_FACE, D_FACE, L_FACE);
		cycleColours(FL, R_FACE, D_FACE, L_FACE);
		cycleColours(FLU, D_FACE, L_FACE, U_FACE);
		cycleColours(FU, D_FACE, L_FACE, U_FACE);
		temp1 = BLD; temp2 = BD;
		BLD = BRD; BD = BR; BRD = BRU; BR = BU; BRU = BLU; BU = BL; BLU = temp1; BL = temp2;
		cycleColours(BLU, D_FACE, L_FACE, U_FACE);
		cycleColours(BU, D_FACE, L_FACE, U_FACE);
		cycleColours(BRU, L_FACE, U_FACE, R_FACE);
		cycleColours(BR, L_FACE, U_FACE, R_FACE);
		cycleColours(BRD, U_FACE, R_FACE, D_FACE);
		cycleColours(BD, U_FACE, R_FACE, D_FACE);
		cycleColours(BLD, R_FACE, D_FACE, L_FACE);
		cycleColours(BL, R_FACE, D_FACE, L_FACE);
		temp1 = CU; temp2 = CRU;
		CU = CL; CL = CD; CD = CR; CR = temp1;
		CRU = CLU; CLU = CLD; CLD = CRD; CRD = temp2;
		cycleColours(CU, D_FACE, L_FACE, U_FACE);
		cycleColours(CRU, L_FACE, U_FACE, R_FACE);
		cycleColours(CR, L_FACE, U_FACE, R_FACE);
		cycleColours(CRD, U_FACE, R_FACE, D_FACE);
		cycleColours(CD, U_FACE, R_FACE, D_FACE);
		cycleColours(CLD, R_FACE, D_FACE, L_FACE);
		cycleColours(CL, R_FACE, D_FACE, L_FACE);
		cycleColours(CLU, D_FACE, L_FACE, U_FACE);
	}

}
