package averagebyalgs;

import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class NetManager {

	static private Image white = new Image("resources/CubeWHITE.png");
	static private Image yellow = new Image("resources/CubeYELLOW.png");
	static private Image red= new Image("resources/CubeRED.png");
	static private Image green = new Image("resources/CubeGREEN.png");
	static private Image orange = new Image("resources/CubeORANGE.png");
	static private Image blue = new Image("resources/CubeBLUE.png");
	
	private ImageView whiteIV = new ImageView(white);
	private ImageView yellowIV = new ImageView(yellow);
	private ImageView redIV = new ImageView(red);
    private ImageView greenIV = new ImageView(green);
	private ImageView orangeIV = new ImageView(orange);
	private ImageView blueIV = new ImageView(blue);
	
	private ImageView whiteIV2 = new ImageView(white);
	private ImageView yellowIV2 = new ImageView(yellow);
	private ImageView redIV2 = new ImageView(red);
    private ImageView greenIV2 = new ImageView(green);
	private ImageView orangeIV2 = new ImageView(orange);
	private ImageView blueIV2 = new ImageView(blue);
	
	private ImageView whiteIVNS = new ImageView(new Image("resources/CubeWHITE-NS.png"));
	private ImageView yellowIVNS = new ImageView(new Image("resources/CubeYELLOW-NS.png"));
	private ImageView redIVNS = new ImageView(new Image("resources/CubeRED-NS.png"));
    private ImageView greenIVNS = new ImageView(new Image("resources/CubeGREEN-NS.png"));
	private ImageView orangeIVNS = new ImageView(new Image("resources/CubeORANGE-NS.png"));
	private ImageView blueIVNS = new ImageView(new Image("resources/CubeBLUE-NS.png"));
	
	private ImageView whiteIVS = new ImageView(new Image("resources/CubeWHITE-S.png"));
	private ImageView yellowIVS = new ImageView(new Image("resources/CubeYELLOW-S.png"));
	private ImageView redIVS = new ImageView(new Image("resources/CubeRED-S.png"));
    private ImageView greenIVS = new ImageView(new Image("resources/CubeGREEN-S.png"));
	private ImageView orangeIVS = new ImageView(new Image("resources/CubeORANGE-S.png"));
	private ImageView blueIVS = new ImageView(new Image("resources/CubeBLUE-S.png"));
	
	public static Image topColour;
	public static Image frontColour;
	public static Image leftColour;
	public static Image rightColour;
	public static Image backColour;
	public static Image bottomColour;
	
	private Image cubeNet = new Image("resources/CubeNet.png");
    private ImageView iv1 = new ImageView();
	
	public ArrayList<Image> colours = new ArrayList<Image>();
	public ArrayList<ImageView> cubieArray = new ArrayList<ImageView>();
	public ArrayList<Image> colourSet = new ArrayList<Image>();
	
	private String uFace;
	
	private Group cubieG;
	
	Pane init(Pane p) {
		iv1.setImage(cubeNet);
	    iv1.setScaleX(0.5);
	    iv1.setScaleY(0.5);
	    iv1.setPreserveRatio(true);
	    iv1.setSmooth(true);
	    iv1.setLayoutX(-300);
	    iv1.setLayoutY(-319);
	        

	    colours.add(new Image("resources/CubeWHITE.png"));
	    colours.add(new Image("resources/CubeGREEN.png"));
	    colours.add(new Image("resources/CubeYELLOW.png"));
	    colours.add(new Image("resources/CubeORANGE.png"));
	    colours.add(new Image("resources/CubeRED.png"));
	    colours.add(new Image("resources/CubeBLUE.png"));
	      
	    scaleColours();
	        
	        
	    cubieG = cubieInit(p,"White-Green");
	    p.getChildren().add(cubieG);
	    p.getChildren().add(iv1);
	        
	    orientationButtonInit(p);
	    
	    return p;
	}
	
	private void scaleColours() {
		whiteIV.setFitHeight(26);
  	  	whiteIV.setFitWidth(26);
  	  	yellowIV.setFitHeight(26);
	  	yellowIV.setFitWidth(26);
	  	redIV.setFitHeight(26);
  	  	redIV.setFitWidth(26);
  	  	greenIV.setFitHeight(26);
	  	greenIV.setFitWidth(26);
	  	orangeIV.setFitHeight(26);
  	  	orangeIV.setFitWidth(26);
  	  	blueIV.setFitHeight(26);
	  	blueIV.setFitWidth(26);
	  	
	  	whiteIV2.setFitHeight(26);
  	  	whiteIV2.setFitWidth(26);
  	  	yellowIV2.setFitHeight(26);
	  	yellowIV2.setFitWidth(26);
	  	redIV2.setFitHeight(26);
  	  	redIV2.setFitWidth(26);
  	  	greenIV2.setFitHeight(26);
	  	greenIV2.setFitWidth(26);
	  	orangeIV2.setFitHeight(26);
  	  	orangeIV2.setFitWidth(26);
  	  	blueIV2.setFitHeight(26);
	  	blueIV2.setFitWidth(26);
	  	
	  	whiteIVS.setFitHeight(26);
  	  	whiteIVS.setFitWidth(26);
  	  	yellowIVS.setFitHeight(26);
	  	yellowIVS.setFitWidth(26);
	  	redIVS.setFitHeight(26);
  	  	redIVS.setFitWidth(26);
  	  	greenIVS.setFitHeight(26);
	  	greenIVS.setFitWidth(26);
	  	orangeIVS.setFitHeight(26);
  	  	orangeIVS.setFitWidth(26);
  	  	blueIVS.setFitHeight(26);
	  	blueIVS.setFitWidth(26);
	  	
	  	whiteIVNS.setFitHeight(26);
  	  	whiteIVNS.setFitWidth(26);
  	  	yellowIVNS.setFitHeight(26);
	  	yellowIVNS.setFitWidth(26);
	  	redIVNS.setFitHeight(26);
  	  	redIVNS.setFitWidth(26);
  	  	greenIVNS.setFitHeight(26);
	  	greenIVNS.setFitWidth(26);
	  	orangeIVNS.setFitHeight(26);
  	  	orangeIVNS.setFitWidth(26);
  	  	blueIVNS.setFitHeight(26);
	  	blueIVNS.setFitWidth(26);
	}

	private void orientationButtonInit(Pane p) {
		ArrayList<Button> uButtons = orientSelect(p, 0);
        ArrayList<Button> fButtons = orientSelect(p, 1);
        
        //Button list goes WYORGB

        
        uButtons.get(0).setOnAction(value -> {
        	setButtonCols(0, 0, uButtons, fButtons);
        });
        uButtons.get(1).setOnAction(value -> {
        	setButtonCols(0, 1, uButtons, fButtons);
        });
        uButtons.get(2).setOnAction(value -> {
        	setButtonCols(1, 0, uButtons, fButtons);
        });
        uButtons.get(3).setOnAction(value -> {
        	setButtonCols(1, 1, uButtons, fButtons);
        });
        uButtons.get(4).setOnAction(value -> {
        	setButtonCols(2, 0, uButtons, fButtons);
        });
        uButtons.get(5).setOnAction(value -> {
        	setButtonCols(2, 1, uButtons, fButtons);
        });
        
        fButtons.get(0).setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		fButtons.get(0).setGraphic(whiteIVS);
        		fButtons.get(1).setGraphic(yellowIV2);
        		if (uFace.equals("Red") || uFace.equals("Orange")) {
	        		fButtons.get(2).setGraphic(redIVNS);
	        		fButtons.get(3).setGraphic(orangeIVNS);
	        		fButtons.get(4).setGraphic(greenIV2);
            		fButtons.get(5).setGraphic(blueIV2);
        		} else if (uFace.equals("Green") || uFace.equals("Blue")){
        			fButtons.get(4).setGraphic(greenIVNS);
            		fButtons.get(5).setGraphic(blueIVNS);
            		fButtons.get(2).setGraphic(redIV2);
	        		fButtons.get(3).setGraphic(orangeIV2);
        		}
        		
        		p.getChildren().remove(cubieG);
        		p.getChildren().remove(iv1);
        		cubieG = cubieInit(p, uFace+"-White");
        		p.getChildren().add(cubieG);
                p.getChildren().add(iv1);
        	}
        });
        fButtons.get(1).setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		fButtons.get(0).setGraphic(whiteIV2);
        		fButtons.get(1).setGraphic(yellowIVS);
        		if (uFace.equals("Red") || uFace.equals("Orange")) {
	        		fButtons.get(2).setGraphic(redIVNS);
	        		fButtons.get(3).setGraphic(orangeIVNS);
	        		fButtons.get(4).setGraphic(greenIV2);
            		fButtons.get(5).setGraphic(blueIV2);
        		} else if (uFace.equals("Green") || uFace.equals("Blue")){
        			fButtons.get(4).setGraphic(greenIVNS);
            		fButtons.get(5).setGraphic(blueIVNS);
            		fButtons.get(2).setGraphic(redIV2);
	        		fButtons.get(3).setGraphic(orangeIV2);
        		}
        		p.getChildren().remove(cubieG);
        		p.getChildren().remove(iv1);
        		cubieG = cubieInit(p, uFace+"-Yellow");
        		p.getChildren().add(cubieG);
                p.getChildren().add(iv1);
        	}
        });
        fButtons.get(2).setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		fButtons.get(2).setGraphic(redIVS);
        		fButtons.get(3).setGraphic(orangeIV2);
        		if (uFace.equals("White") || uFace.equals("Yellow")) {
	        		fButtons.get(0).setGraphic(whiteIVNS);
	        		fButtons.get(1).setGraphic(yellowIVNS);
	        		fButtons.get(4).setGraphic(greenIV2);
            		fButtons.get(5).setGraphic(blueIV2);
        		} else if (uFace.equals("Green") || uFace.equals("Blue")){
        			fButtons.get(0).setGraphic(whiteIV2);
	        		fButtons.get(1).setGraphic(yellowIV2);
        			fButtons.get(4).setGraphic(greenIVNS);
            		fButtons.get(5).setGraphic(blueIVNS);
            		
        		}
        		p.getChildren().remove(cubieG);
        		p.getChildren().remove(iv1);
        		cubieG = cubieInit(p, uFace+"-Red");
        		p.getChildren().add(cubieG);
                p.getChildren().add(iv1);
        	}
        });
        fButtons.get(3).setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		fButtons.get(2).setGraphic(redIV2);
        		fButtons.get(3).setGraphic(orangeIVS);
        		if (uFace.equals("White") || uFace.equals("Yellow")) {
	        		fButtons.get(0).setGraphic(whiteIVNS);
	        		fButtons.get(1).setGraphic(yellowIVNS);
	        		fButtons.get(4).setGraphic(greenIV2);
            		fButtons.get(5).setGraphic(blueIV2);
        		} else if (uFace.equals("Green") || uFace.equals("Blue")){
        			fButtons.get(0).setGraphic(whiteIV2);
	        		fButtons.get(1).setGraphic(yellowIV2);
        			fButtons.get(4).setGraphic(greenIVNS);
            		fButtons.get(5).setGraphic(blueIVNS);
            		
        		}
        		p.getChildren().remove(cubieG);
        		p.getChildren().remove(iv1);
        		cubieG = cubieInit(p, uFace+"-Orange");
        		p.getChildren().add(cubieG);
                p.getChildren().add(iv1);
        	}
        });
        fButtons.get(4).setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		fButtons.get(4).setGraphic(greenIVS);
        		fButtons.get(5).setGraphic(blueIV2);
        		if (uFace.equals("White") || uFace.equals("Yellow")) {
	        		fButtons.get(0).setGraphic(whiteIVNS);
	        		fButtons.get(1).setGraphic(yellowIVNS);
	        		fButtons.get(2).setGraphic(redIV2);
            		fButtons.get(3).setGraphic(orangeIV2);
        		} else if (uFace.equals("Red") || uFace.equals("Orange")){
        			fButtons.get(0).setGraphic(whiteIV2);
	        		fButtons.get(1).setGraphic(yellowIV2);
        			fButtons.get(2).setGraphic(redIVNS);
            		fButtons.get(3).setGraphic(orangeIVNS);
            		
        		}
        		p.getChildren().remove(cubieG);
        		p.getChildren().remove(iv1);
        		cubieG = cubieInit(p, uFace+"-Green");
        		p.getChildren().add(cubieG);
                p.getChildren().add(iv1);
        	}
        });
        fButtons.get(5).setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		fButtons.get(4).setGraphic(greenIV2);
        		fButtons.get(5).setGraphic(blueIVS);
        		if (uFace.equals("White") || uFace.equals("Yellow")) {
	        		fButtons.get(0).setGraphic(whiteIVNS);
	        		fButtons.get(1).setGraphic(yellowIVNS);
	        		fButtons.get(2).setGraphic(redIV2);
            		fButtons.get(3).setGraphic(orangeIV2);
        		} else if (uFace.equals("Red") || uFace.equals("Orange")){
        			fButtons.get(0).setGraphic(whiteIV2);
	        		fButtons.get(1).setGraphic(yellowIV2);
        			fButtons.get(2).setGraphic(redIVNS);
            		fButtons.get(3).setGraphic(orangeIVNS);
            		
        		}
        		p.getChildren().remove(cubieG);
        		p.getChildren().remove(iv1);
        		cubieG = cubieInit(p, uFace+"-Blue");
        		p.getChildren().add(cubieG);
                p.getChildren().add(iv1);
        	}
        });
        
        
        for (Button button : uButtons) {
        	p.getChildren().add(button);
        }
        
        for (Button button : fButtons) {
        	p.getChildren().add(button);
        }
        
        
        //We want first click on top row to open up second row of choices.
        //Second row of choices then applies the orientation to the puzzle.
	}
	
	private void setButtonCols(int mode, int spec, ArrayList<Button> uButtons, ArrayList<Button> fButtons) {
		if (mode == 0) {
			if (spec == 0) {
				uButtons.get(0).setGraphic(whiteIVS);
				uButtons.get(1).setGraphic(yellowIV);
				uFace = "White";
			} else {
				uButtons.get(1).setGraphic(yellowIVS);
				uButtons.get(0).setGraphic(whiteIV);
				uFace = "Yellow";
			} 
			uButtons.get(2).setGraphic(redIV);
			uButtons.get(3).setGraphic(orangeIV);
			uButtons.get(4).setGraphic(greenIV);
			uButtons.get(5).setGraphic(blueIV);
			
			fButtons.get(0).setGraphic(whiteIVNS);
			fButtons.get(0).setDisable(true);
			fButtons.get(1).setGraphic(yellowIVNS);
			fButtons.get(1).setDisable(true);
			fButtons.get(2).setGraphic(redIV2);
			fButtons.get(2).setDisable(false);
			fButtons.get(3).setGraphic(orangeIV2);
			fButtons.get(3).setDisable(false);
			fButtons.get(4).setGraphic(greenIV2);
			fButtons.get(4).setDisable(false);
			fButtons.get(5).setGraphic(blueIV2);
			fButtons.get(5).setDisable(false);
			
		} else if (mode == 1) {
			if (spec == 0) {
				uButtons.get(2).setGraphic(redIVS);
				uButtons.get(3).setGraphic(orangeIV);
				uFace = "Orange";
			} else {
				uButtons.get(3).setGraphic(orangeIVS);
				uButtons.get(2).setGraphic(redIV);
				uFace = "Red";
			} 
			uButtons.get(0).setGraphic(whiteIV);
			uButtons.get(1).setGraphic(yellowIV);
			uButtons.get(4).setGraphic(greenIV);
			uButtons.get(5).setGraphic(blueIV);
			
			fButtons.get(0).setGraphic(whiteIV2);
			fButtons.get(0).setDisable(false);
			fButtons.get(1).setGraphic(yellowIV2);
			fButtons.get(1).setDisable(false);
			fButtons.get(2).setGraphic(redIVNS);
			fButtons.get(2).setDisable(true);
			fButtons.get(3).setGraphic(orangeIVNS);
			fButtons.get(3).setDisable(true);
			fButtons.get(4).setGraphic(greenIV2);
			fButtons.get(4).setDisable(false);
			fButtons.get(5).setGraphic(blueIV2);
			fButtons.get(5).setDisable(false);
			
		} else if (mode == 2) {
			if (spec == 0) {
				uButtons.get(4).setGraphic(greenIVS);
				uButtons.get(5).setGraphic(blueIV);
				uFace = "Green";
			} else {
				uButtons.get(5).setGraphic(blueIVS);
				uButtons.get(4).setGraphic(greenIV);
				uFace = "Blue";
			} 
			uButtons.get(0).setGraphic(whiteIV);
			uButtons.get(1).setGraphic(yellowIV);
			uButtons.get(2).setGraphic(redIV);
			uButtons.get(3).setGraphic(orangeIV);
			
			fButtons.get(0).setGraphic(whiteIV2);
			fButtons.get(0).setDisable(false);
			fButtons.get(1).setGraphic(yellowIV2);
			fButtons.get(1).setDisable(false);
			fButtons.get(2).setGraphic(redIV2);
			fButtons.get(2).setDisable(false);
			fButtons.get(3).setGraphic(orangeIV2);
			fButtons.get(3).setDisable(false);
			fButtons.get(4).setGraphic(greenIVNS);
			fButtons.get(4).setDisable(true);
			fButtons.get(5).setGraphic(blueIVNS);
			fButtons.get(5).setDisable(true);
		}
		
	}
    
    private Group cubieInit(Pane g, Object value) {
    	
    	System.out.println("Called with value "+value);
    	colourSet.clear();
    	cubieArray.clear();
    	
    	switch ((String) value) {
    		case "White-Green": Collections.addAll(colourSet, white,green,yellow,orange,red,blue); break;
    		case "White-Blue": Collections.addAll(colourSet, white,blue,yellow,red,orange,green); break;
    		case "White-Orange": Collections.addAll(colourSet, white,orange,yellow,blue,green,red); break;
    		case "White-Red": Collections.addAll(colourSet, white,red,yellow,green,blue,orange); break;
    		case "Yellow-Green": Collections.addAll(colourSet, yellow,green,white,red,orange,blue); break;
    		case "Yellow-Blue": Collections.addAll(colourSet, yellow,blue,white,orange,red,green); break;
    		case "Yellow-Orange": Collections.addAll(colourSet, yellow,orange,white,green,blue,red); break;
    		case "Yellow-Red": Collections.addAll(colourSet, yellow,red,white,blue,green,orange); break;
    		
    		case "Green-Orange": Collections.addAll(colourSet, green,orange,blue,white,yellow,red); break;
    		case "Orange-Blue": Collections.addAll(colourSet, orange,blue,red,white,yellow,green); break;
    		case "Blue-Red": Collections.addAll(colourSet, blue,red,green,white,yellow,orange); break;
    		case "Red-Green": Collections.addAll(colourSet, red,green,orange,white,yellow,blue); break;
    		case "Green-Red": Collections.addAll(colourSet, green,red,blue,yellow,white,orange); break;
    		case "Red-Blue": Collections.addAll(colourSet, red,blue,orange,yellow,white,green); break;
    		case "Blue-Orange": Collections.addAll(colourSet, blue,orange,green,yellow,white,red); break;
    		case "Orange-Green": Collections.addAll(colourSet, orange,green,red,yellow,white,blue); break;
    		
    		case "Red-Yellow": Collections.addAll(colourSet, red,yellow,orange,green,blue,white); break;
    		case "Orange-Yellow": Collections.addAll(colourSet, orange,yellow,red,blue,green,white); break;
    		case "Blue-Yellow": Collections.addAll(colourSet, blue,yellow,green,red,orange,white); break;
    		case "Green-Yellow": Collections.addAll(colourSet, green,yellow,blue,orange,red,white); break;
    		case "Red-White": Collections.addAll(colourSet, red,white,orange,blue,green,yellow); break;
    		case "Orange-White": Collections.addAll(colourSet, orange,white,red,green,blue,yellow); break;
    		case "Blue-White": Collections.addAll(colourSet, blue,white,green,orange,red,yellow); break;
    		case "Green-White": Collections.addAll(colourSet, green,white,blue,red,orange,yellow); break;
    	}

    	topColour = colourSet.get(0);
    	frontColour = colourSet.get(1);
    	bottomColour = colourSet.get(2);
    	leftColour = colourSet.get(3);
    	rightColour = colourSet.get(4);
    	backColour = colourSet.get(5);
    	
    	
    	for (int i = 0; i < 54; i++) {
    		if (i < 9) {
	    		ImageView iv = new ImageView(topColour);
	    		cubieArray.add(iv);
    		} else if (i < 18) {
    			ImageView iv = new ImageView(frontColour);
	    		cubieArray.add(iv);
    		} else if (i < 27) {
    			ImageView iv = new ImageView(bottomColour);
	    		cubieArray.add(iv);
    		} else if (i < 36) {
    			ImageView iv = new ImageView(leftColour);
	    		cubieArray.add(iv);
    		} else if (i < 45) {
    			ImageView iv = new ImageView(rightColour);
	    		cubieArray.add(iv);
    		} else {
    			ImageView iv = new ImageView(backColour);
	    		cubieArray.add(iv);
    		}
    	}
    	
    	
    	Double[] posX = new Double[] {160.0, 32.0, 288.0, 417.0};
    	Double[] posY = new Double[] {72.5, 80.5, 88.5};
    	
    	
    	// WHITE FACE POSITIONING
    	for (int x = 0; x < 9; x++) {
    		if (x == 0 || x == 3 || x == 6) {
	    		cubieArray.get(x).setLayoutX(posX[0]);
	    		cubieArray.get(x).setLayoutY(posY[0]+(40*(x/3)));
    		}
    		if (x == 1 || x == 4 || x == 7) {
    			cubieArray.get(x).setLayoutX(posX[0]+39.5);
	    		cubieArray.get(x).setLayoutY(posY[0]+(40*(x/3)));
    		}
    		if (x == 2 || x == 5 || x == 8) {
    			cubieArray.get(x).setLayoutX(posX[0]+79);
	    		cubieArray.get(x).setLayoutY(posY[0]+(40*(x/3)));
    		}
    		cubieArray.get(x).setScaleX(0.48);
        	cubieArray.get(x).setScaleY(0.48);
        	//g.getChildren().add(cubieArray.get(x));
    	}
    	
    	
    	//GREEN FACE POSITIONING
    	for (int x = 9; x < 18; x++) {
    		if (x == 9 || x == 12 || x == 15) {
	    		cubieArray.get(x).setLayoutX(posX[0]);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*(x/3)));
    		}
    		if (x == 10 || x == 13 || x == 16) {
    			cubieArray.get(x).setLayoutX(posX[0]+39.5);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*(x/3)));
    		}
    		if (x == 11 || x == 14 || x == 17) {
    			cubieArray.get(x).setLayoutX(posX[0]+79);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*(x/3)));
    		}
    		cubieArray.get(x).setScaleX(0.48);
        	cubieArray.get(x).setScaleY(0.48);
        	//g.getChildren().add(cubieArray.get(x));
    	}
    	
    	
    	//YELLOW FACE POSITIONING  	
    	for (int x = 18; x < 27; x++) {
    		if (x == 18 || x == 21 || x == 24) {
	    		cubieArray.get(x).setLayoutX(posX[0]);
	    		cubieArray.get(x).setLayoutY(posY[2]+(40*(x/3)));
    		}
    		if (x == 19 || x == 22 || x == 25) {
    			cubieArray.get(x).setLayoutX(posX[0]+39.5);
	    		cubieArray.get(x).setLayoutY(posY[2]+(40*(x/3)));
    		}
    		if (x == 20 || x == 23 || x == 26) {
    			cubieArray.get(x).setLayoutX(posX[0]+79);
	    		cubieArray.get(x).setLayoutY(posY[2]+(40*(x/3)));
    		}
    		cubieArray.get(x).setScaleX(0.48);
        	cubieArray.get(x).setScaleY(0.48);
        	//g.getChildren().add(cubieArray.get(x));
    	}
    	
    	//ORANGE FACE POSITIONING    	
    	for (int x = 27; x < 36; x++) {
    		if (x == 27 || x == 30 || x == 33) {
	    		cubieArray.get(x).setLayoutX(posX[1]);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*((x-18)/3)));
    		}
    		if (x == 28 || x == 31 || x == 34) {
    			cubieArray.get(x).setLayoutX(posX[1]+39.5);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*((x-18)/3)));
    		}
    		if (x == 29 || x == 32 || x == 35) {
    			cubieArray.get(x).setLayoutX(posX[1]+79);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*((x-18)/3)));
    		}
    		cubieArray.get(x).setScaleX(0.48);
        	cubieArray.get(x).setScaleY(0.48);
        	//g.getChildren().add(cubieArray.get(x));
    	}
    	
    	for (int x = 36; x < 45; x++) {
    		if (x == 36 || x == 39 || x == 42) {
	    		cubieArray.get(x).setLayoutX(posX[2]);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*((x-27)/3)));
    		}
    		if (x == 37 || x == 40 || x == 43) {
    			cubieArray.get(x).setLayoutX(posX[2]+39.5);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*((x-27)/3)));
    		}
    		if (x == 38 || x == 41 || x == 44) {
    			cubieArray.get(x).setLayoutX(posX[2]+79);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*((x-27)/3)));
    		}
    		cubieArray.get(x).setScaleX(0.48);
        	cubieArray.get(x).setScaleY(0.48);
        	//g.getChildren().add(cubieArray.get(x));
    	}
    	
    	for (int x = 45; x < 54; x++) {
    		if (x == 45 || x == 48 || x == 51) {
	    		cubieArray.get(x).setLayoutX(posX[3]);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*((x-36)/3)));
    		}
    		if (x == 46 || x == 49 || x == 52) {
    			cubieArray.get(x).setLayoutX(posX[3]+39.5);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*((x-36)/3)));
    		}
    		if (x == 47 || x == 50 || x == 53) {
    			cubieArray.get(x).setLayoutX(posX[3]+79);
	    		cubieArray.get(x).setLayoutY(posY[1]+(40*((x-36)/3)));
    		}
    		cubieArray.get(x).setScaleX(0.48);
        	cubieArray.get(x).setScaleY(0.48);
        	//g.getChildren().add(cubieArray.get(x));
    	}
    	
    	Group cubieGee = new Group();
    	
    	for (int x = 0; x < 54; x++) {
    		cubieGee.getChildren().add(cubieArray.get(x));
    	}
    	
    	
    	//g.getChildren().add(cubieGee);
    	
    	return cubieGee;
    	

    }
    
    private ArrayList<Button> orientSelect(Pane p, int mode) {
	  
	  ImageView whiteIVcur,yellowIVcur,redIVcur,greenIVcur,orangeIVcur,blueIVcur;
	  
	  if (mode == 0) {
		  whiteIVcur = whiteIV;
		  yellowIVcur = yellowIV;
		  redIVcur = redIV;
	      greenIVcur = greenIV;
		  orangeIVcur = orangeIV;
		  blueIVcur = blueIV;
	  } else {
		  whiteIVcur = whiteIVNS;
		  yellowIVcur = yellowIVNS;
		  redIVcur = redIVNS;
	      greenIVcur = greenIVNS;
		  orangeIVcur = orangeIVNS;
		  blueIVcur = blueIVNS;
	  }
	  
	  Group g = new Group();
	  
	  int initX = 355, initY = 0;
	  
	  if (mode == 0) {
		  initY = 410;
	  } else {
		  initY = 442;
	  }

	  Button whiteB = orientButtonSetup(new Button(), whiteIVcur, 0, initX, initY);
	  Button yellowB = orientButtonSetup(new Button(), yellowIVcur, 1, initX, initY);
	  Button redB = orientButtonSetup(new Button(), redIVcur, 2, initX, initY);
	  Button orangeB = orientButtonSetup(new Button(), orangeIVcur, 3, initX, initY);
	  Button greenB = orientButtonSetup(new Button(), greenIVcur, 4, initX, initY);
	  Button blueB = orientButtonSetup(new Button(), blueIVcur, 5, initX, initY);
  
	  
	  ArrayList<Button> butList = new ArrayList<Button>();
	  butList.add(whiteB);
	  butList.add(yellowB);
	  butList.add(redB);
	  butList.add(orangeB);
	  butList.add(greenB);
	  butList.add(blueB);
	  
	  return butList;
	  
  }
  
    private Button orientButtonSetup(Button colourB, ImageView colourIV, int pos, int x, int y) {
	  colourB.setMaxWidth(25);
	  colourB.setMaxHeight(25);
	  colourB.setMinHeight(25);
	  colourB.setMinWidth(25);
	  colourIV.setFitHeight(26);
	  colourIV.setFitWidth(26);
	  colourB.setGraphic(colourIV);
	  
	  colourB.setLayoutX(x+(pos*29.2));
	  colourB.setLayoutY(y);
	  
	  return colourB;
  }
    
    public Group getCubieG() {
    	return cubieG;
    }
    
    public ArrayList<ImageView> getCubieArray() {
    	return cubieArray;
    }
}
