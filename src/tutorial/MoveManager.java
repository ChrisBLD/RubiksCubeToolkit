package tutorial;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MoveManager {
	
	
	public static void main(ArrayList<String> allMoves, ArrayList<Label> elements, Button forward, Button back, int whichEdge) {
		ArrayList<String> movesToUserList = solveCrossEdge(allMoves, whichEdge);
		String moves = "";
		for (String s : movesToUserList) {
			moves = moves+s+" ";
		}
		elements.get(2).setText(moves);
		elements.get(2).setGraphic(null);
		elements.get(2).setVisible(true);
		@SuppressWarnings("unchecked")
		ArrayList<Label> text = (ArrayList<Label>) elements.clone();
		text.remove(0);
		SequentialTransition seqInText = SharedToolbox.initSeqTrans(text, true);
    	SequentialTransition seqOutText = SharedToolbox.initSeqTrans(text, false);
    	seqInText.playFromStart();
    	Button nextMove = new Button("next");
    	String move = movesToUserList.get(0);
    	nextMove.setOnAction(event -> doMove(move));
    	HBox newBox = new HBox(nextMove);
    	TutorialHomepage.toolBarRight.getItems().add(newBox);
		
	}
	private static void doMove(String move) {
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
			case "R2": UserInterface.makeRmove(false); UserInterface.makeRmove(false); break;
			case "U2": UserInterface.makeUmove(false); UserInterface.makeUmove(false); break;
			case "B2": UserInterface.makeBmove(false); UserInterface.makeBmove(false); break;
			case "L2": UserInterface.makeLmove(false); UserInterface.makeLmove(false); break;
			case "D2": UserInterface.makeDmove(false); UserInterface.makeDmove(false); break;
		}
	}
	
	
	private static ArrayList<String> solveCrossEdge(ArrayList<String> allMoves, int whichEdge) {
		String toSolveThis = allMoves.get(whichEdge-1);

		char[] moves = toSolveThis.toCharArray();
		ArrayList<String> movesToUserList = new ArrayList<String>();
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
		return new ArrayList<String>();
	}
}
