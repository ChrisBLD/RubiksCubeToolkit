package timefixer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class TimerFix {
	
	private static ArrayList<String> solves = new ArrayList<String>();

	public static ArrayList parseInp(String in) {
		
		int count = 0;
		
		
			
		Pattern pattern = Pattern.compile("[0-9][0-9].[0-9][0-9][0-9]");
		Matcher m = pattern.matcher(in);
		
		while (m.find()) {
			//System.out.println("s");
			solves.add(m.group());
		}
		return solves;
	}
	
	public static void printCSV(ArrayList<String> solves) {
		
		String out = new String();
		
		for (int i = 0; i<solves.size(); i++) {
			out += solves.get(i)+", ";
		}
		
		//System.out.println(out);
			
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		//System.out.println("Enter time list:");
		String inp = in.next();
		
		
		solves = parseInp(inp);
		
		printCSV(solves);
	}
}
