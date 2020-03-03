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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Setup {
	
	static int bodyCount = 0;
	static boolean forwardOrBack = true;
	static ArrayList<Button> buttonArray = new ArrayList<Button>();
	static ArrayList<Label> elements;
	
	static Button forward, back, restartSection, skipToDemo;
	
	public static final int RED     = 0;
    public static final int GREEN   = 1;
    public static final int BLUE    = 2;
    public static final int YELLOW  = 3;
    public static final int ORANGE  = 4;
    public static final int WHITE   = 5;
    public static final int GRAY    = 6;
    
    static String[][][] changes = 
		{{{"Uw"},{"U","U"},{"D","D"},{"F","R"},{"R","B"},{"B","L"},{"L","F"}},
		{{"Uw'"},{"U","U"},{"D","D"},{"F","L"},{"R","F"},{"B","R"},{"L","B"}},
		{{"Uw2"},{"U","U"},{"D","D"},{"F","B"},{"R","L"},{"B","F"},{"L","R"}},

		{{"Fw"},{"U","L"},{"D","R"},{"F","F"},{"R","U"},{"B","B"},{"L","D"}},
		{{"Fw'"},{"U","R"},{"D","L"},{"F","F"},{"R","D"},{"B","B"},{"L","U"}},
		{{"Fw2"},{"U","D"},{"D","U"},{"F","F"},{"R","L"},{"B","B"},{"L","R"}},

		{{"Rw"},{"U","F"},{"D","B"},{"F","D"},{"R","B"},{"B","U"},{"L","F"}},
		{{"Rw'"},{"U","B"},{"D","F"},{"F","U"},{"R","B"},{"B","D"},{"L","F"}},
		{{"Rw2"},{"U","D"},{"D","U"},{"F","B"},{"R","R"},{"B","F"},{"L","L"}}};


    
    private static SequentialTransition seqOut, seqIn;

	public static void main(ArrayList<Label> elements, Button forward, Button back, Button restartSection, Button skipToDemo, SequentialTransition seqIn, SequentialTransition seqOut) {
		
		String[] bodyText = {"To continue with this tutorial, it is strongly advised that you have your own Rubik's Cube "+
							 "so you can try the moves yourself and follow what happens on screen. Do you have your own puzzle?"+
							 "\n\nClick 'Next Page' for Yes\nClick 'Previous Page' for No",
							 
							 "Great! If your puzzle is currently solved, then scramble it as well as you can before proceeding "+
							 "to the next step. If your puzzle is already scrambled, then you're ready to get going!",
							 
							 "Please open the cube net below and replicate your scrambled puzzle on the net shown. Alternatively, "+
							 "enter a scramble below to run through the tutorial using that instead."
									 
		};
		String[] badText = {"You can still complete this tutorial without your own cube, but you will be limited to using a default "+
						    "scramble and following the tutorial using only the virtual cube on the left of the screen. This scramble "+
						    "will teach you everything you need to know - but the best way to learn is to try it yourself!",
						    
						    "This is the scramble that Yusheng Du set the current 3x3 Speedsolve World Record on. He used "+
						    "a more advanced version of the method that we'll be learning, so we won't quite do the same solution as him!"
		};
		Setup.seqIn = seqIn;
		Setup.seqOut = seqOut;
		Setup.elements = elements;
		Setup.forward = forward;
		Setup.back = back;
		Setup.restartSection = restartSection;
		Setup.skipToDemo = skipToDemo;
		
		boolean[] buttonValueArray = {false, false, true, true}; //forward, back, restartSection, skipToDemo
    	disableButtons(forward, back, restartSection, skipToDemo);
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
    			System.out.println("active");
 	    		enableButtons(forward, back, restartSection, skipToDemo, buttonValueArray);
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
    				buttonValueArray[1] = true;
	    			elements.get(1).setText(bodyText[bodyCount]);
	    			elements.get(2).setText("");
	    			if (bodyCount == 2) {
	    				buttonValueArray[0] = true;
	    				generateInputButton();
	    			}
    			} else {
    				buttonValueArray[1] = true;
    				if (bodyCount == 4) {
    					UserInterface.yushengScramble();
    					UserInterface.timeline.playFromStart();
    					elements.get(2).setGraphic(new ImageView(new Image("/resources/yusheng-du-record.jpg")));
    					elements.get(1).setText(badText[bodyCount-3]);
		    			elements.get(2).setText("");
		    			seqInText.playFromStart();
    				} else if (bodyCount == 5) {
    					elements.get(2).setGraphic(null);
    					elements.get(2).setText("                                                                                     ");
    					elements.get(1).setText("");
    					ArrayList<String> allMoves = CubeSolver.deriveSolution(buttonArray);
    					CrossSection.begin(allMoves, seqOut, seqIn, elements, forward, back, restartSection, skipToDemo);
    				} else {
	    				elements.get(1).setText(badText[bodyCount-3]);
		    			elements.get(2).setText("");
		    			seqInText.playFromStart();
    				}
    			}
    			back.setDisable(true);
    			seqInText.playFromStart();
    		}
    	});
    	
    	seqInText.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			enableButtons(forward, back, restartSection, skipToDemo, buttonValueArray);
    		}
    	});
    	
    	forward.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			disableButtons(forward, back, restartSection, skipToDemo);
    			System.out.println("bodycount: "+bodyCount);
    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    			System.out.println("bodycount: "+bodyCount);
    			seqOutText.playFromStart();
    		}
    	});
    	
    	back.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    	    	disableButtons(forward, back, restartSection, skipToDemo);
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
		Button submit = new Button();
		TextField scramble = new TextField();
		
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
				
				
				
				UserInterface.makeRmove(true); UserInterface.makeUmove(true); UserInterface.makeFmove(true); UserInterface.makeLmove(true); UserInterface.makeDmove(true);
				UserInterface.makeDmove(false); UserInterface.makeLmove(false); UserInterface.makeFmove(false); UserInterface.makeUmove(false); UserInterface.makeRmove(false);
				ArrayList<String> allMoves = CubeSolver.deriveSolution(buttonArray);
				getColours.setVisible(false);
				submit.setVisible(false);
				scramble.setVisible(false);
				CrossSection.begin(allMoves, seqOut, seqIn, elements, forward, back, restartSection, skipToDemo);
			}
		});
		
        scramble.setPromptText("Enter a scramble here");
        scramble.setMaxWidth(250); scramble.setMinWidth(250);

        submit.setMinSize(125, 59); submit.setMaxSize(125, 59);
        submit.setGraphic(new ImageView(new Image("/resources/submitButton.png")));
        submit.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		String scram = scramble.getText();
        		ArrayList<String> moves = convertScramble(scram);
        		if (moves.size() == 0) {
        			elements.get(1).setText("That scramble isn't valid. Please try again, or use the colour selection input instead.");
        		} else {
        			for (String move : moves) {
        				System.out.println(move);
        			}
        			applyMoves(moves);
        			ArrayList<String> allMoves = CubeSolver.deriveSolution(buttonArray);
    				getColours.setVisible(false);
    				submit.setVisible(false);
    				scramble.setVisible(false);
    				CrossSection.begin(allMoves, seqOut, seqIn, elements, forward, back, restartSection, skipToDemo);
        		}
        		
        	}
        });
        
       

		VBox test = new VBox(35); 
		test.getChildren().addAll(getColours, scramble, submit);
		test.setAlignment(Pos.CENTER);
		
		

		TutorialHomepage.toolBarRight.getItems().set(2,test);

	}
	
	private static void disableButtons(Button forward, Button back, Button restartSection, Button skipToDemo) {
		forward.setDisable(true);
		back.setDisable(true);
		restartSection.setDisable(true);
		skipToDemo.setDisable(true);
	}
	
	private static void enableButtons(Button forward, Button back, Button restartSection, Button skipToDemo, boolean[] buttonValueArray) {
		forward.setDisable(buttonValueArray[0]);
		back.setDisable(buttonValueArray[1]);
		restartSection.setDisable(buttonValueArray[2]);
		skipToDemo.setDisable(buttonValueArray[3]);
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
	
	public static ArrayList<String> convertScramble(String scramble) {
    	String wideMove = "";
    	
    	ArrayList<String> moves = new ArrayList<String>();
    	char[] scramChars = scramble.toCharArray();
    	for (int i = 0; i < scramChars.length; i++) {
    		char c = scramChars[i];
    		if (c == 'U' || c == 'F' || c == 'R' || c == 'B' || c == 'L' || c == 'D') {
    			try {
    				if (scramChars[i+1] == '\'') {
    					moves.add(c+"p");
    				} else if (scramChars[i+1] == '2') {
    					moves.add(Character.toString(c));
    					moves.add(Character.toString(c));
    				} else if (scramChars[i+1] == 'w') {
    					if (wideMove == "") {
    						wideMove+= c;
    						wideMove+= "w";
    						//System.out.println("Character: "+c);
    						try {
    							if (scramChars[i+2] == '\'') {
    								wideMove+= "'";
    								//System.out.println("Character: "+c);
    								
    								switch (c) {
    								case 'U': moves.add('D'+"p"); break;
    								case 'F': moves.add('B'+"p"); break;
    								case 'R': moves.add('L'+"p"); break;
    								case 'B': moves.add('F'+"p"); break;
    								case 'L': moves.add('R'+"p"); break;
    								case 'D': moves.add('U'+"p"); break;
    								}
    							} else if (scramChars[i+2] == '2') {
    								wideMove+= "2";
    								switch (c) {
    								case 'U': moves.add(Character.toString('D')); moves.add(Character.toString('D')); break;
    								case 'F': moves.add(Character.toString('B')); moves.add(Character.toString('B'));  break;
    								case 'R': moves.add(Character.toString('L')); moves.add(Character.toString('L'));  break;
    								case 'B': moves.add(Character.toString('F')); moves.add(Character.toString('F'));  break;
    								case 'L': moves.add(Character.toString('R')); moves.add(Character.toString('R'));  break;
    								case 'D': moves.add(Character.toString('U')); moves.add(Character.toString('U'));  break;
    								}
    							} else {
    								switch (c) {
    								case 'U': moves.add(Character.toString('D')); break;
    								case 'F': moves.add(Character.toString('B')); break;
    								case 'R': moves.add(Character.toString('L')); break;
    								case 'B': moves.add(Character.toString('F')); break;
    								case 'L': moves.add(Character.toString('R')); break;
    								case 'D': moves.add(Character.toString('U')); break;
    								}
    							}
    						} catch (Exception e) {
    							switch (c) {
    							case 'U': moves.add(Character.toString('D')); break;
    							case 'F': moves.add(Character.toString('B')); break;
    							case 'R': moves.add(Character.toString('L')); break;
    							case 'B': moves.add(Character.toString('F')); break;
    							case 'L': moves.add(Character.toString('R')); break;
    							case 'D': moves.add(Character.toString('U')); break;
    							}
    						}
    					} else { //Second Wide Move	
    						String secondMove = c+"w";
    						try {
    							if (scramChars[i+2] == '\'') {
    								secondMove += "'";
    							} else if (scramChars[i+2] == '2') {
    								secondMove += "2";
    							}
    						} catch (Exception e) {};

    						String convertedMove = "";
    						for (String[][] listOChanges : changes) {
    							if (wideMove.equals(listOChanges[0][0])) {
    								for (int x = 1; x < listOChanges.length; x++) {
    									if (listOChanges[x][0].equals(Character.toString(c))) {
    										convertedMove = listOChanges[x][1];
    									}
    								}
    							}
    						}
    						convertedMove += "w";
    						for (char a : secondMove.toCharArray()) {
    							if (a == '\'') {
    								convertedMove += "'";
    							} else if (a == '2') {
    								convertedMove += "2";
    							}
    						}

    						//System.out.println("First Wide Move: "+wideMove+", Second Wide Move: "+secondMove+", Converted Second Wide Move: "+convertedMove);
    						
    						if (convertedMove.length() == 2) {
    							switch (convertedMove.toCharArray()[0]) {
    							case 'U': moves.add(Character.toString('D')); break;
    							case 'F': moves.add(Character.toString('B')); break;
    							case 'R': moves.add(Character.toString('L')); break;
    							case 'B': moves.add(Character.toString('F')); break;
    							case 'L': moves.add(Character.toString('R')); break;
    							case 'D': moves.add(Character.toString('U')); break;
    							}
    						} else if (convertedMove.toCharArray()[2] == '2') {
    							switch (convertedMove.toCharArray()[0]) {
    							case 'U': moves.add(Character.toString('D')); moves.add(Character.toString('D')); break;
    							case 'F': moves.add(Character.toString('B')); moves.add(Character.toString('B'));  break;
    							case 'R': moves.add(Character.toString('L')); moves.add(Character.toString('L'));  break;
    							case 'B': moves.add(Character.toString('F')); moves.add(Character.toString('F'));  break;
    							case 'L': moves.add(Character.toString('R')); moves.add(Character.toString('R'));  break;
    							case 'D': moves.add(Character.toString('U')); moves.add(Character.toString('U'));  break;
    							}
    						} else {
    							switch (convertedMove.toCharArray()[0]) {
    							case 'U': moves.add('D'+"p"); break;
    							case 'F': moves.add('B'+"p"); break;
    							case 'R': moves.add('L'+"p"); break;
    							case 'B': moves.add('F'+"p"); break;
    							case 'L': moves.add('R'+"p"); break;
    							case 'D': moves.add('U'+"p"); break;
    							}
    						}
    	
    					}
    					//moves.add(Character.toString(c));
    					
    				} else {
    					moves.add(Character.toString(c));
    				}
    			} catch (Exception e) {
    				//System.out.println("FUCK");
    				moves.add(Character.toString(c));
    			}
    		}
    	}	
	    return moves;
    }
	
    public static void applyMoves(ArrayList<String> moves) {
    	for (String move : moves) {
    		switch(move) {
    		case "U": UserInterface.makeUmove(false); System.out.println("doing U move"); break;
    		case "F": UserInterface.makeFmove(false); System.out.println("doing F move"); break;
    		case "R": UserInterface.makeRmove(false); System.out.println("doing R move"); break;
    		case "D": UserInterface.makeDmove(false); System.out.println("doing D move"); break;
    		case "B": UserInterface.makeBmove(false); System.out.println("doing B move"); break;
    		case "L": UserInterface.makeLmove(false); System.out.println("doing L move"); break;
    		case "Up": UserInterface.makeUmove(true); System.out.println("doing U' move"); break;
    		case "Fp": UserInterface.makeFmove(true); System.out.println("doing F' move"); break;
    		case "Rp": UserInterface.makeRmove(true); System.out.println("doing R' move"); break;
    		case "Dp": UserInterface.makeDmove(true); System.out.println("doing D' move"); break;
    		case "Bp": UserInterface.makeBmove(true); System.out.println("doing B' move"); break;
    		case "Lp": UserInterface.makeLmove(true); System.out.println("doing L' move"); break;
    		}
    	}
    }
}
