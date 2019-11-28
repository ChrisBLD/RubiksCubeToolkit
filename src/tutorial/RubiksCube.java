package tutorial;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Simple implementation of the Rubik's cube using JavaFX 3D
 * http://stackoverflow.com/questions/34001900/how-to-render-3d-graphics-properly
 * @author JosePereda
 */
public class RubiksCube extends Application {
	
	
	
	
	
	//https://stackoverflow.com/questions/38164068/how-to-overlay-gui-over-3d-scene-in-javafx 
	//FOR SUBSCENE TO OVERLAY GUI ^^^^^^^^^^^^^^^^
    
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
    
    
    
    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    
    private int moveCounter = 0;
    
    private PhongMaterial mat = new PhongMaterial();
    
    @Override
    public void start(Stage primaryStage) {

        Group sceneRoot = new Group();
        SubScene subScene = new SubScene(sceneRoot, 500, 500, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.SILVER);
        Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        //camera.setTranslateZ(-15);
        camera.getTransforms().addAll (
                pivot,
                yRotate,
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -15)
        );
        
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(0), 
                new KeyValue(yRotate.angleProperty(), 0)
        ));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(15), 
                new KeyValue(yRotate.angleProperty(), 360)
        ));
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        subScene.setCamera(camera);

        mat.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/tutcolours.png")));
        Group meshGroup = new Group();

        buildMesh(sceneRoot, mat, meshGroup);


        
       // sceneRoot.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
        
        BorderPane pane = new BorderPane();
        pane.setCenter(subScene);
        Button button = new Button("Reset");
        ToolBar toolBar = new ToolBar(button);
        toolBar.setOrientation(Orientation.HORIZONTAL);
        toolBar.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setBottom(toolBar);
        ToolBar toolBarRight = new ToolBar(button);
        toolBarRight.setOrientation(Orientation.VERTICAL);
        toolBarRight.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setRight(toolBarRight);
        pane.setPrefSize(300, 300);
        toolBar.setPrefWidth(100);
        toolBar.setPrefHeight(100);
        toolBarRight.setPrefWidth(100);
        toolBarRight.setPrefHeight(700);

        //Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
        //Rotate rotateY = new Rotate(15,Rotate.Y_AXIS);
        
        Scene scene = new Scene(pane);
        scene.setFill(Color.BLACK);
        
        scene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        /*scene.setOnMouseDragged(me -> {

            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            rotateX.setAngle(rotateX.getAngle()+(mousePosY - mouseOldY));
            rotateY.setAngle(rotateY.getAngle()-(mouseOldX - mousePosX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
        });*/
        scene.setOnKeyPressed(e -> {
        	if (e.getCode() == KeyCode.R) {
        		makeRmove(sceneRoot, meshGroup, false);
        	} else if (e.getCode() == KeyCode.E) {
        		makeRmove(sceneRoot, meshGroup, true);
        	} else if (e.getCode() == KeyCode.U) {
        		makeUmove(sceneRoot, meshGroup, false);
        	} else if (e.getCode() == KeyCode.Y) {
        		makeUmove(sceneRoot, meshGroup, true);
        	} else if (e.getCode() == KeyCode.F) {
        		makeFmove(sceneRoot, meshGroup, false);
        	} else if (e.getCode() == KeyCode.G) {
        		makeFmove(sceneRoot, meshGroup, true);
        	} else if (e.getCode() == KeyCode.D) { 
        		makeDmove(sceneRoot, meshGroup, false);
        	} else if (e.getCode() == KeyCode.V) {
        		makeDmove(sceneRoot, meshGroup, true);
        	}
        });
        
        
        
        
        primaryStage.setTitle("Simple Rubik's Cube - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	private void buildMesh(Group sceneRoot, PhongMaterial mat, Group meshGroup) {
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

	private void makeRmove(Group sceneRoot, Group meshGroup, boolean prime) {
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
	}
	
	private void makeFmove(Group sceneRoot, Group meshGroup, boolean prime) {
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
		
	}
	
	private void makeUmove(Group sceneRoot, Group meshGroup, boolean prime) {
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
		
	}

	private void makeDmove(Group sceneRoot, Group meshGroup, boolean prime) {
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
		
	}
	
	
	private static void cycleColours(int[] list, int one, int two, int three) {
		int temp = list[three];
		list[three] = list[two];
		list[two] = list[one];
		list[one] = temp;
	}
    
    //These definitions go "layer by layer", defining the colours for each side of a single piece. 
    //I'm going to have to add a listener of some sort to each of these points so I can track the colour currently at that location.
    //I'd imagine these points are fixed and I could rotate the actual blocks without affecting them? Not sure though.
    
    //Next task is to figure out how to make these objects rotate together around a centre pivot located in the middle of thats face's centre.
    
                                              // F   R   U   B   L   D
    private static int[] FLD  = new int[]{GREEN, GRAY, GRAY, GRAY, ORANGE, YELLOW};
    private static int[] FD   = new int[]{GREEN, GRAY, GRAY, GRAY, GRAY, YELLOW};
    private static int[] FRD  = new int[]{GREEN, RED, GRAY, GRAY, GRAY, YELLOW};
    private static int[] FL   = new int[]{GREEN, GRAY, GRAY, GRAY, ORANGE, GRAY};
    private static int[] F    = new int[]{GREEN, GRAY, GRAY, GRAY, GRAY, GRAY}; //F face centre piece.
    private static int[] FR   = new int[]{GREEN, RED, GRAY, GRAY, GRAY, GRAY};
    private static int[] FLU  = new int[]{GREEN, GRAY, WHITE, GRAY, ORANGE, GRAY};
    private static int[] FU   = new int[]{GREEN, GRAY, WHITE, GRAY, GRAY, GRAY};
    private static int[] FRU  = new int[]{GREEN, RED, WHITE, GRAY, GRAY, GRAY};
    
    private static Point3D pFLD   = new Point3D(-1.04,  1.04, -1.04);
    private static Point3D pFD    = new Point3D(   0,  1.04, -1.04);
    private static Point3D pFRD   = new Point3D( 1.04,  1.04, -1.04);
    private static Point3D pFL    = new Point3D(-1.04,    0, -1.04);
    private static Point3D pF     = new Point3D(   0,    0, -1.04);
    private static Point3D pFR    = new Point3D( 1.04,    0, -1.04);
    private static Point3D pFLU   = new Point3D(-1.04, -1.04, -1.04);
    private static Point3D pFU    = new Point3D(   0, -1.04, -1.04);
    private static Point3D pFRU   = new Point3D( 1.04, -1.04, -1.04);
    
    private static int[] CLD  = new int[]{GRAY, GRAY, GRAY, GRAY, ORANGE, YELLOW};
    private static int[] CD   = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, YELLOW}; //D face centre piece
    private static int[] CRD  = new int[]{GRAY, RED, GRAY, GRAY, GRAY, YELLOW};
    private static int[] CL   = new int[]{GRAY, GRAY, GRAY, GRAY, ORANGE, GRAY}; //L face centre piece
    private static int[] C    = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, GRAY}; //Invisible core
    private static int[] CR   = new int[]{GRAY, RED, GRAY, GRAY, GRAY, GRAY}; //R face centre piece
    private static int[] CLU  = new int[]{GRAY, GRAY, WHITE, GRAY, ORANGE, GRAY};
    private static int[] CU   = new int[]{GRAY, GRAY, WHITE, GRAY, GRAY, GRAY}; //U face centre piece
    private static int[] CRU  = new int[]{GRAY, RED, WHITE, GRAY, GRAY, GRAY};
    
    private static Point3D pCLD   = new Point3D(-1.04,  1.04, 0);
    private static Point3D pCD    = new Point3D(   0,  1.04, 0);
    private static Point3D pCRD   = new Point3D( 1.04,  1.04, 0);
    private static Point3D pCL    = new Point3D(-1.04,    0, 0);
    private static Point3D pC     = new Point3D(   0,    0, 0);
    private static Point3D pCR    = new Point3D( 1.04,    0, 0);
    private static Point3D pCLU   = new Point3D(-1.04, -1.04, 0);
    private static Point3D pCU    = new Point3D(   0, -1.04, 0);
    private static Point3D pCRU   = new Point3D( 1.04, -1.04, 0);
    
    private static int[] BLD  = new int[]{GRAY, GRAY, GRAY, BLUE, ORANGE, YELLOW};
    private static int[] BD   = new int[]{GRAY, GRAY, GRAY, BLUE, GRAY, YELLOW};
    private static int[] BRD  = new int[]{GRAY, RED, GRAY, BLUE, GRAY, YELLOW};
    private static int[] BL   = new int[]{GRAY, GRAY, GRAY, BLUE, ORANGE, GRAY};
    private static int[] B    = new int[]{GRAY, GRAY, GRAY, BLUE, GRAY, GRAY}; //B face centre piece
    private static int[] BR   = new int[]{GRAY, RED, GRAY, BLUE, GRAY, GRAY};
    private static int[] BLU  = new int[]{GRAY, GRAY, WHITE, BLUE, ORANGE, GRAY};
    private static int[] BU   = new int[]{GRAY, GRAY, WHITE, BLUE, GRAY, GRAY};
    private static int[] BRU  = new int[]{GRAY, RED, WHITE, BLUE, GRAY, GRAY};
    
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
    
    private static List<Point3D> rightFacePoints = Arrays.asList(
    		pFRD, pFR, pFRU, pCRD, pCR, pCRU, pBRD, pBR, pBRU);
    
    private static List<Point3D> upFacePoints = Arrays.asList(
    		pFLU, pFU, pFRU, pCLU, pCU, pCRU, pBLU, pBU, pBRU);
    
    private static List<Point3D> frontFacePoints = Arrays.asList(
    		pFLD, pFD, pFRD, pFL, pF, pFR, pFLU, pFU, pFRU);
    
    private static List<Point3D> downFacePoints = Arrays.asList(
    		pFLD, pFD, pFRD, pCLD, pCD, pCRD, pBLD, pBD, pBRD);
    
    private static List<Point3D> leftFacePoints = Arrays.asList(
    		pFLD, pFL, pFLU, pCLD, pCL, pCLU, pBLD, pBL, pBLU);
    
    private static List<Point3D> backFacePoints = Arrays.asList(
    		pBLD, pBD, pBRD, pBL, pB, pBR, pBLU, pBU, pBRU);
    
    private static final List<Point3D> pointsFaceF = Arrays.asList(
            pFLD, pFD, pFRD, pFL, pF, pFR, pFLU, pFU, pFRU,
            pCLD, pCD, pCRD, pCL, pC, pCR, pCLU, pCU, pCRU,
            pBLD, pBD, pBRD, pBL, pB, pBR, pBLU, pBU, pBRU);
    
    private TriangleMesh createCube(int[] face) {
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

    public static void main(String[] args) {
        launch(args);
    }
    
}