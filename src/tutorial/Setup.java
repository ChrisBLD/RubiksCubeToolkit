package tutorial;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Setup {
	
	static int bodyCount = 0;
	static boolean forwardOrBack = true;
	static ArrayList<Button> buttonArray = new ArrayList<Button>();
	static ArrayList<Label> elements;
	
	static Button forward, back;
	
	public static final int RED     = 0;
    public static final int GREEN   = 1;
    public static final int BLUE    = 2;
    public static final int YELLOW  = 3;
    public static final int ORANGE  = 4;
    public static final int WHITE   = 5;
    public static final int GRAY    = 6;
    
    private static SequentialTransition seqOut, seqIn;

	public static void main(ArrayList<Label> elements, Button forward, Button back, SequentialTransition seqIn, SequentialTransition seqOut) {
		
		String[] bodyText = {"To continue with this tutorial, it is strongly advised that you have your own Rubik's Cube so you can try the moves yourself"
				+ " and follow what happens on screen. Do you have your own puzzle?",
				"Great! If your puzzle is currently solved, then scramble it as well as you can before proceeding to the next step. If your puzzle is already scrambled,"
				+ " then you're ready to get going!",
				"Please open the cube net below and replicate your scrambled puzzle on the net shown. It's important you get it right so that you can follow along with"
				+ " your own puzzle throughout the tutorial."};
		String badText = "You can still complete this tutorial without your own cube, but you will be limited to using a default scramble and following the tutorial using only the"
				+ " virtual cube on the left of the screen. This scramble will teach you everything you need to know - but the best way to learn is to try it yourself!";
		Setup.seqIn = seqIn;
		Setup.seqOut = seqOut;
		Setup.elements = elements;
		Setup.forward = forward;
		Setup.back = back;
    	seqOut.playFromStart();
    	seqOut.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    	    	elements.get(0).setText("Tutorial Setup");
    	    	elements.get(1).setText(bodyText[bodyCount]);  
    	    	elements.get(2).setText("");
    	    	elements.get(3).setText("");
    			seqIn.playFromStart();
    		}
    	});
    	
    	seqIn.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			forward.setDisable(false);
    			back.setDisable(false);
    		}
    	});
    	
    	ArrayList<Label> hasCube = new ArrayList<Label>();
    	hasCube.add(elements.get(1));
    	hasCube.add(elements.get(2));
    	SequentialTransition seqInText = SharedToolbox.initSeqTrans(hasCube, true);
    	SequentialTransition seqOutText = SharedToolbox.initSeqTrans(hasCube, false);
    	
    	seqOutText.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			if (bodyCount < 3) {
	    			elements.get(1).setText(bodyText[bodyCount]);
	    			elements.get(2).setText("");
	    			if (bodyCount == 2) {
	    				generateInputButton();
	    			}
    			} else {
    				elements.get(1).setText(badText);
	    			elements.get(2).setText("");
	    			seqInText.playFromStart();
    			}
    			back.setDisable(true);
    			seqInText.playFromStart();
    		}
    	});
    	
    	forward.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			System.out.println("bodycount: "+bodyCount);
    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    			System.out.println("bodycount: "+bodyCount);
    			seqOutText.playFromStart();
    		}
    	});
    	
    	back.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    			seqOutText.playFromStart();
    		}
    	});
	}
	
	private static void generateInputButton() {
		forward.setDisable(true);
		Button getColours = new Button();
		getColours.setMinSize(187,96); getColours.setMaxSize(187, 96);
		getColours.setGraphic(new ImageView(new Image("/resources/puzzleSetupButton.png")));
		getColours.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CubeInput ci = new CubeInput();
				boolean passCheck = true;
				while (passCheck) {
					try {
						ci.inputWindow.showAndWait();
						setupVirtualCube(elements);
						passCheck = false;
					} catch (Exception e) {
						//doNothing
					}
				}
				
				
				UserInterface.makeRmove(true); UserInterface.makeRmove(false);
				ArrayList<String> allMoves = CubeSolver.deriveSolution(buttonArray);
				getColours.setVisible(false);
				CrossSection.begin(allMoves, seqOut, seqIn, elements, forward, back);
			}
		});

		HBox test = new HBox(getColours); test.setAlignment(Pos.CENTER);
		test.setPadding(new Insets(70,70,70,70));
		TutorialHomepage.toolBarRight.getItems().set(2,test);

	}
	
	private static void setupVirtualCube(ArrayList<Label> elements) {
		ArrayList<Integer> cubeColours = new ArrayList<Integer>();
		int count = 0;
		for (Button b : buttonArray) {
			System.out.println("count: "+count+", col: "+b.getText());
			switch (b.getText()) {
			case "W": cubeColours.add(WHITE); break;
			case "Y": cubeColours.add(YELLOW); break;
			case "B": cubeColours.add(BLUE); break;
			case "G": cubeColours.add(GREEN); break;
			case "R": cubeColours.add(RED); break; 
			case "O": cubeColours.add(ORANGE); break;
			}
			count++;
		}
		
		UserInterface.FLD[0] = cubeColours.get(15); UserInterface.FLD[4] = cubeColours.get(35); UserInterface.FLD[5] = cubeColours.get(18);
		UserInterface.FD[0] = cubeColours.get(16); UserInterface.FD[5] = cubeColours.get(19);
		UserInterface.FRD[0] = cubeColours.get(17); UserInterface.FRD[1] = cubeColours.get(42); UserInterface.FRD[5] = cubeColours.get(20);
		UserInterface.FL[0] = cubeColours.get(12); UserInterface.FL[4] = cubeColours.get(32);
		UserInterface.FR[0] = cubeColours.get(14); UserInterface.FR[1] = cubeColours.get(39);
		UserInterface.FLU[0] = cubeColours.get(9); UserInterface.FLU[2] = cubeColours.get(6); UserInterface.FLU[4] = cubeColours.get(29); 
		UserInterface.FU[0] = cubeColours.get(10); UserInterface.FU[2] = cubeColours.get(7);
		UserInterface.FRU[0] = cubeColours.get(11); UserInterface.FRU[1] = cubeColours.get(36); UserInterface.FRU[2] = cubeColours.get(8);
		
		
		UserInterface.CLD[4] = cubeColours.get(34); UserInterface.CLD[5] = cubeColours.get(21); 
		UserInterface.CRD[1] = cubeColours.get(43); UserInterface.CRD[5] = cubeColours.get(23); 
		UserInterface.CLU[2] = cubeColours.get(3); UserInterface.CLU[4] = cubeColours.get(28); 
		UserInterface.CRU[1] = cubeColours.get(37); UserInterface.CRU[2] = cubeColours.get(5); 
		
		UserInterface.BLD[3] = cubeColours.get(53); UserInterface.BLD[4] = cubeColours.get(33); UserInterface.BLD[5] = cubeColours.get(24);
		UserInterface.BD[3] = cubeColours.get(52); UserInterface.BD[5] = cubeColours.get(25);
		UserInterface.BRD[1] = cubeColours.get(44); UserInterface.BRD[3] = cubeColours.get(51); UserInterface.BRD[5] = cubeColours.get(26);
		UserInterface.BL[3] = cubeColours.get(50); UserInterface.BL[4] = cubeColours.get(30);
		UserInterface.BR[1] = cubeColours.get(41); UserInterface.BR[3] = cubeColours.get(48);
		UserInterface.BLU[2] = cubeColours.get(0); UserInterface.BLU[3] = cubeColours.get(47); UserInterface.BLU[4] = cubeColours.get(27); 
		UserInterface.BU[2] = cubeColours.get(1); UserInterface.BU[3] = cubeColours.get(46);
		UserInterface.BRU[1] = cubeColours.get(38); UserInterface.BRU[2] = cubeColours.get(2); UserInterface.BRU[3] = cubeColours.get(45);
		
		
		
	}

}
