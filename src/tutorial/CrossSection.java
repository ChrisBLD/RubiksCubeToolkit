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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CrossSection {

	static ArrayList<String> placeMoves = new ArrayList<String>();
	static ArrayList<String> orientMoves = new ArrayList<String>();
	static boolean forwardOrBack;
	static int bodyCount, bodyCountFloor;
	static final int MAX = 24;
	
	
	public static void begin(ArrayList<String> allMoves, SequentialTransition seqOut, SequentialTransition seqIn, ArrayList<Label> elements, Button forward, Button back) {
		for (String s : allMoves) {
			if (s.toCharArray()[0] == '1') {
				placeMoves.add(s);
			} else if (s.toCharArray()[0] == '2') {
				orientMoves.add(s);
			}
		}
		bodyCountFloor = 0;
		
		String[] bodyText = {"We will use the centre stickers on the Rubik's Cube as our guide. We should " + 
							 "always consider centres as fixed points that never change place - every " + 
							 "other piece moves around them without swapping them, just twisting them.",
							 "When we refer to a 'location', that means the place between two centres. " + 
							 "On the virtual cube to the left, you can see that the edge sitting between " + 
							 "the white and green centre pieces would be the white-green 'location' - " + 
							 "when the cube is solved, the white-green edge piece will be in it.",
							 "The first step of solving the Rubik's Cube is to build the foundations of " + 
							 "the first layer which you will use to solve other pieces around later on. " + 
							 "This stage is known as the Cross.",
							 "The Cross is a simple yet powerful way to start a solve - it's even used " + 
							 "in the most popular speedsolving method, where some people have solved the " + 
							 "cube in under five seconds!",
							 "The Cross involves building a cross (or plus) shape around one of the centres " + 
							 "on the puzzle. It can be done in two steps: " + 
							 "\n1) Putting the cross pieces into the locations that they need to be in, " + 
							 "\n2) Orienting the cross pieces so the same colour is facing up on all pieces. ",
							 "Make sure that the piece you're solving is always the one directly in front " + 
							 "of you on the top layer! That means each time you solve a piece, rotate the cube "
							 + "so the next piece you solve is the edge on the top and front:", 
							 "Pieces of the Cross can be in any place on the cube, but there are three " + 
							 "distinct categories that tell us how to go about solving each piece: " + 
							 "\n1) The Cross piece is on the bottom layer (D), " + 
							 "\n2) The Cross piece is on the top layer (U), " + 
							 "\n3) The Cross piece is in the middle layer",
							 "When a Cross piece is on the bottom layer, it's very simple to move it into " + 
							 "position: Simply place it below the location it belongs in using D moves, " + 
							 "and then move it up to the top layer with an F2 move (turn the front face " + 
							 "180 degrees). ", 
							 "Note: When we say place the piece below the location where it belongs, this means " + 
							 "to look at the other colour of the cross edge and make sure that matches the " + 
							 "centre piece on the face that's in front of you.",
							 "When a Cross piece is on the top layer, we first have to check if it's in the " + 
							 "correct position. If it is, then we can leave it alone as it is already fine! " + 
							 "If it isn't, then we need to bring it down, by turning that face 180 degrees. Then " + 
							 "we can solve it in the same way we would for a piece that started on the " + 
							 "bottom layer - bring it below its positon with D moves then perform an F2 " + 
							 "move.",
							 "It's important to do this instead of just doing a U' here because we don't want "+
							 "to affect the pieces we've already solved on the cross. Don't cut corners or "+
							 "it'll cause big problems!",
							 "When a Cross piece is in the middle layer, it can either be in the front or " + 
							 "the back of the cube. If it's in the front, we can solve it simply with " + 
							 "either an F or an F' move. If it's in the back, we need to bring it to the " + 
							 "front with a 180 degree turn of the left or right face, do an F or an F' " + 
							 "move to put it in place, then undo the 180 degree turn.",
							 "We have to do this final R2 in order to fix the Cross - we need to break it slightly "+
							 "in order to get the piece we're solving where we want, which is fine as long as we fix "+
							 "it at the end!",
							 "With these three cases, you should now be able to place all four cross edges " + 
							 "into their locations successfully. Let's see how we can do it on your scramble: ",
							 "Great! Now let's move on to placing the second cross edge...",
							 "Great! Now let's move on to placing the third cross edge...   ",
							 "Great! Now let's move on to placing the fourth cross edge...",
							 "We've now successfully placed each cross edge in its correct location! Now we move on "+
							 "to the next stage, which is orienting each of the mis-oriented cross edges.",
							 "After this stage, the entire cross should be solved with all edges matching the "+
							 "correct centres.",
							 "When we have an edge that is misoriented, it's pretty simple to solve. We can simply hold it "+
							 "at the front, and then perform this simple algorithm to flip it without affecting the other cross "+
							 "pieces.",
							 "So let's have a go! We're going to check if each edge is flipped",
							 "Great! Now let's move on to orienting the second cross edge...",
							 "Great! Now let's move on to orienting the third cross edge...   ",
							 "Great! Now let's move on to orienting the fourth cross edge...",
							 "We've now successfully finished the cross! All four cross pieces are in place and correctly oriented. "+
							 "Now, let's move on to the next stage: Finishing the first layer by solving the corners.\n\n Press "+
							 "\"Next\" when you're ready to continue."};
		
							 
		
		String[] resources = {"NULL","wgLocation.png", "NULL", "NULL", "whiteCross.png", "crossRotate.png", "crossLocations.png", "dLayerEdge.png",
							  "NULL", "uLayerEdge.png", "NULL", "eLayerEdge.png", "NULL", "NULL", "NULL", "NULL", "NULL", "orientingEdges.png", 
							  "solvedCross.png", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL"};
		
														 
		
		seqOut.playFromStart();
		seqOut.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			if (bodyCount == MAX) {
    				elements.get(0).setText("");
    				elements.get(1).setText("                                                    "+
    										"                                                    ");
    				FirstLayerCornersSection.begin(allMoves, seqOut, seqIn, elements, forward, back);
    			} else {
	    			forward.setDisable(false);
	    			back.setDisable(false);
	    	    	elements.get(0).setText("Solving the Cross");
	    	    	elements.get(1).setText(bodyText[bodyCount]);  
	    	    	HBox newBox = new HBox(elements.get(2));
	    	    	newBox.setPadding(new Insets(40,0,0,0));
	    	    	newBox.setAlignment(Pos.CENTER);
	    	    	TutorialHomepage.toolBarRight.getItems().set(2, newBox);
	    	    	elements.get(2).setText("");
	    	    	elements.get(3).setText("");
	    	    	
	    	    	elements.get(2).setVisible(false);
	    	    	elements.get(3).setVisible(false);
	    	    	TutorialHomepage.toolBarRight.getItems().remove(TutorialHomepage.toolBarRight.getItems().size()-1);
	    			seqIn.playFromStart();
    			}
    		}
		});
		
		
		@SuppressWarnings("unchecked")
		ArrayList<Label> text = (ArrayList<Label>) elements.clone();
		text.remove(0);
		SequentialTransition seqInText = SharedToolbox.initSeqTrans(text, true);
    	SequentialTransition seqOutText = SharedToolbox.initSeqTrans(text, false);
    	
    	bodyCount = 0;
    	forwardOrBack = true;
    	
    	 seqOutText.setOnFinished(new EventHandler<ActionEvent>() {
 	    	@Override
 	    	public void handle(ActionEvent event) {
 	    		System.out.println("BODY COUNT IS:"+bodyCount);
 	    		if (bodyCount <=11 || bodyCount == 16 || bodyCount == 17 || bodyCount == 18 || bodyCount == 23) {
 	    			if (bodyCount == 16 || bodyCount == 23) {
 	    				if (bodyCount == 23) {
 	    					UserInterface.makeYrotation(false);
 	    				} 
 	    				if (forwardOrBack) {
 	    					MoveManager.kill();
 	    					back.setDisable(false);
 	    					UserInterface.timeline2.play();
 	    				}
 	    				elements.get(2).setText("");
 	    			}
 	    			if (bodyCount == 16) {
 	    				bodyCountFloor = 17;
 	    			} else if (bodyCount == 23) {
 	    				back.setDisable(true);
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
 	    		} else if (bodyCount <=15) {
 	    			if (bodyCount == 12) {
 	    				MoveManager.prepareDemo(elements);
 	    			}
 	    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    				elements.get(1).setText(bodyText[bodyCount]);
    				System.out.println("BODY COUNT: "+bodyCount+", STAGE: "+(bodyCount-12));
    				MoveManager.main(allMoves, elements, forward, back, bodyCount-12); 	    
 	    			seqInText.playFromStart(); 
 	    		}
 	    		else if (bodyCount <= 22) {
 	    			if (bodyCount == 19) {
 	    				System.out.println("BODY COUNT IS 20");
 	    				MoveManager.prepareDemo(elements);
 	    			} else {
 	    				UserInterface.makeYrotation(false);
 	    			}
 	    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    				elements.get(1).setText(bodyText[bodyCount]);
    				MoveManager.main(allMoves, elements, forward, back, bodyCount-15);    
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
