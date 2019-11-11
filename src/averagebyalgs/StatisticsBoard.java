package averagebyalgs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class StatisticsBoard {
	
	private ArrayList<Double> algCountByNum = new ArrayList<Double>();

	
	public ArrayList<Integer> processResults(ArrayList<Double> timeList, ArrayList<String> scrambleList, ArrayList<ImageView> cubieArray, Group cubieG, Pane p) {
		
		ArrayList<Integer> algList = new ArrayList<Integer>();

		ScrambleManager sm = new ScrambleManager(p, cubieArray, cubieG);
		String fastScram = "";
		Double fastTime = 999999.999;
		TargetCounter tm;
		
		

		for (int i = 0; i < timeList.size(); i++) {
			if (timeList.get(i) < fastTime) {
				fastScram = scrambleList.get(i);
				fastTime = timeList.get(i);
			}


			cubieArray = sm.submitted(scrambleList.get(i), cubieArray, cubieG, false);
			//System.out.println("TEST: "+cubieArray.get(0).getImage());
			tm = new TargetCounter(sm, cubieArray);
			int algs = countAlgs(tm);
			
			algList.add(algs);
			
	
			tm = null;
		}
		
		
		//NOTE: Net shows fastest scramble. Add labels that let the user knows this as well as showing their result next to it!
		
		System.out.println("Fastest Scramble: "+fastScram+", Time: "+fastTime);
		cubieArray = sm.submitted(fastScram, cubieArray, cubieG, true);
		
		sm = null;
		
		
		return algList;
	}

	private int countAlgs(TargetCounter tm) {
		boolean paritySwap = MainRedesigned.paritySwap;
		boolean quadFlip = MainRedesigned.quadFlip;
		boolean dt = MainRedesigned.dt;
		boolean ot = MainRedesigned.ot;
		
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
		//System.out.println("FORMAL NOTATION: "+targets);
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
		if (quadFlip) {
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

		int twistAlgs = tm.twistCalculator(dt, ot);
		//System.out.println(twistAlgs);

		totalAlgs += flipAlgs + twistAlgs;
		//System.out.println("This scramble is: "+targets);
		//System.out.println("Alg Count: "+(totalAlgs));
		//System.out.println("PARITY AVD?: "+paritySwap);
		
		return totalAlgs;
	}

	public void sort(ArrayList<Double> timeList, ArrayList<Integer> algList, ArrayList<String> scrambleList) {
		for (int i = 0; i < 9; i++) {
			algCountByNum.add((double)0);
		}
		for (int z = 0; z < timeList.size(); z++) {
			switch (algList.get(z)) {
				case 6: algCountByNum.set(0, algCountByNum.get(0)+1); break;
				case 7: algCountByNum.set(1, algCountByNum.get(1)+1); break;
				case 8: algCountByNum.set(2, algCountByNum.get(2)+1); break;
				case 9: algCountByNum.set(3, algCountByNum.get(3)+1); break;
				case 10: algCountByNum.set(4, algCountByNum.get(4)+1); break;
				case 11: algCountByNum.set(5, algCountByNum.get(5)+1); break;
				case 12: algCountByNum.set(6, algCountByNum.get(6)+1); break;
				case 13: algCountByNum.set(7, algCountByNum.get(7)+1); break;
				default: algCountByNum.set(8, algCountByNum.get(8)+1); break;
			}
			System.out.print(", "+timeList.get(z));
		}
		System.out.println("");
		
		double totalResults = timeList.size();
		
		if (totalResults < 1) {
			return;
		}
		
		System.out.println("This session had "+totalResults+" total successess");
		System.out.println("There are "+algCountByNum.get(0).intValue()+" six algers in this dataset, which is "+round(((algCountByNum.get(0)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(1).intValue()+" seven algers in this dataset, which is "+round(((algCountByNum.get(1)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(2).intValue()+" eight algers in this dataset, which is "+round(((algCountByNum.get(2)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(3).intValue()+" nine algers in this dataset, which is "+round(((algCountByNum.get(3)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(4).intValue()+" ten algers in this dataset, which is "+round(((algCountByNum.get(4)/totalResults)*100), 2)+"% of all scrambles.");;
		System.out.println("There are "+algCountByNum.get(5).intValue()+" eleven algers in this dataset, which is "+round(((algCountByNum.get(5)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(6).intValue()+" twelve algers in this dataset, which is "+round(((algCountByNum.get(6)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(7).intValue()+" thirteen algers in this dataset, which is "+round(((algCountByNum.get(7)/totalResults)*100), 2)+"% of all scrambles.");
		
		ArrayList<Double> sixAlgers = new ArrayList<Double>();
		ArrayList<Double> sevenAlgers = new ArrayList<Double>();
		ArrayList<Double> eightAlgers = new ArrayList<Double>();
		ArrayList<Double> nineAlgers = new ArrayList<Double>();
		ArrayList<Double> tenAlgers = new ArrayList<Double>();
		ArrayList<Double> elevenAlgers = new ArrayList<Double>();
		ArrayList<Double> twelveAlgers = new ArrayList<Double>();
		ArrayList<Double> thirteenAlgers = new ArrayList<Double>();
		
		for (int i = 0; i < timeList.size(); i++) {
			switch (algList.get(i)) {
			case 6: sixAlgers.add(timeList.get(i)); break;
			case 7: sevenAlgers.add(timeList.get(i)); break;
			case 8: eightAlgers.add(timeList.get(i)); break;
			case 9: nineAlgers.add(timeList.get(i)); break;
			case 10: tenAlgers.add(timeList.get(i)); break;
			case 11: elevenAlgers.add(timeList.get(i)); break;
			case 12: twelveAlgers.add(timeList.get(i)); break;
			case 13: thirteenAlgers.add(timeList.get(i)); break;
			}
		}
		
		for (int a = 0; a < timeList.size(); a++) {
			System.out.println("Time: "+timeList.get(a)+", Alg Count: "+algList.get(a)+", Scramble: "+scrambleList.get(a));
		}
		
		System.out.println("Your mean time on six alg scrambles was: "+getMean(sixAlgers));
		System.out.println("Your mean time on seven alg scrambles was: "+getMean(sevenAlgers));
		System.out.println("Your mean time on eight alg scrambles was: "+getMean(eightAlgers));
		System.out.println("Your mean time on nine alg scrambles was: "+getMean(nineAlgers));
		System.out.println("Your mean time on ten alg scrambles was: "+getMean(tenAlgers));
		System.out.println("Your mean time on eleven alg scrambles was: "+getMean(elevenAlgers));
		System.out.println("Your mean time on twelve alg scrambles was: "+getMean(twelveAlgers));
		System.out.println("Your mean time on thirteen alg scrambles was: "+getMean(thirteenAlgers));
		
	}
	
	
	private double getMean(ArrayList<Double> times) {
		if (times.size() == 0) {
			return 0;
		}
		double sum = 0;
		for (Double d : times) {
			sum += d;
		}
		sum /= times.size();
		sum = round(sum, 2);
		return sum;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
