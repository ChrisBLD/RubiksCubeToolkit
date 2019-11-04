package averagebyalgs;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class StatisticsBoard {

	
	public void processResults(Map<Double, String> results, ArrayList<ImageView> cubieArray, Group cubieG, Pane p) {
		
		ArrayList<Integer> algCount = new ArrayList<Integer>();
		
		//Hard coded settings that need to be implemented
		boolean paritySwap = true;
		String edgeBuf = "UF";
		String cornerBuf = "UFR";
		boolean quadBox = true;
		

		ScrambleManager sm = new ScrambleManager(p, cubieArray, cubieG);
		boolean first = true;
		String fastScram = "";

		for (Map.Entry<Double, String> entry : results.entrySet()) {
			if (first) {
				fastScram = entry.getValue();
				first = false;
			}
			cubieArray = sm.submitted(entry.getValue(), cubieArray, cubieG);
			System.out.println("TEST: "+cubieArray.get(0).getImage());
			TargetCounter tm = new TargetCounter(sm, cubieArray);
			int[] info = tm.getInfo();
			
			String targets = "";
			targets += info[0];
			int dashCount = 0;
			for (int i = 0; i < info[2]; i++) {
				dashCount++;
				targets += "'";
			}
			targets+= "/";
			targets += info[1];
			for (int i = 0; i < info[3]; i++) {
				
				targets += "'";
			}
			System.out.println("FORMAL NOTATION: "+targets);
			int edgeAlgs = 0;
			int cornerAlgs = 0;
			if (info[0] % 2 == 0) {
				edgeAlgs = (info[0]/2);
			} else {
				edgeAlgs = ((info[0]-1)/2)+1;
			}
			
			if (info[1] % 2 == 0) {
				cornerAlgs = (info[1]/2);
			} else {
				cornerAlgs = ((info[1]-1)/2)+1;
			}
			
			int totalAlgs = edgeAlgs+cornerAlgs;
			int flipAlgs = 0;
			if (quadBox) {
				if (dashCount == 0) {
					flipAlgs = 0;
			    } else if (dashCount < 5) {
					flipAlgs = 1;
				} else if (dashCount < 9) {
					flipAlgs = 2;
				} else {
					flipAlgs = 3;
				}
			} else {
				if (dashCount == 0) {
					flipAlgs = 0;
				} if (dashCount % 2 == 0) {
					flipAlgs = dashCount/2;
				} else {
					flipAlgs = (dashCount+1)/2;
				}
			}
			CheckBox dtBox = new CheckBox();
			CheckBox otBox = new CheckBox();
			ComboBox cornerBufChoose = new ComboBox();
			cornerBufChoose.setValue(cornerBuf);
			int twistAlgs = tm.twistCalculator(dtBox, otBox, cornerBufChoose);
			System.out.println(twistAlgs);
	
			totalAlgs += flipAlgs + twistAlgs;
			System.out.println("This scramble is: "+targets);
			System.out.println("Alg Count: "+(totalAlgs));
		}
		
		System.out.println("Fastest Scramble: "+fastScram);
		cubieArray = sm.submitted(fastScram, cubieArray, cubieG);
			
	}
}
