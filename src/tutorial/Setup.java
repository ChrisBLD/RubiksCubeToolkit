package tutorial;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Setup {
	
	static int bodyCount = 0;
	static boolean forwardOrBack = true;

	public static void main(ArrayList<Label> elements, Button forward, Button back, SequentialTransition seqIn, SequentialTransition seqOut) {
		
		String[] bodyText = {"To continue with this tutorial, it is strongly advised that you have your own Rubik's Cube so you can try the moves yourself"
				+ " and follow what happens on screen. Do you have your own puzzle?"};
		
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
	}
}
