package averagebyalgs;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ScrambleManager {
	
	String[] acceptedMoves = {"U","U'","U2","F","F'","F2","R","R'","R2",
							  "B","B'","B2","L","L'","L2","D","D'","D2"};
	
	String[][][] changes = {{{"Uw"},{"U","U"},{"D","D"},{"F","R"},{"R","B"},{"B","L"},{"L","F"}},
							{{"Uw'"},{"U","U"},{"D","D"},{"F","L"},{"R","F"},{"B","R"},{"L","B"}},
							{{"Uw2"},{"U","U"},{"D","D"},{"F","B"},{"R","L"},{"B","F"},{"L","R"}},
			
							{{"Fw"},{"U","L"},{"D","R"},{"F","F"},{"R","U"},{"B","B"},{"L","D"}},
							{{"Fw'"},{"U","R"},{"D","L"},{"F","F"},{"R","D"},{"B","B"},{"L","U"}},
							{{"Fw2"},{"U","D"},{"D","U"},{"F","F"},{"R","L"},{"B","B"},{"L","R"}},
			
							{{"Rw"},{"U","F"},{"D","B"},{"F","D"},{"R","B"},{"B","U"},{"L","F"}},
							{{"Rw'"},{"U","B"},{"D","F"},{"F","U"},{"R","B"},{"B","D"},{"L","F"}},
							{{"Rw2"},{"U","D"},{"D","U"},{"F","B"},{"R","R"},{"B","F"},{"L","L"}}};
	
	MoveManager m;
	
	
	public ScrambleManager(Group g, ArrayList<ImageView> cubieArray, Group cubieG) {
		m = new MoveManager(g, cubieArray, cubieG);
        
	}
	
	public ScrambleManager(Pane p, ArrayList<ImageView> cubieArray, Group cubieG) {
		m = new MoveManager(p, cubieArray, cubieG);
	}
	
	public ArrayList<ImageView> submitted(String scramble, ArrayList<ImageView> cubieArray, Group cubieG) {
		String wideMove = "";
		
		ArrayList<String> moves = new ArrayList<String>();
		char[] scramChars = scramble.toCharArray();
		for (int i = 0; i < scramChars.length; i++) {
			char c = scramChars[i];
			if (c == 'U' || c == 'F' || c == 'R' || c == 'B' || c == 'L' || c == 'D') {
				try {
					if (scramChars[i+1] == '\'') {
						moves.add(c+"p");
					} else if (scramChars[i+1] == '2') {
						moves.add(Character.toString(c));
						moves.add(Character.toString(c));
					} else if (scramChars[i+1] == 'w') {
						if (wideMove == "") {
							wideMove+= c;
							wideMove+= "w";
							System.out.println("Character: "+c);
							try {
								if (scramChars[i+2] == '\'') {
									wideMove+= "'";
									System.out.println("Character: "+c);
									
									switch (c) {
									case 'U': moves.add('D'+"p"); break;
									case 'F': moves.add('B'+"p"); break;
									case 'R': moves.add('L'+"p"); break;
									case 'B': moves.add('F'+"p"); break;
									case 'L': moves.add('R'+"p"); break;
									case 'D': moves.add('U'+"p"); break;
									}
								} else if (scramChars[i+2] == '2') {
									wideMove+= "2";
									switch (c) {
									case 'U': moves.add(Character.toString('D')); moves.add(Character.toString('D')); break;
									case 'F': moves.add(Character.toString('B')); moves.add(Character.toString('B'));  break;
									case 'R': moves.add(Character.toString('L')); moves.add(Character.toString('L'));  break;
									case 'B': moves.add(Character.toString('F')); moves.add(Character.toString('F'));  break;
									case 'L': moves.add(Character.toString('R')); moves.add(Character.toString('R'));  break;
									case 'D': moves.add(Character.toString('U')); moves.add(Character.toString('U'));  break;
									}
								} else {
									switch (c) {
									case 'U': moves.add(Character.toString('D')); break;
									case 'F': moves.add(Character.toString('B')); break;
									case 'R': moves.add(Character.toString('L')); break;
									case 'B': moves.add(Character.toString('F')); break;
									case 'L': moves.add(Character.toString('R')); break;
									case 'D': moves.add(Character.toString('U')); break;
									}
								}
							} catch (Exception e) {
								switch (c) {
								case 'U': moves.add(Character.toString('D')); break;
								case 'F': moves.add(Character.toString('B')); break;
								case 'R': moves.add(Character.toString('L')); break;
								case 'B': moves.add(Character.toString('F')); break;
								case 'L': moves.add(Character.toString('R')); break;
								case 'D': moves.add(Character.toString('U')); break;
								}
							}
						} else { //Second Wide Move	
							String secondMove = c+"w";
							try {
								if (scramChars[i+2] == '\'') {
									secondMove += "'";
								} else if (scramChars[i+2] == '2') {
									secondMove += "2";
								}
							} catch (Exception e) {};

							String convertedMove = "";
							for (String[][] listOChanges : changes) {
								if (wideMove.equals(listOChanges[0][0])) {
									for (int x = 1; x < listOChanges.length; x++) {
										if (listOChanges[x][0].equals(Character.toString(c))) {
											convertedMove = listOChanges[x][1];
										}
									}
								}
							}
							convertedMove += "w";
							for (char a : secondMove.toCharArray()) {
								if (a == '\'') {
									convertedMove += "'";
								} else if (a == '2') {
									convertedMove += "2";
								}
							}

							System.out.println("First Wide Move: "+wideMove+", Second Wide Move: "+secondMove+", Converted Second Wide Move: "+convertedMove);
							
							if (convertedMove.length() == 2) {
								switch (convertedMove.toCharArray()[0]) {
								case 'U': moves.add(Character.toString('D')); break;
								case 'F': moves.add(Character.toString('B')); break;
								case 'R': moves.add(Character.toString('L')); break;
								case 'B': moves.add(Character.toString('F')); break;
								case 'L': moves.add(Character.toString('R')); break;
								case 'D': moves.add(Character.toString('U')); break;
								}
							} else if (convertedMove.toCharArray()[2] == '2') {
								switch (convertedMove.toCharArray()[0]) {
								case 'U': moves.add(Character.toString('D')); moves.add(Character.toString('D')); break;
								case 'F': moves.add(Character.toString('B')); moves.add(Character.toString('B'));  break;
								case 'R': moves.add(Character.toString('L')); moves.add(Character.toString('L'));  break;
								case 'B': moves.add(Character.toString('F')); moves.add(Character.toString('F'));  break;
								case 'L': moves.add(Character.toString('R')); moves.add(Character.toString('R'));  break;
								case 'D': moves.add(Character.toString('U')); moves.add(Character.toString('U'));  break;
								}
							} else {
								switch (convertedMove.toCharArray()[0]) {
								case 'U': moves.add('D'+"p"); break;
								case 'F': moves.add('B'+"p"); break;
								case 'R': moves.add('L'+"p"); break;
								case 'B': moves.add('F'+"p"); break;
								case 'L': moves.add('R'+"p"); break;
								case 'D': moves.add('U'+"p"); break;
								}
							}
		
						}
						//moves.add(Character.toString(c));
						
					} else {
						moves.add(Character.toString(c));
					}
				} catch (Exception e) {
					System.out.println("FUCK");
					moves.add(Character.toString(c));
				}
			}
		}	
		
		for (String move : moves) {
			System.out.print(move);
		}
		
		return m.applyMoves(moves);
	}
	
	MoveManager getMoveMan() {
		return m;
	}
	
}