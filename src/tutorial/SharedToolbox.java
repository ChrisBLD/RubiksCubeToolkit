package tutorial;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class SharedToolbox {

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
    
    public static int bodyCountInc(int bc) {
    	return bc+1;
    }
    
    public static int bodyCountDec(int bc) {
    	return bc-1;
    }
    
}
