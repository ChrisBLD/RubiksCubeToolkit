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
    
    
    private PhongMaterial mat = new PhongMaterial();
    
    @Override
    public void start(Stage primaryStage) {

        Group sceneRoot = new Group();
        SubScene subScene = new SubScene(sceneRoot, 500, 500, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.rgb(66,66,66));
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
                Duration.seconds(1), 
                new KeyValue(yRotate.angleProperty(), -35)
        ));
        
        timeline.setCycleCount(1);
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
        
        /*scene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(me -> {

            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            rotateX.setAngle(rotateX.getAngle()+(mousePosY - mouseOldY));
            rotateY.setAngle(rotateY.getAngle()-(mouseOldX - mousePosX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
        });*/
        scene.setOnKeyPressed(e -> {
        	
        	switch(e.getCode()) {
	        	case DIGIT5: makeMmove(sceneRoot, meshGroup, false); break;
	        	case DIGIT6: makeMmove(sceneRoot, meshGroup, false); break;
	        	case Q: makeZrotation(sceneRoot, meshGroup, true); break;
	        	case W: makeBmove(sceneRoot, meshGroup, false); break;
	        	case E: makeLmove(sceneRoot, meshGroup, true); break;
	        	case R: makeLwideMove(sceneRoot, meshGroup, true); break;
	        	case T: makeXrotation(sceneRoot, meshGroup, false); break;
	        	case Y: makeXrotation(sceneRoot, meshGroup, false); break;
	        	case U: makeRwideMove(sceneRoot, meshGroup, false); break;
	        	case I: makeRmove(sceneRoot, meshGroup, false); break;
	        	case O: makeBmove(sceneRoot, meshGroup, true); break;
	        	case P: makeZrotation(sceneRoot, meshGroup, false); break;
	        	case A: makeYrotation(sceneRoot, meshGroup, true); break;
	        	case S: makeDmove(sceneRoot, meshGroup, false); break;
	        	case D: makeLmove(sceneRoot, meshGroup, false); break;
	        	case F: makeUmove(sceneRoot, meshGroup, true); break;
	        	case G: makeFmove(sceneRoot, meshGroup, true); break;
	        	case H: makeFmove(sceneRoot, meshGroup, false); break;
	        	case J: makeUmove(sceneRoot, meshGroup, false); break;
	        	case K: makeRmove(sceneRoot, meshGroup, true); break;
	        	case L: makeDmove(sceneRoot, meshGroup, true); break;
	        	case SEMICOLON: makeYrotation(sceneRoot, meshGroup, false); break;
	        	case Z: makeDwideMove(sceneRoot, meshGroup, false); break;
	        	case X: makeMmove(sceneRoot, meshGroup, true); break;
	        	case C: makeUwideMove(sceneRoot, meshGroup, true); break;
	        	case V: makeLwideMove(sceneRoot, meshGroup, false); break;
	        	case B: makeXrotation(sceneRoot, meshGroup, true); break;
	        	case N: makeXrotation(sceneRoot, meshGroup, true); break;
	        	case M: makeRwideMove(sceneRoot, meshGroup, true); break;
	        	case COMMA: makeUwideMove(sceneRoot, meshGroup, false); break;
	        	case PERIOD: makeMmove(sceneRoot, meshGroup, true); break;
	        	case SLASH: makeDwideMove(sceneRoot, meshGroup, true); break;
	        	default: break;
        	}});
        
        //test
        
        
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
	
	private void makeBmove(Group sceneRoot, Group meshGroup, boolean prime) {
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
	
	private void makeLmove(Group sceneRoot, Group meshGroup, boolean prime) {
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
	}
	
	private void makeMmove(Group sceneRoot, Group meshGroup, boolean prime) {
		for (int x = 0; x < middleSlicePoints.size(); x++) {

        	MeshView msh = (MeshView) sceneRoot.getChildren().get((x*6)+2);
        	Point3D pt = middleSlicePoints.get(x);
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
        	sceneRoot.getChildren().set((x*6)+2, msh);
        }
		
		if (prime) {
			int[] temp1 = FU; int[] temp2 = F;
			FU = FD; FD = BD; BD = BU; BU = temp1;
			F = CD; CD = B; B = CU; CU = temp2;
			Point3D ptemp1 = pFU; Point3D ptemp2 = pF;
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
		} else {
			int[] temp1 = FU; int[] temp2 = F;
			FU = BU; BU = BD; BD = FD; FD = temp1;
			F = CU; CU = B; B = CD; CD = temp2;
			Point3D ptemp1 = pFU; Point3D ptemp2 = pF;
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
			
		}

		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);		
		
	}
	
	private void makeXrotation(Group sceneRoot, Group meshGroup, boolean prime) {
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
	
	private void makeYrotation(Group sceneRoot, Group meshGroup, boolean prime) {
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
	
	private void makeZrotation(Group sceneRoot, Group meshGroup, boolean prime) {
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

	private void makeRwideMove(Group sceneRoot, Group meshGroup, boolean prime) {
		int elem = 0;
		boolean oddeve = true;
		for (int x = 0; x < rightWideFacePoints.size(); x++) {
			elem = x*3;
			if (oddeve) {
				elem+= 2;
				oddeve = false;
			} else {
				elem+= 1;
				oddeve = true;
			}
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = rightWideFacePoints.get(x);
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
        	sceneRoot.getChildren().set(elem, msh);
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
			temp1 = FU; temp2 = F; ptemp1 = pFU; ptemp2 = pF;
			FU = FD; FD = BD; BD = BU; BU = temp1;
			F = CD; CD = B; B = CU; CU = temp2;
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
	
	private void makeLwideMove(Group sceneRoot, Group meshGroup, boolean prime) {
		int elem = 0;
		boolean oddeve = true;
		for (int x = 0; x < leftWideFacePoints.size(); x++) {
			elem = x*3;
			if (oddeve) {
				oddeve = false;
			} else {
				elem -= 1;
				oddeve = true;
			}
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = leftWideFacePoints.get(x);
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
        	sceneRoot.getChildren().set(elem, msh);
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
		} else {
			int[] temp1 = FLD; int[] temp2 = FL;
			FLD = FLU; FL = CLU; FLU = BLU; CLU = BL; BLU = BLD; BL = CLD; BLD = temp1; CLD = temp2;
			Point3D ptemp1 = pFLD; Point3D ptemp2 = pFL;
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
		}

		patternFaceF = Arrays.asList(    		
	            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
	            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
	            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
	}

	private void makeUwideMove(Group sceneRoot, Group meshGroup, boolean prime) {
		int elem = 0;
		for (int x = 0; x < upWideFacePoints.size(); x++) {
			if (x <= 5) {
				elem = 6+(x*2);
			} else if (x <= 11) {
				elem = 12+(x*2);
			} else {
				elem = 18+(x*2);
			}
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = upWideFacePoints.get(x);
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
	
	private void makeDwideMove(Group sceneRoot, Group meshGroup, boolean prime) {
		int elem = 0;
		for (int x = 0; x < downWideFacePoints.size(); x++) {
			if (x <= 5) {
				elem = (x*2);
			} else if (x <= 11) {
				elem = 6+(x*2);
			} else {
				elem = 12+(x*2);
			}
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(elem);
        	Point3D pt = downWideFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.Y_AXIS);
        	if (prime) {
        		rt.setByAngle(90);
        	} else {
        		rt.setByAngle(-90);
        	}
        	rt.setCycleCount(1);
    		rt.setOnFinished(e -> buildMesh(sceneRoot, mat, meshGroup));
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }
		
		if (prime) {
			int[] temp1 = CLD; int[] temp2 = FLD;
			CLD = FD; FLD = FRD; FD = CRD; FRD = BRD; CRD = BD; BRD = BLD; BD = temp1; BLD = temp2;
			Point3D ptemp1 = pCLD; Point3D ptemp2 = pFLD;
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
    
    private static List<Point3D> middleSlicePoints = Arrays.asList(
    		pFD, pF, pFU, pCD, pC, pCU, pBD, pB, pBU);
    
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