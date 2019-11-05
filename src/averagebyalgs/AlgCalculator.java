package averagebyalgs;

import java.util.ArrayList;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class AlgCalculator implements Runnable {

	TextArea userTextField;
	ArrayList<Double> timeList;
	ArrayList<String> scrambleList;
	ArrayList<Integer> algList;
	NetManager nm;
	Pane p;
	
	
	public AlgCalculator(TextArea uTF, ArrayList<Double> tL, ArrayList<String> sL, ArrayList<Integer> aL, NetManager netMan, Pane pane) {
		userTextField = uTF;
		timeList = tL;
		scrambleList = sL;
		algList = aL;
		nm = netMan;
		p = pane;
	}
	
	
	@Override
	public void run() {
		InputParse.start(MainRedesigned.graphic,userTextField.getText());
		timeList = InputParse.getTimeList();
		scrambleList = InputParse.getScrambleList();
		StatisticsBoard sb = new StatisticsBoard();
		algList = sb.processResults(timeList, scrambleList, nm.getCubieArray(), nm.getCubieG(), p);
		sb.sort(timeList, algList);	
	}

}

		
