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

public class FirstLayerCornersSection {
	
	static ArrayList<String> bringCornerDownMoves = new ArrayList<String>();
	static ArrayList<String> insertCornerMoves = new ArrayList<String>();
	static boolean forwardOrBack;
	static int bodyCount, bodyCountFloor;
	static final int MAX = 18;
	
	
	public static void begin(ArrayList<String> allMoves, SequentialTransition seqOut, SequentialTransition seqIn, ArrayList<Label> elements, Button forward, Button back) {
		for (String s : allMoves) {
			if (s.toCharArray()[0] == '3') {
				bringCornerDownMoves.add(s);
			} else if (s.toCharArray()[0] == '4') {
				insertCornerMoves.add(s);
			}
		}
		
		String[] bodyText = {"In order to finish the first layer of the puzzle, we have to insert the " + 
							 "corners that match the edges into the respective slots. Once we've inserted " + 
							 "every corner, we should have one entire \"layer\" (not just a face!) solved.",
							 
							 "We will solve these corners in two distinct sets of moves: The first will be "+
							 "to position the corners below the location they need to be in, the second "+
							 "will be to apply moves to \"bring them up\" to that location without disturbing "+
							 "the cross.",
							 
							 "Remember, as before, a location is defined by centres. You can see here that "+
							 "this \"location\" will be the white, green and red location as it is next to "+
							 "those three centres. When it's solved, you'll know if it's correct because "+
							 "the colours of the corner will match the edges adjacent to it.",
							 
							 "There are two separate cases to consider when moving a piece below its location: "+ 
							 "the case where the piece is already on the bottom layer, or the case where "+ 
							 "the piece is inserted into a different slot on the top layer.",
							 
							 "If the piece is already on the bottom layer, we just have to twist the bottom " + 
							 "layer (apply D moves) until the corner we want to solve is below its location. ",
							 
							 "If the piece is on the top layer, then you'll have to first get it out - this " + 
							 "can be done by rotating the puzzle to place that corner in the location facing " + 
							 "you, performing this short algorithm, and then rotating back.",
							 
							 "After you've got the piece below it's location, you have to check how it is " + 
							 "oriented to decide which algorithm you use. The corner can be twisted in three " + 
							 "different ways:",
							 
							 "The first case is the simplest - where the white sticker is facing the right " + 
							 "of the puzzle. This can be solved with a simple combination of four moves. "+
							 "Don't forget to the do the final D move - it doesn't matter here, but missing it "+
							 "later could ruin all of your progress!",
							 
							 "The second case is also quite simple - you use the same four moves you applied " + 
							 "to solve the previous case, but backwards.",
							 
							 "The final case is slightly more complicated, but just follow the white piece " + 
							 "to see how it works. It has two parts to it: Setting up to the first case, and " + 
							 "then executing the first case. Navigate back a few pages if you need to review the "+
							 "moves to solve from this point!",
							 
							 "You can apply these moves to solve each corner of the first layer - let's give " + 
							 "it a go with your scramble. Follow the moves to position the first corner below its location: ",
							 
							 "Great! Now let's solve the piece you just brought down:                          ",
							 
							 "Perfect! That's one corner solved. Now let's do the same thing for the rest. Don't "+
							 "forget to rotate your cube so the piece you're solving is in that top front spot!",
							 
							 "Great! Now let's solve the piece you just brought down:\n "+
							 "                                                                                        ",
							 
							 "Now let's move the third corner below it's location....\n " + 
							 "							                                                              ",
							 "...and place the third corner into it's location.\n " + 
							 "						                                                                   ",
							 "Once more for the last corner, let's bring it below its location:\n "+
							 "							                                                               ",
							 "and solve it to complete the First Layer!\n "+
							 "							                                                                ",
							 "Congratulations! You've now solved the first layer of the Rubik's Cube! Now that the top is "+
							 "completely solved, you can flip the puzzle round to place the white face on the bottom - we "+
						     "won't need it anymore. Keep the Green centre on the front though!"
		};
							 
		
		String[] resources = {"solvedLayer1.png", "stepsForFirstLayer.png", "incorrectLocation.png", "twoCasesFLC.png", "bottomLayerFLC.png", "topLayerFLC.png",
							  "placingCornerCasesFLC.png", "solveWhiteRightFLC.png", "solveWhiteFrontFLC.png", "solveWhiteBottomFLC.png", "NULL"};
		System.out.println("here!");
		bodyCount = 9;
		seqOut.playFromStart();
		seqOut.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			if (bodyCount == 18) {
    				UserInterface.makeZ2rotation();
    				elements.get(0).setText("");
    				elements.get(1).setText("                                                    "+
    										"                                                    ");
    				SecondLayerEdgesSection.begin(allMoves, seqOut, seqIn, elements, forward, back);
    				System.out.println("we're done!");
    			} else {
	    			forward.setDisable(false);
	    			back.setDisable(false);
	    	    	elements.get(0).setText("Solving the First Layer Corners");
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
 	    		if (bodyCount <= 8) {
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
 	    		} else if (bodyCount <=16) {
 	    			if (bodyCount == 9) {
 	    				MoveManager.prepareDemo(elements);
 	    			} else if (bodyCount % 2 == 1) {
 	    				UserInterface.makeYrotation(false);
 	    			}
 	    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    				elements.get(1).setText(bodyText[bodyCount]);
    				System.out.println("BODY COUNT: "+bodyCount+", STAGE: "+(bodyCount-1));
    				MoveManager.main(allMoves, elements, forward, back, bodyCount-1); 	    
 	    			seqInText.playFromStart(); 
 	    		} else if (bodyCount == 17) {
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
	
}
