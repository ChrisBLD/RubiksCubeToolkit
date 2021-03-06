package tutorial;

import java.util.ArrayList;

import averagebyalgs.MainRedesigned;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CubeInput {
	

	Scene inputScene;
	Stage inputWindow;
	
	int[][] cantMatchEdges = {{7,10},{3,28},{1,46},{5,37},{12,32},{14,39},{41,48},{50,30},{16,19},{43,23},{52,25},{34,21}};
	int[][] cantMatchCorners = {{6,9,29},{8,36,11},{2,45,38},{0,27,47},{18,35,15},{20,17,42},{26,44,51},{24,53,33}};
	
	String[][] whiteCorners = {{"G","O"},{"R","G"},{"B","R"},{"O","B"}};
	String[][] yellowCorners = {{"O","G"},{"G","R"},{"R","B"},{"B","O"}};
	String[][] greenCorners = {{"Y","O"},{"R","Y"},{"W","R"},{"O","W"}};
	String[][] blueCorners = {{"R","W"},{"W","O"},{"O","Y"},{"Y","R"}};
	String[][] redCorners = {{"G","W"},{"W","B"},{"B","Y"},{"Y","G"}};
	String[][] orangeCorners = {{"W","G"},{"G","Y"},{"Y","B"},{"B","W"}};
	
	String[][][] changes = {{{"Uw"},{"U","U"},{"D","D"},{"F","R"},{"R","B"},{"B","L"},{"L","F"}},
					       {{"Uw'"},{"U","U"},{"D","D"},{"F","L"},{"R","F"},{"B","R"},{"L","B"}},
						   {{"Uw2"},{"U","U"},{"D","D"},{"F","B"},{"R","L"},{"B","F"},{"L","R"}},
			
						   {{"Fw"},{"U","L"},{"D","R"},{"F","F"},{"R","U"},{"B","B"},{"L","D"}},
						   {{"Fw'"},{"U","R"},{"D","L"},{"F","F"},{"R","D"},{"B","B"},{"L","U"}},
						   {{"Fw2"},{"U","D"},{"D","U"},{"F","F"},{"R","L"},{"B","B"},{"L","R"}},
			
						   {{"Rw"},{"U","F"},{"D","B"},{"F","D"},{"R","B"},{"B","U"},{"L","F"}},
						   {{"Rw'"},{"U","B"},{"D","F"},{"F","U"},{"R","B"},{"B","D"},{"L","F"}},
						   {{"Rw2"},{"U","D"},{"D","U"},{"F","B"},{"R","R"},{"B","F"},{"L","L"}}};

	
	int[][] allEdges = {{1,46},{3,28},{5,37},{7,10},{19,16},{21,34},{23,43},{25,52},{12,32},{14,39},{48,41},{50,30}};
	
	ArrayList<Button> buttonArray;
	ArrayList<String> moves;
	
	public CubeInput() {
		Pane p = new Pane();
		inputScene = new Scene(p, 600, 400);
		
		String image = MainRedesigned.class.getResource("/resources/greybg.png").toExternalForm();
    	p.setStyle("-fx-background-image: url('" + image + "'); " +
    	           "-fx-background-position: center center; " +
    	           "-fx-background-repeat: stretch;");
    	
		inputWindow = new Stage();
		inputWindow.setScene(inputScene);
		inputWindow.setResizable(false);
		
		Label colourPick = new Label("Click the colours and select white (W), yellow (Y), green (G), blue (B), "
								   + "orange (O) or red (R) with your keyboard, or enter a scramble.");
        colourPick.setTextFill(Color.WHITE);
        colourPick.setWrapText(true);
        colourPick.setMaxWidth(300);
        colourPick.setTextAlignment(TextAlignment.CENTER);
        colourPick.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 23));
        
        colourPick.setLayoutX(300);
        colourPick.setLayoutY(30);
        
        p.getChildren().add(colourPick);
        
        
        buttonArray = new ArrayList<Button>();
        for (int face = 1; face < 7; face++) {
        	for (int i = 0; i < 9; i++) {
        		if (i == 4 || i == 31 || i == 13 || i == 40 || i == 49 || i == 22) {
        			buttonArray.add(colourButtonInit(face, true));
        		} else {
        			buttonArray.add(colourButtonInit(face, false));
        		}
            }
        }
        
        Button submit = new Button();
        submit.setOnAction(event -> {
        	int result = verifyInput();
        	switch(result) {
        	case 0: Setup.buttonArray = buttonArray; UserInterface.timeline2.play(); inputWindow.close(); break;
        	case 1: colourPick.setText("Woops! You have an edge with two of the same colour on it. Please fix it and try again!"); break;
        	case 2: colourPick.setText("Woops! You have an edge with two opposite colours on it. Please fix it and try again!"); break;
        	case 3: colourPick.setText("Woops! You have a duplicate edge. Please fix it and try again!"); break;
        	case 4: colourPick.setText("Woops! You have a corner with two of the same colour on it. Please fix it and try again!"); break;
        	case 5: colourPick.setText("Woops! You have a corner with two opposite colours on it. Please fix it and try again!"); break;
        	case 6: colourPick.setText("Woops! You have a corner that can't exist (entered incorrectly). Please fix it and try again!"); break;
        	case 7: colourPick.setText("Woops! You have a duplicate corner. Please fix it and try again!"); break;
        	case 8: colourPick.setText("Woops! You have a corner that is twisted, so the cube isn't solvable. Try twisting a single corner and entering it again"); break;
        	case 9: colourPick.setText("Woops! You have an edge that is flipped, so the cube isn't solvable. Take out an edge, flip it, and put it back in, then try entering it again!"); break;
        	}
        });
        submit.setMinSize(125, 59); submit.setMaxSize(125, 59);
        submit.setLayoutX(355);
        submit.setLayoutY(300);
        submit.setGraphic(new ImageView(new Image("/resources/submitButton.png")));
        
        p.getChildren().add(submit);
        
        placeButtons(p);
        
	}

	
	Button colourButtonInit(int face, boolean centre) {
		Button b = new Button();
		switch (face) {
		case 1: b.setText("W"); b.setGraphic(new ImageView(new Image("/resources/whiteBorder.png"))); break;
		case 2: b.setText("G"); b.setGraphic(new ImageView(new Image("/resources/greenBorder.png"))); break;
		case 3: b.setText("Y"); b.setGraphic(new ImageView(new Image("/resources/yellowBorder.png"))); break;
		case 4: b.setText("O"); b.setGraphic(new ImageView(new Image("/resources/orangeBorder.png"))); break;
		case 5: b.setText("R"); b.setGraphic(new ImageView(new Image("/resources/redBorder.png"))); break;
		case 6: b.setText("B"); b.setGraphic(new ImageView(new Image("/resources/blueBorder.png"))); break;
		}
		
		if (!centre) {
	        b.setOnMouseClicked(event -> {
	        	deselectButtons();
	        	String col = b.getText();
	        	switch (col) {
	        	case "Y": b.setGraphic(new ImageView(new Image("/resources/yellowBorderS.png"))); break;
	        	case "W": b.setGraphic(new ImageView(new Image("/resources/whiteBorderS.png"))); break;
	        	case "B": b.setGraphic(new ImageView(new Image("/resources/blueBorderS.png"))); break;
	        	case "G": b.setGraphic(new ImageView(new Image("/resources/greenBorderS.png"))); break;
	        	case "O": b.setGraphic(new ImageView(new Image("/resources/orangeBorderS.png"))); break;
	        	case "R": b.setGraphic(new ImageView(new Image("/resources/redBorderS.png"))); break;
	        	}
	        	b.setOnKeyPressed(e -> {
	        		switch(e.getCode()) {
		        	case Y: b.setText("Y"); b.setGraphic(new ImageView(new Image("/resources/yellowBorderS.png"))); break;
		        	case W: b.setText("W"); b.setGraphic(new ImageView(new Image("/resources/whiteBorderS.png"))); break;
		        	case B: b.setText("B"); b.setGraphic(new ImageView(new Image("/resources/blueBorderS.png"))); break;
		        	case G: b.setText("G"); b.setGraphic(new ImageView(new Image("/resources/greenBorderS.png"))); break;
		        	case O: b.setText("O"); b.setGraphic(new ImageView(new Image("/resources/orangeBorderS.png"))); break;
		        	case R: b.setText("R"); b.setGraphic(new ImageView(new Image("/resources/redBorderS.png"))); break;
	        		}
	        	});
	        });
	        
		}
		
		
        
        b.setMaxSize(0,0); b.setMinSize(0,0);
        return b;
        
	}
	
	private void deselectButtons() {
		for (int i = 0; i < buttonArray.size(); i++) {
			Button b = buttonArray.get(i);
			switch (b.getText()) {
			case "W": b.setGraphic(new ImageView(new Image("/resources/whiteBorder.png"))); break;
			case "G": b.setGraphic(new ImageView(new Image("/resources/greenBorder.png"))); break;
			case "Y": b.setGraphic(new ImageView(new Image("/resources/yellowBorder.png"))); break;
			case "O": b.setGraphic(new ImageView(new Image("/resources/orangeBorder.png"))); break;
			case "R": b.setGraphic(new ImageView(new Image("/resources/redBorder.png"))); break;
			case "B": b.setGraphic(new ImageView(new Image("/resources/blueBorder.png"))); break;
			}
			if (i == 4 || i == 31 || i == 13 || i == 40 || i == 49 || i == 22) {
			} 

		}
	}

	void placeButtons(Pane p) {
		
		int initX = 160;
		int initY = 30;
			
		for (int i = 0; i < 9; i++) {
			if (i < 3) {
				buttonArray.get(i).setLayoutX(initX+(40*i));
				buttonArray.get(i).setLayoutY(initY);
			} else if (i < 6) {
				buttonArray.get(i).setLayoutX(initX+((i-3)*40));
				buttonArray.get(i).setLayoutY(initY+40);
			} else {
				buttonArray.get(i).setLayoutX(initX+((i-6)*40));
				buttonArray.get(i).setLayoutY(initY+80);
			}
			p.getChildren().add(buttonArray.get(i));
		}
		
		initY = 160;
	
		for (int i = 9; i < 18; i++) {
			if (i < 12) {
				buttonArray.get(i).setLayoutX(initX+(40*(i-9)));
				buttonArray.get(i).setLayoutY(initY);
			} else if (i < 15) {
				buttonArray.get(i).setLayoutX(initX+((i-12)*40));
				buttonArray.get(i).setLayoutY(initY+40);
			} else {
				buttonArray.get(i).setLayoutX(initX+((i-15)*40));
				buttonArray.get(i).setLayoutY(initY+80);
			}
			p.getChildren().add(buttonArray.get(i));
		}
		
		initY = 290;
		
		for (int i = 18; i < 27; i++) {
			if (i < 21) {
				buttonArray.get(i).setLayoutX(initX+(40*(i-18)));
				buttonArray.get(i).setLayoutY(initY);
			} else if (i < 24) {
				buttonArray.get(i).setLayoutX(initX+((i-21)*40));
				buttonArray.get(i).setLayoutY(initY+40);
			} else {
				buttonArray.get(i).setLayoutX(initX+((i-24)*40));
				buttonArray.get(i).setLayoutY(initY+80);
			}
			p.getChildren().add(buttonArray.get(i));
		}
		
		initY = 160;
		initX = 30;
		
		for (int i = 27; i < 36; i++) {
			if (i < 30) {
				buttonArray.get(i).setLayoutX(initX+(40*(i-27)));
				buttonArray.get(i).setLayoutY(initY);
			} else if (i < 33) {
				buttonArray.get(i).setLayoutX(initX+((i-30)*40));
				buttonArray.get(i).setLayoutY(initY+40);
			} else {
				buttonArray.get(i).setLayoutX(initX+((i-33)*40));
				buttonArray.get(i).setLayoutY(initY+80);
			}
			p.getChildren().add(buttonArray.get(i));
		}
		
		initX = 290;
		
		for (int i = 36; i < 45; i++) {
			if (i < 39) {
				buttonArray.get(i).setLayoutX(initX+(40*(i-36)));
				buttonArray.get(i).setLayoutY(initY);
			} else if (i < 42) {
				buttonArray.get(i).setLayoutX(initX+((i-39)*40));
				buttonArray.get(i).setLayoutY(initY+40);
			} else {
				buttonArray.get(i).setLayoutX(initX+((i-42)*40));
				buttonArray.get(i).setLayoutY(initY+80);
			}
			p.getChildren().add(buttonArray.get(i));
		}
		
		initX = 420;
		
		for (int i = 45; i < 54; i++) {
			if (i < 48) {
				buttonArray.get(i).setLayoutX(initX+(40*(i-45)));
				buttonArray.get(i).setLayoutY(initY);
			} else if (i < 51) {
				buttonArray.get(i).setLayoutX(initX+((i-48)*40));
				buttonArray.get(i).setLayoutY(initY+40);
			} else {
				buttonArray.get(i).setLayoutX(initX+((i-51)*40));
				buttonArray.get(i).setLayoutY(initY+80);
			}
			p.getChildren().add(buttonArray.get(i));
		}
	}
	
	int verifyInput() {

		Button b1, b2, b3;
		String foundPair;
		ArrayList<String> pairsPresent = new ArrayList<String>();
		String[][] usedList = {{}};
		for (int[] pair : cantMatchEdges) {
			b1 = buttonArray.get(pair[0]);
			b2 = buttonArray.get(pair[1]);
			if (b1.getText().equals(b2.getText())) {
				System.out.println("Invalid edges at: "+pair[0]+", "+pair[1]+" (match)");
				return 1;
			}
			switch (b1.getText()) {
			case "W": if (b2.getText().equals("Y")) { System.out.println("Invalid edges at: "+pair[0]+", "+pair[1]+" (opp)"); return 2;}; break;
			case "Y": if (b2.getText().equals("W")) { System.out.println("Invalid edges at: "+pair[0]+", "+pair[1]+" (opp)"); return 2;}; break;
			case "B": if (b2.getText().equals("G")) { System.out.println("Invalid edges at: "+pair[0]+", "+pair[1]+" (opp)"); return 2;}; break;
			case "G": if (b2.getText().equals("B")) { System.out.println("Invalid edges at: "+pair[0]+", "+pair[1]+" (opp)"); return 2;}; break;
			case "O": if (b2.getText().equals("R")) { System.out.println("Invalid edges at: "+pair[0]+", "+pair[1]+" (opp)"); return 2;}; break;
			case "R": if (b2.getText().equals("O")) { System.out.println("Invalid edges at: "+pair[0]+", "+pair[1]+" (opp)"); return 2;}; break;
			}
			foundPair = b1.getText()+b2.getText();
			if (pairsPresent.contains(foundPair)) {
				System.out.println("Invalid edges at: "+pair[0]+", "+pair[1]+" (already exists)"); 
				return 3;
			}
			pairsPresent.add(foundPair);
			foundPair = b2.getText()+b1.getText();
			pairsPresent.add(foundPair);
		}
		for (int[] trip : cantMatchCorners) {
			b1 = buttonArray.get(trip[0]);
			b2 = buttonArray.get(trip[1]);
			b3 = buttonArray.get(trip[2]);
			if (b1.getText().equals(b2.getText()) || b2.getText().equals(b3.getText()) || b1.getText().equals(b3.getText())) {
				System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (match)");
				return 4;
			}			
			switch(b1.getText()) {
			case "W": if (b2.getText().equals("Y") || b3.getText().equals("Y")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "Y": if (b2.getText().equals("W") || b3.getText().equals("W")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "B": if (b2.getText().equals("G") || b3.getText().equals("G")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "G": if (b2.getText().equals("B") || b3.getText().equals("B")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "O": if (b2.getText().equals("R") || b3.getText().equals("R")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "R": if (b2.getText().equals("O") || b3.getText().equals("O")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			}
			switch(b2.getText()) {
			case "W": if (b1.getText().equals("Y") || b3.getText().equals("Y")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "Y": if (b1.getText().equals("W") || b3.getText().equals("W")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "B": if (b1.getText().equals("G") || b3.getText().equals("G")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "G": if (b1.getText().equals("B") || b3.getText().equals("B")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "O": if (b1.getText().equals("R") || b3.getText().equals("R")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			case "R": if (b1.getText().equals("O") || b3.getText().equals("O")) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (opp)"); return 5;}; break;
			}
			
			switch(b1.getText()) {
			case "W": usedList = whiteCorners; break;
			case "Y": usedList = yellowCorners; break;
			case "B": usedList = blueCorners; break;
			case "G": usedList = greenCorners; break;
			case "O": usedList = orangeCorners; break;
			case "R": usedList = redCorners; break;
			}

			boolean goodTriple = false;
			//System.out.println("Triple: "+b1.getText()+b2.getText()+b3.getText());
			for (String[] triple : usedList) {
				System.out.println("Using: "+triple[0]+triple[1]);
				if (b2.getText().equals(triple[0])) {
					if (b3.getText().equals(triple[1])) {
						goodTriple = true;
					}
				}
			}

			if (!goodTriple) { System.out.println("Invalid corners at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (not possible given scheme)"); return 6;}
			
			foundPair = b1.getText()+b2.getText()+b3.getText();
			//System.out.println("Attempting to add: "+foundPair);
			if (pairsPresent.contains(foundPair)) {
				System.out.println("Invalid edges at: "+trip[0]+", "+trip[1]+", "+trip[2]+" (already exists)"); 
				return 7;
			}
			//System.out.println("Adding: "+foundPair);
			pairsPresent.add(foundPair);
			foundPair = b1.getText()+b3.getText()+b2.getText(); pairsPresent.add(foundPair);
			foundPair = b2.getText()+b1.getText()+b3.getText(); pairsPresent.add(foundPair);
			foundPair = b2.getText()+b3.getText()+b1.getText(); pairsPresent.add(foundPair);
			foundPair = b3.getText()+b1.getText()+b2.getText(); pairsPresent.add(foundPair);
			foundPair = b3.getText()+b2.getText()+b1.getText(); pairsPresent.add(foundPair);
			
			
		}
		
		int cornerOrientTotal = 0;
		
		for (int[] corner : cantMatchCorners) {
			if (buttonArray.get(corner[0]).getText() == "W" || buttonArray.get(corner[0]).getText() == "Y") {			
				System.out.println("Corner: "+corner[0]+", "+corner[1]+", "+corner[2]+" value 0");
			} else if (buttonArray.get(corner[1]).getText() == "W" || buttonArray.get(corner[1]).getText() == "Y") {	
				System.out.println("Corner: "+corner[0]+", "+corner[1]+", "+corner[2]+" value 1");
				cornerOrientTotal++;
			} else {
				System.out.println("Corner: "+corner[0]+", "+corner[1]+", "+corner[2]+" value 2");
				cornerOrientTotal+=2;
			}
		}
		
		if (cornerOrientTotal % 3 != 0) {
			System.out.println("Invalid corners (corner twist, cube unsolvable)");
			return 8;
		}
		
		int edgeOrientTotal = 0;
		for (int[] edge : allEdges) {
			if (buttonArray.get(edge[0]).getText() == "W" || buttonArray.get(edge[0]).getText() == "Y") {
				System.out.println("Edge: "+edge[0]+", "+edge[1]+" oriented correctly.");
				edgeOrientTotal++;
			} else if (buttonArray.get(edge[0]).getText() == "G" || buttonArray.get(edge[0]).getText() == "B") {
				if (buttonArray.get(edge[1]).getText() == "W" || buttonArray.get(edge[1]).getText() == "Y") { 
					System.out.println("Edge: "+edge[0]+", "+edge[1]+" NOT oriented correctly.");
				} else {
					System.out.println("Edge: "+edge[0]+", "+edge[1]+" oriented correctly.");
					edgeOrientTotal++;
				}
			} else {
				System.out.println("Edge: "+edge[0]+", "+edge[1]+" NOT oriented correctly.");
			}
		}

		if (edgeOrientTotal % 2 != 0) {
			System.out.println("Invalid edges (edge flipped in place, cube unsolvable)");
			return 9;
		}
		
		return 0;
		
		/*
		 * I will have to work out some way of telling whether or not I have three types of parity:
		 * 1) Single flipped edge - DONE
		 * 2) Single twisted corner - DONE
		 * 3) Two swapped pieces
		 */
		
		
		
	}
	
	void show() {
		inputWindow.show();
	}
}
