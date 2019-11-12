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
	static double sixAlgPercent, sevenAlgPercent, eightAlgPercent, nineAlgPercent, tenAlgPercent, elevenAlgPercent, twelveAlgPercent, thirteenAlgPercent;
	static ImageView ivBlock = new ImageView(new Image("resources/stat-five.png"));

	static ImageView ivZero, ivFive, ivTen, ivFifteen, ivTwenty, ivTwentyFive, ivThirty, ivThirtyFive, ivFourty, ivFourtyFive, ivFifty, ivFiftyFive, ivSixty, ivSixtyFive, ivSeventy,
		ivSeventyFive, ivEighty, ivEightyFive, ivNinety, ivNinetyFive, ivHundred;
	
	public static void process() {
		algList = StatisticsBoard.getAlgList();
		algCountByNum = StatisticsBoard.getAlgCountByNum();
		barArray = new ArrayList<Label>();
		
		int totalTimes = algList.size();
		System.out.println(algCountByNum.get(3));
		
		sixAlgPercent = round(((algCountByNum.get(0)) / totalTimes)*100, 0);
		sevenAlgPercent = round(((algCountByNum.get(1)) / totalTimes)*100, 0);
		eightAlgPercent = round(((algCountByNum.get(2)) / totalTimes)*100, 0);
		nineAlgPercent = round(((algCountByNum.get(4)) / totalTimes)*100, 0);
		tenAlgPercent = round(((algCountByNum.get(5)) / totalTimes)*100, 0);
		elevenAlgPercent = round(((algCountByNum.get(6)) / totalTimes)*100, 0);
		twelveAlgPercent = round(((algCountByNum.get(7)) / totalTimes)*100, 0);
		thirteenAlgPercent = round(((algCountByNum.get(8)) / totalTimes)*100, 0);
		
		System.out.println("\n6: "+sixAlgPercent+"\n7: "+sevenAlgPercent+"\n8: "+eightAlgPercent+"\n9: "+nineAlgPercent+"\n10: "+tenAlgPercent+
				"\n11: "+elevenAlgPercent+"\n12: "+twelveAlgPercent+"\n13: "+thirteenAlgPercent);
		
		Label nineBar = new Label();
		
		nineBar = updateBar(nineBar, nineAlgPercent);
		
		barArray.add(nineBar);
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	private static Label updateBar(Label bar, Double percent) {
		
		int percInt = percent.intValue();
		percInt = 5*(Math.round(percInt/5));
		System.out.println(percInt);
		/*switch (percInt) {
			case 0: 
			case 5:
				
		}*/
		
		bar.setGraphic(ivBlock);
		
		return bar;
	}
	
	public static ArrayList<Label> getBarArray() {
		return barArray;
	}
}
