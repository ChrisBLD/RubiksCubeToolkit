package tutorial;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import main.Home;

public class UserInterface extends Application {
	
	/*
	 * User Interface would ideally look something like this:
	 * Solved cube is shown to the user by default. User is first asked if they know the notation of a Rubik's Cube
	 * If they do, then they can skip this section. Otherwise, a short animation can be shown displaying each move that can be made on the cube
	 * Perhaps some buttons could appear to let them try the moves themselves?
	 * Once they're happy, they can proceed.
	 * 
	 * I might need some form of colour scheme selection? At the moment, the colours for faces are hard coded but are irrelevant to the actual functionality
	 * So implementing a way for a user to enter their own colour scheme isn't out of the question. Probably just going to assume they use standard for now though.
	 * 
	 * The user is asked if their physical cube is scrambled or solved. We're assuming most people will have a scrambled puzzle - if it's solved, they can
	 * be instructed to scramble it and proceed entering its state. This will *probably* be easier than getting them to execute a scramble in notation.
	 * I will most likely have to allow the user multiple attempts at this.
	 * 
	 * From here on we're assuming that the scramble input onto the cube on the screen matches the cube in their hands.
	 * Here, we can actually compute the solution for the puzzle. It will likely follow these steps:
	 * 
	 * 1) Solve cross by means of placing edge above edge location and either inserting with a half turn or some sledge variant.
	 * 2) Solve corners of the first layer by placing them above their location and using RDRD style trigger.
	 * 3) Solve edges of the second layer by placing them above their location and using U R U R' U' F' U' F and mirror.
	 * 4) I can either teach them rudimentary OLL+PLL or go for a full beginner solution by solving edges and corners separately. I haven't decided yet.
	 * 
	 * I would like to allow users to move back and forward between steps as much as they like. However, implementing Get-Out-Of-Jail-Free mechanisms to resolve their
	 * probable fuck ups will be far too unpredictable to actually implement. As such, the user will probably have to just start again if they do something wrong.
	 * So I have to make my tutorial simple enough to understand so that fuck ups are as infrequent as possible. Having to restart often will probably put a lot
	 * of people off.
	 * 
	 * For the cross I'd like to demonstrate what the end result should look like. Perhaps I can use the derived cross solution to demonstrate what the final result
	 * should be of a solved cross, and then go through it piece by piece by going back to the start in a manner the user can follow along. I'll probably also include
	 * some form of progress tracker for the current step (applies to all stages of the solve)
	 * 
	 * For the corners of the first layer, I can basically do a similar thing - show the user the end result (a solved first layer), then allow them to see how to solve
	 * each piece one at a time. 
	 * 
	 * Writing the above has made me realise that we might have edge cases where the user gets a scramble that doesn't actually show the user some states. For example,
	 * for corners of the first layer, it could happen that each corner on the scramble is on the top layer when it needs to be. This might confuse the user as to how to
	 * get corners out of the slots if they're inserted either in wrong slots or twisted in place.
	 * 
	 * For second layer edges, it would be a similar thing. I imagine the process for developing the first three steps is going to be very similar and present
	 * similar problems, so by the time I come to implement this it should be pretty efficient.
	 * 
	 *  
	 * 
	 * 
	 */
	
	
	public static Scene scene;
	
    public static final int RED     = 0;
    public static final int GREEN   = 1;
    public static final int BLUE    = 2;
    public static final int YELLOW  = 3;
    public static final int ORANGE  = 4;
    public static final int WHITE   = 5;
    public static final int GRAY    = 6;
    
    public static final float X_RED     = 0.5f / 7f;
    public static final float X_GREEN   = 1.5f / 7f;
    public static final float X_BLUE    = 2.5f / 7f;
    public static final float X_YELLOW  = 3.5f / 7f;
    public static final float X_ORANGE  = 4.5f / 7f;
    public static final float X_WHITE   = 5.5f / 7f;
    public static final float X_GRAY    = 6.5f / 7f;
    
    private static final int F_FACE = 0;
    private static final int R_FACE = 1;
    private static final int U_FACE = 2;
    private static final int B_FACE = 3;
    private static final int L_FACE = 4;
    private static final int D_FACE = 5;
	
	//These definitions go "layer by layer", defining the colours for each side of a single piece. 
    //I'm going to have to add a listener of some sort to each of these points so I can track the colour currently at that location.

                                              // F   R   U   B   L   D
    static int[] FLD  = new int[]{GREEN, GRAY, GRAY, GRAY, ORANGE, YELLOW}; 
    static int[] FD   = new int[]{GREEN, GRAY, GRAY, GRAY, GRAY, YELLOW};
    static int[] FRD  = new int[]{GREEN, RED, GRAY, GRAY, GRAY, YELLOW};
    static int[] FL   = new int[]{GREEN, GRAY, GRAY, GRAY, ORANGE, GRAY};
    static int[] F    = new int[]{GREEN, GRAY, GRAY, GRAY, GRAY, GRAY}; //F face centre piece.
    static int[] FR   = new int[]{GREEN, RED, GRAY, GRAY, GRAY, GRAY};
    static int[] FLU  = new int[]{GREEN, GRAY, WHITE, GRAY, ORANGE, GRAY};
    static int[] FU   = new int[]{GREEN, GRAY, WHITE, GRAY, GRAY, GRAY};
    static int[] FRU  = new int[]{GREEN, RED, WHITE, GRAY, GRAY, GRAY};
    
    
    
    private static Point3D pFLD   = new Point3D(-1.04,  1.04, -1.04);
    private static Point3D pFD    = new Point3D(   0,  1.04, -1.04);
    private static Point3D pFRD   = new Point3D( 1.04,  1.04, -1.04);
    private static Point3D pFL    = new Point3D(-1.04,    0, -1.04);
    private static Point3D pF     = new Point3D(   0,    0, -1.04);
    private static Point3D pFR    = new Point3D( 1.04,    0, -1.04);
    private static Point3D pFLU   = new Point3D(-1.04, -1.04, -1.04);
    private static Point3D pFU    = new Point3D(   0, -1.04, -1.04);
    private static Point3D pFRU   = new Point3D( 1.04, -1.04, -1.04);
    
