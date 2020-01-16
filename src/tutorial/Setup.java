package tutorial;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Setup {
	
	static int bodyCount = 0;
	static boolean forwardOrBack = true;

	public static void main(ArrayList<Label> elements, Button forward, Button back, SequentialTransition seqIn, SequentialTransition seqOut) {
		
		String[] bodyText = {"To continue with this tutorial, it is strongly advised that you have your own Rubik's Cube so you can try the moves yourself"
				+ " and follow what happens on screen. Do you have your own puzzle?",
				"Great! If your puzzle is currently solved, then scramble it as well as you can before proceeding to the next step. If your puzzle is already scrambled,"
				+ " then you're ready to get going!",
				"Please open the cube net below and replicate your scrambled puzzle on the net shown. It's important you get it right so that you can follow along with"
				+ " your own puzzle throughout the tutorial."};
		String badText = "You can still complete this tutorial without your own cube, but you will be limited to using a default scramble and following the tutorial using only the"
				+ " virtual cube on the left of the screen. This scramble will teach you everything you need to know - but the best way to learn is to try it yourself!";
		
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
    	
    	seqIn.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			forward.setDisable(false);
    			back.setDisable(false);
    		}
    	});
    	
    	ArrayList<Label> hasCube = new ArrayList<Label>();
    	hasCube.add(elements.get(1));
    	hasCube.add(elements.get(2));
    	SequentialTransition seqInText = SharedToolbox.initSeqTrans(hasCube, true);
    	SequentialTransition seqOutText = SharedToolbox.initSeqTrans(hasCube, false);
    	
    	seqOutText.setOnFinished(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			if (bodyCount < 3) {
	    			elements.get(1).setText(bodyText[bodyCount]);
	    			elements.get(2).setText("");
	    			if (bodyCount == 2) {
	    				generateInputButton();
	    			}
    			} else {
    				elements.get(1).setText(badText);
	    			elements.get(2).setText("");
	    			seqInText.playFromStart();
    			}
    			back.setDisable(true);
    			seqInText.playFromStart();
    		}
    	});
    	
    	forward.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			System.out.println("bodycount: "+bodyCount);
    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    			System.out.println("bodycount: "+bodyCount);
    			seqOutText.playFromStart();
    		}
    	});
    	
    	back.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    			bodyCount = SharedToolbox.bodyCountInc(bodyCount);
    			seqOutText.playFromStart();
    		}
    	});
	}
	
	private static void generateInputButton() {
		Button getColours = new Button();
		
		getColours.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CubeInput ci = new CubeInput();
				ci.show();
			}
		});
		HBox test = new HBox(); test.setAlignment(Pos.CENTER);
		test.getChildren().add(getColours);
		TutorialHomepage.toolBarRight.getItems().add(test);
	}
}
