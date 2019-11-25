package tutorial;

import java.awt.RenderingHints.Key;
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
    
    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    
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

        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/tutcolours.png")));

        Group meshGroup = new Group();

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
        		makeRmove(sceneRoot);
        	} else if (e.getCode() == KeyCode.U) {
        		makeUmove(sceneRoot);
        	}
        });
        
        
        
        
        primaryStage.setTitle("Simple Rubik's Cube - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	private void makeRmove(Group sceneRoot) {
		for (int x = 0; x < rightFacePoints.size(); x++) {
        	MeshView msh = (MeshView) sceneRoot.getChildren().get(((x+1)*6)-2);
        	Point3D pt = rightFacePoints.get(x);
        	msh.getTransforms().clear();
        	msh.getTransforms().add(new Translate(pt.getX(), pt.getY(), pt.getZ()));
        	RotateTransition rt = new RotateTransition(Duration.millis(300), msh);
        	rt.setAxis(Rotate.X_AXIS);
        	rt.setByAngle(-90);
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(((x+1)*6)-2, msh);
        }
	}
	
	private void makeUmove(Group sceneRoot) {
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
        	rt.setByAngle(-90);
        	rt.setCycleCount(1);
        	rt.play();
        	sceneRoot.getChildren().set(elem, msh);
        }		
	}
    
    //These definitions go "layer by layer", defining the colours for each side of a single piece. 
    //I'm going to have to add a listener of some sort to each of these points so I can track the colour currently at that location.
    //I'd imagine these points are fixed and I could rotate the actual blocks without affecting them? Not sure though.
    
    //Next task is to figure out how to make these objects rotate together around a centre pivot located in the middle of thats face's centre.
    
                                              // F   R   U   B   L   D
    private static final int[] FLD  = new int[]{GREEN, GRAY, GRAY, GRAY, ORANGE, YELLOW};
    private static final int[] FD   = new int[]{GREEN, GRAY, GRAY, GRAY, GRAY, YELLOW};
    private static final int[] FRD  = new int[]{GREEN, RED, GRAY, GRAY, GRAY, YELLOW};
    private static final int[] FL   = new int[]{GREEN, GRAY, GRAY, GRAY, ORANGE, GRAY};
    private static final int[] F    = new int[]{GREEN, GRAY, GRAY, GRAY, GRAY, GRAY}; //F face centre piece.
    private static final int[] FR   = new int[]{GREEN, RED, GRAY, GRAY, GRAY, GRAY};
    private static final int[] FLU  = new int[]{GREEN, GRAY, WHITE, GRAY, ORANGE, GRAY};
    private static final int[] FU   = new int[]{GREEN, GRAY, WHITE, GRAY, GRAY, GRAY};
    private static final int[] FRU  = new int[]{GREEN, RED, WHITE, GRAY, GRAY, GRAY};
    
    private static Point3D pFLD   = new Point3D(-1.04,  1.04, -1.04);
    private static Point3D pFD    = new Point3D(   0,  1.04, -1.04);
    private static Point3D pFRD   = new Point3D( 1.04,  1.04, -1.04);
    private static Point3D pFL    = new Point3D(-1.04,    0, -1.04);
    private static Point3D pF     = new Point3D(   0,    0, -1.04);
    private static Point3D pFR    = new Point3D( 1.04,    0, -1.04);
    private static Point3D pFLU   = new Point3D(-1.04, -1.04, -1.04);
    private static Point3D pFU    = new Point3D(   0, -1.04, -1.04);
    private static Point3D pFRU   = new Point3D( 1.04, -1.04, -1.04);
    
    private static final int[] CLD  = new int[]{GRAY, GRAY, GRAY, GRAY, ORANGE, YELLOW};
    private static final int[] CD   = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, YELLOW}; //D face centre piece
    private static final int[] CRD  = new int[]{GRAY, RED, GRAY, GRAY, GRAY, YELLOW};
    private static final int[] CL   = new int[]{GRAY, GRAY, GRAY, GRAY, ORANGE, GRAY}; //L face centre piece
    private static final int[] C    = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, GRAY}; //Invisible core
    private static final int[] CR   = new int[]{GRAY, RED, GRAY, GRAY, GRAY, GRAY}; //R face centre piece
    private static final int[] CLU  = new int[]{GRAY, GRAY, WHITE, GRAY, ORANGE, GRAY};
    private static final int[] CU   = new int[]{GRAY, GRAY, WHITE, GRAY, GRAY, GRAY}; //U face centre piece
    private static final int[] CRU  = new int[]{GRAY, RED, WHITE, GRAY, GRAY, GRAY};
    
    private static Point3D pCLD   = new Point3D(-1.04,  1.04, 0);
    private static Point3D pCD    = new Point3D(   0,  1.04, 0);
    private static Point3D pCRD   = new Point3D( 1.04,  1.04, 0);
    private static Point3D pCL    = new Point3D(-1.04,    0, 0);
    private static Point3D pC     = new Point3D(   0,    0, 0);
    private static Point3D pCR    = new Point3D( 1.04,    0, 0);
    private static Point3D pCLU   = new Point3D(-1.04, -1.04, 0);
    private static Point3D pCU    = new Point3D(   0, -1.04, 0);
    private static Point3D pCRU   = new Point3D( 1.04, -1.04, 0);
    
    private static final int[] BLD  = new int[]{GRAY, GRAY, GRAY, BLUE, ORANGE, YELLOW};
    private static final int[] BD   = new int[]{GRAY, GRAY, GRAY, BLUE, GRAY, YELLOW};
    private static final int[] BRD  = new int[]{GRAY, RED, GRAY, BLUE, GRAY, YELLOW};
    private static final int[] BL   = new int[]{GRAY, GRAY, GRAY, BLUE, ORANGE, GRAY};
    private static final int[] B    = new int[]{GRAY, GRAY, GRAY, BLUE, GRAY, GRAY}; //B face centre piece
    private static final int[] BR   = new int[]{GRAY, RED, GRAY, BLUE, GRAY, GRAY};
    private static final int[] BLU  = new int[]{GRAY, GRAY, WHITE, BLUE, ORANGE, GRAY};
    private static final int[] BU   = new int[]{GRAY, GRAY, WHITE, BLUE, GRAY, GRAY};
    private static final int[] BRU  = new int[]{GRAY, RED, WHITE, BLUE, GRAY, GRAY};
    
    private static Point3D pBLD   = new Point3D(-1.04,  1.04, 1.04);
    private static Point3D pBD    = new Point3D(   0,  1.04, 1.04);
    private static Point3D pBRD   = new Point3D( 1.04,  1.04, 1.04);
    private static Point3D pBL    = new Point3D(-1.04,    0, 1.04);
    private static Point3D pB     = new Point3D(   0,    0, 1.04);
    private static Point3D pBR    = new Point3D( 1.04,    0, 1.04);
    private static Point3D pBLU   = new Point3D(-1.04, -1.04, 1.04);
    private static Point3D pBU    = new Point3D(   0, -1.04, 1.04);
    private static Point3D pBRU   = new Point3D( 1.04, -1.04, 1.04);
    
    private static final List<int[]> patternFaceF = Arrays.asList(    		
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
    

    private static final List<Point3D> rightFacePoints = Arrays.asList(
    		pFRD, pFR, pFRU, pCRD, pCR, pCRU, pBRD, pBR, pBRU);
    
    private static final List<Point3D> upFacePoints = Arrays.asList(
    		pFLU, pFU, pFRU, pCLU, pCU, pCRU, pBLU, pBU, pBRU);
    
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