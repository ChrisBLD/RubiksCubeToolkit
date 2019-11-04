package averagebyalgs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class Main extends Application {
	
	static Image white = new Image("resources/CubeWHITE.png");
	static Image yellow = new Image("resources/CubeYELLOW.png");
	static Image red = new Image("resources/CubeRED.png");
	static Image green = new Image("resources/CubeGREEN.png");
	static Image orange = new Image("resources/CubeORANGE.png");
	static Image blue = new Image("resources/CubeBLUE.png");
	
	public static Image topColour;
	public static Image frontColour;
	public static Image leftColour;
	public static Image rightColour;
	public static Image backColour;
	public static Image bottomColour;
	
	public static String edgeBuf;
	public static String cornerBuf;

	
	/*private ImageView whiteIV = new ImageView(white);
	private ImageView yellowIV = new ImageView(yellow);
	private ImageView redIV = new ImageView(red);
	private ImageView greenIV = new ImageView(green);
	private ImageView orangeIV = new ImageView(orange);
	private ImageView blueIV = new ImageView(blue);*/
	
	public ArrayList<Image> colours = new ArrayList<Image>();
	
	public ArrayList<ImageView> cubieArray = new ArrayList<ImageView>();
	
	public ArrayList<Image> colourSet = new ArrayList<Image>();

	
	private Group cubieG;
	
	public static boolean paritySwap = false;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AverageByAlgs - BLD Tool");
        
        primaryStage.show();
        Group g = new Group();

        Scene scene = new Scene(g, 750, 750, Color.valueOf("#424242"));
        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);
        
        Text scenetitle = new Text("AverageByAlgs - Target/Alg Counter");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setLayoutX(20);
        scenetitle.setLayoutY(100);
        g.getChildren().add(scenetitle);

        Label scramble = new Label("Enter Scramble:");
        scramble.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scramble.setLayoutX(20);
        scramble.setLayoutY(120);
        g.getChildren().add(scramble);

        TextField userTextField = new TextField();
        userTextField.setLayoutX(180);
        userTextField.setLayoutY(120);
        userTextField.setMinWidth(500);
        g.getChildren().add(userTextField);


        Image image = new Image("resources/CubeNet.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setScaleX(0.5);
        iv1.setScaleY(0.5);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setLayoutX(-320);
        iv1.setLayoutY(-50);
        
        colours.add(white);
        colours.add(green);
        colours.add(yellow);
        colours.add(orange);
        colours.add(red);
        colours.add(blue);
        
    	ObservableList<String> orientationList = FXCollections.observableArrayList("White-Green","White-Blue","White-Orange","White-Red",
 			   "Yellow-Green","Yellow-Blue","Yellow-Orange","Yellow-Red",
 			   "Green-Orange","Orange-Blue","Blue-Red","Red-Green",
 			   "Green-Red","Red-Blue","Blue-Orange","Orange-Green",
 			   "Red-Yellow","Orange-Yellow","Blue-Yellow","Green-Yellow",
 			   "Red-White","Orange-White","Blue-White","Green-White");
    	
    	ObservableList<String> edgeBufList = FXCollections.observableArrayList("UF","DF");
    	ObservableList<String> cornerBufList = FXCollections.observableArrayList("UBL","UFR");
        
    	
        Label orientation = new Label("Orientation (Top-Front): ");
        Label edgeBuffer = new Label("Edge Buffer: ");
        Label cornerBuffer = new Label("Corner Buffer ");
    	
        ComboBox orientationBox = new ComboBox(orientationList);
        
        orientation.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        orientation.setLayoutX(20);
        orientation.setLayoutY(180);
        g.getChildren().add(orientation);
        orientationBox.setLayoutX(250);
        orientationBox.setLayoutY(180);
        orientationBox.setValue("White-Green");
        g.getChildren().add(orientationBox);
        
        
        edgeBuffer.setLayoutX(250);
        edgeBuffer.setLayoutY(220);
        edgeBuffer.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        g.getChildren().add(edgeBuffer);
        
        ComboBox edgeBufChoose = new ComboBox(edgeBufList);
        edgeBufChoose.setLayoutX(380);
        edgeBufChoose.setLayoutY(220);
        edgeBufChoose.setValue("UF");
        g.getChildren().add(edgeBufChoose);
        
        cornerBuffer.setLayoutX(250);
        cornerBuffer.setLayoutY(260);
        cornerBuffer.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        g.getChildren().add(cornerBuffer);
        
        ComboBox cornerBufChoose = new ComboBox(cornerBufList);
        cornerBufChoose.setLayoutX(380);
        cornerBufChoose.setLayoutY(260);
        cornerBufChoose.setValue("UBL");
        g.getChildren().add(cornerBufChoose);        
        
        Button enterOrientation = new Button("Select");
        enterOrientation.setLayoutX(400);
        enterOrientation.setLayoutY(180);
        g.getChildren().add(enterOrientation);
        
        System.out.println(orientationBox.getValue());
        enterOrientation.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		g.getChildren().remove(cubieG);
        		g.getChildren().remove(iv1);
        		cubieG = cubieInit(g, orientationBox.getValue());
        		g.getChildren().add(cubieG);
                g.getChildren().add(iv1);

        	}
        });
        
        Label notation = new Label("");
        notation.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        notation.setLayoutX(20);
        notation.setLayoutY(250);
        g.getChildren().add(notation);
        
        Label algCount = new Label("");
        algCount.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        algCount.setLayoutX(20);
        algCount.setLayoutY(290);
        g.getChildren().add(algCount);
        
        
        System.out.println("CUBIEARRAY SIZE: "+cubieArray.size());
        
        cubieG = cubieInit(g,"White-Green");
        g.getChildren().add(cubieG);
        g.getChildren().add(iv1);
        
        System.out.println("CUBIEARRAY SIZE: "+cubieArray.size());
        System.out.println("CUBIEARRAY ELEMENT 1 IMG: "+cubieArray.get(0).getImage());
        
        Label parAvd = new Label("Parity Swap:");
        parAvd.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        parAvd.setLayoutX(450);
        parAvd.setLayoutY(180);
        g.getChildren().add(parAvd);
        CheckBox ubulSwap = new CheckBox();
        ubulSwap.setLayoutX(640);
        ubulSwap.setLayoutY(183);
        g.getChildren().add(ubulSwap);
        
        Label quadFlip = new Label("Quad Flip:");
        quadFlip.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        quadFlip.setLayoutX(450);
        quadFlip.setLayoutY(220);
        g.getChildren().add(quadFlip);
        CheckBox quadBox = new CheckBox();
        quadBox.setLayoutX(640);
        quadBox.setLayoutY(223);
        g.getChildren().add(quadBox);
        
        Label twoInOne = new Label("Adj. Double Twists:");
        twoInOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        twoInOne.setLayoutX(450);
        twoInOne.setLayoutY(260);
        g.getChildren().add(twoInOne);
        CheckBox dtBox = new CheckBox();
        dtBox.setLayoutX(640);
        dtBox.setLayoutY(263);
        g.getChildren().add(dtBox);
        
        /*Label threeInOne = new Label("Triple Twists (B):");
        threeInOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        threeInOne.setLayoutX(450);
        threeInOne.setLayoutY(300);
        g.getChildren().add(threeInOne);
        CheckBox ttBox = new CheckBox();
        ttBox.setLayoutX(640);
        ttBox.setLayoutY(303);
        g.getChildren().add(ttBox);*/  //Discontinued for now.
        
        Label oppositeTwists = new Label("Opp. Double Twists:");
        oppositeTwists.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        oppositeTwists.setLayoutX(450);
        oppositeTwists.setLayoutY(300);
        g.getChildren().add(oppositeTwists);
        CheckBox otBox = new CheckBox();
        otBox.setLayoutX(640);
        otBox.setLayoutY(303);
        g.getChildren().add(otBox);
        
        Label paHelp = initHelp("Parity Swap \n\nSelect this if you memorise your edges such that "
        						+ "after solving them, the two edges adjacent to your corner buffer are swapped.", 670, 180, "resources/cubenet-ps.png");
        Label qdHelp = initHelp("Quad Flip \n\nSelect this if, when there are 4 flipped edges in a solve, you solve them "
        						+ "all with a single algorithm.", 670, 220, "resources/cubenet-qd.png");
        Label dtHelp = initHelp("Adjacent Double Twists \n\nThis refers to how you solve a case where you have two non-buffer adjacent corners "
        		+ "twisted in opposite directions. If you twist each corner individually, leave this unchecked. If you do a single algorithm "
        		+ "to solve both at the same time, check this box.", 670, 260, "resources/cubenet-dt.png");
        //Label ttHelp = initHelp("Triple Twists (Buffer)\n\nSelect this if, when you have three twisted corners *where one of them "
        //						+ "is your corner buffer*, you solve them with a single algorithm.", 670, 300, "resources/cubenet-tt.png");
        Label otHelp = initHelp("Opposite Double Twists \n\nThis refers to how you solve a case where you have two non-buffer opposite corners " 
        		        		+"twisted in opposite directions. If you twist each corner individually, leave this unchecked. If you do a single algorithm "
        		        		+"to solve both at the same time, check this box.", 670, 300, "resources/cubenet-ot.png");
        
        
        g.getChildren().add(paHelp);
        g.getChildren().add(qdHelp);
        g.getChildren().add(dtHelp);
        //g.getChildren().add(ttHelp);
        g.getChildren().add(otHelp);
        
        
        ScrambleManager sm = new ScrambleManager(g, cubieArray, cubieG);
        
        Button enter = new Button("Submit");
        enter.setOnAction(value -> {
        	process(userTextField, edgeBufChoose, cornerBufChoose, notation, algCount, ubulSwap, quadBox, dtBox, otBox,
					sm);

        });
        enter.setLayoutX(690);
        enter.setLayoutY(120);
        g.getChildren().add(enter);
        //final Text actiontarget = new Text();
        //g.getChildren().add(actiontarget);
        

        
        
        

    }

	private void process(TextField userTextField, ComboBox edgeBufChoose, ComboBox cornerBufChoose, Label notation,
			Label algCount, CheckBox ubulSwap, CheckBox quadBox, CheckBox dtBox, CheckBox otBox, ScrambleManager sm) {
		edgeBuf = (String) edgeBufChoose.getValue();
		cornerBuf = (String) cornerBufChoose.getValue();
		if (ubulSwap.isSelected()) {
			paritySwap = true;
		} else {
			paritySwap = false;
		}
		
		
		
		System.out.println("TEST CUBIE ARRAY: "+cubieArray.get(0).getImage());
		cubieArray = sm.submitted("D B U R2 U' B2 R' U2 B2 U D2 L2 D' R2 L2 U R2 B2 D' F'");
		System.out.println("TEST CUBIE ARRAY 2: "+cubieArray.get(0).getImage());
		
		cubieArray = sm.submitted(userTextField.getText()); 
		System.out.println("TEST: "+cubieArray.get(0).getImage());
		TargetCounter tm = new TargetCounter(sm, cubieArray);
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
		System.out.println("FORMAL NOTATION: "+targets);
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
		
		if (quadBox.isSelected()) {
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
		
		int twistAlgs = tm.twistCalculator(dtBox, otBox, cornerBufChoose);
		System.out.println(twistAlgs);

		totalAlgs += flipAlgs + twistAlgs;
		notation.setText("This scramble is: "+targets);
		algCount.setText("Alg Count: "+(totalAlgs));
	}
    
    private Group cubieInit(Group g, Object value) {
    	
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
    	
    	
    	Double[] posX = new Double[] {140.0, 12.0, 268.0, 397.0};
    	Double[] posY = new Double[] {342.5, 350.5, 358.5};
    	
    	
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
    
    Label initHelp(String message, int x, int y, String resource) {
    	Label l = new Label("");
        l.setGraphic(new ImageView(new Image("resources/HelpIconSmall.png")));
        Tooltip ttps = new Tooltip();
        ttps.setShowDelay(Duration.seconds(0.1));
        ttps.setPrefSize(400,100);
        ttps.setWrapText(true);
        ttps.setText(message);
        ttps.setStyle("-fx-font-size: 12");
        ttps.setGraphic(new ImageView(new Image(resource)));
        l.setTooltip(ttps);
        l.setLayoutX(x);
        l.setLayoutY(y);
        
        return l;
    }
    

} 