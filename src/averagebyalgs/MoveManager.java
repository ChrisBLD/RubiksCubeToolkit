package averagebyalgs;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MoveManager {
	

	/* Slice moves unused in scrambles.
	private int[][] M = {{52,49,46},{25,22,19},{16,13,10},{7,4,1}};
	private int[][] S = {{28,31,34},{21,22,23},{43,40,37},{3,4,5}};
	private int[][] E = {{32,31,30},{50,49,48},{41,40,39},{14,13,12}};	
	private int[][] Mp = {{1,4,7},{10,13,16},{19,22,25},{46,49,52}};
	private int[][] Sp = {{3,4,5},{37,40,43},{23,22,21},{34,31,28}};
	private int[][] Ep = {{12,13,14},{39,40,41},{48,49,50},{30,31,32}};
	*/
	
	final private int [][] Up = {{47,46,45},{38,37,36},{11,10,9},{29,28,27},{0,2,8,6},{1,5,7,3}};
	final private int [][] U = {{27,28,29},{9,10,11},{36,37,38},{45,46,47},{6,8,2,0},{3,7,5,1}};
	final private int [][] Fp = {{6,7,8},{36,39,42},{20,19,18},{35,32,29},{9,11,17,15},{10,14,16,12}};
	final private int [][] F = {{29,32,35},{18,19,20},{42,39,36},{8,7,6},{15,17,11,9},{12,16,14,10}};
	final private int [][] Rp = {{17,14,11},{8,5,2},{45,48,51},{26,23,20},{36,38,44,42},{37,41,43,39}};
	final private int [][] R = {{20,23,26},{51,48,45},{2,5,8},{11,14,17},{42,44,38,36},{39,43,41,37}};
	final private int [][] Bp = {{44,41,38},{2,1,0},{27,30,33},{24,25,26},{45,47,53,51},{46,50,52,48}};
	final private int [][] B = {{26,25,24},{33,30,27},{0,1,2},{38,41,44},{51,53,47,45},{48,52,50,46}};
	final private int [][] Lp = {{9,12,15},{18,21,24},{53,50,47},{0,3,6},{27,29,35,33},{28,32,34,30}};
	final private int [][] L = {{6,3,0},{47,50,53},{24,21,18},{15,12,9},{33,35,29,27},{30,34,32,28}};
	final private int [][] Dp = {{15,16,17},{42,43,44},{51,52,53},{33,34,35},{18,20,26,24},{19,23,25,21}};
	final private int [][] D = {{35,34,33},{53,52,51},{44,43,42},{17,16,15},{24,26,20,18},{21,25,23,19}};
	
	final private int [][] Um = {{1,4},{2,1},{3,2},{4,3}}; 
	final private int [][] Upm = {{1,2},{2,3},{3,4},{4,1}};
	final private int [][] Fm = {{3,4},{4,5},{5,6},{6,3}};
	final private int [][] Fpm = {{3,6},{4,3},{5,4},{6,5}};
	final private int [][] Rm = {{2,3},{3,6},{6,7},{7,2}};
	final private int [][] Rpm = {{2,7},{3,2},{6,3},{7,6}};
	final private int [][] Bm = {{1,2},{2,7},{7,8},{8,1}};
	final private int [][] Bpm = {{1,8},{2,1},{7,2},{8,7}};
	final private int [][] Lm = {{1,8},{4,1},{5,4},{8,5}};
	final private int [][] Lpm = {{1,4},{4,5},{5,8},{8,1}};
	final private int [][] Dm = {{5,8},{6,5},{7,6},{8,7}};
	final private int [][] Dpm = {{5,6},{6,7},{7,8},{8,5}};
	
	final private int [][] Ue = {{1,4},{2,1},{3,2},{4,3}}; 
	final private int [][] Upe = {{1,2},{2,3},{3,4},{4,1}};
	final private int [][] Fe = {{3,5},{5,9},{9,6},{6,3}};
	final private int [][] Fpe = {{3,6},{6,9},{9,5},{5,3}};
	final private int [][] Re = {{2,6},{6,10},{10,7},{7,2}};
	final private int [][] Rpe = {{2,7},{7,10},{10,6},{6,2}};
	final private int [][] Be = {{1,7},{7,11},{11,8},{8,1}};
	final private int [][] Bpe = {{1,8},{8,11},{11,7},{7,1}};
	final private int [][] Le = {{4,8},{8,12},{12,5},{5,4}};
	final private int [][] Lpe = {{4,5},{5,12},{12,8},{8,4}};
	final private int [][] De = {{9,12},{12,11},{11,10},{10,9}};
	final private int [][] Dpe = {{9,10},{10,11},{11,12},{12,9}};
	
	

	private int[] cornerPositions = {1,2,3,4,5,6,7,8};
	private int[] cornerPositionsDEFAULT = {1,2,3,4,5,6,7,8};
	private int[] cornerOrientations = {0,0,0,0,0,0,0,0};
	private int[] cornerOrientationsDEFAULT = {0,0,0,0,0,0,0,0};
	private int[] edgePositions = {1,2,3,4,5,6,7,8,9,10,11,12};
	private int[] edgePositionsDEFAULT = {1,2,3,4,5,6,7,8,9,10,11,12};
	private int[] edgePositionsPARITYUBL = {4,2,3,1,5,6,7,8,9,10,11,12};
	private int[] edgePositionsPARITYUFR = {1,3,2,4,5,6,7,8,9,10,11,12};
			
	
	private Group g, cubieG;
	private ArrayList<ImageView> cubieArray;
	private ImageView iv1;
	
	public MoveManager(Group group, ArrayList<ImageView> cA, Group cubG) {
		g = group;
		cubieArray = cA;
		cubieG = cubG;
	}

	
	public Group executeMove(String c) {
		int[][] change = {{}};
		switch (c) {
			case "U": change = U; break;
			case "Up": change = Up; break;
			case "F": change = F; break;
			case "Fp": change = Fp; break;
			case "R": change = R; break;
			case "Rp": change = Rp; break;
			case "B": change = B; break;
			case "Bp": change = Bp; break;
			case "L": change = L; break;
			case "Lp": change = Lp; break;
			case "D": change = D; break;
			case "Dp": change = Dp; break;
			
		};
		
		Image temp1 = cubieArray.get(change[0][0]).getImage();
		Image temp2 = cubieArray.get(change[0][1]).getImage();
		Image temp3 = cubieArray.get(change[0][2]).getImage();

		for (int i = 0; i < 4; i++) {
			for (int x = 0; x < 3; x++) {
				if (i != 3) {
					cubieArray.get(change[i][x]).setImage(cubieArray.get(change[i+1][x]).getImage());
				} else {
					if (x == 0) { cubieArray.get(change[i][x]).setImage(temp1);
					} else if (x == 1) { cubieArray.get(change[i][x]).setImage(temp2);
				    } else {cubieArray.get(change[i][x]).setImage(temp3);}
				}
			}
		}
		
		
		Image temp4 = cubieArray.get(change[4][0]).getImage();
		Image temp5 = cubieArray.get(change[5][0]).getImage();
		
		for (int y = 0; y < 4; y++) {
			try { 
				cubieArray.get(change[4][y]).setImage(cubieArray.get(change[4][y+1]).getImage());
				cubieArray.get(change[5][y]).setImage(cubieArray.get(change[5][y+1]).getImage());
			} catch (Exception e) {
				cubieArray.get(change[4][y]).setImage(temp4);
				cubieArray.get(change[5][y]).setImage(temp5);
			}
		}
		Group cubieGee = new Group();
		
		for (int x = 0; x < 54; x++) {
    		cubieGee.getChildren().add(cubieArray.get(x));
    	}
		
		return cubieGee;
		
		
		
		
		
	}
	
	int[] trackCornerPos(String c) {
		int[] cornerPositionsNEW = cornerPositions.clone();
		int[][] selected = {{}};

		switch(c) {
			case "U" : selected = Um; break;
			case "Up" : selected = Upm; break;
			case "F" : selected = Fm; break;
			case "Fp" : selected = Fpm; break;
			case "R" : selected = Rm; break;
			case "Rp" : selected = Rpm; break;
			case "B" : selected = Bm; break;
			case "Bp" : selected = Bpm; break;
			case "L" : selected = Lm; break;
			case "Lp" : selected = Lpm; break;
			case "D" : selected = Dm; break;
			case "Dp" : selected = Dpm; break;	
		}
		
		for (int[] change : selected) {
			int old = change[0];
			int nuevo = change[1];
				
			System.out.println("Old: "+old+", New: "+nuevo);
			cornerPositionsNEW[old-1] = cornerPositions[nuevo-1];
			for (int i : cornerPositionsNEW) {
				System.out.print(i+", ");
			}
		}

		
		return cornerPositionsNEW;
	}
	
	int[] trackEdgePos(String c) {

		int[] edgePositionsNEW = edgePositions.clone();
		int[][] selected = {{}};
		
		switch(c) {
			case "U" : selected = Ue; break;
			case "Up" : selected = Upe; break;
			case "F" : selected = Fe; break;
			case "Fp" : selected = Fpe; break;
			case "R" : selected = Re; break;
			case "Rp" : selected = Rpe; break;
			case "B" : selected = Be; break;
			case "Bp" : selected = Bpe; break;
			case "L" : selected = Le; break;
			case "Lp" : selected = Lpe; break;
			case "D" : selected = De; break;
			case "Dp" : selected = Dpe; break;	
		}
		
		for (int[] change : selected) {
			int old = change[0];
			int nuevo = change[1];
				
			System.out.println("Old: "+old+", New: "+nuevo);
			edgePositionsNEW[old-1] = edgePositions[nuevo-1];
			for (int i : edgePositionsNEW) {
				System.out.print(i+", ");
			}
		}
		
		return edgePositionsNEW;
	
			
	}
	
	
	ArrayList<ImageView> applyMoves(ArrayList<String> moves) {
		caReset();
		if (Main.paritySwap) {
			if (Main.cornerBuf.equals("UBL")) {
				edgePositions = edgePositionsPARITYUBL.clone();
			} else if (Main.cornerBuf.equals("UFR")) {
				edgePositions = edgePositionsPARITYUFR.clone();
				System.out.println("Set to parity");
			}
		} else {
			edgePositions = edgePositionsDEFAULT.clone();
		}
		cornerPositions = cornerPositionsDEFAULT.clone();
		cornerOrientations = cornerOrientationsDEFAULT.clone();
		Group cubieGee = new Group();
		for (String move : moves) {
			cubieGee = executeMove(move);
			cornerPositions = trackCornerPos(move);
			edgePositions = trackEdgePos(move);
			//System.out.print(move+" ");
		}
		
		Image image = new Image("resources/CubeNet.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setScaleX(0.5);
        iv1.setScaleY(0.5);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setLayoutX(-320);
        iv1.setLayoutY(-50);
        
		g.getChildren().remove(cubieG);
		g.getChildren().remove(iv1);
		g.getChildren().add(cubieGee);
		g.getChildren().add(iv1);
		
		return cubieArray;
		
	}
	void caReset() {
		for (int i = 0; i < 54; i++) {
    		if (i < 9) {
	    		cubieArray.get(i).setImage(Main.topColour);
    		} else if (i < 18) {
    			cubieArray.get(i).setImage(Main.frontColour);
    		} else if (i < 27) {
    			cubieArray.get(i).setImage(Main.bottomColour);
    		} else if (i < 36) {
    			cubieArray.get(i).setImage(Main.leftColour);
    		} else if (i < 45) {
    			cubieArray.get(i).setImage(Main.rightColour);
    		} else {
    			cubieArray.get(i).setImage(Main.backColour);
    		}
    	}
	}
	
	int[] getCornerPos() {
		return cornerPositions;
	}
	
	int[] getEdgePos() {
		return edgePositions;
	}
	
	
	
}
