package tutorial;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public abstract class NotationSection {
	
	static int bodyCount = 0;
	static boolean forwardOrBack = true;
	static ArrayList<Label> elements;
	static Button forward;
	static Button back;
	static SequentialTransition seqIn, seqOut;

	
	 public static void main(ArrayList<Label> elements, Button forward, Button back, SequentialTransition seqIn, SequentialTransition seqOut) {
 	   	
		 NotationSection.elements = elements;
		 NotationSection.forward = forward;
		 NotationSection.back = back;
		 NotationSection.seqIn = seqIn;
		 NotationSection.seqOut = seqOut;
		 
	    String[] bodyText = {"Rubik's Cube Notation uses six letters to refer to each face of the puzzle:"
				+ "\n\n R - Right Face Turn \n U - Upper Face Turn \n F - Front Face Turn \n L - Left Face Turn \n B - Back Face Turn \n D - Down Face Turn",
				"A single letter by itself refers to a clockwise face rotation of 90 degrees (known as a quarter turn)"
				+ "\n\n R - Turn the Right Face 90 degrees Clockwise \n U - Turn the Upper Face 90 degrees Clockwise"
				+ "\n F - Turn the Front Face 90 degrees Clockwise \n ...and so on",
				"A letter followed by an apostrophe refers to a counter-clockwise face rotation of 90 degrees (also known as a quarter turn, but in the opposite direction)"
		    	+ "\n\n R' - Turn the Right Face 90 degrees Counter-Clockwise \n U' - Turn the Upper Face 90 degrees Counter-Clockwise"
		    	+ "\n F' - Turn the Front Face 90 degrees Counter-Clockwise \n ...and so on",
		    	"A letter followed by the number 2 denotes a double turn. That is, a 180 degree turn of a face in either direction."
		    	+ "\n\n R2 - Turn the Right Face 180 degrees \n U2 - Turn the Upper Face 180 degrees "
		    	+ "\n F2 - Turn the Front Face 180 degrees \n ...and so on",
		    	"Let's see what these moves look like on an actual cube.			",
		    	"R", "R'", "R2",
		    	"Moves can be combined to make algorithms. Algorithms are sets of moves that perform a specific goal. Often, you'll have to perform them more"
		    	+ " than once in order to achieve the desired outcome", "R", "U", "R'", "U'",
		    	"Repeating these moves six times will return the cube to a solved state. This is not true for all algorithms, but it's an example of something"
		    	+ " similar to what we will use later in the tutorial.",
		    	"There will be numerous algorithm demonstrations similar to the one just shown during the tutorial. Just press the back button at any stage of"
		    	+ " the algorithm to undo the moves made and watch it happen again"};
		
	    String[] moveText = {"Turn the Right Face 90 degrees Clockwise", "Turn the Right Face 90 degrees Counter-Clockwise", "Turn the Right Face 180 degrees",
	    		"Turn the Right Face 90 degrees Clockwise", "Turn the Upper Face 90 degrees Clockwise", "Turn the Right Face 90 degrees Counter-Clockwise", 
	    		"Turn the Upper Face 90 degrees Counter-Clockwise"};
	    
	    bodyCount = 0;
	    forwardOrBack = true;
	    seqOut.playFromStart();
	    seqOut.setOnFinished(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	        	elements.get(0).setText("Notation");
	        	elements.get(1).setText(bodyText[bodyCount]);  
	    		seqIn.playFromStart();
	    	}
	    });
	    
	    ArrayList<Label> allButTitle = new ArrayList<Label>();
	    for (int i = 1; i < elements.size(); i++) {
	    	allButTitle.add(elements.get(i));
	    }
	    
	    ArrayList<Label> moveOnly = new ArrayList<Label>();
	    moveOnly.add(elements.get(2));
	    moveOnly.add(elements.get(3));
	    
	    SequentialTransition seqInText = initSeqTrans(allButTitle, true);
	    SequentialTransition seqOutText = initSeqTrans(allButTitle, false);
	    
	    SequentialTransition seqInMove = initSeqTrans(moveOnly, true);
	    SequentialTransition seqOutMove = initSeqTrans(moveOnly, false);
	    
	    seqOutText.setOnFinished(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		if (forwardOrBack) {
	       			bodyCountInc();
	       			elements.get(1).setText(bodyText[bodyCount]);
	       			elements.get(2).setText("");
	       			elements.get(3).setText("");
	       		} else {
	       			bodyCountDec();
	       			if (bodyCount == 4) {
	       				bodyCountDec();
	       			} else if (bodyCount == 7) {
	    				elements.get(1).setText(bodyText[4]);
	    				elements.get(2).setText(bodyText[bodyCount]);
	        			elements.get(3).setText(moveText[bodyCount-5]);
	        			UserInterface.makeR2move();
	       			} else if (bodyCount == 8) {
	       				elements.get(1).setText(bodyText[bodyCount]);
	       				elements.get(2).setText("");
	       				elements.get(3).setText("");
	       			} else {
		       			elements.get(1).setText(bodyText[bodyCount]);
		       			elements.get(2).setText("");
		       			elements.get(3).setText("");
	       			}
	       		}		
	       		seqInText.playFromStart();    			
	    	}
	    });
	    
	    seqInText.setOnFinished(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		if (bodyCount == 13) {
	    			UserInterface.makeUmove(false); 
	    			UserInterface.makeRmove(false); 
	    			UserInterface.makeUmove(true); 
	    			UserInterface.makeRmove(true);
	    		}
	    	}
	    });
	    
	    seqOutMove.setOnFinished(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		if (forwardOrBack) {
		   			bodyCountInc();
		   			if (bodyCount >= 8) {
		   				if (bodyCount == 8) {
		   					elements.get(1).setText(bodyText[bodyCount]);
		    				bodyCountInc();
		   				}
		    			elements.get(2).setText(bodyText[bodyCount]);
		    			elements.get(3).setText(moveText[bodyCount-6]);
		   			} else {
		    			elements.get(2).setText(bodyText[bodyCount]);
		    			elements.get(3).setText(moveText[bodyCount-5]);
		   			}
	    		} else {
	    			bodyCountDec();
	    			System.out.println("Decreased to: "+bodyCount);
		   			elements.get(2).setText(bodyText[bodyCount]);
		   			elements.get(3).setText(moveText[bodyCount-5]);
	    		}
	    		seqInMove.playFromStart();
	    	}
	    	
	    });
	    	
	    	
	    seqInMove.setOnFinished(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		switch (bodyCount) {
	    		case 5: UserInterface.makeRmove(false); break;
	    		case 6: UserInterface.makeRmove(true); break;
	    		case 7: UserInterface.makeR2move(); break;
	    		case 9: UserInterface.makeRmove(false); break;
	    		case 10: UserInterface.makeUmove(false); break;
	    		case 11: UserInterface.makeRmove(true); break;
	    		case 12: UserInterface.makeUmove(true); break;
	    		}
	    	}
	    });
	    
	    forward.setOnAction(event -> {changeDir(true); checkValid(seqOutText, seqOutMove, bodyText); checkIfDone();});
	    
	    back.setOnAction(event -> {changeDir(false); checkValid(seqOutText, seqOutMove, bodyText);});
	    	
	    	
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
	    
	    public static FadeTransition getFade(Node l, boolean dir) {
	    	FadeTransition fade = new FadeTransition(Duration.millis(1000));
	    	fade.setNode(l);
	    	if (dir) {
	    		fade.setFromValue(0.0);
	            fade.setToValue(1.0);
	    	} else {
	    		fade.setFromValue(1.0);
	            fade.setToValue(0.0);
	    	}
	        fade.setFromValue(0.0);
	        fade.setToValue(1.0);
	        fade.setCycleCount(1);
	        fade.setAutoReverse(false);
	        return fade;
	    }

	    public static void checkValid(SequentialTransition seqOutText, SequentialTransition seqOutMove, String[] bodyText) {
	    	System.out.println("bodycount: "+bodyCount);
	    	if (forwardOrBack) {
	    		switch (bodyCount) {
		    		case 0: seqOutText.playFromStart(); break;
		    		case 1: seqOutText.playFromStart(); break;
		    		case 2: seqOutText.playFromStart(); break;
		    		case 3: seqOutText.playFromStart(); break;
		    		case 4: seqOutMove.playFromStart(); break;
		    		case 5: UserInterface.makeRmove(true); seqOutMove.playFromStart(); break;
		    		case 6: UserInterface.makeRmove(false); seqOutMove.playFromStart(); break;
		    		case 7: UserInterface.makeR2move(); seqOutText.playFromStart(); break;
		    		case 8: seqOutMove.playFromStart(); break;
		    		case 9: seqOutMove.playFromStart(); break;
		    		case 10: seqOutMove.playFromStart(); break;
		    		case 11: seqOutMove.playFromStart(); break; 
		    		case 12: seqOutText.playFromStart(); break;
		    		case 13: seqOutText.playFromStart(); break;
		    		
	    		}
	    	} else {
	    		switch (bodyCount) {
		    		//case 0: seqOutText.playFromStart(); break;
		    		case 1: seqOutText.playFromStart(); break;
		    		case 2: seqOutText.playFromStart(); break;
		    		case 3: seqOutText.playFromStart(); break;
		    		case 4: seqOutText.playFromStart(); break;
		    		case 5: UserInterface.makeRmove(true); seqOutText.playFromStart(); break;
		    		case 6: UserInterface.makeRmove(false); seqOutMove.playFromStart(); break;
		    		case 7: UserInterface.makeR2move(); seqOutMove.playFromStart(); break;
		    		case 8: seqOutText.playFromStart(); break;
		    		case 9: UserInterface.makeRmove(true); bodyCount = 9; seqOutText.playFromStart(); break;
		    		case 10: UserInterface.makeUmove(true); UserInterface.makeRmove(true); bodyCount = 9; seqOutText.playFromStart(); break;
		    		case 11: UserInterface.makeRmove(false); UserInterface.makeUmove(true); UserInterface.makeRmove(true); bodyCount = 9; seqOutText.playFromStart(); break;
		    		case 12: UserInterface.makeUmove(false); UserInterface.makeRmove(false); UserInterface.makeUmove(true); UserInterface.makeRmove(true); bodyCount = 9; seqOutText.playFromStart(); break;
		    		case 13: seqOutText.playFromStart();
		    		case 14: seqOutText.playFromStart();
	    		}
	    	}
	    }
	    
	    public static void changeDir(boolean dir) {
	    	forwardOrBack = dir;
	    }
	    
	    public static void bodyCountInc() {
	    	bodyCount++;
	    }
	    
	    public static void bodyCountDec() {
	    	bodyCount--;
	    }
	    
	    public static void checkIfDone() {
	    	if (bodyCount == 14) {
	    		Setup.main(elements, forward, back, seqIn, seqOut);
	    	}
	    }
	    
		
}
