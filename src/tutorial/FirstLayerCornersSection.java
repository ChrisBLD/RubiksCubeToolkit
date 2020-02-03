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
	static final int MAX = 4;
	
	
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
							 "it will look solved.",
							 "There are two separate cases to consider when moving a piece below its location: "+ 
							 "the case where the piece is already on the bottom layer, or the case where "+ 
							 "the piece is inserted into a different slot on the top layer."
							 
							 
		
		
		};
							 
		
		String[] resources = {"solvedLayer1.png", "stepsForFirstLayer.png", "incorrectLocation.png", "twoCasesFLC.png"};
		System.out.println("here!");
	
		seqOut.playFromStart();
		seqOut.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
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
    	    	TutorialHomepage.toolBarRight.getItems().remove(TutorialHomepage.toolBarRight.getItems().size()-1);
    			seqIn.playFromStart();
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
	       				elements.get(2).setGraphic(new ImageView(new Image("/resources/"+resources[bodyCount])));
	 	    		}
	 	    		seqInText.playFromStart();   
 	    		}
 	    	}
    	});
    	
    	
    	forward.setOnAction(event -> {changeDir(true); checkValid(seqOutText, bodyText);});
 	    
 	    back.setOnAction(event -> {changeDir(false); checkValid(seqOutText,bodyText);});
 	    	
	}
    	
    private static void checkValid(SequentialTransition seqOutText, String[] bodyText) {
    		if (forwardOrBack) {
    			if (bodyCount != MAX) {
    				seqOutText.playFromStart();
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