    static int[] CLD  = new int[]{GRAY, GRAY, GRAY, GRAY, ORANGE, YELLOW};
    static int[] CD   = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, YELLOW}; //D face centre piece
    static int[] CRD  = new int[]{GRAY, RED, GRAY, GRAY, GRAY, YELLOW};
    static int[] CL   = new int[]{GRAY, GRAY, GRAY, GRAY, ORANGE, GRAY}; //L face centre piece
    static int[] C    = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, GRAY}; //Invisible core
    static int[] CR   = new int[]{GRAY, RED, GRAY, GRAY, GRAY, GRAY}; //R face centre piece
    static int[] CLU  = new int[]{GRAY, GRAY, WHITE, GRAY, ORANGE, GRAY};
    static int[] CU   = new int[]{GRAY, GRAY, WHITE, GRAY, GRAY, GRAY}; //U face centre piece
    static int[] CRU  = new int[]{GRAY, RED, WHITE, GRAY, GRAY, GRAY};
    

    private static Point3D pCLD   = new Point3D(-1.04,  1.04, 0);
    private static Point3D pCD    = new Point3D(   0,  1.04, 0);
    private static Point3D pCRD   = new Point3D( 1.04,  1.04, 0);
    private static Point3D pCL    = new Point3D(-1.04,    0, 0);
    private static Point3D pC     = new Point3D(   0,    0, 0);
    private static Point3D pCR    = new Point3D( 1.04,    0, 0);
    private static Point3D pCLU   = new Point3D(-1.04, -1.04, 0);
    private static Point3D pCU    = new Point3D(   0, -1.04, 0);
    private static Point3D pCRU   = new Point3D( 1.04, -1.04, 0);
    
    static int[] BLD  = new int[]{GRAY, GRAY, GRAY, BLUE, ORANGE, YELLOW};
    static int[] BD   = new int[]{GRAY, GRAY, GRAY, BLUE, GRAY, YELLOW};
    static int[] BRD  = new int[]{GRAY, RED, GRAY, BLUE, GRAY, YELLOW};
    static int[] BL   = new int[]{GRAY, GRAY, GRAY, BLUE, ORANGE, GRAY};
    static int[] B    = new int[]{GRAY, GRAY, GRAY, BLUE, GRAY, GRAY}; //B face centre piece
    static int[] BR   = new int[]{GRAY, RED, GRAY, BLUE, GRAY, GRAY};
    static int[] BLU  = new int[]{GRAY, GRAY, WHITE, BLUE, ORANGE, GRAY};
    static int[] BU   = new int[]{GRAY, GRAY, WHITE, BLUE, GRAY, GRAY};
    static int[] BRU  = new int[]{GRAY, RED, WHITE, BLUE, GRAY, GRAY};
    
    
    private static Point3D pBLD   = new Point3D(-1.04,  1.04, 1.04);
    private static Point3D pBD    = new Point3D(   0,  1.04, 1.04);
    private static Point3D pBRD   = new Point3D( 1.04,  1.04, 1.04);
    private static Point3D pBL    = new Point3D(-1.04,    0, 1.04);
    private static Point3D pB     = new Point3D(   0,    0, 1.04);
    private static Point3D pBR    = new Point3D( 1.04,    0, 1.04);
    private static Point3D pBLU   = new Point3D(-1.04, -1.04, 1.04);
    private static Point3D pBU    = new Point3D(   0, -1.04, 1.04);
    private static Point3D pBRU   = new Point3D( 1.04, -1.04, 1.04);
    
    
    private static List<int[]> patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
    
    private static List<Point3D> middleSlicePoints = Arrays.asList(
    		pFD, pF, pFU, pCD, pC, pCU, pBD, pB, pBU);
    
    private static List<Point3D> standingSlicePoints = Arrays.asList(
    		pCLD, pCD, pCRD, pCL, pC, pCR, pCLU, pCU, pCRU);
    
    private static List<Point3D> equatorSlicePoints = Arrays.asList(
    		pFL, pF, pFR, pCL, pC, pCR, pBL, pB, pBR);
    
    private static List<Point3D> rightFacePoints = Arrays.asList(
    		pFRD, pFR, pFRU, pCRD, pCR, pCRU, pBRD, pBR, pBRU);
    
    private static List<Point3D> rightWideFacePoints = Arrays.asList(
    		pFD, pFRD, pF, pFR, pFU, pFRU,
    		pCD, pCRD, pC, pCR, pCU, pCRU,
    		pBD, pBRD, pB, pBR, pBU, pBRU);
    
    private static List<Point3D> upFacePoints = Arrays.asList(
    		pFLU, pFU, pFRU, pCLU, pCU, pCRU, pBLU, pBU, pBRU);
    
    private static List<Point3D> upWideFacePoints = Arrays.asList(
    		pFL, pF, pFR, pFLU, pFU, pFRU,
    		pCL, pC, pCR, pCLU, pCU, pCRU,
    		pBL, pB, pBR, pBLU, pBU, pBRU);
    
    private static List<Point3D> frontFacePoints = Arrays.asList(
    		pFLD, pFD, pFRD, pFL, pF, pFR, pFLU, pFU, pFRU);
    
    private static List<Point3D> frontWideFacePoints = Arrays.asList(
    		pFLD, pFD, pFRD, pFL, pF, pFR, pFLU, pFU, pFRU,
    		pCLD, pCD, pCRD, pCL, pC, pCR, pCLU, pCU, pCRU);
    
    private static List<Point3D> downFacePoints = Arrays.asList(
    		pFLD, pFD, pFRD, pCLD, pCD, pCRD, pBLD, pBD, pBRD);
    
    private static List<Point3D> downWideFacePoints = Arrays.asList(
    		pFLD, pFD, pFRD, pFL, pF, pFR,
    		pCLD, pCD, pCRD, pCL, pC, pCR,
    		pBLD, pBD, pBRD, pBL, pB, pBR);
    
    private static List<Point3D> leftFacePoints = Arrays.asList(
    		pFLD, pFL, pFLU, pCLD, pCL, pCLU, pBLD, pBL, pBLU);
    
    private static List<Point3D> leftWideFacePoints = Arrays.asList(
    		pFLD, pFD, pFL, pF, pFLU, pFU,
    		pCLD, pCD, pCL, pC, pCLU, pCU,
    		pBLD, pBD, pBL, pB, pBLU, pBU);
    
    private static List<Point3D> backFacePoints = Arrays.asList(
    		pBLD, pBD, pBRD, pBL, pB, pBR, pBLU, pBU, pBRU);
    
    private static List<Point3D> backWideFacePoints = Arrays.asList(
    		pCLD, pCD, pCRD, pCL, pC, pCR, pCLU, pCU, pCRU,
    		pBLD, pBD, pBRD, pBL, pB, pBR, pBLU, pBU, pBRU);
    
    private static final List<Point3D> pointsFaceF = Arrays.asList(
            pFLD, pFD, pFRD, pFL, pF, pFR, pFLU, pFU, pFRU,
            pCLD, pCD, pCRD, pCL, pC, pCR, pCLU, pCU, pCRU,
            pBLD, pBD, pBRD, pBL, pB, pBR, pBLU, pBU, pBRU);
    
	static int[] solvedFLD;
	static int[] solvedFD;
	static int[] solvedFRD;
	static int[] solvedFL;
	static int[] solvedF;
	static int[] solvedFR;
	static int[] solvedFLU;
	static int[] solvedFU;
	static int[] solvedFRU;
	static int[] solvedCLD, solvedCD, solvedCRD, solvedCL, solvedC, solvedCR, solvedCLU, solvedCU, solvedCRU;
	static int[] solvedBLD, solvedBD, solvedBRD, solvedBL, solvedB, solvedBR, solvedBLU, solvedBU, solvedBRU;
		
    private static Group sceneRoot = new Group();
    private static Group meshGroup = new Group();
    
    private static final double MINIMUM = -10;
    private static final double MAXIMUM = -20;
    private static final double DEFAULT = -15;
    private static double current = DEFAULT;
    
    static int bodyCount = 0;
    static boolean forwardOrBack = true;
    static Timeline timeline, timeline2;
    
    NotationTutorial data = new NotationTutorial(false);
	static String[][][] changes = 
    		{{{"Uw"},{"U","U"},{"D","D"},{"F","R"},{"R","B"},{"B","L"},{"L","F"}},
			{{"Uw'"},{"U","U"},{"D","D"},{"F","L"},{"R","F"},{"B","R"},{"L","B"}},
			{{"Uw2"},{"U","U"},{"D","D"},{"F","B"},{"R","L"},{"B","F"},{"L","R"}},

			{{"Fw"},{"U","L"},{"D","R"},{"F","F"},{"R","U"},{"B","B"},{"L","D"}},
			{{"Fw'"},{"U","R"},{"D","L"},{"F","F"},{"R","D"},{"B","B"},{"L","U"}},
			{{"Fw2"},{"U","D"},{"D","U"},{"F","F"},{"R","L"},{"B","B"},{"L","R"}},

			{{"Rw"},{"U","F"},{"D","B"},{"F","D"},{"R","B"},{"B","U"},{"L","F"}},
			{{"Rw'"},{"U","B"},{"D","F"},{"F","U"},{"R","B"},{"B","D"},{"L","F"}},
			{{"Rw2"},{"U","D"},{"D","U"},{"F","B"},{"R","R"},{"B","F"},{"L","L"}}};
    
    
    private static PhongMaterial mat = new PhongMaterial();
    
    @Override
    public void start(Stage primaryStage) {
    	System.out.println("test");
    	sceneRoot = new Group();
        SubScene subScene = new SubScene(sceneRoot, 500, 500, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.rgb(51,51,51));
        Translate pivot = new Translate();
        Rotate ytate = new Rotate(0, Rotate.Y_AXIS);
        data.camera = new PerspectiveCamera(true);
        data.camera.setNearClip(0.1);
        data.camera.setFarClip(10000.0);
        //camera.setTranslateZ(-15);
        data.camera.getTransforms().addAll (
                pivot,
                ytate,
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, DEFAULT)
        );
        
        timeline2 = new Timeline();
        timeline2.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0), 
                new KeyValue(ytate.angleProperty(), -395)
        ));
        timeline2.getKeyFrames().add(new KeyFrame(
                Duration.seconds(3), 
                new KeyValue(ytate.angleProperty(), -755)
        ));
        
        timeline2.setCycleCount(1);
        
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0), 
                new KeyValue(ytate.angleProperty(), 0)
        ));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(3), 
                new KeyValue(ytate.angleProperty(), -395)
        ));
        
        timeline.setCycleCount(1);
        timeline.play();
        subScene.setCamera(data.camera);

        mat.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/tutcolours.png")));

        buildMesh(sceneRoot, mat, meshGroup);

        BorderPane pane = new BorderPane();
        pane.setCenter(subScene);
        
        Button back = new Button();
        back.setGraphic(new ImageView(new Image("/resources/backButton.png")));
        back.setMinSize(90,67); back.setMaxSize(90,67);
        Button forward = new Button();
        forward.setGraphic(new ImageView(new Image("/resources/nextButton.png")));
        forward.setMinSize(90,67); forward.setMaxSize(90,67);
        Separator s1 = new Separator();
        s1.setOrientation(Orientation.VERTICAL);
        s1.setVisible(false);
        Separator s2 = new Separator();
        s2.setOrientation(Orientation.VERTICAL);
        s2.setVisible(false);
        Button restartSection = new Button();
        restartSection.setGraphic(new ImageView(new Image("/resources/sectionStartButton.png")));
        restartSection.setMinSize(111,67); restartSection.setMaxSize(111,67);
        restartSection.setDisable(true);
        Button skipToDemo = new Button();
        skipToDemo.setGraphic(new ImageView(new Image("/resources/skipToDemoButton.png")));
        skipToDemo.setMinSize(111,67); skipToDemo.setMaxSize(111,67);
        skipToDemo.setDisable(true);
        
        HBox h = new HBox(10);
        h.getChildren().addAll(s1, back, forward, s2, restartSection, skipToDemo);

        ToolBar toolBar = new ToolBar(h);
        toolBar.setOrientation(Orientation.HORIZONTAL);
        toolBar.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setBottom(toolBar);
        
        
        Label stepLabel = new Label("NAME OF STEP");
        stepLabel.setTextFill(Color.rgb(213,225,227));
        stepLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 40));
        
        Label description = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam arcu lorem, tempus id dui eu, pharetra facilisis dolor. "
        		+ "Integer id odio eu augue tincidunt pharetra. Donec augue leo, euismod ut rutrum a, tincidunt ac quam. Morbi odio odio, fermentum eu purus id, "
        		+ "consequat semper sapien. Phasellus et urna metus.");
        description.setTextFill(Color.rgb(213,225,227));
        description.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 23));
        description.setWrapText(true);
        description.setMaxWidth(500);
        
        SharedToolbox st = new SharedToolbox();
        st.createInfoLabel();
        
        Label moves = new Label("R U R' U'");
        moves.setTextFill(Color.rgb(213,225,227));
        moves.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 60));
        moves.setWrapText(true);
        moves.setMaxWidth(500);
        
        Label bottom = new Label("");
        bottom.setTextFill(Color.WHITE);
        bottom.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 23));
        bottom.setWrapText(true);
        bottom.setMaxWidth(500);
        
        HBox h1 = new HBox(); h1.getChildren().add(stepLabel); h1.setAlignment(Pos.CENTER);
        h1.setPadding(new Insets(10,0,10,0));
        HBox h2 = new HBox(); h2.getChildren().add(description);h2.setAlignment(Pos.CENTER);
        h2.setPadding(new Insets(10,10,10,10));
        HBox h3 = new HBox(); h3.getChildren().addAll(moves); h3.setAlignment(Pos.CENTER);
        h3.setPadding(new Insets(10,10,10,10));
        HBox h4 = new HBox(); h4.getChildren().add(bottom); h4.setAlignment(Pos.CENTER);
        

        ToolBar toolBarRight = new ToolBar(h1, h2, h3, h4);
        toolBarRight.setOrientation(Orientation.VERTICAL);
        toolBarRight.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setRight(toolBarRight);
        pane.setPrefSize(300, 300);
        toolBarRight.setMinWidth(400);
        toolBar.setPrefHeight(100);
       // toolBarRight.setMinWidth(320);
        toolBarRight.setPrefHeight(700);
        
        scene = new Scene(pane);
        scene.setFill(Color.BLACK);
        

        
        primaryStage.setTitle("Simple Rubik's Cube - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        ArrayList<Label> elements = new ArrayList<Label>();
        elements.add(stepLabel);
        elements.add(description);
        elements.add(moves);
        elements.add(bottom);
        
        
   	 	/* F = F
   			 * R = R
   			 * U = U
   			 * B = B
   			 * L = L
   			 * D = D
   			 * 
   			 * F' = G
   			 * R' = T
   			 * U' = I
   			 * B' = N
   			 * L' = K
   			 * D' = S
   		*/
        scene.setOnKeyPressed(e -> {
        	switch(e.getCode()) {
        		case F: makeFmove(false); break;
	        	case R: makeRmove(false); break;
	        	case U: makeUmove(false); break;
	        	case B: makeBmove(false); break;
	        	case L: makeLmove(false); break;
	        	case D: makeDmove(false); break;
	        	case G: makeFmove(true); break;
	        	case T: makeRmove(true); break;
	        	case I: makeUmove(true); break;
	        	case N: makeBmove(true); break;
	        	case K: makeLmove(true); break;
	        	case S: makeDmove(true); break;
	        	case Q: makeYrotation(true); break;
	        	case Y: makeYrotation(false); break;
	        	default: break;

        	}
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        	@Override
        	public void handle(WindowEvent e) {
        		System.out.println("called");
        		Home.onCloseTut();
        		System.exit(0);
        	}
        });
        
        TutorialHomepage.main(elements, back, forward, restartSection, skipToDemo, toolBarRight, toolBar);
        
        //beginTutorial(stepLabel, description, moves, bottom, back, forward, toolBarRight, toolBar);
    }

   
	private static void buildMesh(Group sceneRoot, PhongMaterial mat, Group meshGroup) {
		meshGroup = new Group();
		sceneRoot.getChildren().clear();
		AtomicInteger cont = new AtomicInteger();
        for (int i = 0; i < patternFaceF.size(); i++) {
        	MeshView meshP = new MeshView();
            meshP.setMesh(createCube(patternFaceF.get(i)));
            meshP.setMaterial(mat);
            Point3D pt = pointsFaceF.get(cont.getAndIncrement());
            meshP.getTransforms().addAll(new Translate(pt.getX(), pt.getY(), pt.getZ()));
            meshGroup.getChildren().add(meshP);
            sceneRoot.getChildren().addAll(meshP, new AmbientLight(Color.WHITE));
        }
	}

	static void makeR2move() {
		for (int x = 0; x < rightFacePoints.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(((x+1)*6)-2);
        	Point3D pt = rightFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(450), msh);
        	rt.setAxis(Rotate.X_AXIS);
        	rt.setByAngle(-180);
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(((x+1)*6)-2, msh);
        }
		
		int[] temp1 = FRD; int[] temp2 = FR;		
		FR = CRD; FRD = BRD; CRD = BR; BRD = BRU; BR = CRU; BRU = FRU; CRU = temp2; FRU = temp1;
		Point3D ptemp1 = pFRD; Point3D ptemp2 = pFR;
		pFR = pCRD; pFRD = pBRD; pCRD = pBR; pBRD = pBRU; pBR = pCRU; pBRU = pFRU; pCRU = ptemp2; pFRU = ptemp1;
		cycleColours(FR, D_FACE, F_FACE, U_FACE);
		cycleColours(FRU, D_FACE, F_FACE, U_FACE);
		cycleColours(CRU, F_FACE, U_FACE, B_FACE);
		cycleColours(BRU, F_FACE, U_FACE, B_FACE);
		cycleColours(BR, U_FACE, B_FACE, D_FACE);
		cycleColours(BRD, U_FACE, B_FACE, D_FACE);
		cycleColours(CRD, F_FACE, B_FACE, D_FACE);
		cycleColours(FRD, F_FACE, B_FACE, D_FACE);
		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
				
		temp1 = FRD; temp2 = FR;		
		FR = CRD; FRD = BRD; CRD = BR; BRD = BRU; BR = CRU; BRU = FRU; CRU = temp2; FRU = temp1;
		ptemp1 = pFRD; ptemp2 = pFR;
		pFR = pCRD; pFRD = pBRD; pCRD = pBR; pBRD = pBRU; pBR = pCRU; pBRU = pFRU; pCRU = ptemp2; pFRU = ptemp1;
		cycleColours(FR, D_FACE, F_FACE, U_FACE);
		cycleColours(FRU, D_FACE, F_FACE, U_FACE);
		cycleColours(CRU, F_FACE, U_FACE, B_FACE);
		cycleColours(BRU, F_FACE, U_FACE, B_FACE);
		cycleColours(BR, U_FACE, B_FACE, D_FACE);
		cycleColours(BRD, U_FACE, B_FACE, D_FACE);
		cycleColours(CRD, F_FACE, B_FACE, D_FACE);
		cycleColours(FRD, F_FACE, B_FACE, D_FACE);
		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
	}
	
	static void makeRmove(boolean prime) {
		for (int x = 0; x < rightFacePoints.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(((x+1)*6)-2);
        	Point3D pt = rightFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.X_AXIS);
        	if (prime) {
        		rt.setByAngle(90);
        	} else {
        		rt.setByAngle(-90);
        	}
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(((x+1)*6)-2, msh);
        }
		
		if (prime) {
			int[] temp1 = CRU; int[] temp2 = BRU;
			CRU = BR; BRU = BRD; BR = CRD; BRD = FRD; CRD = FR; FRD = FRU; FR = temp1; FRU = temp2;
			Point3D ptemp1 = pCRU; Point3D ptemp2 = pBRU;
			pCRU = pBR; pBRU = pBRD; pBR = pCRD; pBRD = pFRD; pCRD = pFR; pFRD = pFRU; pFR = ptemp1; pFRU = ptemp2;
			cycleColours(FR, B_FACE, U_FACE, F_FACE);
			cycleColours(FRU, B_FACE, U_FACE, F_FACE);
			cycleColours(CRU, D_FACE, B_FACE, U_FACE);
			cycleColours(BRU, D_FACE, B_FACE, U_FACE);
			cycleColours(BR, F_FACE, D_FACE, B_FACE);
			cycleColours(BRD, F_FACE, D_FACE, B_FACE);
			cycleColours(CRD, U_FACE, F_FACE, D_FACE);
			cycleColours(FRD, U_FACE, F_FACE, D_FACE);
		} else {
			int[] temp1 = FRD; int[] temp2 = FR;		
			FR = CRD; FRD = BRD; CRD = BR; BRD = BRU; BR = CRU; BRU = FRU; CRU = temp2; FRU = temp1;
			Point3D ptemp1 = pFRD; Point3D ptemp2 = pFR;
			pFR = pCRD; pFRD = pBRD; pCRD = pBR; pBRD = pBRU; pBR = pCRU; pBRU = pFRU; pCRU = ptemp2; pFRU = ptemp1;
			cycleColours(FR, D_FACE, F_FACE, U_FACE);
			cycleColours(FRU, D_FACE, F_FACE, U_FACE);
			cycleColours(CRU, F_FACE, U_FACE, B_FACE);
			cycleColours(BRU, F_FACE, U_FACE, B_FACE);
			cycleColours(BR, U_FACE, B_FACE, D_FACE);
			cycleColours(BRD, U_FACE, B_FACE, D_FACE);
			cycleColours(CRD, F_FACE, B_FACE, D_FACE);
			cycleColours(FRD, F_FACE, B_FACE, D_FACE);
		}


		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
		isSolved();
	}
	
	static void makeFmove(boolean prime) {
		int elem = 0;
		for (int x = 0; x < frontFacePoints.size(); x++) {
			elem = x*2;
			MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = frontFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.Z_AXIS);
        	if (prime) {
        		rt.setByAngle(-90);
        	} else { 
        		rt.setByAngle(90);
        	}
        	rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }
		
		if (prime) {
			int[] temp1 = FLU; int[] temp2 = FU;
			FLU = FRU; FU = FR; FRU = FRD; FR = FD; FRD = FLD; FD = FL; FLD = temp1; FL = temp2;
			Point3D ptemp1 = pFLU; Point3D ptemp2 = pFU;
			pFLU = pFRU; pFU = pFR; pFRU = pFRD; pFR = pFD; pFRD = pFLD; pFD = pFL; pFLD = ptemp1; pFL = ptemp2;
			cycleColours(FRU, D_FACE, R_FACE, U_FACE);
			cycleColours(FR, D_FACE, R_FACE, U_FACE);
			cycleColours(FRD, L_FACE, D_FACE, R_FACE);
			cycleColours(FD, L_FACE, D_FACE, R_FACE);
			cycleColours(FLD, U_FACE, L_FACE, D_FACE);
			cycleColours(FL, U_FACE, L_FACE, D_FACE);
			cycleColours(FLU, R_FACE, U_FACE, L_FACE);
			cycleColours(FU, R_FACE, U_FACE, L_FACE);			
		} else {
			int[] temp1 = FU; int[] temp2 = FLU;
			FU = FL; FLU = FLD; FL = FD; FLD = FRD; FD = FR; FRD = FRU; FR = temp1; FRU = temp2;
			Point3D ptemp1 = pFU; Point3D ptemp2 = pFLU;
			pFU = pFL; pFLU = pFLD; pFL = pFD; pFLD = pFRD; pFD = pFR; pFRD = pFRU; pFR = ptemp1; pFRU = ptemp2;
			cycleColours(FRU, L_FACE, U_FACE, R_FACE);
			cycleColours(FR, L_FACE, U_FACE, R_FACE);
			cycleColours(FRD, U_FACE, R_FACE, D_FACE);
			cycleColours(FD, U_FACE, R_FACE, D_FACE);
			cycleColours(FLD, R_FACE, D_FACE, L_FACE);
			cycleColours(FL, R_FACE, D_FACE, L_FACE);
			cycleColours(FLU, D_FACE, L_FACE, U_FACE);
			cycleColours(FU, D_FACE, L_FACE, U_FACE);
		}		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
		isSolved();
		
	}
	
	static void makeF2move() {
		int elem = 0;
		for (int x = 0; x < frontFacePoints.size(); x++) {
			elem = x*2;
			MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = frontFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(450), msh);
        	rt.setAxis(Rotate.Z_AXIS);
        	rt.setByAngle(180);
        	rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }
		int[] temp1 = FU; int[] temp2 = FLU;
		FU = FL; FLU = FLD; FL = FD; FLD = FRD; FD = FR; FRD = FRU; FR = temp1; FRU = temp2;
		Point3D ptemp1 = pFU; Point3D ptemp2 = pFLU;
		pFU = pFL; pFLU = pFLD; pFL = pFD; pFLD = pFRD; pFD = pFR; pFRD = pFRU; pFR = ptemp1; pFRU = ptemp2;
		cycleColours(FRU, L_FACE, U_FACE, R_FACE);
		cycleColours(FR, L_FACE, U_FACE, R_FACE);
		cycleColours(FRD, U_FACE, R_FACE, D_FACE);
		cycleColours(FD, U_FACE, R_FACE, D_FACE);
		cycleColours(FLD, R_FACE, D_FACE, L_FACE);
		cycleColours(FL, R_FACE, D_FACE, L_FACE);
		cycleColours(FLU, D_FACE, L_FACE, U_FACE);
		cycleColours(FU, D_FACE, L_FACE, U_FACE);
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		temp1 = FU; temp2 = FLU;
		FU = FL; FLU = FLD; FL = FD; FLD = FRD; FD = FR; FRD = FRU; FR = temp1; FRU = temp2;
		ptemp1 = pFU; ptemp2 = pFLU;
		pFU = pFL; pFLU = pFLD; pFL = pFD; pFLD = pFRD; pFD = pFR; pFRD = pFRU; pFR = ptemp1; pFRU = ptemp2;
		cycleColours(FRU, L_FACE, U_FACE, R_FACE);
		cycleColours(FR, L_FACE, U_FACE, R_FACE);
		cycleColours(FRD, U_FACE, R_FACE, D_FACE);
		cycleColours(FD, U_FACE, R_FACE, D_FACE);
		cycleColours(FLD, R_FACE, D_FACE, L_FACE);
		cycleColours(FL, R_FACE, D_FACE, L_FACE);
		cycleColours(FLU, D_FACE, L_FACE, U_FACE);
		cycleColours(FU, D_FACE, L_FACE, U_FACE);
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
	}

	static void makeBmove(boolean prime) {
		int elem = 0;
		for (int x = 0; x < backFacePoints.size(); x++) {
			elem = (x*2)+36;
			MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = backFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.Z_AXIS);
        	if (prime) {
        		rt.setByAngle(90);
        	} else { 
        		rt.setByAngle(-90);
        	}
        	rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }
		
		if (prime) {
			int[] temp1 = BLD; int[] temp2 = BD;
			BLD = BRD; BD = BR; BRD = BRU; BR = BU; BRU = BLU; BU = BL; BLU = temp1; BL = temp2;
			Point3D ptemp1 = pBLD; Point3D ptemp2 = pBD;
			pBLD = pBRD; pBD = pBR; pBRD = pBRU; pBR = pBU; pBRU = pBLU; pBU = pBL; pBLU = ptemp1; pBL = ptemp2;
			cycleColours(BLU, D_FACE, L_FACE, U_FACE);
			cycleColours(BU, D_FACE, L_FACE, U_FACE);
			cycleColours(BRU, L_FACE, U_FACE, R_FACE);
			cycleColours(BR, L_FACE, U_FACE, R_FACE);
			cycleColours(BRD, U_FACE, R_FACE, D_FACE);
			cycleColours(BD, U_FACE, R_FACE, D_FACE);
			cycleColours(BLD, R_FACE, D_FACE, L_FACE);
			cycleColours(BL, R_FACE, D_FACE, L_FACE);
		} else {
			int[] temp1 = BLU; int[] temp2 = BU;
			BLU = BRU; BU = BR; BRU = BRD; BR = BD; BRD = BLD; BD = BL; BLD = temp1; BL = temp2;
			Point3D ptemp1 = pBLU; Point3D ptemp2 = pBU;
			pBLU = pBRU; pBU = pBR; pBRU = pBRD; pBR = pBD; pBRD = pBLD; pBD = pBL; pBLD = ptemp1; pBL = ptemp2;
			cycleColours(BLU, R_FACE, U_FACE, L_FACE);
			cycleColours(BL, R_FACE, U_FACE, L_FACE);
			cycleColours(BLD, U_FACE, L_FACE, D_FACE);
			cycleColours(BD, U_FACE, L_FACE, D_FACE);
			cycleColours(BRD, L_FACE, D_FACE, R_FACE);
			cycleColours(BR, L_FACE, D_FACE, R_FACE);
			cycleColours(BRU, D_FACE, R_FACE, U_FACE);
			cycleColours(BU, D_FACE, R_FACE, U_FACE);
		}		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
		isSolved();
		
	}

	static void makeB2move() {
		int elem = 0;
		for (int x = 0; x < backFacePoints.size(); x++) {
			elem = (x*2)+36;
			MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = backFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(450), msh);
        	rt.setAxis(Rotate.Z_AXIS);
        	rt.setByAngle(-180);
        	rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }
		int[] temp1 = BLU; int[] temp2 = BU;
		BLU = BRU; BU = BR; BRU = BRD; BR = BD; BRD = BLD; BD = BL; BLD = temp1; BL = temp2;
		Point3D ptemp1 = pBLU; Point3D ptemp2 = pBU;
		pBLU = pBRU; pBU = pBR; pBRU = pBRD; pBR = pBD; pBRD = pBLD; pBD = pBL; pBLD = ptemp1; pBL = ptemp2;
		cycleColours(BLU, R_FACE, U_FACE, L_FACE);
		cycleColours(BL, R_FACE, U_FACE, L_FACE);
		cycleColours(BLD, U_FACE, L_FACE, D_FACE);
		cycleColours(BD, U_FACE, L_FACE, D_FACE);
		cycleColours(BRD, L_FACE, D_FACE, R_FACE);
		cycleColours(BR, L_FACE, D_FACE, R_FACE);
		cycleColours(BRU, D_FACE, R_FACE, U_FACE);
		cycleColours(BU, D_FACE, R_FACE, U_FACE);	
		
		patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);

		temp1 = BLU; temp2 = BU;
		BLU = BRU; BU = BR; BRU = BRD; BR = BD; BRD = BLD; BD = BL; BLD = temp1; BL = temp2;
		ptemp1 = pBLU; ptemp2 = pBU;
		pBLU = pBRU; pBU = pBR; pBRU = pBRD; pBR = pBD; pBRD = pBLD; pBD = pBL; pBLD = ptemp1; pBL = ptemp2;
		cycleColours(BLU, R_FACE, U_FACE, L_FACE);
		cycleColours(BL, R_FACE, U_FACE, L_FACE);
		cycleColours(BLD, U_FACE, L_FACE, D_FACE);
		cycleColours(BD, U_FACE, L_FACE, D_FACE);
		cycleColours(BRD, L_FACE, D_FACE, R_FACE);
		cycleColours(BR, L_FACE, D_FACE, R_FACE);
		cycleColours(BRU, D_FACE, R_FACE, U_FACE);
		cycleColours(BU, D_FACE, R_FACE, U_FACE);	
		
		patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
	}
	
	static void makeUmove(boolean prime) {
		int elem = 0;
		for (int x = 0; x < upFacePoints.size(); x++) {
			switch(x) {
			case 0: elem = 12; break;
			case 1: elem = 14; break;
			case 2: elem = 16; break;
			case 3: elem = 30; break;
			case 4: elem = 32; break;
			case 5: elem = 34; break;
			case 6: elem = 48; break;
			case 7: elem = 50; break;
			case 8: elem = 52; break;
			
			}
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = upFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.Y_AXIS);
        	if (prime) {
        		rt.setByAngle(-90);
        	} else { 
        		rt.setByAngle(90);
        	}
        	rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }		
		
		if (prime) {
			int[] temp1 = FRU; int[] temp2 = FU;
			FRU = FLU; FU = CLU; FLU = BLU; CLU = BU; BLU = BRU; BU = CRU; BRU = temp1; CRU = temp2; 
			Point3D ptemp1 = pFRU; Point3D ptemp2 = pFU;
			pFRU = pFLU; pFU = pCLU; pFLU = pBLU; pCLU = pBU; pBLU = pBRU; pBU = pCRU; pBRU = ptemp1; pCRU = ptemp2;	
			cycleColours(FU, L_FACE, F_FACE, R_FACE);
			cycleColours(FRU, L_FACE, F_FACE, R_FACE);
			cycleColours(CRU, F_FACE, R_FACE, B_FACE);
			cycleColours(BRU, F_FACE, R_FACE, B_FACE);
			cycleColours(BU, R_FACE,  B_FACE, L_FACE);
			cycleColours(BLU, R_FACE, B_FACE, L_FACE);
			cycleColours(CLU, B_FACE, L_FACE, F_FACE);
			cycleColours(FLU, B_FACE, L_FACE, F_FACE);
		} else {
			int[] temp1 = FRU; int[] temp2 = CRU;		
			FRU = BRU; CRU = BU; BRU = BLU; BU = CLU; BLU = FLU; CLU = FU; FLU = temp1; FU = temp2;
			Point3D ptemp1 = pFRU; Point3D ptemp2 = pCRU;
			pFRU = pBRU; pCRU = pFU; pBRU = pBLU; pBU = pCLU; pBLU = pFLU; pCLU = pFU; pFLU = ptemp1; pFU = ptemp2;
			cycleColours(FU, B_FACE, R_FACE, F_FACE);
			cycleColours(FRU, B_FACE, R_FACE, F_FACE);
			cycleColours(CRU, L_FACE, B_FACE, R_FACE);
			cycleColours(BRU, L_FACE, B_FACE, R_FACE);
			cycleColours(BU, F_FACE, L_FACE, B_FACE);
			cycleColours(BLU, F_FACE, L_FACE, B_FACE);
			cycleColours(CLU, R_FACE, F_FACE, L_FACE);
			cycleColours(FLU, R_FACE, F_FACE, L_FACE);	
		}		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
		isSolved();
		
	}

	static void makeU2move() {
		int elem = 0;
		for (int x = 0; x < upFacePoints.size(); x++) {
			switch(x) {
			case 0: elem = 12; break;
			case 1: elem = 14; break;
			case 2: elem = 16; break;
			case 3: elem = 30; break;
			case 4: elem = 32; break;
			case 5: elem = 34; break;
			case 6: elem = 48; break;
			case 7: elem = 50; break;
			case 8: elem = 52; break;
			
			}
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = upFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(450), msh);
        	rt.setAxis(Rotate.Y_AXIS);
        	rt.setByAngle(180);
        	rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }	
		int[] temp1 = FRU; int[] temp2 = CRU;		
		FRU = BRU; CRU = BU; BRU = BLU; BU = CLU; BLU = FLU; CLU = FU; FLU = temp1; FU = temp2;
		Point3D ptemp1 = pFRU; Point3D ptemp2 = pCRU;
		pFRU = pBRU; pCRU = pFU; pBRU = pBLU; pBU = pCLU; pBLU = pFLU; pCLU = pFU; pFLU = ptemp1; pFU = ptemp2;
		cycleColours(FU, B_FACE, R_FACE, F_FACE);
		cycleColours(FRU, B_FACE, R_FACE, F_FACE);
		cycleColours(CRU, L_FACE, B_FACE, R_FACE);
		cycleColours(BRU, L_FACE, B_FACE, R_FACE);
		cycleColours(BU, F_FACE, L_FACE, B_FACE);
		cycleColours(BLU, F_FACE, L_FACE, B_FACE);
		cycleColours(CLU, R_FACE, F_FACE, L_FACE);
		cycleColours(FLU, R_FACE, F_FACE, L_FACE);	
		
		patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
		temp1 = FRU; temp2 = CRU;		
		FRU = BRU; CRU = BU; BRU = BLU; BU = CLU; BLU = FLU; CLU = FU; FLU = temp1; FU = temp2;
		ptemp1 = pFRU; ptemp2 = pCRU;
		pFRU = pBRU; pCRU = pFU; pBRU = pBLU; pBU = pCLU; pBLU = pFLU; pCLU = pFU; pFLU = ptemp1; pFU = ptemp2;
		cycleColours(FU, B_FACE, R_FACE, F_FACE);
		cycleColours(FRU, B_FACE, R_FACE, F_FACE);
		cycleColours(CRU, L_FACE, B_FACE, R_FACE);
		cycleColours(BRU, L_FACE, B_FACE, R_FACE);
		cycleColours(BU, F_FACE, L_FACE, B_FACE);
		cycleColours(BLU, F_FACE, L_FACE, B_FACE);
		cycleColours(CLU, R_FACE, F_FACE, L_FACE);
		cycleColours(FLU, R_FACE, F_FACE, L_FACE);	
		patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
	}

	static void makeDmove(boolean prime) {
		int elem = 0;
		for (int x = 0; x < downFacePoints.size(); x++) {
			switch(x) {
			case 0: elem = 0; break;
			case 1: elem = 2; break;
			case 2: elem = 4; break;
			case 3: elem = 18; break;
			case 4: elem = 20; break;
			case 5: elem = 22; break;
			case 6: elem = 36; break;
			case 7: elem = 38; break;
			case 8: elem = 40; break;
			
			}
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = downFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.Y_AXIS);
        	if (prime) {
        		rt.setByAngle(90);
        	} else { 
        		rt.setByAngle(-90);
        	}
        	rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }		
		
		if (prime) {
			int[] temp1 = CLD; int[] temp2 = FLD;
			CLD = FD; FLD = FRD; FD = CRD; FRD = BRD; CRD = BD; BRD = BLD; BD = temp1; BLD = temp2;  
			Point3D ptemp1 = pCLD; Point3D ptemp2 = pFLD;
			pCLD = pFD; pFLD = pFRD; pFD = pCRD; pFRD = pBRD; pCRD = pBD; pBRD = pFRD; pBD = ptemp1; pBLD = ptemp2; 
			cycleColours(FLD, R_FACE, F_FACE, L_FACE);
			cycleColours(CLD, R_FACE, F_FACE, L_FACE);
			cycleColours(BLD, F_FACE, L_FACE, B_FACE); 
			cycleColours(BD, F_FACE, L_FACE, B_FACE);
			cycleColours(BRD, L_FACE, B_FACE, R_FACE);
			cycleColours(CRD, L_FACE, B_FACE, R_FACE);
			cycleColours(FRD, B_FACE, R_FACE, F_FACE);
			cycleColours(FD, B_FACE, R_FACE, F_FACE);
		} else {
			
			int[] temp1 = CLD; int[] temp2 = BLD;
			CLD = BD; BLD = BRD; BD = CRD; BRD = FRD; CRD = FD; FRD = FLD; FD = temp1; FLD = temp2;
			Point3D ptemp1 = pCLD; Point3D ptemp2 = pBLD;
			pCLD = pBD; pBLD = pBRD; pBD = pCRD; pBRD = pFRD; pCRD = pFD; pFRD = pFLD; pFD = ptemp1; pFLD = ptemp2;
			cycleColours(CLD, R_FACE, B_FACE, L_FACE);
			cycleColours(BLD, R_FACE, B_FACE, L_FACE);
			cycleColours(BD, F_FACE, R_FACE, B_FACE);
			cycleColours(BRD, F_FACE, R_FACE, B_FACE);
			cycleColours(CRD, L_FACE, F_FACE, R_FACE);
			cycleColours(FRD, L_FACE, F_FACE, R_FACE);
			cycleColours(FD, B_FACE, L_FACE, F_FACE);
			cycleColours(FLD, B_FACE, L_FACE, F_FACE);
			
			//FLD FD FRD CLD CRD BLD BD BRD
		}		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
		isSolved();
		
	}

	static void makeD2move() {
		int elem = 0;
		for (int x = 0; x < downFacePoints.size(); x++) {
			switch(x) {
			case 0: elem = 0; break;
			case 1: elem = 2; break;
			case 2: elem = 4; break;
			case 3: elem = 18; break;
			case 4: elem = 20; break;
			case 5: elem = 22; break;
			case 6: elem = 36; break;
			case 7: elem = 38; break;
			case 8: elem = 40; break;
			
			}
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = downFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(450), msh);
        	rt.setAxis(Rotate.Y_AXIS);
        	rt.setByAngle(-180);
        	rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }
		
		int[] temp1 = CLD; int[] temp2 = BLD;
		CLD = BD; BLD = BRD; BD = CRD; BRD = FRD; CRD = FD; FRD = FLD; FD = temp1; FLD = temp2;
		Point3D ptemp1 = pCLD; Point3D ptemp2 = pBLD;
		pCLD = pBD; pBLD = pBRD; pBD = pCRD; pBRD = pFRD; pCRD = pFD; pFRD = pFLD; pFD = ptemp1; pFLD = ptemp2;
		cycleColours(CLD, R_FACE, B_FACE, L_FACE);
		cycleColours(BLD, R_FACE, B_FACE, L_FACE);
		cycleColours(BD, F_FACE, R_FACE, B_FACE);
		cycleColours(BRD, F_FACE, R_FACE, B_FACE);
		cycleColours(CRD, L_FACE, F_FACE, R_FACE);
		cycleColours(FRD, L_FACE, F_FACE, R_FACE);
		cycleColours(FD, B_FACE, L_FACE, F_FACE);
		cycleColours(FLD, B_FACE, L_FACE, F_FACE);
	
		patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
		temp1 = CLD; temp2 = BLD;
		CLD = BD; BLD = BRD; BD = CRD; BRD = FRD; CRD = FD; FRD = FLD; FD = temp1; FLD = temp2;
		ptemp1 = pCLD; ptemp2 = pBLD;
		pCLD = pBD; pBLD = pBRD; pBD = pCRD; pBRD = pFRD; pCRD = pFD; pFRD = pFLD; pFD = ptemp1; pFLD = ptemp2;
		cycleColours(CLD, R_FACE, B_FACE, L_FACE);
		cycleColours(BLD, R_FACE, B_FACE, L_FACE);
		cycleColours(BD, F_FACE, R_FACE, B_FACE);
		cycleColours(BRD, F_FACE, R_FACE, B_FACE);
		cycleColours(CRD, L_FACE, F_FACE, R_FACE);
		cycleColours(FRD, L_FACE, F_FACE, R_FACE);
		cycleColours(FD, B_FACE, L_FACE, F_FACE);
		cycleColours(FLD, B_FACE, L_FACE, F_FACE);
	
		patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
	}
	
	static void makeLmove(boolean prime) {
		for (int x = 0; x < leftFacePoints.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(x*6);
        	Point3D pt = leftFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.X_AXIS);
        	if (prime) {
        		rt.setByAngle(-90);
        	} else {
        		rt.setByAngle(90);
        	}
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(x*6, msh);
        }
		
		if (prime) {
			int[] temp1 = CLD; int[] temp2 = BLD;
			CLD = BL; BLD = BLU; BL = CLU; BLU = FLU; CLU = FL; FLU = FLD; FL = temp1; FLD = temp2;
			Point3D ptemp1 = pCLD; Point3D ptemp2 = pBLD;
			pCLD = pBL; pBLD = pBLU; pBL = pCLU; pBLU = pFLU; pCLU = pFL; pFLU = pFLD; pFL = ptemp1; pFLD = ptemp2;
			cycleColours(FLD, B_FACE, D_FACE, F_FACE);
			cycleColours(FL, B_FACE, D_FACE, F_FACE);
			cycleColours(FLU, D_FACE, F_FACE, U_FACE);
			cycleColours(CLU, D_FACE, F_FACE, U_FACE);
			cycleColours(BLU, F_FACE, U_FACE, B_FACE);
			cycleColours(BL, F_FACE, U_FACE, B_FACE);
			cycleColours(BLD, U_FACE, B_FACE, D_FACE);
			cycleColours(CLD, U_FACE, B_FACE, D_FACE);
		} else {
			int[] temp1 = FLD; int[] temp2 = FL;
			FLD = FLU; FL = CLU; FLU = BLU; CLU = BL; BLU = BLD; BL = CLD; BLD = temp1; CLD = temp2;
			Point3D ptemp1 = pFLD; Point3D ptemp2 = pCLD;
			pFLD = pFLU; pFL = pCLU; pFLU = pBLU; pCLU = pBL; pBLU = pBLD; pBL = pCLD; pBLD = ptemp1; pCLD = ptemp2;
			cycleColours(FLD, U_FACE, F_FACE, D_FACE);
			cycleColours(FL, B_FACE, U_FACE, F_FACE);
			cycleColours(FLU, B_FACE, U_FACE, F_FACE);
			cycleColours(CLU, D_FACE, B_FACE, U_FACE);
			cycleColours(BLU, D_FACE, B_FACE, U_FACE);
			cycleColours(BL, F_FACE, D_FACE, B_FACE);
			cycleColours(BLD, F_FACE, D_FACE, B_FACE);
			cycleColours(CLD, U_FACE, F_FACE, D_FACE);
		}

		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
		isSolved();
	}

	static void makeL2move() {
		for (int x = 0; x < leftFacePoints.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(x*6);
        	Point3D pt = leftFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(450), msh);
        	rt.setAxis(Rotate.X_AXIS);
        	rt.setByAngle(180);
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(x*6, msh);
        }
		
		int[] temp1 = FLD; int[] temp2 = FL;
		FLD = FLU; FL = CLU; FLU = BLU; CLU = BL; BLU = BLD; BL = CLD; BLD = temp1; CLD = temp2;
		Point3D ptemp1 = pFLD; Point3D ptemp2 = pCLD;
		pFLD = pFLU; pFL = pCLU; pFLU = pBLU; pCLU = pBL; pBLU = pBLD; pBL = pCLD; pBLD = ptemp1; pCLD = ptemp2;
		cycleColours(FLD, U_FACE, F_FACE, D_FACE);
		cycleColours(FL, B_FACE, U_FACE, F_FACE);
		cycleColours(FLU, B_FACE, U_FACE, F_FACE);
		cycleColours(CLU, D_FACE, B_FACE, U_FACE);
		cycleColours(BLU, D_FACE, B_FACE, U_FACE);
		cycleColours(BL, F_FACE, D_FACE, B_FACE);
		cycleColours(BLD, F_FACE, D_FACE, B_FACE);
		cycleColours(CLD, U_FACE, F_FACE, D_FACE);
		
		patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);

		temp1 = FLD; temp2 = FL;
		FLD = FLU; FL = CLU; FLU = BLU; CLU = BL; BLU = BLD; BL = CLD; BLD = temp1; CLD = temp2;
		ptemp1 = pFLD; ptemp2 = pCLD;
		pFLD = pFLU; pFL = pCLU; pFLU = pBLU; pCLU = pBL; pBLU = pBLD; pBL = pCLD; pBLD = ptemp1; pCLD = ptemp2;
		cycleColours(FLD, U_FACE, F_FACE, D_FACE);
		cycleColours(FL, B_FACE, U_FACE, F_FACE);
		cycleColours(FLU, B_FACE, U_FACE, F_FACE);
		cycleColours(CLU, D_FACE, B_FACE, U_FACE);
		cycleColours(BLU, D_FACE, B_FACE, U_FACE);
		cycleColours(BL, F_FACE, D_FACE, B_FACE);
		cycleColours(BLD, F_FACE, D_FACE, B_FACE);
		cycleColours(CLD, U_FACE, F_FACE, D_FACE);
		
		patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
	}
	
	static void makeXrotation(boolean prime) {
		for (int x = 0; x < pointsFaceF.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(x*2);
        	Point3D pt = pointsFaceF.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.X_AXIS);
        	if (prime) {
        		rt.setByAngle(90);
        	} else {
        		rt.setByAngle(-90);
        	}
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(x*2, msh);
        }
		
		if (prime) {
			int[] temp1 = CRU; int[] temp2 = BRU;
			CRU = BR; BRU = BRD; BR = CRD; BRD = FRD; CRD = FR; FRD = FRU; FR = temp1; FRU = temp2;
			Point3D ptemp1 = pCRU; Point3D ptemp2 = pBRU;
			pCRU = pBR; pBRU = pBRD; pBR = pCRD; pBRD = pFRD; pCRD = pFR; pFRD = pFRU; pFR = ptemp1; pFRU = ptemp2;
			cycleColours(FR, B_FACE, U_FACE, F_FACE);
			cycleColours(FRU, B_FACE, U_FACE, F_FACE);
			cycleColours(CRU, D_FACE, B_FACE, U_FACE);
			cycleColours(BRU, D_FACE, B_FACE, U_FACE);
			cycleColours(BR, F_FACE, D_FACE, B_FACE);
			cycleColours(BRD, F_FACE, D_FACE, B_FACE);
			cycleColours(CRD, U_FACE, F_FACE, D_FACE);
			cycleColours(FRD, U_FACE, F_FACE, D_FACE);
			temp1 = FLD; temp2 = FL;
			FLD = FLU; FL = CLU; FLU = BLU; CLU = BL; BLU = BLD; BL = CLD; BLD = temp1; CLD = temp2;
			ptemp1 = pFLD; ptemp2 = pFL;
			pFLD = pFLU; pFL = pCLU; pFLU = pBLU; pCLU = pBL; pBLU = pBLD; pBL = pCLD; pBLD = ptemp1; pCLD = ptemp2;
			cycleColours(FLD, U_FACE, F_FACE, D_FACE);
			cycleColours(FL, B_FACE, U_FACE, F_FACE);
			cycleColours(FLU, B_FACE, U_FACE, F_FACE);
			cycleColours(CLU, D_FACE, B_FACE, U_FACE);
			cycleColours(BLU, D_FACE, B_FACE, U_FACE);
			cycleColours(BL, F_FACE, D_FACE, B_FACE);
			cycleColours(BLD, F_FACE, D_FACE, B_FACE);
			cycleColours(CLD, U_FACE, F_FACE, D_FACE);
			temp1 = FU; temp2 = F;
			FU = BU; BU = BD; BD = FD; FD = temp1;
			F = CU; CU = B; B = CD; CD = temp2;
			ptemp1 = pFU; ptemp2 = pF;
			pFU = pBU; pBU = pBD; pBD = pFD; pFD = ptemp1;
			pF = pCU; pCU = pB; pB = pCD; pCD = ptemp2;
			cycleColours(FU, B_FACE, U_FACE, F_FACE);
			cycleColours(F, B_FACE, U_FACE, F_FACE);
			cycleColours(FD, U_FACE, F_FACE, D_FACE);
			cycleColours(CD, U_FACE, F_FACE, D_FACE);
			cycleColours(BD, F_FACE, D_FACE, B_FACE);
			cycleColours(B, F_FACE, D_FACE, B_FACE);
			cycleColours(BU, D_FACE, B_FACE, U_FACE);
			cycleColours(CU, D_FACE, B_FACE, U_FACE);
			
		} else {
			int[] temp1 = CLD; int[] temp2 = BLD;
			CLD = BL; BLD = BLU; BL = CLU; BLU = FLU; CLU = FL; FLU = FLD; FL = temp1; FLD = temp2;
			Point3D ptemp1 = pCLD; Point3D ptemp2 = pBLD;
			pCLD = pBL; pBLD = pBLU; pBL = pCLU; pBLU = pFLU; pCLU = pFL; pFLU = pFLD; pFL = ptemp1; pFLD = ptemp2;
			cycleColours(FLD, B_FACE, D_FACE, F_FACE);
			cycleColours(FL, B_FACE, D_FACE, F_FACE);
			cycleColours(FLU, D_FACE, F_FACE, U_FACE);
			cycleColours(CLU, D_FACE, F_FACE, U_FACE);
			cycleColours(BLU, F_FACE, U_FACE, B_FACE);
			cycleColours(BL, F_FACE, U_FACE, B_FACE);
			cycleColours(BLD, U_FACE, B_FACE, D_FACE);
			cycleColours(CLD, U_FACE, B_FACE, D_FACE);
			temp1 = FRD; temp2 = FR;		
			FR = CRD; FRD = BRD; CRD = BR; BRD = BRU; BR = CRU; BRU = FRU; CRU = temp2; FRU = temp1;
			ptemp1 = pFRD; ptemp2 = pFR;		
			pFR = pCRD; pFRD = pBRD; pCRD = pBR; pBRD = pBRU; pBR = pCRU; pBRU = pFRU; pCRU = ptemp2; pFRU = ptemp1;
			cycleColours(FR, D_FACE, F_FACE, U_FACE);
			cycleColours(FRU, D_FACE, F_FACE, U_FACE);
			cycleColours(CRU, F_FACE, U_FACE, B_FACE);
			cycleColours(BRU, F_FACE, U_FACE, B_FACE);
			cycleColours(BR, U_FACE, B_FACE, D_FACE);
			cycleColours(BRD, U_FACE, B_FACE, D_FACE);
			cycleColours(CRD, F_FACE, B_FACE, D_FACE);
			cycleColours(FRD, F_FACE, B_FACE, D_FACE);
			temp1 = FU; temp2 = F;
			FU = FD; FD = BD; BD = BU; BU = temp1;
			F = CD; CD = B; B = CU; CU = temp2;
			ptemp1 = pFU; ptemp2 = pF;
			pFU = pFD; pFD = pBD; pBD = pBU; pBU = ptemp1;
			pF = pCD; pCD = pB; pB = pCU; pCU = ptemp2;
			cycleColours(FU, D_FACE, F_FACE, U_FACE);
			cycleColours(F, D_FACE, F_FACE, U_FACE);
			cycleColours(FD, B_FACE, D_FACE, F_FACE);
			cycleColours(CD, B_FACE, D_FACE, F_FACE);
			cycleColours(BD, U_FACE, B_FACE, D_FACE);
			cycleColours(B, U_FACE, B_FACE, D_FACE);
			cycleColours(BU, F_FACE, U_FACE, B_FACE);
			cycleColours(CU, F_FACE, U_FACE, B_FACE);
		}

		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
	}
	
	static void makeYrotation(boolean prime) {
		for (int x = 0; x < pointsFaceF.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(x*2);
        	Point3D pt = pointsFaceF.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.Y_AXIS);
        	if (prime) {
        		rt.setByAngle(-90);
        	} else {
        		rt.setByAngle(90);
        	}
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(x*2, msh);
        }
		
		if (prime) {
			int[] temp1 = FRU; int[] temp2 = FU;
			FRU = FLU; FU = CLU; FLU = BLU; CLU = BU; BLU = BRU; BU = CRU; BRU = temp1; CRU = temp2;
			Point3D ptemp1 = pFRU; Point3D ptemp2 = pFU;
			pFRU = pFLU; pFU = pCLU; pFLU = pBLU; pCLU = pBU; pBLU = pBRU; pBU = pCRU; pBRU = ptemp1; pCRU = ptemp2; 
			cycleColours(FU, L_FACE, F_FACE, R_FACE);
			cycleColours(FRU, L_FACE, F_FACE, R_FACE);
			cycleColours(CRU, F_FACE, R_FACE, B_FACE);
			cycleColours(BRU, F_FACE, R_FACE, B_FACE);
			cycleColours(BU, R_FACE,  B_FACE, L_FACE);
			cycleColours(BLU, R_FACE, B_FACE, L_FACE);
			cycleColours(CLU, B_FACE, L_FACE, F_FACE);
			cycleColours(FLU, B_FACE, L_FACE, F_FACE);
			temp1 = CLD; temp2 = BLD;
			CLD = BD; BLD = BRD; BD = CRD; BRD = FRD; CRD = FD; FRD = FLD; FD = temp1; FLD = temp2;
			ptemp1 = pCLD; ptemp2 = pBLD;
			pCLD = pBD; pBLD = pBRD; pBD = pCRD; pBRD = pFRD; pCRD = pFD; pFRD = pFLD; pFD = ptemp1; pFLD = ptemp2;
			cycleColours(CLD, R_FACE, B_FACE, L_FACE);
			cycleColours(BLD, R_FACE, B_FACE, L_FACE);
			cycleColours(BD, F_FACE, R_FACE, B_FACE);
			cycleColours(BRD, F_FACE, R_FACE, B_FACE);
			cycleColours(CRD, L_FACE, F_FACE, R_FACE);
			cycleColours(FRD, L_FACE, F_FACE, R_FACE);
			cycleColours(FD, B_FACE, L_FACE, F_FACE);
			cycleColours(FLD, B_FACE, L_FACE, F_FACE);
			temp1 = F; temp2 = FR;
			F = CL; CL = B; B = CR; CR = temp1;
			FR = FL; FL = BL; BL = BR; BR = temp2;
			ptemp1 = pF; ptemp2 = pFR;
			pF = pCL; pCL = pB; pB = pCR; pCR = ptemp1;
			pFR = pFL; pFL = pBL; pBL = pBR; pBR = ptemp2;
			cycleColours(F, B_FACE, L_FACE, F_FACE);
			cycleColours(FR, L_FACE, F_FACE, R_FACE);
			cycleColours(CR, L_FACE, F_FACE, R_FACE);
			cycleColours(BR, F_FACE, R_FACE, B_FACE);
			cycleColours(B, F_FACE, R_FACE, B_FACE);
			cycleColours(BL, R_FACE, B_FACE, L_FACE);
			cycleColours(CL, R_FACE, B_FACE, L_FACE);
			cycleColours(FL, B_FACE, L_FACE, F_FACE);
		} else {
			int[] temp1 = FRU; int[] temp2 = CRU;		
			FRU = BRU; CRU = BU; BRU = BLU; BU = CLU; BLU = FLU; CLU = FU; FLU = temp1; FU = temp2;
			Point3D ptemp1 = pFRU; Point3D ptemp2 = pCRU;		
			pFRU = pBRU; pCRU = pBU; pBRU = pBLU; pBU = pCLU; pBLU = pFLU; pCLU = pFU; pFLU = ptemp1; pFU = ptemp2;
			cycleColours(FU, B_FACE, R_FACE, F_FACE);
			cycleColours(FRU, B_FACE, R_FACE, F_FACE);
			cycleColours(CRU, L_FACE, B_FACE, R_FACE);
			cycleColours(BRU, L_FACE, B_FACE, R_FACE);
			cycleColours(BU, F_FACE, L_FACE, B_FACE);
			cycleColours(BLU, F_FACE, L_FACE, B_FACE);
			cycleColours(CLU, R_FACE, F_FACE, L_FACE);
			cycleColours(FLU, R_FACE, F_FACE, L_FACE);	
			temp1 = CLD; temp2 = FLD;
			CLD = FD; FLD = FRD; FD = CRD; FRD = BRD; CRD = BD; BRD = BLD; BD = temp1; BLD = temp2;
			ptemp1 = pCLD; ptemp2 = pFLD;
			pCLD = pFD; pFLD = pFRD; pFD = pCRD; pFRD = pBRD; pCRD = pBD; pBRD = pBLD; pBD = ptemp1; pBLD = ptemp2;  
			cycleColours(FLD, R_FACE, F_FACE, L_FACE);
			cycleColours(CLD, R_FACE, F_FACE, L_FACE);
			cycleColours(BLD, F_FACE, L_FACE, B_FACE); 
			cycleColours(BD, F_FACE, L_FACE, B_FACE);
			cycleColours(BRD, L_FACE, B_FACE, R_FACE);
			cycleColours(CRD, L_FACE, B_FACE, R_FACE);
			cycleColours(FRD, B_FACE, R_FACE, F_FACE);
			cycleColours(FD, B_FACE, R_FACE, F_FACE);
			temp1 = F; temp2 = FR; ptemp1 = pF; ptemp2 = pFR;
			F = CR; CR = B; B = CL; CL = temp1;
			FR = BR; BR = BL; BL = FL; FL = temp2;
			pF = pCR; pCR = pB; pB = pCL; pCL = ptemp1;
			pFR = pBR; pBR = pBL; pBL = pFL; pFL = ptemp2;
			cycleColours(FL, R_FACE, F_FACE, L_FACE);
			cycleColours(F, R_FACE, F_FACE, L_FACE);
			cycleColours(FR, B_FACE, R_FACE, F_FACE);
			cycleColours(CR, B_FACE, R_FACE, F_FACE);
			cycleColours(BR, L_FACE, B_FACE, R_FACE);
			cycleColours(B, L_FACE, B_FACE, R_FACE);
			cycleColours(BL, F_FACE, L_FACE, B_FACE);
			cycleColours(CL, F_FACE, L_FACE, B_FACE);
		}
		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
	}

	static void makeY2rotation() {
		for (int x = 0; x < pointsFaceF.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(x*2);
        	Point3D pt = pointsFaceF.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(500), msh);
        	rt.setAxis(Rotate.Y_AXIS);
        	rt.setByAngle(180);
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(x*2, msh);
        }
		
		int[] temp1 = FRU; int[] temp2 = CRU;		
		FRU = BRU; CRU = BU; BRU = BLU; BU = CLU; BLU = FLU; CLU = FU; FLU = temp1; FU = temp2;
		Point3D ptemp1 = pFRU; Point3D ptemp2 = pCRU;		
		pFRU = pBRU; pCRU = pBU; pBRU = pBLU; pBU = pCLU; pBLU = pFLU; pCLU = pFU; pFLU = ptemp1; pFU = ptemp2;
		cycleColours(FU, B_FACE, R_FACE, F_FACE);
		cycleColours(FRU, B_FACE, R_FACE, F_FACE);
		cycleColours(CRU, L_FACE, B_FACE, R_FACE);
		cycleColours(BRU, L_FACE, B_FACE, R_FACE);
		cycleColours(BU, F_FACE, L_FACE, B_FACE);
		cycleColours(BLU, F_FACE, L_FACE, B_FACE);
		cycleColours(CLU, R_FACE, F_FACE, L_FACE);
		cycleColours(FLU, R_FACE, F_FACE, L_FACE);	
		temp1 = CLD; temp2 = FLD;
		CLD = FD; FLD = FRD; FD = CRD; FRD = BRD; CRD = BD; BRD = BLD; BD = temp1; BLD = temp2;
		ptemp1 = pCLD; ptemp2 = pFLD;
		pCLD = pFD; pFLD = pFRD; pFD = pCRD; pFRD = pBRD; pCRD = pBD; pBRD = pBLD; pBD = ptemp1; pBLD = ptemp2;  
		cycleColours(FLD, R_FACE, F_FACE, L_FACE);
		cycleColours(CLD, R_FACE, F_FACE, L_FACE);
		cycleColours(BLD, F_FACE, L_FACE, B_FACE); 
		cycleColours(BD, F_FACE, L_FACE, B_FACE);
		cycleColours(BRD, L_FACE, B_FACE, R_FACE);
		cycleColours(CRD, L_FACE, B_FACE, R_FACE);
		cycleColours(FRD, B_FACE, R_FACE, F_FACE);
		cycleColours(FD, B_FACE, R_FACE, F_FACE);
		temp1 = F; temp2 = FR; ptemp1 = pF; ptemp2 = pFR;
		F = CR; CR = B; B = CL; CL = temp1;
		FR = BR; BR = BL; BL = FL; FL = temp2;
		pF = pCR; pCR = pB; pB = pCL; pCL = ptemp1;
		pFR = pBR; pBR = pBL; pBL = pFL; pFL = ptemp2;
		cycleColours(FL, R_FACE, F_FACE, L_FACE);
		cycleColours(F, R_FACE, F_FACE, L_FACE);
		cycleColours(FR, B_FACE, R_FACE, F_FACE);
		cycleColours(CR, B_FACE, R_FACE, F_FACE);
		cycleColours(BR, L_FACE, B_FACE, R_FACE);
		cycleColours(B, L_FACE, B_FACE, R_FACE);
		cycleColours(BL, F_FACE, L_FACE, B_FACE);
		cycleColours(CL, F_FACE, L_FACE, B_FACE);
		
		temp1 = FRU; temp2 = CRU;		
		FRU = BRU; CRU = BU; BRU = BLU; BU = CLU; BLU = FLU; CLU = FU; FLU = temp1; FU = temp2;
		ptemp1 = pFRU; ptemp2 = pCRU;		
		pFRU = pBRU; pCRU = pBU; pBRU = pBLU; pBU = pCLU; pBLU = pFLU; pCLU = pFU; pFLU = ptemp1; pFU = ptemp2;
		cycleColours(FU, B_FACE, R_FACE, F_FACE);
		cycleColours(FRU, B_FACE, R_FACE, F_FACE);
		cycleColours(CRU, L_FACE, B_FACE, R_FACE);
		cycleColours(BRU, L_FACE, B_FACE, R_FACE);
		cycleColours(BU, F_FACE, L_FACE, B_FACE);
		cycleColours(BLU, F_FACE, L_FACE, B_FACE);
		cycleColours(CLU, R_FACE, F_FACE, L_FACE);
		cycleColours(FLU, R_FACE, F_FACE, L_FACE);	
		temp1 = CLD; temp2 = FLD;
		CLD = FD; FLD = FRD; FD = CRD; FRD = BRD; CRD = BD; BRD = BLD; BD = temp1; BLD = temp2;
		ptemp1 = pCLD; ptemp2 = pFLD;
		pCLD = pFD; pFLD = pFRD; pFD = pCRD; pFRD = pBRD; pCRD = pBD; pBRD = pBLD; pBD = ptemp1; pBLD = ptemp2;  
		cycleColours(FLD, R_FACE, F_FACE, L_FACE);
		cycleColours(CLD, R_FACE, F_FACE, L_FACE);
		cycleColours(BLD, F_FACE, L_FACE, B_FACE); 
		cycleColours(BD, F_FACE, L_FACE, B_FACE);
		cycleColours(BRD, L_FACE, B_FACE, R_FACE);
		cycleColours(CRD, L_FACE, B_FACE, R_FACE);
		cycleColours(FRD, B_FACE, R_FACE, F_FACE);
		cycleColours(FD, B_FACE, R_FACE, F_FACE);
		temp1 = F; temp2 = FR; ptemp1 = pF; ptemp2 = pFR;
		F = CR; CR = B; B = CL; CL = temp1;
		FR = BR; BR = BL; BL = FL; FL = temp2;
		pF = pCR; pCR = pB; pB = pCL; pCL = ptemp1;
		pFR = pBR; pBR = pBL; pBL = pFL; pFL = ptemp2;
		cycleColours(FL, R_FACE, F_FACE, L_FACE);
		cycleColours(F, R_FACE, F_FACE, L_FACE);
		cycleColours(FR, B_FACE, R_FACE, F_FACE);
		cycleColours(CR, B_FACE, R_FACE, F_FACE);
		cycleColours(BR, L_FACE, B_FACE, R_FACE);
		cycleColours(B, L_FACE, B_FACE, R_FACE);
		cycleColours(BL, F_FACE, L_FACE, B_FACE);
		cycleColours(CL, F_FACE, L_FACE, B_FACE);
		
		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
		
	}
	
	static void makeZrotation(boolean prime) {
		for (int x = 0; x < pointsFaceF.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(x*2);
        	Point3D pt = pointsFaceF.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.Z_AXIS);
        	if (prime) {
        		rt.setByAngle(-90);
        	} else {
        		rt.setByAngle(90);
        	}
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(x*2, msh);
        }
		
		if (prime) {
			int[] temp1 = FLU; int[] temp2 = FU;
			FLU = FRU; FU = FR; FRU = FRD; FR = FD; FRD = FLD; FD = FL; FLD = temp1; FL = temp2;
			Point3D ptemp1 = pFLU; Point3D ptemp2 = pFU;
			pFLU = pFRU; pFU = pFR; pFRU = pFRD; pFR = pFD; pFRD = pFLD; pFD = pFL; pFLD = ptemp1; pFL = ptemp2;
			cycleColours(FRU, D_FACE, R_FACE, U_FACE);
			cycleColours(FR, D_FACE, R_FACE, U_FACE);
			cycleColours(FRD, L_FACE, D_FACE, R_FACE);
			cycleColours(FD, L_FACE, D_FACE, R_FACE);
			cycleColours(FLD, U_FACE, L_FACE, D_FACE);
			cycleColours(FL, U_FACE, L_FACE, D_FACE);
			cycleColours(FLU, R_FACE, U_FACE, L_FACE);
			cycleColours(FU, R_FACE, U_FACE, L_FACE);	
			temp1 = BLU; temp2 = BU;
			ptemp1 = pBLU; ptemp2 = pBU;
			BLU = BRU; BU = BR; BRU = BRD; BR = BD; BRD = BLD; BD = BL; BLD = temp1; BL = temp2;
			pBLU = pBRU; pBU = pBR; pBRU = pBRD; pBR = pBD; pBRD = pBLD; pBD = pBL; pBLD = ptemp1; pBL = ptemp2;
			cycleColours(BLU, R_FACE, U_FACE, L_FACE);
			cycleColours(BL, R_FACE, U_FACE, L_FACE);
			cycleColours(BLD, U_FACE, L_FACE, D_FACE);
			cycleColours(BD, U_FACE, L_FACE, D_FACE);
			cycleColours(BRD, L_FACE, D_FACE, R_FACE);
			cycleColours(BR, L_FACE, D_FACE, R_FACE);
			cycleColours(BRU, D_FACE, R_FACE, U_FACE);
			cycleColours(BU, D_FACE, R_FACE, U_FACE);
			temp1 = CU; temp2 = CRU;
			ptemp1 = pCU; ptemp2 = pCRU;
			CU = CR; CR = CD; CD = CL; CL = temp1;
			CRU = CRD; CRD = CLD; CLD = CLU; CLU = temp2;
			pCU = pCR; pCR = pCD; pCD = pCL; pCL = ptemp1;
			pCRU = pCRD; pCRD = pCLD; pCLD = pCLU; pCLU = ptemp2;
			cycleColours(CU, D_FACE, R_FACE, U_FACE);
			cycleColours(CRU, D_FACE, R_FACE, U_FACE);
			cycleColours(CR, L_FACE, D_FACE, R_FACE);
			cycleColours(CRD, L_FACE, D_FACE, R_FACE);
			cycleColours(CD, U_FACE, L_FACE, D_FACE);
			cycleColours(CLD, U_FACE, L_FACE, D_FACE);
			cycleColours(CL, R_FACE, U_FACE, L_FACE);
			cycleColours(CLU, R_FACE, U_FACE, L_FACE);
		} else {
			int[] temp1 = FU; int[] temp2 = FLU;
			FU = FL; FLU = FLD; FL = FD; FLD = FRD; FD = FR; FRD = FRU; FR = temp1; FRU = temp2;
			Point3D ptemp1 = pFU; Point3D ptemp2 = pFLU;
			pFU = pFL; pFLU = pFLD; pFL = pFD; pFLD = pFRD; pFD = pFR; pFRD = pFRU; pFR = ptemp1; pFRU = ptemp2;
			cycleColours(FRU, L_FACE, U_FACE, R_FACE);
			cycleColours(FR, L_FACE, U_FACE, R_FACE);
			cycleColours(FRD, U_FACE, R_FACE, D_FACE);
			cycleColours(FD, U_FACE, R_FACE, D_FACE);
			cycleColours(FLD, R_FACE, D_FACE, L_FACE);
			cycleColours(FL, R_FACE, D_FACE, L_FACE);
			cycleColours(FLU, D_FACE, L_FACE, U_FACE);
			cycleColours(FU, D_FACE, L_FACE, U_FACE);
			temp1 = BLD; temp2 = BD;
			BLD = BRD; BD = BR; BRD = BRU; BR = BU; BRU = BLU; BU = BL; BLU = temp1; BL = temp2;
			ptemp1 = pBLD; ptemp2 = pBD;
			pBLD = pBRD; pBD = pBR; pBRD = pBRU; pBR = pBU; pBRU = pBLU; pBU = pBL; pBLU = ptemp1; pBL = ptemp2;
			cycleColours(BLU, D_FACE, L_FACE, U_FACE);
			cycleColours(BU, D_FACE, L_FACE, U_FACE);
			cycleColours(BRU, L_FACE, U_FACE, R_FACE);
			cycleColours(BR, L_FACE, U_FACE, R_FACE);
			cycleColours(BRD, U_FACE, R_FACE, D_FACE);
			cycleColours(BD, U_FACE, R_FACE, D_FACE);
			cycleColours(BLD, R_FACE, D_FACE, L_FACE);
			cycleColours(BL, R_FACE, D_FACE, L_FACE);
			temp1 = CU; temp2 = CRU;
			CU = CL; CL = CD; CD = CR; CR = temp1;
			CRU = CLU; CLU = CLD; CLD = CRD; CRD = temp2;
			//ptemp1 = pCU; ptemp2 = pCRU;
			//pCU = pCL; pCL = pCD; pCD = pCR; pCR = ptemp1;
			//pCRU = pCLU; pCLU = pCLD; pCLD = pCRD; pCRD = ptemp2;
			cycleColours(CU, D_FACE, L_FACE, U_FACE);
			cycleColours(CRU, L_FACE, U_FACE, R_FACE);
			cycleColours(CR, L_FACE, U_FACE, R_FACE);
			cycleColours(CRD, U_FACE, R_FACE, D_FACE);
			cycleColours(CD, U_FACE, R_FACE, D_FACE);
			cycleColours(CLD, R_FACE, D_FACE, L_FACE);
			cycleColours(CL, R_FACE, D_FACE, L_FACE);
			cycleColours(CLU, D_FACE, L_FACE, U_FACE);
		}
		
		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
	}

	static void makeZ2rotation() {
		for (int x = 0; x < pointsFaceF.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(x*2);
        	Point3D pt = pointsFaceF.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(450), msh);
        	rt.setAxis(Rotate.Z_AXIS);
        	rt.setByAngle(180);
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(x*2, msh);
        }
		
		int[] temp1 = FU; int[] temp2 = FLU;
		FU = FL; FLU = FLD; FL = FD; FLD = FRD; FD = FR; FRD = FRU; FR = temp1; FRU = temp2;
		Point3D ptemp1 = pFU; Point3D ptemp2 = pFLU;
		pFU = pFL; pFLU = pFLD; pFL = pFD; pFLD = pFRD; pFD = pFR; pFRD = pFRU; pFR = ptemp1; pFRU = ptemp2;
		cycleColours(FRU, L_FACE, U_FACE, R_FACE);
		cycleColours(FR, L_FACE, U_FACE, R_FACE);
		cycleColours(FRD, U_FACE, R_FACE, D_FACE);
		cycleColours(FD, U_FACE, R_FACE, D_FACE);
		cycleColours(FLD, R_FACE, D_FACE, L_FACE);
		cycleColours(FL, R_FACE, D_FACE, L_FACE);
		cycleColours(FLU, D_FACE, L_FACE, U_FACE);
		cycleColours(FU, D_FACE, L_FACE, U_FACE);
		temp1 = BLD; temp2 = BD;
		BLD = BRD; BD = BR; BRD = BRU; BR = BU; BRU = BLU; BU = BL; BLU = temp1; BL = temp2;
		ptemp1 = pBLD; ptemp2 = pBD;
		pBLD = pBRD; pBD = pBR; pBRD = pBRU; pBR = pBU; pBRU = pBLU; pBU = pBL; pBLU = ptemp1; pBL = ptemp2;
		cycleColours(BLU, D_FACE, L_FACE, U_FACE);
		cycleColours(BU, D_FACE, L_FACE, U_FACE);
		cycleColours(BRU, L_FACE, U_FACE, R_FACE);
		cycleColours(BR, L_FACE, U_FACE, R_FACE);
		cycleColours(BRD, U_FACE, R_FACE, D_FACE);
		cycleColours(BD, U_FACE, R_FACE, D_FACE);
		cycleColours(BLD, R_FACE, D_FACE, L_FACE);
		cycleColours(BL, R_FACE, D_FACE, L_FACE);
		temp1 = CU; temp2 = CRU;
		CU = CL; CL = CD; CD = CR; CR = temp1;
		CRU = CLU; CLU = CLD; CLD = CRD; CRD = temp2;
		//ptemp1 = pCU; ptemp2 = pCRU;
		//pCU = pCL; pCL = pCD; pCD = pCR; pCR = ptemp1;
		//pCRU = pCLU; pCLU = pCLD; pCLD = pCRD; pCRD = ptemp2;
		cycleColours(CU, D_FACE, L_FACE, U_FACE);
		cycleColours(CRU, L_FACE, U_FACE, R_FACE);
		cycleColours(CR, L_FACE, U_FACE, R_FACE);
		cycleColours(CRD, U_FACE, R_FACE, D_FACE);
		cycleColours(CD, U_FACE, R_FACE, D_FACE);
		cycleColours(CLD, R_FACE, D_FACE, L_FACE);
		cycleColours(CL, R_FACE, D_FACE, L_FACE);
		cycleColours(CLU, D_FACE, L_FACE, U_FACE);	
		
		temp1 = FU; temp2 = FLU;
		FU = FL; FLU = FLD; FL = FD; FLD = FRD; FD = FR; FRD = FRU; FR = temp1; FRU = temp2;
		ptemp1 = pFU; ptemp2 = pFLU;
		pFU = pFL; pFLU = pFLD; pFL = pFD; pFLD = pFRD; pFD = pFR; pFRD = pFRU; pFR = ptemp1; pFRU = ptemp2;
		cycleColours(FRU, L_FACE, U_FACE, R_FACE);
		cycleColours(FR, L_FACE, U_FACE, R_FACE);
		cycleColours(FRD, U_FACE, R_FACE, D_FACE);
		cycleColours(FD, U_FACE, R_FACE, D_FACE);
		cycleColours(FLD, R_FACE, D_FACE, L_FACE);
		cycleColours(FL, R_FACE, D_FACE, L_FACE);
		cycleColours(FLU, D_FACE, L_FACE, U_FACE);
		cycleColours(FU, D_FACE, L_FACE, U_FACE);
		temp1 = BLD; temp2 = BD;
		BLD = BRD; BD = BR; BRD = BRU; BR = BU; BRU = BLU; BU = BL; BLU = temp1; BL = temp2;
		ptemp1 = pBLD; ptemp2 = pBD;
		pBLD = pBRD; pBD = pBR; pBRD = pBRU; pBR = pBU; pBRU = pBLU; pBU = pBL; pBLU = ptemp1; pBL = ptemp2;
		cycleColours(BLU, D_FACE, L_FACE, U_FACE);
		cycleColours(BU, D_FACE, L_FACE, U_FACE);
		cycleColours(BRU, L_FACE, U_FACE, R_FACE);
		cycleColours(BR, L_FACE, U_FACE, R_FACE);
		cycleColours(BRD, U_FACE, R_FACE, D_FACE);
		cycleColours(BD, U_FACE, R_FACE, D_FACE);
		cycleColours(BLD, R_FACE, D_FACE, L_FACE);
		cycleColours(BL, R_FACE, D_FACE, L_FACE);
		temp1 = CU; temp2 = CRU;
		CU = CL; CL = CD; CD = CR; CR = temp1;
		CRU = CLU; CLU = CLD; CLD = CRD; CRD = temp2;
		//ptemp1 = pCU; ptemp2 = pCRU;
		//pCU = pCL; pCL = pCD; pCD = pCR; pCR = ptemp1;
		//pCRU = pCLU; pCLU = pCLD; pCLD = pCRD; pCRD = ptemp2;
		cycleColours(CU, D_FACE, L_FACE, U_FACE);
		cycleColours(CRU, L_FACE, U_FACE, R_FACE);
		cycleColours(CR, L_FACE, U_FACE, R_FACE);
		cycleColours(CRD, U_FACE, R_FACE, D_FACE);
		cycleColours(CD, U_FACE, R_FACE, D_FACE);
		cycleColours(CLD, R_FACE, D_FACE, L_FACE);
		cycleColours(CL, R_FACE, D_FACE, L_FACE);
		cycleColours(CLU, D_FACE, L_FACE, U_FACE);	
	
	patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
	}
	
	private static void cycleColours(int[] list, int one, int two, int three) {
		int temp = list[three];
		list[three] = list[two];
		list[two] = list[one];
		list[one] = temp;
	}
   
    
    private static TriangleMesh createCube(int[] face) {
        TriangleMesh m = new TriangleMesh();

        // POINTS
        m.getPoints().addAll(
             0.5f,  0.5f,  0.5f,
             0.5f, -0.5f,  0.5f,
             0.5f,  0.5f, -0.5f,
             0.5f, -0.5f, -0.5f,
            -0.5f,  0.5f,  0.5f,
            -0.5f, -0.5f,  0.5f,
            -0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f
        );

        // TEXTURES
        m.getTexCoords().addAll(
            X_RED, 0.5f, 
            X_GREEN, 0.5f,
            X_BLUE, 0.5f, 
            X_YELLOW, 0.5f, 
            X_ORANGE, 0.5f,  
            X_WHITE, 0.5f,
            X_GRAY, 0.5f
        );

            // FACES
        m.getFaces().addAll(
            2,face[0],3,face[0],6,face[0],      // F      
            3,face[0],7,face[0],6,face[0],  

            0,face[1],1,face[1],2,face[1],      // R     
            2,face[1],1,face[1],3,face[1],         

            1,face[2],5,face[2],3,face[2],      // U   
            5,face[2],7,face[2],3,face[2],

            0,face[3],4,face[3],1,face[3],      // B      
            4,face[3],5,face[3],1,face[3],       

            4,face[4],6,face[4],5,face[4],      // L      
            6,face[4],7,face[4],5,face[4],    

            0,face[5],2,face[5],4,face[5],      // D      
            2,face[5],6,face[5],4,face[5]         
        );
        return m;
    }
       
    public static void isSolved() {

    	int frontCentre = F[0];
    	int rightCentre = CR[1];
    	int upCentre = CU[2];
    	int backCentre = B[3];
    	int leftCentre = CL[4];
    	int downCentre = CD[5];

    	solvedFLD  = new int[]{frontCentre, GRAY, GRAY, GRAY, leftCentre, downCentre};
    	solvedFD   = new int[]{frontCentre, GRAY, GRAY, GRAY, GRAY, downCentre};
    	solvedFRD  = new int[]{frontCentre, rightCentre, GRAY, GRAY, GRAY, downCentre};
    	solvedFL   = new int[]{frontCentre, GRAY, GRAY, GRAY, leftCentre, GRAY};
    	solvedF    = new int[]{frontCentre, GRAY, GRAY, GRAY, GRAY, GRAY}; //F face centre piece.
    	solvedFR   = new int[]{frontCentre, rightCentre, GRAY, GRAY, GRAY, GRAY};
    	solvedFLU  = new int[]{frontCentre, GRAY, upCentre, GRAY, leftCentre, GRAY};
    	solvedFU   = new int[]{frontCentre, GRAY, upCentre, GRAY, GRAY, GRAY};
    	solvedFRU  = new int[]{frontCentre, rightCentre, upCentre, GRAY, GRAY, GRAY};

    	solvedCLD  = new int[]{GRAY, GRAY, GRAY, GRAY, leftCentre, downCentre};
    	solvedCD   = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, downCentre}; //D face centre piece
    	solvedCRD  = new int[]{GRAY, rightCentre, GRAY, GRAY, GRAY, downCentre};
    	solvedCL   = new int[]{GRAY, GRAY, GRAY, GRAY, leftCentre, GRAY}; //L face centre piece
    	solvedC    = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, GRAY}; //Invisible core
    	solvedCR   = new int[]{GRAY, rightCentre, GRAY, GRAY, GRAY, GRAY}; //R face centre piece
    	solvedCLU  = new int[]{GRAY, GRAY, upCentre, GRAY, leftCentre, GRAY};
    	solvedCU   = new int[]{GRAY, GRAY, upCentre, GRAY, GRAY, GRAY}; //U face centre piece
    	solvedCRU  = new int[]{GRAY, rightCentre, upCentre, GRAY, GRAY, GRAY};

 		solvedBLD  = new int[]{GRAY, GRAY, GRAY, backCentre, leftCentre, downCentre};
 		solvedBD   = new int[]{GRAY, GRAY, GRAY, backCentre, GRAY, downCentre};
 		solvedBRD  = new int[]{GRAY, rightCentre, GRAY, backCentre, GRAY, downCentre};
 		solvedBL   = new int[]{GRAY, GRAY, GRAY, backCentre, leftCentre, GRAY};
 		solvedB    = new int[]{GRAY, GRAY, GRAY, backCentre, GRAY, GRAY}; //B face centre piece
 		solvedBR   = new int[]{GRAY, rightCentre, GRAY, backCentre, GRAY, GRAY};
 		solvedBLU  = new int[]{GRAY, GRAY, upCentre, backCentre, leftCentre, GRAY};
 		solvedBU   = new int[]{GRAY, GRAY, upCentre, backCentre, GRAY, GRAY};
 		solvedBRU  = new int[]{GRAY, rightCentre, upCentre, backCentre, GRAY, GRAY};
 		
 		int[][] solvedPieces = {solvedFLD, solvedFD, solvedFRD, solvedFL, solvedF, solvedFR, solvedFLU, solvedFU, solvedFRU,
 								solvedCLD, solvedCD, solvedCRD, solvedCL, solvedC, solvedCR, solvedCLU, solvedCU, solvedCRU,
 								solvedBLD, solvedBD, solvedBRD, solvedBL, solvedB, solvedBR, solvedBLU, solvedBU, solvedBRU};
 		
 		int[][] currentPieces = {FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
 							     CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
 							     BLD, BD, BRD, BL, B, BR, BLU, BU, BRU};
 		
 		boolean solved = true;
 		for (int i = 0; i < 27; i++) {
 			for (int x = 0; x < 6; x++) {
 				if (solvedPieces[i][x] != currentPieces[i][x]) {
 					//System.out.println("Not solved");
 					solved = false;
 					i = 27; x = 6;
 					break;
 				}
 			}
 		}
 		
 		//System.out.println(solved);

    }
    
    public static void yushengScramble() {
    	makeFmove(false); makeU2move(); makeL2move(); makeB2move(); makeFmove(true); makeUmove(false); makeL2move();
    	makeUmove(false); makeR2move(); makeD2move(); makeLmove(true); makeBmove(false); makeL2move(); makeBmove(true);
    	makeR2move(); makeU2move();
    	//F U2 L2 B2 F' U L2 U R2 D2 L' B L2 B' R2 U2
    }
    
    
    
    public void playRotate() {
    	timeline.play();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}