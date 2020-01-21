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
	 */
		
	static ArrayList<Button> buttonArray;
	static int[] edgeStickers = {1,3,5,7,10,12,14,16,19,21,23,25,28,30,32,34,37,39,41,43,46,48,50,52};
	static int[] cornerStickers = {0,2,6,8,9,11,15,17,18,20,24,26,27,29,33,35,36,38,42,44,45,47,51,53};
	static int[][] edges = {{1,46},{3,28},{5,37},{7,10},{19,16},{21,34},{23,43},{25,52},{12,32},{14,39},{48,41},{50,30}};
	static int[][] corners = {{6,9,29},{8,36,11},{2,45,38},{0,27,47},{18,35,15},{20,17,42},{26,44,51},{24,53,33}};
	
	static ArrayList<String> movesToSolve = new ArrayList<String>();
	
	static String[] solvedCrossEdges = {"UF","UR","UB","UL"};
	static String[] flippedCrossEdges = {"FU","RU","BU","LU"};
	static String[] edgeLetters = {"FD", "FL", "FR", "FU", "CLD", "CRD", "CLU", "CRU", "BD", "BL", "BR", "BU"};

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
    
    static int[] FLD, FD, FRD, FL, FR, FLU, FU, FRU,
    			 CLD, CRD, CLU, CRU,
    			 BLD, BD, BRD, BL, BR, BLU, BU, BRU;
	
	
	public static void deriveSolution(ArrayList<Button> buttonArray) {

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
		System.out.println(crossEdgeLoc);
		String movesToSolve = solveCrossEdge(crossEdgeLoc, "FU");
		System.out.println("Moves to solve White-Green edge: "+movesToSolve);
		applyMovesLogically(movesToSolve);
		
		crossEdgeLoc = findCrossEdge(0); //Green, Red, Blue, Orange 1,0,2,4
		System.out.println(crossEdgeLoc);
		movesToSolve = solveCrossEdge(crossEdgeLoc, "CRU");
		System.out.println("Moves to solve White-Red edge: "+movesToSolve);
		applyMovesLogically(movesToSolve);
		
		crossEdgeLoc = findCrossEdge(2); //Green, Red, Blue, Orange 1,0,2,4
		System.out.println(crossEdgeLoc);
		movesToSolve = solveCrossEdge(crossEdgeLoc, "BU");
		System.out.println("Moves to solve White-Blue edge: "+movesToSolve);
		applyMovesLogically(movesToSolve);
		
		crossEdgeLoc = findCrossEdge(4); //Green, Red, Blue, Orange 1,0,2,4
		System.out.println(crossEdgeLoc);
		movesToSolve = solveCrossEdge(crossEdgeLoc, "CLU");
		System.out.println("Moves to solve White-Orange edge: "+movesToSolve);
		applyMovesLogically(movesToSolve);
		
		
		
		
		
		//System.out.println("Cross Edges: "+crossEdges.get(0)+", "+crossEdges.get(1)+", "+crossEdges.get(2)+", "+crossEdges.get(3));
		//solveCrossEdges(crossEdges);
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
			default: return "solved";
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
			default: return "solved";
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
			case "BU": return "LLSBB";			
			default: return "solved";
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
			default: return "solved";
			}
		}
		return "";
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
			}
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

	



}
