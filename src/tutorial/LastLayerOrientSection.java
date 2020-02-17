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

public class LastLayerOrientSection {
	static boolean forwardOrBack;
	static int bodyCount, bodyCountFloor;
	static final int MAX = 30;
	
	
	public static void begin(ArrayList<String> allMoves, SequentialTransition seqOut, SequentialTransition seqIn, ArrayList<Label> elements, Button forward, Button back, Button restartSection, Button skipToDemo) {
		
		String[] bodyText = {"We've now solved the first two layers of the Rubik's Cube! Now all that's left is " + 
				 			 "the final challenge: The Last Layer. Unfortunately, as we've already built so much, " + 
				 			 "we have to be extra careful when solving pieces here and it takes quite a few steps " + 
				 			 "to solve them whilst maintaining our solved first two layers.",
				 			 
				 			 "We'll tackle this problem in two different steps. The first step will be to orient " + 
				 			 "all of the remaining pieces correctly (get all yellow stickers facing upwards), and " + 
				 			 "the second step will be to permute the pieces (move them around the top layers) until " + 
				 			 "the cube is completely solved. We'll just look at the first step for now.",
				 			 
				 			 "We need to get all of the yellow stickers facing upwards. We can split this into two " + 
				 			 "steps: Building a yellow cross on top, and then orienting the yellow corners. ",
				 			 
				 			 "To build the yellow cross, we need to see which of the four possible cases we have. " + 
				 			 "We can either have an L shape (two adjacent edges oriented correctly), a bar (two " + 
				 			 "opposite edges oriented correctly), a dot (no edges oriented correctly), or a cross " + 
				 			 "(all edges oriented correctly).",
				 			 
				 			 "If you have an L shape, you need to place the adjacent pieces in the top left (so the " + 
				 			 "solved pieces are in the back and left positions shown in the image), and perform the " + 
				 			 "following algorithm:",
				 			 
				 			 "If you have a bar shape, you need to place the bar so it is horizontal (so the solved " + 
				 			 "pieces are in the left and right positions shown in the image) and perform the following " + 
				 			 "algorithm:",
				 			
				 			 "If you have a dot, then simply do the bar algorithm from any angle and you will get an " + 
				 			 "L case. Then, solve that L case using the algorithm provided.",
				 			  
				 			 "Finally, if you have a cross (all four edges oriented correctly), then you're good to go! " + 
				 			 "This step is already solved for you, so move on to the next one.", 
				 			 
				 			 
				 			 
				 			 
		};
		
		String[] resources = {"solvedF2L.png", "NULL", "edgeLocationF2L.png", "twoCasesF2L.png", "topLayerF2L.png", 
							  "middleLayerF2L.png", "edgeOrientationsF2L.png", "insertEdgeOverviewF2L.png", 
							  "insertEdgeOneF2L.png", "insertEdgeTwoF2L.png", "insertEdgeOverviewBF2L.png", 
							  "insertEdgeThreeF2L.png", "insertEdgeFourF2L.png", "middleLayerBF2L.png"};

		bodyCount = 0;
		restartSection.setDisable(false);
		skipToDemo.setDisable(false);
		seqOut.playFromStart();
		seqOut.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			if (bodyCount == 30) {

    				elements.get(0).setText("");
    				elements.get(1).setText("                                                    "+
    										"                                                    ");
    				//SecondLayerEdgesSection.begin(allMoves, seqOut, seqIn, elements, forward, back);
    				System.out.println("we're done!");
    			} else {
	    			forward.setDisable(false);
	    			back.setDisable(false);
	    	    	elements.get(0).setText("Solving the Second Layer Edges");
	    	    	elements.get(1).setText(bodyText[bodyCount]);
	    	    	
	    	    	elements.get(2).setGraphic(new ImageView(new Image("/resources/"+resources[bodyCount])));
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
 	    		if (bodyCount <= 12) {
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
 	    		} else if (bodyCount <=20) {
 	    			if (bodyCount == 13) {
 	    				skipToDemo.setDisable(true);
 	    				restartSection.setDisable(true);
 	    				MoveManager.prepareDemo(elements);
 	    			} else if (bodyCount % 2 == 1) {
 	    				UserInterface.makeYrotation(false);
 	    			}
 	    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    				elements.get(1).setText(bodyText[bodyCount]);
    				System.out.println("BODY COUNT: "+bodyCount+", STAGE: "+(bodyCount+3));
    				MoveManager.main(allMoves, elements, forward, back, bodyCount+3); 	    
 	    			seqInText.playFromStart(); 
 	    		} else if (bodyCount == 23) {
	    			UserInterface.makeYrotation(false);
	    			if (forwardOrBack) {
	    				bodyCount = SharedToolbox.bodyCountInc(bodyCount);
	    				MoveManager.kill();
	    				back.setDisable(false);
	    				UserInterface.timeline2.play();
	    			}
	    			elements.get(1).setText(bodyText[bodyCount]);
	    			elements.get(2).setText("");
	    			elements.get(2).setGraphic(null);
	    			elements.get(3).setText("");
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
		bodyCount = 13;
		seqOutText.playFromStart();
	}
	
	private static void restart(SequentialTransition seqOutText) {
		bodyCount = -1;
		seqOutText.playFromStart();
	}
}
