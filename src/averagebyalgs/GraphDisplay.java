package averagebyalgs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GraphDisplay {
	
	private static ArrayList<Integer> algList;
	private static ArrayList<Double> algCountByNum;
	private static ArrayList<Label> barArray;
	private static ArrayList<Double> percentArray;
	static double sixAlgPercent, sevenAlgPercent, eightAlgPercent, nineAlgPercent, tenAlgPercent, elevenAlgPercent, twelveAlgPercent, thirteenAlgPercent;
	static ImageView ivBlock = new ImageView(new Image("resources/stat-five.png"));

	static ImageView ivZero, ivFive, ivTen, ivFifteen, ivTwenty, ivTwentyFive, ivThirty, ivThirtyFive, ivFourty, ivFourtyFive, ivFifty, ivFiftyFive, ivSixty, ivSixtyFive, ivSeventy,
		ivSeventyFive, ivEighty, ivEightyFive, ivNinety, ivNinetyFive, ivHundred;
	
	static Label barSix, barSeven, barEight, barNine, barTen, barEleven, barTwelve, barThirteen;
	
	public static void process() {
		algList = StatisticsBoard.getAlgList();
		algCountByNum = StatisticsBoard.getAlgCountByNum();
		barArray = new ArrayList<Label>();
		percentArray = new ArrayList<Double>();
		
		double totalTimes = algList.size();
		//System.out.println(algCountByNum.get(3)+" divided by "+totalTimes+" equals" +(algCountByNum.get(3) / totalTimes)*100d);
		
		
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
				
		initIvs();
		
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
	}
	
	private static int roundToFives(double d) {
	    
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
		
		switch (percInt) {
			case 0: bar.setGraphic(ivZero); break;
			case 5: bar.setGraphic(ivFive); break;
			case 10: bar.setGraphic(ivTen); break;
			case 15: bar.setGraphic(ivFifteen); break;
			case 20: bar.setGraphic(ivTwenty); break;
			case 25: bar.setGraphic(ivTwentyFive); break;
			case 30: bar.setGraphic(ivThirty); break;
			case 35: bar.setGraphic(ivThirtyFive); break;
			case 40: bar.setGraphic(ivFourty); break;
			case 45: bar.setGraphic(ivFourtyFive); break;
			case 50: bar.setGraphic(ivFifty); break;
			case 55: bar.setGraphic(ivFiftyFive); break;
			case 60: bar.setGraphic(ivSixty); break;
			case 65: bar.setGraphic(ivSixtyFive); break;
			case 70: bar.setGraphic(ivSeventy); break;
			case 75: bar.setGraphic(ivSeventyFive); break;
			case 80: bar.setGraphic(ivEighty); break;
			case 85: bar.setGraphic(ivEightyFive); break;
			case 90: bar.setGraphic(ivNinety); break;
			case 95: bar.setGraphic(ivNinetyFive); break;
			case 100: bar.setGraphic(ivHundred); break;
		}
		
		return bar;
	}
	
	public static ArrayList<Label> getBarArray() {
		return barArray;
	}
	
	private static void initIvs() {
		ivZero = new ImageView(new Image("/resources/stat-zero.png"));
		ivFive = new ImageView(new Image("/resources/stat-five.png"));
		ivTen = new ImageView(new Image("/resources/stat-ten.png"));
		ivFifteen = new ImageView(new Image("/resources/stat-fifteen.png"));
		ivTwenty = new ImageView(new Image("/resources/stat-twenty.png"));
		ivTwentyFive = new ImageView(new Image("/resources/stat-twentyfive.png"));
		ivThirty = new ImageView(new Image("/resources/stat-thirty.png"));
		ivThirtyFive = new ImageView(new Image("/resources/stat-thirtyfive.png"));
		ivFourty = new ImageView(new Image("/resources/stat-fourty.png"));
		ivFourtyFive = new ImageView(new Image("/resources/stat-fourtyfive.png"));
		ivFifty = new ImageView(new Image("/resources/stat-fifty.png"));
		ivFiftyFive = new ImageView(new Image("/resources/stat-fiftyfive.png"));
		ivSixty = new ImageView(new Image("/resources/stat-sixty.png"));
		ivSixtyFive = new ImageView(new Image("/resources/stat-sixtyfive.png"));
		ivSeventy = new ImageView(new Image("/resources/stat-seventy.png"));
		ivSeventyFive = new ImageView(new Image("/resources/stat-seventyfive.png"));
		ivEighty = new ImageView(new Image("/resources/stat-eighty.png"));
		ivEightyFive = new ImageView(new Image("/resources/stat-eightyfive.png"));
		ivNinety = new ImageView(new Image("/resources/stat-ninety.png"));
		ivNinetyFive = new ImageView(new Image("/resources/stat-ninetyfive.png"));
		ivHundred = new ImageView(new Image("/resources/stat-hundred.png"));		
	}
}
