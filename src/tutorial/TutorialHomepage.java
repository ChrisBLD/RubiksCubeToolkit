package tutorial;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.util.Duration;

public class TutorialHomepage {


	public static void main(Label step, Label description, Label moves, Label bottom, Button back, Button forward, ToolBar toolBarRight, ToolBar toolBar) {
    	
			//System.out.println("we here");
	    	ArrayList<Label> elements = new ArrayList<Label>();
	    	
	    	elements.add(step); elements.add(description); elements.add(moves); elements.add(bottom);
	    	
	    	step.setText("Welcome!");
	    	description.setText("This is an interactive Rubik's Cube tutorial. Using this tool, you can learn how to solve "
	    					    +"the Rubik's Cube using the Layer by Layer method. To begin, would you like to learn the notation for the Rubik's Cube?"
	    					    + " (recommended for complete beginners!)");
	    	moves.setText("");
	    	bottom.setText("");
	    	SequentialTransition seqIn = initSeqTrans(elements, true);
	    	SequentialTransition seqOut = initSeqTrans(elements, false);
	    	
	    	
	    	forward.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					NotationSection.main(elements, forward, back, seqIn, seqOut);
				}
	    	});
	    	
	    	back.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent arg0) {
	    			Setup.main(elements, forward, back, seqIn, seqOut);
	    		}
	    	});
    	
    	
        
    	
	    	for (Node element : elements) {
	    		element.setVisible(true);
	    	}
	    	seqIn.playFromStart();
    	
    }
   
    public static SequentialTransition initSeqTrans(ArrayList<Label> elements, boolean dir) {
    	SequentialTransition seq = new SequentialTransition();
    	for (int i = 0; i < elements.size(); i++) {
    		FadeTransition fade = new FadeTransition(Duration.millis(400));
    		fade.setNode(elements.get(i));
    		if (dir) {
    			fade.setFromValue(0.0);
                fade.setToValue(1.0);
    		} else {
    			fade.setFromValue(1.0);
                fade.setToValue(0.0);
    		}
            fade.setCycleCount(1);
            fade.setAutoReverse(false);
            seq.getChildren().add(fade);
    	}
    	return seq;
    }
}
