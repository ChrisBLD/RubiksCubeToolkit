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

public abstract class StatisticsBoard {
	
	private static ArrayList<Double> algCountByNum = new ArrayList<Double>();
	private static ArrayList<Integer> algList = new ArrayList<Integer>();
	private static ArrayList<Double> timeList;
	private static ArrayList<String> scrambleList;
	private static ArrayList<Double> sixAlgers, sevenAlgers, eightAlgers, nineAlgers, tenAlgers, elevenAlgers, 
									 twelveAlgers, thirteenAlgers;
	private static ArrayList<Double> bestTimeArray = new ArrayList<Double>();
	private static ArrayList<String> bestScrambleArray = new ArrayList<String>();
	private static ArrayList<Double> meanArray = new ArrayList<Double>();
	private static String fastScram;
	private static Double fastTime;
	private static int totalResults;
	
	
	public static ArrayList<Integer> processResults(ArrayList<Double> tList, ArrayList<String> sList, ArrayList<ImageView> cubieArray, Group cubieG, Pane p) {

		algList.clear();
		timeList = tList;
		scrambleList = sList;
		ScrambleManager sm = new ScrambleManager(p, cubieArray, cubieG);
		fastScram = "";
		fastTime = 999999.999;
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

	private static int countAlgs(TargetCounter tm) {
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

	public static void sort(ArrayList<Double> timeList, ArrayList<Integer> algList, ArrayList<String> scrambleList) {
		
		algCountByNum.clear();
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
			//System.out.print(", "+timeList.get(z));
		}
		//System.out.println("");
		
		totalResults = timeList.size();
		
		if (totalResults < 1) {
			return;
		}
	
		sixAlgers = new ArrayList<Double>();
		sevenAlgers = new ArrayList<Double>();
		eightAlgers = new ArrayList<Double>();
		nineAlgers = new ArrayList<Double>();
		tenAlgers = new ArrayList<Double>();
		elevenAlgers = new ArrayList<Double>();
		twelveAlgers = new ArrayList<Double>();
		thirteenAlgers = new ArrayList<Double>();
		
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
			default: System.out.println("uhh"); System.out.println("time: "+timeList.get(i)+", scram: "+scrambleList.get(i)); break;
			}
		}
		
		//for (int a = 0; a < timeList.size(); a++) {
		//	//System.out.println("Time: "+timeList.get(a)+", Alg Count: "+algList.get(a)+", Scramble: "+scrambleList.get(a));
		//}
		
