package tutorial;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SecondLayerEdgesSection {
	
	static ArrayList<String> positionEdge = new ArrayList<String>();
	static ArrayList<String> insertEdge = new ArrayList<String>();
	static boolean forwardOrBack;
	static int bodyCount, bodyCountFloor;
	static final int MAX = 18;
	
	
	public static void begin(ArrayList<String> allMoves, SequentialTransition seqOut, SequentialTransition seqIn, ArrayList<Label> elements, Button forward, Button back) {
		for (String s : allMoves) {
			if (s.toCharArray()[0] == '5') {
				positionEdge.add(s);
			} else if (s.toCharArray()[0] == '6') {
				insertEdge.add(s);
			}
		}
		
		String[] bodyText = {};
		
		String[] resources = {"solvedF2L.png", "NULL", "edgeLocationF2L.png", "topLayerF2L.png", "middleLayerF2L.png", "edgeOrientationsF2L.png",
							  "mirrorAlgF2L.png", ""};

	}
}
