package tutorial;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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
	static Label infoLabel;
	static ArrayList<String> movesToUserList;
	static int count;
	
	public static void main(ArrayList<String> allMoves, ArrayList<Label> elements, Button forward, Button back, int stage) {
		
		movesToUserList = nextSection(allMoves, stage); //Gets array of moves which need to be applied to cube
		globalForward = forward;
		globalBackward = back;
		
		globalForward.setDisable(true);
		globalBackward.setDisable(true); //The user shouldn't be able to move forward or backward in the main program during the demo phase.
		
		String moves = "";
		for (String s : movesToUserList) {
			moves = moves+s+" ";
		} //Writes contents of move array to a single string
		
		if (moves.equals("SOLVED")) { //This will be returned from the nextSection function if there are no moves required to complete this section.
			//doNothing
		} else {
			count = 0;
			elements.get(2).setText(moves);
			elements.get(2).setGraphic(null);
			elements.get(2).setVisible(true);
			@SuppressWarnings("unchecked")
			ArrayList<Label> text = (ArrayList<Label>) elements.clone();
			text.remove(0);
			SequentialTransition seqInText = SharedToolbox.initSeqTrans(text, true);
	    	SequentialTransition seqOutText = SharedToolbox.initSeqTrans(text, false);
	    	seqInText.playFromStart();
		}
	}
	
	private static void undoMove() {
		globalForward.setDisable(true);
		if (count != 0) {
			count--;
			String move = movesToUserList.get(count);
			if (count == 0) {
				localBackward.setDisable(true);
			} else {
				localForward.setDisable(false);
				localBackward.setDisable(false);
			}
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
				case "F2": UserInterface.makeFmove(true); UserInterface.makeFmove(true); break;
				case "R2": UserInterface.makeR2move(); break;
				case "U2": UserInterface.makeUmove(false); UserInterface.makeUmove(true); break;
				case "B2": UserInterface.makeBmove(false); UserInterface.makeBmove(false); break;
				case "L2": UserInterface.makeLmove(false); UserInterface.makeLmove(false); break;
				case "D2": UserInterface.makeDmove(false); UserInterface.makeDmove(false); break;
			}
		}
	}
	
	private static void doMove() {
		if (count != movesToUserList.size()) {
			String move = movesToUserList.get(count);
			count++;
			if (count == movesToUserList.size()) {
				localForward.setDisable(true);
				globalForward.setDisable(false);
			} else {
				localForward.setDisable(false);
				localBackward.setDisable(false);	
				globalForward.setDisable(true);
			}
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
				case "F2": UserInterface.makeFmove(false); UserInterface.makeFmove(false); break;
				case "R2": UserInterface.makeR2move(); break;
				case "U2": UserInterface.makeUmove(false); UserInterface.makeUmove(false); break;
				case "B2": UserInterface.makeBmove(false); UserInterface.makeBmove(false); break;
				case "L2": UserInterface.makeLmove(false); UserInterface.makeLmove(false); break;
				case "D2": UserInterface.makeDmove(false); UserInterface.makeDmove(false); break;
			}
		}
	}

	private static ArrayList<String> nextSection(ArrayList<String> allMoves, int stage) {
		String toSolveThis = allMoves.get(stage-1);

		char[] moves = toSolveThis.toCharArray();
		ArrayList<String> movesToUserList = new ArrayList<String>();
		ArrayList<String> solvedCase = new ArrayList<String>();
		solvedCase.add("SOLVED");
		String movesToUser = "";
		String currentMove = "";
		if (moves[1] == '*') {
			System.out.println("nothing to do");
		} else {
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

	public static void prepareDemo(ArrayList<Label> elements) {
		localForward = new Button(">");
		localForward.setOnAction(event -> doMove());
		localBackward = new Button("<");
		localBackward.setOnAction(event -> undoMove());
		infoLabel = new Label();
		HBox infoRow = new HBox(infoLabel);
		HBox buttonRow = new HBox(localBackward, localForward);
		if (TutorialHomepage.toolBarRight.getItems().size() < 5) {
			TutorialHomepage.toolBarRight.getItems().add(buttonRow);
			TutorialHomepage.toolBarRight.getItems().add(infoRow);
		} else {
			TutorialHomepage.toolBarRight.getItems().set(3, buttonRow);
			TutorialHomepage.toolBarRight.getItems().set(4, infoRow);
		}
	}
}
