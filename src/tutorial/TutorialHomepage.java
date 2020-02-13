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

	static ToolBar toolBarRight, toolBar;
	
	public static void main(ArrayList<Label> elements, Button back, Button forward, Button restartSection, Button skipToDemo, ToolBar toolBarRight, ToolBar toolBar) {
    	
			TutorialHomepage.toolBarRight = toolBarRight;
			TutorialHomepage.toolBar = toolBar;
			//System.out.println("we here");

	    	elements.get(0).setText("Welcome!");
	    	elements.get(1).setText("This is an interactive Rubik's Cube tutorial. Using this tool, you can learn how to solve "
	    					    +"the Rubik's Cube using the Layer by Layer method. To begin, would you like to learn the notation for the Rubik's Cube?"
	    					    + " (recommended for complete beginners!)\n\nClick 'Next Page' for Yes\nClick 'Previous Page' for No");
	    	elements.get(2).setText("");
	    	elements.get(3).setText("");

	    	SequentialTransition seqIn = SharedToolbox.initSeqTrans(elements, true);
	    	SequentialTransition seqOut = SharedToolbox.initSeqTrans(elements, false);
	    	
	    	
	    	forward.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					NotationSection.main(elements, forward, back, restartSection, seqIn, seqOut);
				}
	    	});
	    	
	    	back.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent arg0) {
	    			back.setDisable(true);
	    			forward.setDisable(true);
	    			Setup.main(elements, forward, back, restartSection, skipToDemo, seqIn, seqOut);
	    		}
	    	});
    	
    	
        
    	
	    	for (Node element : elements) {
	    		element.setVisible(true);
	    	}
	    	seqIn.playFromStart();
    	
    }
   


}
