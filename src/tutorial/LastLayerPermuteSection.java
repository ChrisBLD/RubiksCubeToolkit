package tutorial;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class LastLayerPermuteSection {
	static boolean forwardOrBack;
	static int bodyCount, bodyCountFloor;
	static final int MAX = 16;
	
	
	public static void begin(ArrayList<String> allMoves, SequentialTransition seqOut, SequentialTransition seqIn, ArrayList<Label> elements, Button forward, Button back, Button restartSection, Button skipToDemo) {
		
		String[] bodyText = {"We're on to the final stage of solving the Rubik's Cube - permuting the last layer (PLL).  " + 
							 "We need to move the last layer pieces around to get them into the correct places. ",
							 
							 "Again, we'll break this down into two steps: First, we want to solve all of the corner  " + 
							 "pieces, and secondly we want to solve all of the edge pieces. ",
							 
							 "When solving the corners, we only need to know one algorithm which we will repeat up  " + 
							 "to three times until we've solved the corners - just like the Sune algorithm from before. ",
							 
							 "This algorithm is known as an A-permutation, and it cycles 3 corners on the puzzle. It's " + 
							 "all we need to solve all of the corners. ",
							 
							 "The most important concept for this section is headlights. Headlights refers to the pattern  " + 
							 "on the top layer where two adjacent sides of the corners match. You can have multiple sets " + 
							 "of headlights, or none. ",
							 
							 "The first step is to adjust the top layer so that a pair of headlights is facing away from " + 
							 "you on the B face. If you don't have any headlights, then performing the algorithm from any " + 
							 "angle will create a pair of headlights. ",
							 
							 "Let's try this on your cube. First, let's position the headlights in the back (or perform  " + 
							 "the algorithm from the front to get headlights if there aren't any): ",
							 
							 "Now, let's perform the algorithm we saw to solve the top layer corners: ",
							 
							 "Great! Now that we've solved the corners we just have one thing left to do: solve the edges " + 
							 "of the top layer.",	
				 			 
							 "There is a chance that your cube is already solved here. If so, that's great! But just  " + 
							 "remember that this won't always happen, so it's probably worthwhile reading the last " + 
							 "section. ",
							 
							 "We need two algorithms for this: one that cycles three edges in one direction, and another " + 
							 "that cycles three edges in the opposite direction. The second algorithm is the inverse of " + 
							 "the first one - it's the same algorithm backwards. ",
							 
							 "The first algorithm will cycle the front three edges anti-clockwise. This algorithm will " + 
							 "only move these three edges - once completed, the rest of the cube will be unaffected. This " + 
							 "algorithm is known as a Ua permutation. ",
							 
							 "The second algorithm will cycle the front three edges clockwise. It's optional to learn this, " + 
							 "since you can just perform the first algorithm twice for the same outcome. However, since " + 
							 "it's just the first algorithm backwards, it's worth learning. We'll be using it in the demo. " + 
							 "This algorithm is known as a Ub permutation. ",
							 
							 "So now we can actually solve the edges. First, we need to look around our cube and see if we " + 
							 "have a bar. A bar is where the edge between two corners is the same colour as the corners. " + 
							 "If we have a bar, we want to move the U face to position it at the back, just like we did for " + 
							 "the headlights earlier. If we don't have a bar, we can do this algorithm from any angle. ",
							 
							 "So, as before, first we have to orient the top layer. Let's move the bar to the back (or, if " + 
							 "we don't have a bar, we'll do a Ua permutation to get one, and put that bar in the back). ",
							 
							 "Now we have a bar in the back, let's perform either a Ua or a Ub permutation to finish the cube!"
				 			 
				 			 
		};
		
		String[] resources = {"stepsToSolvePLL.png", "twoStepsPLL.png", "NULL", "aperm.png", "headlights.png", "headlightsInBack.png", "NULL", "NULL",
				              "stepsToSolvePLLedges.png", "NULL", "uPermBoth.png", "uPermCC.png", "uPermC.png", "solvedBar.png", "NULL", "NULL"};

		bodyCount = 0;
		restartSection.setDisable(false);
		skipToDemo.setDisable(false);
		seqOut.playFromStart();
		seqOut.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			if (bodyCount == MAX) {

    				elements.get(0).setText("");
    				elements.get(1).setText("                                                    "+
    										"                                                    ");
    				//LastLayerPermuteSection.begin(allMoves, seqOut, seqIn, elements, forward, back);
    				System.out.println("we're done!");
    			} else {
	    			forward.setDisable(false);
	    			back.setDisable(false);
	    	    	elements.get(0).setText("Permuting the Last Layer (PLL)");
	    	    	elements.get(1).setText(bodyText[bodyCount]);
	    	    	
	    	    	elements.get(2).setGraphic(new ImageView(new Image("/resources/"+resources[0])));
	    	    	HBox newBox = new HBox(elements.get(2));
	    	    	newBox.setPadding(new Insets(40,0,0,0));
	    	    	newBox.setAlignment(Pos.CENTER);
	    	    	TutorialHomepage.toolBarRight.getItems().set(2, newBox);
	    	    	back.setDisable(true);
	    	    	elements.get(2).setText("");
	    	    	elements.get(3).setText("");
	    	    	
	    	    	elements.get(2).setVisible(true);
	    	    	elements.get(3).setVisible(false);
	    	    	//TutorialHomepage.toolBarRight.getItems().remove(TutorialHomepage.toolBarRight.getItems().size()-1);
	    			seqIn.playFromStart();
    			}
    		}
		});
		
		@SuppressWarnings("unchecked")
		ArrayList<Label> text = (ArrayList<Label>) elements.clone();
		text.remove(0);
		SequentialTransition seqInText = SharedToolbox.initSeqTrans(text, true);
    	SequentialTransition seqOutText = SharedToolbox.initSeqTrans(text, false);
    	
    	seqOutText.setOnFinished(new EventHandler<ActionEvent>() {
 	    	@Override
 	    	public void handle(ActionEvent event) {
 	    		System.out.println("BODY COUNT IS:"+bodyCount);
 	    		if (bodyCount <= 4) {
 	    			if (forwardOrBack) {
	 	    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
	 	       			elements.get(1).setText(bodyText[bodyCount]);
	 	       		} else {
	 	       			bodyCount = SharedToolbox.bodyCountDec(bodyCount);
		       			elements.get(1).setText(bodyText[bodyCount]);
	 	       		}	
	 	    		if (resources[bodyCount].equals("NULL")) {
	 	    			elements.get(2).setGraphic(null);
	 	    			elements.get(2).setVisible(false);
	 	    		} else {
	 	    			elements.get(2).setVisible(true);
	 	    			//System.out.println("setting graphic: "+resources[bodyCount]);
	       				elements.get(2).setGraphic(new ImageView(new Image("/resources/"+resources[bodyCount])));
	 	    		}
	 	    		seqInText.playFromStart();   
 	    		} else if (bodyCount == 5 || bodyCount == 13 || bodyCount == 6 || bodyCount == 14) {
	    			skipToDemo.setDisable(true);
	    			restartSection.setDisable(true);
	    			MoveManager.prepareDemo(elements);
		    		bodyCount = SharedToolbox.bodyCountInc(bodyCount);
					elements.get(1).setText(bodyText[bodyCount]);
					if (bodyCount == 6) {
						System.out.println("BODY COUNT: "+bodyCount+", STAGE: 28");
						MoveManager.main(allMoves, elements, forward, back, 29); 
					} else if (bodyCount == 7) {
						System.out.println("BODY COUNT: "+bodyCount+", STAGE: 29");
						MoveManager.main(allMoves, elements, forward, back, 30); 
					} else if (bodyCount == 14) {					
						System.out.println("BODY COUNT: "+bodyCount+", STAGE: 30");
						MoveManager.main(allMoves, elements, forward, back, 31); 
					} else {
						System.out.println("BODY COUNT: "+bodyCount+", STAGE: 32");
	 	    			MoveManager.main(allMoves, elements, forward, back, 32); 	
					}
	    			seqInText.playFromStart(); 
 	    		} else if (bodyCount <= 12 || bodyCount == 15){
 	    			if (bodyCount == 7 || bodyCount == 15) {
 	    				MoveManager.kill();
    					back.setDisable(true);
    					elements.get(2).setText("");
    					if (bodyCount == 15) {
    						UserInterface.timeline2.playFromStart();
    					}
    					skipToDemo.setDisable(false);
 	    			} else {
 	    				back.setDisable(false);
 	    			}
 	    			if (forwardOrBack) {
	 	    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
	 	       			elements.get(1).setText(bodyText[bodyCount]);
	 	       		} else {
	 	       			bodyCount = SharedToolbox.bodyCountDec(bodyCount);
		       			elements.get(1).setText(bodyText[bodyCount]);
	 	       		}	
	 	    		if (resources[bodyCount].equals("NULL")) {
	 	    			elements.get(2).setGraphic(null);
	 	    			elements.get(2).setVisible(false);
	 	    		} else {
	 	    			elements.get(2).setVisible(true);
	       				elements.get(2).setGraphic(new ImageView(new Image("/resources/"+resources[bodyCount])));
	 	    		}
	 	    		seqInText.playFromStart(); 

 	    		}
 	    			
 	    	}
    	});
    	
    	forward.setOnAction(event -> {changeDir(true); checkValid(seqOut, seqOutText, bodyText);});
 	    
 	    back.setOnAction(event -> {changeDir(false); checkValid(seqOut, seqOutText,bodyText);});
 	    
 	    restartSection.setOnAction(event -> {restart(seqOutText);});
 	    
 	    skipToDemo.setOnAction(event -> {skipInfo(seqOutText);});
	}
	
    private static void checkValid(SequentialTransition seqOut, SequentialTransition seqOutText, String[] bodyText) {
		if (forwardOrBack) {
			if (bodyCount != MAX) {
				seqOutText.playFromStart();
			} else {
				seqOut.playFromStart();
			}
		} else {
			if (bodyCount != bodyCountFloor) {
				seqOutText.playFromStart();
			}
		}
    }

    private static void changeDir(boolean dir) {
    	forwardOrBack = dir;
    }

	private static void skipInfo(SequentialTransition seqOutText) {
		if (bodyCount <=5) {
			bodyCount = 5;
			seqOutText.playFromStart();
		} else {
			bodyCount = 13;
			seqOutText.playFromStart();
		}
	}
	
	private static void restart(SequentialTransition seqOutText) {
		bodyCount = -1;
		seqOutText.playFromStart();
	}
}
