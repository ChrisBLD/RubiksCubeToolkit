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

public class SecondLayerEdgesSection {
	
	static boolean forwardOrBack;
	static int bodyCount, bodyCountFloor;
	static final int MAX = 22;
	
	
	public static void begin(ArrayList<String> allMoves, SequentialTransition seqOut, SequentialTransition seqIn, ArrayList<Label> elements, Button forward, Button back, Button restartSection, Button skipToDemo) {
		
		String[] bodyText = {"Now that we've solved the first layer, we can move on to solving the edges of  " + 
							 "the second layer - this is the only step required to solve the second layer, " + 
							 "since the centres are already in place. Once this step is completed, there will  " + 
							 "only be one layer left to solve.",
							 
							 "We can solve the edges of this layer in a similar way to how we solved the  " + 
							 "corners of the first layer - first, we can bring the edge \"over\" its position, " + 
							 "and then use one of two algorithms to solve it depending on which slot it needs " + 
							 "to go into.",
							 
							 "When we say \"bring an edge over its location\", this means to place the edge " + 
							 "in the top layer such that applying a single F move would put the edge in its  " + 
							 "place. Since we're inserting middle layer edges, we are only looking at edges  " + 
							 "that don't have a yellow sticker - those edges belong on the top layer.",
							 
							 "When getting an edge over its position, there are two cases for the location of " + 
							 "the edge: It can either be on the top layer, or inserted into one of the four " + 
							 "middle layer edge locations. ",
							 
							 "For edges already on the top layer, we just have to apply U moves until the " + 
							 "edge is where we want it to be - just like we applied D moves to position the " + 
							 "cross edges and the first layer corners.",
							 
							 "For edges that are already inserted into locations, we need to first get them out, " + 
							 "and then move then above their proper location. Think of this step just like the " + 
							 "first layer corners - if the corner we wanted was already inserted, we'd have to " + 
							 "first get it out before we could deal with it.",
							 
							 "Now, when we've got the edge where we want it - above its location - we can solve " + 
							 "it. We can solve this with one of two algorithms, depending on which way the edge " + 
							 "is oriented.",

							 "The first algorithm will be used when the side of the edge facing us matches the " + 
							 "centre below it. This algorithm will insert this edge into its location without " + 
							 "ruining any of our progress so far. It does this by taking the corner out that we " + 
							 "solved previously, matching it with the edge, and inserting the pair back in.",
							 
							 "First, we take the corner out that we solved previously using the following moves... ",
							 
							 "...and then we pair it up with the edge and insert the whole pair back into the slot, "+
							 "solving this edge.",
							 
							 "The second algorithm will be used when the side of the edge facing us does not match "+
							 "the centre below it. This algorithm will do the same as the previous one, but just from "+
							 "the other side of the puzzle.",
							 
							 "We solve this in the same way but with slight different moves: First, we take the corner "+
							 "out using the following moves...",
							 
							 "...and then we pair it up with the edge and insert the whole pair back into the slot.",
							 
							 "If an edge is inserted into the wrong location, simply rotate so that the slot is in the "+
							 "front position and perform the first algorithm to get the edge out. That long algorith from "+
							 "earlier is doing just that!",

							 "Now let's try it with your scramble: Place the first edge above its location with the moves "+
							 "below...",
							 
							 "Nice! Now, let's insert that edge using the correct algorithm from the two we just saw: ",
							 
							 "Now for the second edge: Let's first place it above its location as shown previously...",
							 
							 "...and insert it into its position to solve the second edge on the middle layer",
							 
							 "Once again for the third edge, first placing it above its location...                         ",
							 
							 "...and then bringing it down into its location as shown below.",
							 
							 "One more time, we need to bring the final edge above its location...",
							 
							 "...and finally, we need to insert the last edge to complete the second layer...",
							 
							 "We've successfully completed the first two layers of the Rubik's Cube! Now it's time to "+
							 "move on to completing the final layer and solving the Rubik's Cube completely."
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
    			if (bodyCount == MAX) {

    				elements.get(0).setText("");
    				elements.get(1).setText("                                                    "+
    										"                                                    ");
    				LastLayerOrientSection.begin(allMoves, seqOut, seqIn, elements, forward, back, restartSection, skipToDemo);
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
 	    		} else if (bodyCount == 21) {
	    			UserInterface.makeYrotation(false);
	    			if (forwardOrBack) {
	    				bodyCount = SharedToolbox.bodyCountInc(bodyCount);
	    				System.out.println("correctly killing mm");
	    				MoveManager.kill();
	    				back.setDisable(false);
	    				UserInterface.timeline2.play();
	    			}
	    			elements.get(1).setText(bodyText[bodyCount]);
	    			System.out.println("text to display:"+ bodyText[bodyCount]);
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
