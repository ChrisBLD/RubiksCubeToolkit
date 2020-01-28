package tutorial;

import java.util.ArrayList;


import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MoveManager {
	
	/*
	 * I intend for this class to be used by all sections when they want moves to be applied to the cube. Things should run like this:
	 * - User presses next, which begins demo phase
	 * - Demo phase locks main forward/back buttons until complete
	 * - Demo phase introduces new forward/back buttons which can be used to control the moves in the demo.
	 * - When the user has seen the final move, the main forward button should unlock allowing them to progress.
	 * - If the user goes back in the main program, they should be taken to the start of the demo phase again
	 * 
	 * The first HBox should be the title of the section
	 * The second HBox should be the words "Let's see how this works on your scramble:" or something to that effect.
	 * The third HBox should display the moves to be made to solve
	 * The fourth HBox should provide forward and backward moving buttons to progress through the moves
	 * The fifth HBox should give a message to the user in the case of this specific stage being already solved.
	 */
	
	static Button localForward, localBackward;
	static Button globalForward, globalBackward;
	static HBox buttonRow, infoRow;
	static Label infoLabel;
	static ArrayList<String> movesToUserList;
	static int count;
	static int numMoves;

	public static void main(ArrayList<String> allMoves, ArrayList<Label> elements, Button forward, Button back, int stage) {
		
		System.out.println("MOVE MANAGER IS AGO");
		movesToUserList = nextSection(allMoves, stage); //Gets array of moves which need to be applied to cube
		globalForward = forward;
		globalBackward = back;
		
		globalForward.setDisable(true);
		globalBackward.setDisable(true); //The user shouldn't be able to move forward or backward in the main program during the demo phase.
		
		String moves = "";
		numMoves = movesToUserList.size();
		for (String s : movesToUserList) {
			if (moves.toCharArray().length == 0) {
				moves = moves+s;
			} else {
				moves = moves+" "+s;
			}
		} //Writes contents of move array to a single string
		
		clearAll();
		
		if (moves.equals("-")) { //This will be returned from the nextSection function if there are no moves required to complete this section.
			((HBox) TutorialHomepage.toolBarRight.getItems().get(3)).getChildren().add(localForward);
			elements.get(2).setText("Done!");
			elements.get(2).setGraphic(null);
			elements.get(2).setVisible(true);
			SharedToolbox.info.setText("Looks like this part is already solved! Continue as normal with the main navigation buttons.");
			//((Label) buttonRow.getChildren().get(1)).setGraphic(new ImageView(new Image("/resources/moveIndicatorGreen.png")));
			globalForward.setDisable(false);
			localForward.setDisable(true);
			localBackward.setDisable(true);
			SharedToolbox.info.setVisible(true);
		} else {
			SharedToolbox.info.setVisible(true);
			generateIndicators(movesToUserList);
			count = 0;
			elements.get(2).setText(moves);
			elements.get(2).setGraphic(null);
			elements.get(2).setVisible(true);
		}
		@SuppressWarnings("unchecked")
		ArrayList<Label> text = (ArrayList<Label>) elements.clone();
		text.remove(0);
		text.add(SharedToolbox.info);
		SequentialTransition seqInText = SharedToolbox.initSeqTrans(text, true);
    	//SequentialTransition seqOutText = SharedToolbox.initSeqTrans(text, false);
    	seqInText.playFromStart();
	}
	
	private static void undoMove() {
		globalForward.setDisable(true);
		if (count != 0) {
			count--;
			((Label) buttonRow.getChildren().get(count+1)).setGraphic(new ImageView(new Image("/resources/moveIndicatorRed.png")));
			String move = movesToUserList.get(count);
			if (count == 0) {
				localBackward.setDisable(true);
			} else {
				localBackward.setDisable(false);
			}
			localForward.setDisable(false);
			switch(move) {
				case "F'": UserInterface.makeFmove(false); break;
				case "R'": UserInterface.makeRmove(false); break;
				case "U'": UserInterface.makeUmove(false); break;
				case "B'": UserInterface.makeBmove(false); break;
				case "L'": UserInterface.makeLmove(false); break;
				case "D'": UserInterface.makeDmove(false); break;
				case "F": UserInterface.makeFmove(true); break;
				case "R": UserInterface.makeRmove(true); break;
				case "U": UserInterface.makeUmove(true); break;
				case "B": UserInterface.makeBmove(true); break;
				case "L": UserInterface.makeLmove(true); break;
				case "D": UserInterface.makeDmove(true); break;
				case "F2": UserInterface.makeF2move(); break;
				case "R2": UserInterface.makeR2move(); break;
				case "U2": UserInterface.makeU2move(); break;
				case "B2": UserInterface.makeB2move(); break;
				case "L2": UserInterface.makeL2move(); break;
				case "D2": UserInterface.makeD2move(); break;
			}
		}
	}
	
	private static void doMove() {
		if (count != numMoves) {
			String move = movesToUserList.get(count);
			((Label) buttonRow.getChildren().get(count+1)).setGraphic(new ImageView(new Image("/resources/moveIndicatorGreen.png")));
			count++;
			if (count == numMoves) {
				localForward.setDisable(true);
				globalForward.setDisable(false);
			} else {
				localForward.setDisable(false);
				globalForward.setDisable(true);
			}
			localBackward.setDisable(false);
			switch(move) {
				case "F'": UserInterface.makeFmove(true); break;
				case "R'": UserInterface.makeRmove(true); break;
				case "U'": UserInterface.makeUmove(true); break;
				case "B'": UserInterface.makeBmove(true); break;
				case "L'": UserInterface.makeLmove(true); break;
				case "D'": UserInterface.makeDmove(true); break;
				case "F": UserInterface.makeFmove(false); break;
				case "R": UserInterface.makeRmove(false); break;
				case "U": UserInterface.makeUmove(false); break;
				case "B": UserInterface.makeBmove(false); break;
				case "L": UserInterface.makeLmove(false); break;
				case "D": UserInterface.makeDmove(false); break;
				case "F2": UserInterface.makeF2move(); break;
				case "R2": UserInterface.makeR2move(); break;
				case "U2": UserInterface.makeU2move(); break;
				case "B2": UserInterface.makeB2move(); break;
				case "L2": UserInterface.makeL2move(); break;
				case "D2": UserInterface.makeD2move(); break;
			}
		}
	}

	private static ArrayList<String> nextSection(ArrayList<String> allMoves, int stage) {
		ArrayList<String> movesToUserList = new ArrayList<String>();
		String toSolveThis = allMoves.get(stage-1);
		System.out.println("NOW SOLVING: "+toSolveThis);
		char[] moves = toSolveThis.toCharArray();
		ArrayList<String> solvedCase = new ArrayList<String>();
		solvedCase.add("-");
		String movesToUser = "";
		String currentMove = "";
		if (moves[1] == '*') {
			System.out.println("nothing to do");
		} else {
			if (moves[0] == '2') {
				movesToUserList.add("F2"); movesToUserList.add("D"); movesToUserList.add("R"); movesToUserList.add("F'"); movesToUserList.add("R'"); 
				return movesToUserList;
			}
			for (int i = 1; i < moves.length; i++) {
				switch(moves[i]) {
					case 'G': movesToUserList.add("F'"); break;
					case 'T': movesToUserList.add("R'"); break;
					case 'I': movesToUserList.add("U'"); break;
					case 'N': movesToUserList.add("B'"); break;
					case 'K': movesToUserList.add("L'"); break;
					case 'S': movesToUserList.add("D'"); break;
					case 'F': if (currentMove.equals("F")) {movesToUserList.remove(movesToUserList.size()-1); movesToUserList.add("F2");} else { movesToUserList.add("F");}; break;
					case 'R': if (currentMove.equals("R")) {movesToUserList.remove(movesToUserList.size()-1); movesToUserList.add("R2");} else { movesToUserList.add("R");}; break;
					case 'U': if (currentMove.equals("U")) {movesToUserList.remove(movesToUserList.size()-1); movesToUserList.add("U2");} else { movesToUserList.add("U");}; break;
					case 'B': if (currentMove.equals("B")) {movesToUserList.remove(movesToUserList.size()-1); movesToUserList.add("B2");} else { movesToUserList.add("B");}; break;
					case 'L': if (currentMove.equals("L")) {movesToUserList.remove(movesToUserList.size()-1); movesToUserList.add("L2");} else { movesToUserList.add("L");}; break;
					case 'D': if (currentMove.equals("D")) {movesToUserList.remove(movesToUserList.size()-1); movesToUserList.add("D2");} else { movesToUserList.add("D");}; break;
				}
				currentMove = ""+moves[i];
			}
			for (String s : movesToUserList) {
				movesToUser = movesToUser+s+" ";
			}
			return movesToUserList;
		}
		return solvedCase;
	}

	private static void generateIndicators(ArrayList<String> movesToUserList) {
		for (String move : movesToUserList) {
			Label l = new Label("");
			l.setGraphic(new ImageView(new Image("/resources/moveIndicatorRed.png")));
			((HBox) TutorialHomepage.toolBarRight.getItems().get(3)).getChildren().add(l);
		}
		((HBox) TutorialHomepage.toolBarRight.getItems().get(3)).getChildren().add(localForward);
		
	}
	
	public static void prepareDemo(ArrayList<Label> elements) {
		System.out.println("LENGTH OF TBR: "+TutorialHomepage.toolBarRight.getItems().size());
		localForward = new Button();
		localForward.setOnAction(event -> doMove());
		localForward.setGraphic(new ImageView (new Image("/resources/rightArrow.png")));
		localForward.setMinSize(50,50); localForward.setMaxSize(50,50);
		localBackward = new Button();
		localBackward.setOnAction(event -> undoMove());
		localBackward.setDisable(true);
		localBackward.setGraphic(new ImageView (new Image("/resources/leftArrow.png")));
		localBackward.setMinSize(50,50); localBackward.setMaxSize(50,50);
		buttonRow = new HBox(localBackward); buttonRow.setAlignment(Pos.CENTER);
		buttonRow.setSpacing(20);
		buttonRow.setPadding(new Insets(20,20,20,20));
		SharedToolbox.info.setVisible(false);
		SharedToolbox.info.setText("Click the buttons to apply the moves to the cube!");
		infoRow = new HBox(SharedToolbox.info); infoRow.setAlignment(Pos.CENTER);
		if (TutorialHomepage.toolBarRight.getItems().size() >= 5) {
			TutorialHomepage.toolBarRight.getItems().set(3, buttonRow);
			TutorialHomepage.toolBarRight.getItems().set(4, infoRow);
		} else {
			TutorialHomepage.toolBarRight.getItems().add(buttonRow);
			TutorialHomepage.toolBarRight.getItems().add(infoRow);
		}
		
	}
	
	public static void cleanStart() {
		((HBox) TutorialHomepage.toolBarRight.getItems().get(3)).getChildren().clear();
		((HBox) TutorialHomepage.toolBarRight.getItems().get(3)).getChildren().add(localBackward);	
		localForward.setDisable(false);
		localBackward.setDisable(false);
	}
	
	public static void kill() {
		System.out.println("LENGTH OF TBR: "+TutorialHomepage.toolBarRight.getItems().size());
		TutorialHomepage.toolBarRight.getItems().remove(3);
		TutorialHomepage.toolBarRight.getItems().remove(3);
	}
	
	private static void clearAll() {
		((HBox) TutorialHomepage.toolBarRight.getItems().get(3)).getChildren().clear();
		((HBox) TutorialHomepage.toolBarRight.getItems().get(3)).getChildren().add(localBackward);
		localBackward.setDisable(true);
		localForward.setDisable(false);
	}
}
