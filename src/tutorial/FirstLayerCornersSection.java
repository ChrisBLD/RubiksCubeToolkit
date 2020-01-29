package tutorial;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FirstLayerCornersSection {
	
	static ArrayList<String> solveCornerMoves = new ArrayList<String>();
	
	public static void begin(ArrayList<String> allMoves, SequentialTransition seqOut, SequentialTransition seqIn, ArrayList<Label> elements, Button forward, Button back) {
		for (String s : allMoves) {
			if (s.toCharArray()[0] == '3') {
				solveCornerMoves.add(s);
			}
		}
	}
}
