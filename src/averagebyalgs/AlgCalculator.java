package averagebyalgs;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class AlgCalculator implements Runnable {

	TextArea userTextField;
	ArrayList<Double> timeList;
	ArrayList<String> scrambleList;
	ArrayList<Integer> algList;
	NetManager nm;
	Pane p;
	Label loading;
	
	
	public AlgCalculator(TextArea uTF, ArrayList<Double> tL, ArrayList<String> sL, ArrayList<Integer> aL, NetManager netMan, Pane pane, Label ldn) {
		userTextField = uTF;
		timeList = tL;
		scrambleList = sL;
		algList = aL;
		nm = netMan;
		p = pane;
		loading = ldn;
	}
	
	
	@Override
	public void run() {
		InputParse.start(MainRedesigned.graphic,userTextField.getText());
		timeList = InputParse.getTimeList();
		scrambleList = InputParse.getScrambleList();
		algList = StatisticsBoard.processResults(timeList, scrambleList, nm.getCubieArray(), nm.getCubieG(), p);
		StatisticsBoard.sort(timeList, algList, scrambleList);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loading.setVisible(false);
			}			
		});
	}

}

		
