package averagebyalgs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class InputParse {
	
	
	static ArrayList<Double> timeList = new ArrayList<Double>();
	static ArrayList<String> scrambleList = new ArrayList<String>();

	static void start(boolean inputType, String fullResults) {
		if (inputType) {
			cstimerHandler(fullResults);
		} else {
			csvHandler(fullResults);
		}
	}
	
	private static void cstimerHandler(String fullResults) {
		String results[] = fullResults.split("\\r?\\n");
		
		int DNFcount = 0;
		double timeDub = 0.0;
		String time, scramble;
		int nAcc = 0;
		int count = 0;
		ArrayList<Integer> visited = new ArrayList<Integer>();
		
		Pattern scramPat = Pattern.compile("([BDFLRU]w?[\'2]?\\s?)+$");
		Pattern timePat = Pattern.compile("(DNF\\()?\\d+[:.]\\d+(\\.\\d+)?(\\))?");
		boolean flag;
		
		for (String result : results) {
			
			flag = false;
			
			Matcher scramMatch = scramPat.matcher(result);
			if (scramMatch.find()) {
				System.out.println("Match: "+scramMatch.group(0));
				scrambleList.add(scramMatch.group(0));
				flag = true;
			}
			
			if (flag) {
				Matcher timeMatch = timePat.matcher(result);
				if (timeMatch.find()) {
					if (timeMatch.group(0).indexOf('N') == -1) {
						System.out.println("Time: "+timeMatch.group(0));
						int colonInd = timeMatch.group(0).indexOf(':');
						if (colonInd != -1) {
							int mins = Integer.valueOf(timeMatch.group(0).substring(0, colonInd));
							mins *= 60;
							Double remainder = Double.parseDouble(timeMatch.group(0).substring(colonInd+1));
							timeDub = remainder + mins;
						} else {
							timeDub = Double.parseDouble(timeMatch.group(0));
						}
						timeList.add(timeDub);
					} else {
						System.out.println("DNF. Discarding");
					}
				}
			}
		}

		System.out.println("TOTAL GOOD SOLVES: "+timeList.size());
		System.out.println("TOTAL DNFS: "+DNFcount);
		//System.out.println("TOTAL PARSED: "+resultsParsed.size()+DNFcount);
		System.out.println("nAcc: "+nAcc);
		
		//return resultsParsed;
	}
	
	private static void csvHandler(String times) {
		
	}
	
	public static ArrayList<Double> getTimeList() {
		return timeList;
	}
	
	public static ArrayList<String> getScrambleList() {
		return scrambleList;
	}
}


/* Test input:
1784. 21.726   D B U R2 U' B2 R' U2 B2 U D2 L2 D' R2 L2 U R2 B2 D' F' 
1785. DNF(29.396)   D' F2 R B' R' F' U' B2 D2 R2 B U2 F' D2 R2 F R2 L' B' 
1786. 22.840   U' R' U2 B2 D2 R B2 R B2 R2 D2 L' D' F2 U B' U' L D R' 
1787. DNF(28.090)   L2 U2 R2 B2 F2 D' L2 U' F2 L2 U' L B2 D B F2 R' F' D F2 D 
1788. DNF(30.911)   L2 U D' L D R2 F' U' L2 B2 U' B2 D R2 U2 F2 R2 D R' B2 
1789. DNF(24.348)   B2 D' L2 B2 R2 F' B R' U F2 U' B2 L2 F2 D2 R2 B' 
1790. DNF(37.914)   B' D2 R2 B' D2 F R2 F2 U2 F D2 U F' U B F L D2 B' F D 
1791. 21.258   D' B2 L2 B2 R2 U R2 U2 R2 F2 D B2 F' U R B L F' D2 B2 R 
1792. 17.726   B2 D2 F2 D2 L2 F2 L' F2 U2 L U2 R' U' F' U L' D' B2 R F2 D' 
1793. DNF(32.045)   B' D R' B R L' F' R U' D2 F2 R2 F' D2 B D2 B R2 F 
1794. DNF(34.401)   L B D' R D2 R D2 F' L B2 D2 B2 U R2 U2 F2 L2 D' R2 
1795. 21.716   B L D' F2 U' F2 D' F2 U' L2 D2 L2 B U' R' D2 R' U' B U 
1796. DNF(30.135)   F D' B2 D2 L' D2 F2 L U2 F2 L B2 U' F' L F L' B' R' 
1797. 24.547   R2 U R2 B2 U2 R2 U B2 L2 D' F R2 D2 U' F R F' U B2 
1798. DNF(25.493)   U' B2 U B2 R2 F2 U B2 D2 U L2 R D' B D L D' B U F' 
1799. DNF(29.353)   L' B U' R' U F U F B R' L' U2 F2 R' B2 D2 L' B2 L2 
1800. 23.478   L U2 B F2 U2 L2 U2 R2 B' U2 F2 R2 D R D2 L2 F U' L' 
1801. 24.964   R2 F2 L2 B' L2 F' U2 B' L2 F2 R2 D2 R D2 U L U' B U' B2 U' 
1802. 22.902   L' D L2 D R2 D2 L' F D2 F R2 F' R2 D2 F' D2 B' R2 U' B2 
1803. DNF(36.098)   D' F2 U F' B' U' L U' B2 R2 U2 L2 U' L2 D2 B2 U F' U2 
1804. DNF(29.590)   B2 U2 L2 R2 U R2 F2 D' B2 U' F2 B D' U2 L D U F U B R2 
1805. 23.550   R2 U2 D2 B' R' D B2 U R2 D2 F' U2 R2 B' R2 U2 B' U2 F D 
1806. 23.717   R2 D' F2 D U2 R2 F2 D' B2 D R' D' F2 D' R U' F' R' B L'
*/
