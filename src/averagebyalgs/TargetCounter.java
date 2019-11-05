package averagebyalgs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TargetCounter {
	
	private int[] cornersOK = {0,2,8,6,18,20,26,24};
	private int[] cornersCW = {27,45,36,9,35,17,44,53};
	private int[] cornersACW = {47,38,11,29,15,42,51,33};
	
	private String top = "resources/CubeWHITE.png";
	private String bottom = "resources/CubeYELLOW.png";
	Image U,F,R,B,L,D;
	
	final private int[][] corners = {{0,47,27},{2,38,45},{8,11,36},{6,29,9},
									{18,15,35},{20,42,17},{26,51,44},{24,33,53}};
	
	final private int[][] edges = {{1,46},{5,37},{7,10},{3,28},{32,12},{14,39},{41,48},{50,30},
			 					   {16,19},{43,23},{52,25},{34,21}};
	
	final private int[][] UBLtrios = {{2,3}, {2,7}, {4,3}, {4,5}, {8,7}, {8,5}, {2,4}, {2,8}, {4,8}};
	final private int[][] UFRtrios = {{2,1}, {2,7}, {4,1}, {4,5}, {6,5}, {6,7}, {2,4}, {4,6}, {2,6}};
	
	private int[] twistedOrientations = {0,0,0,0,0,0,0,0};
	
	private boolean[] cornerSolved = {false, false, false, false, false, false, false, false};
									//UBL     UBR    UFR    UFL    DFL    DFR    DBR    DBL
	
	private String[] cornerKey = {"UBL","UBR","UFR","UFL","DFL","DFR","DBR","DBL"};
	private String[] edgeKey = {"UB","UR","UF","UL","FL","FR","BR","BL","DF","DR","DB","DL"};
	
	private int[] cornerPositions, edgePositions;
	
	private int twisted = 0;
	private int flipped = 0;
	
	private int edgeCount = 0;
	private int cornerCount = 0;
	
	private ArrayList<Integer> twistedCorners = new ArrayList<Integer>();
	private ArrayList<Integer> flippedEdges = new ArrayList<Integer>();
	
	private boolean hasParity;
	private boolean buffTwist = false;
	
	private int[] cornerOrientations;
	
	int[][] cornerAdjacency = {{2,4,8}, {1,3,7}, {2,4,6}, {1,3,5}, {6,8,4}, {5,7,3}, {6,8,2}, {5,7,1}}; //Defines Adjacency matrix
	int[][] cornerOpposites = {{3,5,7}, {4,6,8}, {1,5,7}, {2,6,8}, {1,3,7}, {2,4,8}, {1,3,5}, {2,4,6}}; //Defines Opposite matrix
	
	
	public TargetCounter(ScrambleManager sm, ArrayList<ImageView> cubieArray) {
		
		
		
		/*
		 * Program parity avoidance technique (UL-UB swap)
		 * Allow user to select custom orientation
		 * Allow user to enter buffers (just UI needed)
		 * General UI work
		 * And then average calculation :)
		 */
		
		String edgeBuffer = MainRedesigned.edgeBuf;
		String cornerBuffer = MainRedesigned.cornerBuf;
		
		MoveManager m = sm.getMoveMan();
		
		cornerPositions = m.getCornerPos();
		edgePositions = m.getEdgePos();
		
		U = cubieArray.get(4).getImage();
		F = cubieArray.get(13).getImage();
		R = cubieArray.get(40).getImage();
		B = cubieArray.get(49).getImage();
		L = cubieArray.get(31).getImage();
		D = cubieArray.get(22).getImage();
		
		cornerOrientations = calculateCornerOrientations(cubieArray);
		
		for (int i : cornerOrientations) {
			System.out.print(i);
		}		
		
		System.out.println("PARITY AVD TC: "+MainRedesigned.paritySwap);
		
		ArrayList<Integer> solvedCorners = solvedCorners(cubieArray, cornerOrientations, cornerBuffer);
		int corners = traceCorners(cornerPositions, cornerBuffer, solvedCorners, twistedCorners);
		
		if (corners % 2 != 0) {
			hasParity = true;
		} else {
			hasParity = false;
		}
		
		ArrayList<Integer> solvedEdges = solvedEdges(cubieArray, edgeBuffer, MainRedesigned.paritySwap);
		

		
		int edges = traceEdges(edgePositions, edgeBuffer, solvedEdges, flippedEdges, MainRedesigned.paritySwap);
		//edges+= flipped;
		
		System.out.println(flipped);
		System.out.println(corners+" is the total number of corner targets (excluding twists)");
		System.out.println(edges+" is the total number of edge targets (excluding flips)");
		
		edgeCount = edges;
		cornerCount = corners;
		
	}
	
	private int[] calculateCornerOrientations(ArrayList<ImageView> cubieArray) {
		//System.out.println("in");
		int[] cornerOrientations = {0,0,0,0,0,0,0,0};
		for (int i = 0; i < 8; i++) {
			System.out.println("GetImage: "+cubieArray.get(0).getImage());
			if (cubieArray.get(cornersACW[i]).getImage().equals(U) || cubieArray.get(cornersACW[i]).getImage().equals(D)) {
				System.out.println("Sticker "+cornersACW[i]+" should be W/Y");
				cornerOrientations[i] = 2;
			}
		}
		for (int s : cornerOrientations) {
			System.out.print(s);
		}	
			
		for (int a = 0; a < 8; a++) {
			if (cubieArray.get(cornersCW[a]).getImage().equals(U) || cubieArray.get(cornersCW[a]).getImage().equals(D)) {
				System.out.println("Sticker "+cornersCW[a]+" should be W/Y");
				cornerOrientations[a] = 1;
			}
		}
		return cornerOrientations;
	}
	
	private ArrayList<Integer> solvedEdges(ArrayList<ImageView> cubieArray, String edgeBuffer, boolean paritySwap) {
		int[] compare = {1,2,3,4,5,6,7,8,9,10,11,12};
		if (paritySwap) {
			if (!hasParity) {
				int[] compare2 = {1,2,3,4,5,6,7,8,9,10,11,12};
				compare = compare2;
			} else {
				if (MainRedesigned.cornerBuf.equals("UBL")) {
					int[] compare2 = {4,2,3,1,5,6,7,8,9,10,11,12};
					compare = compare2;
				} else if (MainRedesigned.cornerBuf.equals("UFR")) {
					System.out.println("BUFFER IS UFR!!!");
					int[] compare2 = {1,3,2,4,5,6,7,8,9,10,11,12};
					compare = compare2;
				}
			}
		} else {
			int[] compare2 = {1,2,3,4,5,6,7,8,9,10,11,12};
			compare = compare2;
		}
		
		System.out.println("SOLVED EDGES: PARITY SWAP: "+paritySwap+ ", HAS PARITY: "+hasParity+", CORNER BUF: "+MainRedesigned.cornerBuf);
		int[] stickers = {1,5,7,3,12,14,48,50,19,23,25,21};
		ArrayList<Integer> solvedEdges = new ArrayList<Integer>();
		
		int buffer = 0;
		for (int i = 0; i < edgeKey.length; i++) {
			if (edgeKey[i].equals(edgeBuffer)) {
				buffer = compare[i];
			}
		}
		System.out.println("COMPARE:");
		for (int i : compare) {
			System.out.print(i);
		}
		System.out.println("EDGEPOS:");
		for (int i : edgePositions) {
			System.out.print(i);
		}
		for (int i = 0; i < 12; i++) {
			if (compare[i] == edgePositions[i]) {
				System.out.println("Edge in place. compare[i]: "+compare[i]+", buffer: "+buffer+", edgePositions[i]: "+edgePositions[i]);
				if (compare[i] == buffer) {
					//
				} else if (i < 4) {
					if (cubieArray.get(stickers[i]).getImage().equals(U)) {
						System.out.println(edgeKey[i]+" is solved");
						solvedEdges.add(compare[i]);
					} else { 
						System.out.println(edgeKey[i]+" is twisted");
						flippedEdges.add(compare[i]);
						flipped++;
					}
				} else if (i < 6) {
					if (cubieArray.get(stickers[i]).getImage().equals(F)) {
						System.out.println(edgeKey[i]+" is solved");
						solvedEdges.add(compare[i]);
					} else { 
						System.out.println(edgeKey[i]+" is twisted");
						flippedEdges.add(compare[i]);
						flipped++;
					}
				} else if (i < 8) {
					if (cubieArray.get(stickers[i]).getImage().equals(B)) {
						System.out.println(edgeKey[i]+" is solved");
						solvedEdges.add(compare[i]);
					} else { 
						System.out.println(edgeKey[i]+" is twisted");
						flippedEdges.add(compare[i]);
						flipped++;
					}
				} else {
					if (cubieArray.get(stickers[i]).getImage().equals(D)) {
						System.out.println(stickers[i]);
						System.out.println(edgeKey[i]+" is solved");
						System.out.println("D did it");
						solvedEdges.add(compare[i]);
					} else { 
						System.out.println(edgeKey[i]+" is twisted");
						flippedEdges.add(compare[i]);
						flipped++;
					}
				}
					
			}
		}
		
		return solvedEdges;
	}
	
	private ArrayList<Integer> solvedCorners(ArrayList<ImageView> cubieArray, int[] cornerOrientations, String cornerBuffer) {
		int[] compare = {1,2,3,4,5,6,7,8};
		int buffer = 0;
		for (int i = 0; i < cornerKey.length; i++) {
			if (cornerKey[i].equals(cornerBuffer)) {
				buffer = compare[i];
			}
		}
		ArrayList<Integer> solvedCorners = new ArrayList<Integer>();
		
		for (int i = 0; i < 8; i++) {
			if (compare[i] == cornerPositions[i]) {
				if (cornerOrientations[i] == 0) {
					System.out.println(cornerKey[i]+" is solved");
					solvedCorners.add(compare[i]);
				} else {
					System.out.println(cornerKey[i]+" is twisted");
					if (compare[i] != buffer) {
						twistedCorners.add(compare[i]);
						twisted++;
					} else {
						buffTwist = true;
					}
				}
			}
		}
		
		return solvedCorners;
	}
	
	private int traceCorners(int[] cornerPositions, String cornerBuffer, ArrayList<Integer> solvedCorners, ArrayList<Integer> twistedCorners) {
		ArrayList<Integer> compare = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8));
		int buffer = 0;
		for (int i = 0; i < cornerKey.length; i++) {
			if (cornerKey[i].equals(cornerBuffer)) {
				buffer = compare.get(i);
			}
		}
		
		for (int corner : solvedCorners) {
			compare.remove((Integer) corner);
		}
		
		for (int tcorner : twistedCorners) {
			compare.remove((Integer) tcorner);
		}
		
		int targetInPos = 0;
		int whileCount = 0;
		int count = 0;
		while (targetInPos != buffer) {
	
			if (whileCount == 0) {
				targetInPos = cornerPositions[buffer-1];
			} else {
				targetInPos = cornerPositions[targetInPos-1];
			}

			System.out.println("targetInPos: "+targetInPos+", next targetInPos: "+cornerPositions[targetInPos-1]);
			System.out.println("Getting remove: "+targetInPos);
			
			compare.remove((Integer)targetInPos);
			if (compare.isEmpty() == false) {
				if (targetInPos == cornerPositions[buffer-1]) {
					if (whileCount == 0 ) {
						count = 1;
					}
				} else {
					if (whileCount == 0) {
						count = 2;
					} else {
						count++;
					}
				}
			}

			System.out.println(count);
			whileCount++;
		}
		

		for (int i : compare) {
			System.out.print(i);
		}
		
		//B' D2 F2 R2 B2 L2 F2 L' U2 R' D2 F2 L' U' L D' B2 R D' F' R2 D' F'
		
		while (compare.isEmpty() == false) {
			for (int i : compare) {
				System.out.print(">"+i+"<");
			}
			System.out.println();
			buffer = compare.get(0); //buffer = 3
			System.out.println("New buffer is position: "+buffer);
			targetInPos = cornerPositions[buffer-1]; //targetinPos = 
			compare.remove((Integer)targetInPos);
			int newCount = 0;
			System.out.println("targetInPos: "+targetInPos+", buffer: "+buffer+", count: "+count);
			while (targetInPos != buffer) {
				System.out.println("targetInPos: "+targetInPos+", next targetInPos: "+cornerPositions[targetInPos-1]);
				targetInPos = cornerPositions[targetInPos-1];
				compare.remove((Integer)targetInPos);
				count++;
				newCount++;
				System.out.println(count);
				for (int i : compare) {
					System.out.print(i);
				}
			}
			
			if (newCount != 0) {
				count++;
			}
			if (compare.isEmpty() == false) {
				count++;
			}
		}
		
		System.out.println(count+"  is the total number of targets (I hope");
		
		
		return count;
		
	}
	
	private int traceEdges(int[] edgePositions, String edgeBuffer, ArrayList<Integer> solvedEdges, ArrayList<Integer> flippedEdges, boolean paritySwap) {
		
		/* PARITY AVOIDANCE
		 * Base case = +1
Parity, UB/UL solved -> unsolved = +1
Parity, UB/UL flipped in correct loc -> unflipped = +1 -'
Parity, UB/UL flipped in incorrect loc -> unflipped = -1 +'
Parity, both solved in correct loc -> unsolved = +1
Parity, both unsolved -> solved = -3
Parity, both flipped in correct loc -> unflipped = +3 -''
Parity, both flipped in incorrect loc -> unflipped = -3 +''
Parity, one flipped one solved correct loc = +3 -'
Parity, one flipped one solved incorrect loc = -3 +'
		 */
		
		/*if (hasParity) {
		compare = new ArrayList<Integer>(Arrays.asList(4,2,3,1,5,6,7,8,9,10,11,12));
	} else {
		compare = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
	}*/
		
		for (int i : edgePositions) {
			System.out.print(i);
		}
		
		if (solvedEdges.size() == 11) {
			System.out.println("All edges solved lol.");
			return 0;
		}
		
		System.out.println("*************PARITY SWAP: "+paritySwap+", hasParity: "+hasParity);
		ArrayList<Integer> compare = new ArrayList<Integer>();
		
		int oneLoc = 0;
		int twoLoc = 0;
		
		if (MainRedesigned.cornerBuf.equals("UBL")) {
			for (int i = 0; i < edgePositions.length; i++) {
				if (edgePositions[i] == 1) {
					oneLoc = i;
				} else if (edgePositions[i] == 4) {
					twoLoc = i;
				}
			}
		} else if (MainRedesigned.cornerBuf.equals("UFR")) {
			for (int i = 0; i < edgePositions.length; i++) {
				if (edgePositions[i] == 2) {
					oneLoc = i;
				} else if (edgePositions[i] == 3) {
					twoLoc = i;
				}
			}
		}
		
		
		if (paritySwap) {
			if (hasParity) {
				if (MainRedesigned.cornerBuf.equals("UBL")) {
					compare = new ArrayList<Integer>(Arrays.asList(4,2,3,1,5,6,7,8,9,10,11,12));
				} else if (MainRedesigned.cornerBuf.equals("UFR")) {
					System.out.println("CORRECTLY SEEN PARITY!");
					compare = new ArrayList<Integer>(Arrays.asList(1,3,2,4,5,6,7,8,9,10,11,12));
				}
				int temp = edgePositions[oneLoc];
				edgePositions[oneLoc] = edgePositions[twoLoc];
				edgePositions[twoLoc] = temp;
				
			} else {
				compare = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
				//int temp = edgePositions[oneLoc];
				//edgePositions[oneLoc] = edgePositions[twoLoc];
				//edgePositions[twoLoc] = temp;
			}
		} else {
			compare = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
		}
		
		
		for (int i : edgePositions) {
			System.out.print(i+" ");
		}
		
		
		int buffer = 0;
		for (int i = 0; i < edgeKey.length; i++) {
			System.out.println("edgeKey[i]: "+edgeKey[i]+", edgeBuffer: "+edgeBuffer);
			if (edgeKey[i].equals(edgeBuffer)) {
				System.out.println("entered uwu");
				buffer = compare.get(i);
			}
		}
		
		if (MainRedesigned.cornerBuf.equals("UFR")) {
			if (paritySwap && hasParity) {
				buffer = 3;
			}
		}
		
		System.out.println();
		for (int i: compare) {
			System.out.print(i);
		}
		System.out.println();
		
		for (int i : compare) {
			System.out.print(i+" ");
		}
		
		compare.remove((Integer) buffer);
		
		for (int i : compare) {
			System.out.print(i+" ");
		}
		
		for (int edge : solvedEdges) {
			compare.remove((Integer) edge);
		}
		for (int fedge : flippedEdges) {
			compare.remove((Integer) fedge);
		}
		
		
		int targetInPos = 0;
		int whileCount = 0;
		int count = 0;
		System.out.println("BUFFER: "+buffer);
		while (targetInPos != buffer) {

			if (whileCount == 0) {
				targetInPos = edgePositions[buffer-1];
			} else {
				targetInPos = edgePositions[targetInPos-1];
			}

			System.out.println("targetInPos: "+targetInPos+", next targetInPos: "+edgePositions[targetInPos-1]);
			System.out.println("Getting remove: "+targetInPos);
			
			compare.remove((Integer)targetInPos);
			if (compare.isEmpty() == false) {
				System.out.println("in");
				if (targetInPos == edgePositions[buffer-1]) {
					if (whileCount == 0 ) {
						count = 1;
					}
				} else {
					if (whileCount == 0) {
						count = 2;
					} else {
						count++;
					}
				}
			} else {
				if (targetInPos != buffer) {
					count++;
				}
			}

			System.out.println(count);
			whileCount++;
		}
		
		for (int i : compare) {
			System.out.print(i);
		}
		
		//B' D2 F2 R2 B2 L2 F2 L' U2 R' D2 F2 L' U' L D' B2 R D' F' R2 D' F'
		
		while (compare.isEmpty() == false) {
			for (int i : compare) {
				System.out.print(">"+i+"<");
			}
			System.out.println();
			buffer = compare.get(0); //buffer = 3
			System.out.println("New buffer is position: "+buffer);
			targetInPos = edgePositions[buffer-1]; //targetinPos = 
			compare.remove((Integer)targetInPos);
			int newCount = 0;
			System.out.println("targetInPos: "+targetInPos+", buffer: "+buffer+", count: "+count);
			while (targetInPos != buffer) {
				System.out.println("targetInPos: "+targetInPos+", next targetInPos: "+edgePositions[targetInPos-1]);
				targetInPos = edgePositions[targetInPos-1];
				compare.remove((Integer)targetInPos);
				count++;
				newCount++;
				System.out.println(count);
			}
			
			if (newCount != 0) {
				System.out.println("PLUS");
				count++;
			} 
			if (compare.isEmpty() == false) {
				System.out.println("PLUS");
				count++;
			}
		}
		
		System.out.println(count+"  is the total number of edge targets (I hope");
		
		
		return count;
		
	}
	
	int[] getInfo() {
		int[] ret = {edgeCount,cornerCount, flipped, twisted};
		return ret;
	}
	
	int[] getOrientations() {
		return cornerOrientations;
	}
	
	ArrayList<Integer> getTwists() {
		return twistedCorners;
	}

	int twistCalculator(boolean dtBox, boolean otBox) {
		ArrayList<String> adjacents = new ArrayList<String>();
		
		int twistAlgs = twistedCorners.size(); //Sets the twist algs to the total number of twists. This is the max possible twist alg count.

		if (twistAlgs < 2) { //If it's less than 2, then there's no need to proceed since adj and opp checks are only done on 2+ twists.
			return twistAlgs;
		}
		
		if (dtBox) { //If DT selected:
			if (!otBox) { //If OT isn't selected, we can just run DT as it's the only one selected.
				return dtSelected(adjacents, cornerAdjacency, twistAlgs);
			} else { //If OT and DT are selected, then we simply run the adjacent corner section before running the opposite corner section.
				twistAlgs = dtSelected(adjacents, cornerAdjacency, twistAlgs);
				return dtSelected(new ArrayList<String>(), cornerOpposites, twistAlgs);
			}
		} else if (otBox){ //If adjacent twists aren't selected, then we just need to run opposite twists and return.
			return dtSelected(adjacents, cornerOpposites, twistAlgs);
		} else { //If neither are selected, then the total alg count = number of twists. So return that.
			return twistAlgs;
		}
	}


	
	private int dtSelected(ArrayList<String> adjacents, int[][] cornerAdjacency, int twistAlgs) {
		for (int i : twistedCorners) {
			System.out.println("Twisted Corner: "+i);
			for (int t : twistedCorners) {
				if (t != i) {
					System.out.println("Other twists: "+t);
					for (int a : cornerAdjacency[i-1]) {
						if (t == a) {
							System.out.println("Adjacent found. "+i+" is adjacent to "+a);
							String word = i+"."+a;
							System.out.println("String: "+word);
							adjacents.add(word);
						}
					}
				}
			}
		}
		
		ArrayList<String> uniqueAdjacents = new ArrayList<String>();

		
		for (String s : adjacents) {
			char one = s.toCharArray()[0];
			char two = s.toCharArray()[2];
			String inv = two+"."+one;
			System.out.print("S: "+s+", inv: "+inv);
			if (!uniqueAdjacents.contains(inv)) {
				uniqueAdjacents.add(s);
			}
		}
		
		for (String s: uniqueAdjacents) {
			System.out.println("IN UNIQUEADJ: "+s);
		}
		
		int wellCount = 0;
		ArrayList<String> uAgoodTwists = new ArrayList<String>();
		for (String s : uniqueAdjacents) {
			int a = Character.getNumericValue(s.toCharArray()[0]);
			int b = Character.getNumericValue(s.toCharArray()[2]);
			System.out.println("a: "+a+" b: "+b);
			int orientation1 = cornerOrientations[a-1];
			int orientation2 = cornerOrientations[b-1];
			
			if (orientation1+orientation2 == 3) {
				wellCount++;
				System.out.println("Twisted well.");
				uAgoodTwists.add(s);
			} else {
				System.out.println("Not twisted well.");
				
			}
		}
		
		
		for (String s : uAgoodTwists) {
			System.out.println("#############");
			int a = Character.getNumericValue(s.toCharArray()[0]);
			int b = Character.getNumericValue(s.toCharArray()[2]);
			System.out.println("a: "+a+" b: "+b);
		}
		
		
		ArrayList<String> finalDuos = crossoverStrip(uAgoodTwists);
		
		if (uAgoodTwists.size() == 3) {
			int a = Character.getNumericValue(uAgoodTwists.get(0).toCharArray()[0]);
			int b = Character.getNumericValue(uAgoodTwists.get(0).toCharArray()[2]);
			int c = Character.getNumericValue(uAgoodTwists.get(1).toCharArray()[0]);
			int d = Character.getNumericValue(uAgoodTwists.get(1).toCharArray()[2]);
			int e = Character.getNumericValue(uAgoodTwists.get(2).toCharArray()[0]);
			int f = Character.getNumericValue(uAgoodTwists.get(2).toCharArray()[2]);
			
			if (a+d == e+f || b+c == e+f) {
				System.out.println("e+f contains elements from both. Removing.");
				uAgoodTwists.remove(2);
			} else if (a+f == c+d || b+e == c+d) {
				System.out.println("c+d contains elements from both. Removing.");
				uAgoodTwists.remove(1);
			} else if (c+f == a+b || d+e == a+b) {
				System.out.println("a+b contains elements from both. Removing.");
				uAgoodTwists.remove(0);
			}
		}
		
		
		System.out.println("Twist algs: "+twistAlgs);
		System.out.println("All in uAgoodTwists: ");
		for (String s : uAgoodTwists) {
			System.out.print(s+", ");
		}
		System.out.println("");
		
		System.out.println("Twist algs: "+twistAlgs);
		System.out.println("All in twist algs stripped: ");
		for (String s : finalDuos) {
			System.out.print(s+", ");
		}
		System.out.println("");
		
		for (String s : finalDuos) {
			int one = Character.getNumericValue(s.toCharArray()[0]);
			int two = Character.getNumericValue(s.toCharArray()[2]);
			twistedCorners.remove(Integer.valueOf(one));
			twistedCorners.remove(Integer.valueOf(two));
		}
		
		twistAlgs-= finalDuos.size();
			
		return twistAlgs;
			
	}

	
	private ArrayList<String> crossoverStrip(ArrayList<String> goodTwists) {
		ArrayList<Integer> visited = new ArrayList<Integer>();
		ArrayList<String> goodDuosStripped = new ArrayList<String>();
		boolean visit = false;
		for (String gd : goodTwists) {
			int one = Character.getNumericValue(gd.toCharArray()[0]);
			int two = Character.getNumericValue(gd.toCharArray()[2]);
			if (visited.isEmpty()) {
				visited.add(one);
				visited.add(two);
				goodDuosStripped.add(gd);
			} else {
				visit = false;
				for (int i : visited) {
					if (i == one || i == two) {
						System.out.println("Visited. Skipping");
						visit = true;
					}
				}
				if (!visit) {
					visited.add(one);
					visited.add(two);
					goodDuosStripped.add(gd);
				}
			}
		}
		
		return goodDuosStripped;
	}


}
  