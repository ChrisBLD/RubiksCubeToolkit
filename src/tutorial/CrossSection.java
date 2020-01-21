package tutorial;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CrossSection {

	static ArrayList<String> placeMoves = new ArrayList<String>();
	static ArrayList<String> orientMoves = new ArrayList<String>();
	static boolean forwardOrBack;
	static int bodyCount;	
	public static void begin(ArrayList<String> allMoves, SequentialTransition seqOut, SequentialTransition seqIn, ArrayList<Label> elements, Button forward, Button back) {
		for (String s : allMoves) {
			if (s.toCharArray()[0] == '1') {
				placeMoves.add(s);
			} else if (s.toCharArray()[0] == '2') {
				orientMoves.add(s);
			}
		}
		
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
							 "cube in under 5 seconds!",
							 "The Cross involves building a cross (or plus) shape around one of the centres " + 
							 "on the puzzle. It can be done in two steps: " + 
							 "1) Putting the cross pieces into the locations that they need to be in, " + 
							 "2) Orienting the cross pieces so the same colour is facing up on all pieces. ",
							 "Make sure that the piece you're solving is always the one directly in front " + 
							 "of you on the top layer!", 
							 "Pieces of the Cross can be in any place on the cube, but there are three " + 
							 "distinct categories that tell us how to go about solving each piece: " + 
							 "1) The Cross piece is on the bottom layer (D), " + 
							 "2) The Cross piece is on the top layer (U), " + 
							 "3) The Cross piece is in the middle layer",
							 "When a Cross piece is on the bottom layer, it's very simple to move it into " + 
							 "position: Simply place it below the location it belongs in* using D moves, " + 
							 "and then move it up to the top layer with an F2 move (turn the front face " + 
							 "180 degrees)." + 
							 "\n*When we say place the piece *below the location where it belongs*, this means\r\n" + 
							 "to look at the other colour of the cross edge and make sure that matches the\r\n" + 
							 "centre piece on the face that's in front of you.",
							 "When a Cross piece is on the top layer, we first have to check if it's in the\r\n" + 
							 "correct position. If it is, then we can leave it alone as it is already fine!\r\n" + 
							 "If it isn't, then we need to bring it down, again with another F2 move. Then\r\n" + 
							 "we can solve it in the same way we would for a piece that started on the\r\n" + 
							 "bottom layer - bring it below its positon with D moves then perform an F2 \r\n" + 
							 "move."};
														 
		
		seqOut.playFromStart();
		seqOut.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    	    	elements.get(0).setText("Solving the Cross");
    	    	elements.get(1).setText(bodyText[bodyCount]);  
    	    	elements.get(2).setText("");
    	    	elements.get(3).setText("");
    	    	elements.get(2).setVisible(false);
    	    	elements.get(3).setVisible(false);
    	    	TutorialHomepage.toolBarRight.getItems().remove(TutorialHomepage.toolBarRight.getItems().size()-1);
    			seqIn.playFromStart();
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
 	    		if (forwardOrBack) {
 	    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
 	       			elements.get(1).setText(bodyText[bodyCount]);
 	       		} else {
 	       			bodyCount = SharedToolbox.bodyCountDec(bodyCount);
	       			elements.get(1).setText(bodyText[bodyCount]);
 	       		}	
 	    		if (bodyCount == 1) {
 	    				elements.get(2).setVisible(true);
	       				elements.get(2).setGraphic(new ImageView(new Image("/resources/dButton.png")));
	       		} else {
	       			elements.get(2).setVisible(false);
	       		}
 	    		
 	       		seqInText.playFromStart();    			
 	    	}
 	    });
    	
    	forward.setOnAction(event -> {changeDir(true); checkValid(seqOutText, bodyText);});
 	    
 	    back.setOnAction(event -> {changeDir(false); checkValid(seqOutText,bodyText);});
 	    	
 	    	
		
	
	}
	
	public static void checkValid(SequentialTransition seqOutText, String[] bodyText) {
		if (forwardOrBack) {
			seqOutText.playFromStart();
		}
	}
	
	public static void changeDir(boolean dir) {
    	forwardOrBack = dir;
    }

}
