package averagebyalgs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class GraphDisplay {
	
	private static ArrayList<Integer> algList;
	private static ArrayList<Double> algCountByNum;
	private static ArrayList<Label> barArray;
	private static ArrayList<Double> percentArray;
	static double sixAlgPercent, sevenAlgPercent, eightAlgPercent, nineAlgPercent, tenAlgPercent, elevenAlgPercent, twelveAlgPercent, thirteenAlgPercent;
	static ImageView ivBlock = new ImageView(new Image("resources/stat-five.png"));
	static Label barSix, barSeven, barEight, barNine, barTen, barEleven, barTwelve, barThirteen;
	private static double totalTimes;
	
	public static void process() {
		algList = StatisticsBoard.getAlgList();
		algCountByNum = StatisticsBoard.getAlgCountByNum();
		barArray = new ArrayList<Label>();
		percentArray = new ArrayList<Double>();
		
		totalTimes = algList.size();
		System.out.println("Total times: "+totalTimes);
		
		
		percentArray.add(((algCountByNum.get(0)) / totalTimes)*100D);
		percentArray.add(((algCountByNum.get(1)) / totalTimes)*100D);
		percentArray.add(((algCountByNum.get(2)) / totalTimes)*100D);
		percentArray.add(((algCountByNum.get(3)) / totalTimes)*100D);
		percentArray.add(((algCountByNum.get(4)) / totalTimes)*100D);
		percentArray.add(((algCountByNum.get(5)) / totalTimes)*100D);
		percentArray.add(((algCountByNum.get(6)) / totalTimes)*100D);
		percentArray.add(((algCountByNum.get(7)) / totalTimes)*100D);
		percentArray.add(((algCountByNum.get(8)) / totalTimes)*100D);
		
		
		for (int i = 0; i < 8; i++) {
			double d = percentArray.get(i);
			double e = roundToFives(d);
			percentArray.set(i, e);
		}
		
		generateBarArray();
	}
	
	private static void generateBarArray() {
		barSix = new Label(); barSeven = new Label(); barEight = new Label(); barNine = new Label(); barTen = new Label();
		barEleven = new Label(); barTwelve = new Label(); barThirteen = new Label();

		
		barSix = updateBar(barSix, percentArray.get(0));
		barSeven = updateBar(barSeven, percentArray.get(1));
		barEight = updateBar(barEight, percentArray.get(2));
		barNine = updateBar(barNine, percentArray.get(3));
		barTen = updateBar(barTen, percentArray.get(4));
		barEleven = updateBar(barEleven, percentArray.get(5));
		barTwelve = updateBar(barTwelve, percentArray.get(6));
		barThirteen = updateBar(barThirteen, percentArray.get(7));
	

		barArray.add(barSix); barArray.add(barSeven); barArray.add(barEight); barArray.add(barNine); barArray.add(barTen);
		barArray.add(barEleven); barArray.add(barTwelve); barArray.add(barThirteen); 
		
		giveToolTips();
	}
	
	private static int roundToFives(double d) {
	    
		System.out.println("d: "+d);
		if (d < 5.00 && d != 0.0) {
			System.out.println("in for "+d);
			return 105;
		}
		
		float inp = (float) d;
		float rem = inp%5.0F;
		int fives = (int) (inp-rem);
		fives /= 5;
		
		//System.out.println("double "+d+" has "+fives+" many fives");
		
		int plus;
		if (rem >= 2.5 ) {
			plus = 5;
		} else {
			plus = 0;
		}
		
		return (fives*5)+plus;
	}
	
	private static Label updateBar(Label bar, Double percent) {
		
		int percInt = (int) Math.round(percent);
		System.out.println(percInt);
		switch (percInt) {
			case 0: bar.setGraphic(new ImageView(new Image("/resources/stat-zero.png"))); break;
			case 5: bar.setGraphic(new ImageView(new Image("/resources/stat-five.png"))); break;
			case 10: bar.setGraphic(new ImageView(new Image("/resources/stat-ten.png"))); break;
			case 15: bar.setGraphic(new ImageView(new Image("/resources/stat-fifteen.png"))); break;
			case 20: bar.setGraphic(new ImageView(new Image("/resources/stat-twenty.png"))); break;
			case 25: bar.setGraphic(new ImageView(new Image("/resources/stat-twentyfive.png"))); break;
			case 30: bar.setGraphic(new ImageView(new Image("/resources/stat-thirty.png"))); break;
			case 35: bar.setGraphic(new ImageView(new Image("/resources/stat-thirtyfive.png"))); break;
			case 40: bar.setGraphic(new ImageView(new Image("/resources/stat-fourty.png"))); break;
			case 45: bar.setGraphic(new ImageView(new Image("/resources/stat-fourtyfive.png"))); break;
			case 50: bar.setGraphic(new ImageView(new Image("/resources/stat-fifty.png"))); break;
			case 55: bar.setGraphic(new ImageView(new Image("/resources/stat-fiftyfive.png"))); break;
			case 60: bar.setGraphic(new ImageView(new Image("/resources/stat-sixty.png"))); break;
			case 65: bar.setGraphic(new ImageView(new Image("/resources/stat-sixtyfive.png"))); break;
			case 70: bar.setGraphic(new ImageView(new Image("/resources/stat-seventy.png"))); break;
			case 75: bar.setGraphic(new ImageView(new Image("/resources/stat-seventyfive.png"))); break;
			case 80: bar.setGraphic(new ImageView(new Image("/resources/stat-eighty.png"))); break;
			case 85: bar.setGraphic(new ImageView(new Image("/resources/stat-eightyfive.png"))); break;
			case 90: bar.setGraphic(new ImageView(new Image("/resources/stat-ninety.png"))); break;
			case 95: bar.setGraphic(new ImageView(new Image("/resources/stat-ninetyfive.png"))); break;
			case 100: bar.setGraphic(new ImageView(new Image("/resources/stat-hundred.png"))); break;
			case 105: bar.setGraphic(new ImageView(new Image("/resources/stat-lt5.png"))); break;
		}
		
		return bar;
	}
	
	private static void giveToolTips() {
		for (int i = 0; i < 8; i++) {
			Tooltip tt = new Tooltip();
			tt.setShowDelay(Duration.seconds(0.1));
			tt.setPrefSize(60,60);
			tt.setText(StatisticsBoard.round(((algCountByNum.get(i) / totalTimes)*100D), 2)+"%");
			barArray.get(i).setTooltip(tt);
		}
	}
	
	public static ArrayList<Label> getBarArray() {
		return barArray;
	}
	
}