		meanArray.clear();
		meanArray.add(getMean(sixAlgers));
		meanArray.add(getMean(sevenAlgers));
		meanArray.add(getMean(eightAlgers));
		meanArray.add(getMean(nineAlgers));
		meanArray.add(getMean(tenAlgers));
		meanArray.add(getMean(elevenAlgers));
		meanArray.add(getMean(twelveAlgers));
		meanArray.add(getMean(thirteenAlgers));
		
	}
	
	
	private static double getMean(ArrayList<Double> times) {
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
	
	protected static double round(double value, int places) {
	    try {
			if (places < 0) throw new IllegalArgumentException();
			
		    BigDecimal bd = BigDecimal.valueOf(value);
		    bd = bd.setScale(places, RoundingMode.HALF_UP);
		    return bd.doubleValue();
	    } catch (Exception e) {
	    	System.out.println("Got unroundable value. Returning value given");
	    	return value;
	    }
	}

	public static void calculate() {
		
		bestTimeArray.clear();
		bestScrambleArray.clear();
		for (int i = 0; i < 8; i++) {
			bestTimeArray.add(99999.999);
			bestScrambleArray.add("");
		}
		
		

		for (double sixRes : sixAlgers) {if (Double.compare(sixRes, bestTimeArray.get(0)) < 0) {bestTimeArray.set(0, sixRes);}};
		for (double sevenRes : sevenAlgers) {if (Double.compare(sevenRes, bestTimeArray.get(1)) < 0) {bestTimeArray.set(1, sevenRes);}};
		for (double eightRes : eightAlgers) {if (Double.compare(eightRes, bestTimeArray.get(2)) < 0) {bestTimeArray.set(2, eightRes);}};
		for (double nineRes : nineAlgers) {if (Double.compare(nineRes, bestTimeArray.get(3)) < 0) {bestTimeArray.set(3, nineRes);}};
		for (double tenRes : tenAlgers) {if (Double.compare(tenRes, bestTimeArray.get(4)) < 0) {bestTimeArray.set(4, tenRes);}};
		for (double elevenRes : elevenAlgers) {if (Double.compare(elevenRes, bestTimeArray.get(5)) < 0) {bestTimeArray.set(5, elevenRes);}};
		for (double twelveRes : twelveAlgers) {if (Double.compare(twelveRes, bestTimeArray.get(6)) < 0) {bestTimeArray.set(6, twelveRes);}};
		for (double thirteenRes : thirteenAlgers) {if (Double.compare(thirteenRes, bestTimeArray.get(7)) < 0) {bestTimeArray.set(7, thirteenRes);}};
		
		
		for (int a = 0; a < 8; a++) {
			try {
				bestScrambleArray.set(a, scrambleList.get(timeList.indexOf(bestTimeArray.get(a))));
			} catch (IndexOutOfBoundsException ioobe) {
				System.out.println("No results with "+(a+6)+" algs.");
			}
		}
 	}

	public static void display() {
		
		
		System.out.println("This session had "+totalResults+" total successess");
		System.out.println("There are "+algCountByNum.get(0).intValue()+" six algers in this dataset, which is "+round(((algCountByNum.get(0)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(1).intValue()+" seven algers in this dataset, which is "+round(((algCountByNum.get(1)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(2).intValue()+" eight algers in this dataset, which is "+round(((algCountByNum.get(2)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(3).intValue()+" nine algers in this dataset, which is "+round(((algCountByNum.get(3)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(4).intValue()+" ten algers in this dataset, which is "+round(((algCountByNum.get(4)/totalResults)*100), 2)+"% of all scrambles.");;
		System.out.println("There are "+algCountByNum.get(5).intValue()+" eleven algers in this dataset, which is "+round(((algCountByNum.get(5)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(6).intValue()+" twelve algers in this dataset, which is "+round(((algCountByNum.get(6)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(7).intValue()+" thirteen algers in this dataset, which is "+round(((algCountByNum.get(7)/totalResults)*100), 2)+"% of all scrambles.");
		System.out.println("There are "+algCountByNum.get(8).intValue()+" fourteen+ algers in this dataset, which is "+round(((algCountByNum.get(8)/totalResults)*100), 2)+"% of all scrambles.");
		
		
		System.out.println("Your mean time on six alg scrambles was "+meanArray.get(0)+", with a best of "+bestTimeArray.get(0)+" on this scramble: "+bestScrambleArray.get(0));
		System.out.println("Your mean time on seven alg scrambles was "+meanArray.get(1)+", with a best of "+bestTimeArray.get(1)+" on this scramble: "+bestScrambleArray.get(1));
		System.out.println("Your mean time on eight alg scrambles was "+meanArray.get(2)+", with a best of "+bestTimeArray.get(2)+" on this scramble: "+bestScrambleArray.get(2));
		System.out.println("Your mean time on nine alg scrambles was "+meanArray.get(3)+", with a best of "+bestTimeArray.get(3)+" on this scramble: "+bestScrambleArray.get(3));
		System.out.println("Your mean time on ten alg scrambles was "+meanArray.get(4)+", with a best of "+bestTimeArray.get(4)+" on this scramble: "+bestScrambleArray.get(4));
		System.out.println("Your mean time on eleven alg scrambles was "+meanArray.get(5)+", with a best of "+bestTimeArray.get(5)+" on this scramble: "+bestScrambleArray.get(5));
		System.out.println("Your mean time on twelve alg scrambles was "+meanArray.get(6)+", with a best of "+bestTimeArray.get(6)+" on this scramble: "+bestScrambleArray.get(6));
		System.out.println("Your mean time on thirteen alg scrambles was "+meanArray.get(7)+", with a best of "+bestTimeArray.get(7)+" on this scramble: "+bestScrambleArray.get(7));

		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("Seven Alg scrambles: ");
		for (int ai = 0; ai < algList.size(); ai++) {
			if (algList.get(ai) == 7) {
				System.out.println("Time: "+timeList.get(ai)+", Scramble: "+scrambleList.get(ai));
			}
		}
	}
	public static ArrayList<Double> getAlgCountByNum() {
		return algCountByNum;
	}
	
	public static ArrayList<Integer> getAlgList() {
		return algList;
	}
}
