package averagebyalgs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
		
		for (String result : results) {
			String orig = result;
			result = result.substring(result.indexOf('.')+2);
			if (result.indexOf('N') != -1) { //Entered if DNF, solve can be discarded.
				visited.add(Integer.valueOf(orig.substring(0,orig.indexOf('.'))));
				//System.out.println(orig.substring(0,orig.indexOf('.'))+", RECOGNISED");
				DNFcount++;
			} else {
				if (result.indexOf('+') != -1) { //Either +2 or splits mode on in session (or both)
					int n = result.indexOf('=');
					if (n != -1) { //Splits mode on in session
						time = result.substring(0, n);
						System.out.println("Time after split removal: "+time);
						if (result.indexOf('+') != -1) { //Also a +2 to go
							time = time.replace("+","");
						}
						scramble = result.substring(result.indexOf(' ')+3);
						System.out.println("Time: "+time+", Scramble: "+scramble);
					} else { //No split, just a +2
						result = result.replace("+",""); 
						//System.out.println(result); 
						time = result.substring(0, result.indexOf(' '));
						scramble = result.substring(result.indexOf(' ')+3);
						System.out.println("Time: "+time+", Scramble: "+scramble);
					}
				} else {
					//System.out.println(result); 
					time = result.substring(0, result.indexOf(' '));
					scramble = result.substring(result.indexOf(' ')+3);
					System.out.println("Time: "+time+", Scramble: "+scramble);
				}
				
				if (time.indexOf(':') != -1) {
					try {
						double min = Double.valueOf(time.substring(0,1));
						min *= 60;
						time = time.substring(time.indexOf(':')+1);
						double timeDubTemp = Double.valueOf(time);
						timeDubTemp += min;
						time = ""+timeDubTemp;
					} catch (Exception e) {
						System.out.println("Not acceptable input.");
						nAcc++;
					}
				}
				
				try {
					timeDub = Double.parseDouble(time);					
					timeList.add(timeDub);
					scrambleList.add(scramble);
					count++;
					//System.out.println(orig.substring(0,orig.indexOf('.'))+", RECOGNISED");
					visited.add(Integer.valueOf(orig.substring(0,orig.indexOf('.'))));
					//System.out.println("ADDED");
				} catch (Exception e) {
					System.out.println("Not acceptable input.");
					nAcc++;
				}
				
				
				System.out.println("COUNT: "+count+", TIMES: "+timeList.size()+", SCRAMBLES: "+scrambleList.size());
				
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
