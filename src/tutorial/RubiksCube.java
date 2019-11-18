package tutorial;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

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
        Scene scene = new Scene(sceneRoot, 600, 600, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BLACK);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-10);
        scene.setCamera(camera);

        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/tutcolours.png")));

        Group meshGroup = new Group();

        AtomicInteger cont = new AtomicInteger();
        patternFaceF.forEach(p -> {
            MeshView meshP = new MeshView();
            meshP.setMesh(createCube(p));
            meshP.setMaterial(mat);
            Point3D pt = pointsFaceF.get(cont.getAndIncrement());
            meshP.getTransforms().addAll(new Translate(pt.getX(), pt.getY(), pt.getZ()));
            meshGroup.getChildren().add(meshP);
        });
        
        Rotate rotateX = new Rotate(30, 0, 0, 0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(20, 0, 0, 0, Rotate.Y_AXIS);
        meshGroup.getTransforms().addAll(rotateX, rotateY);
        
        sceneRoot.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
        
        Box box = new Box(500,500,500);
        sceneRoot.getChildren().add(box);

        scene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(me -> {

            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            rotateX.setAngle(rotateX.getAngle()+(mousePosY - mouseOldY));
            rotateY.setAngle(rotateY.getAngle()-(mousePosX - mouseOldX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
        });
        
        primaryStage.setTitle("Simple Rubik's Cube - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
                                              // F   R   U   B   L   D
    private static final int[] FLD  = new int[]{BLUE, GRAY, GRAY, GRAY, ORANGE, WHITE};
    private static final int[] FD   = new int[]{BLUE, GRAY, GRAY, GRAY, GRAY, WHITE};
    private static final int[] FRD  = new int[]{BLUE, RED, GRAY, GRAY, GRAY, WHITE};
    private static final int[] FL   = new int[]{BLUE, GRAY, GRAY, GRAY, ORANGE, GRAY};
    private static final int[] F    = new int[]{BLUE, GRAY, GRAY, GRAY, GRAY, GRAY};
    private static final int[] FR   = new int[]{BLUE, RED, GRAY, GRAY, GRAY, GRAY};
    private static final int[] FLU  = new int[]{BLUE, GRAY, YELLOW, GRAY, ORANGE, GRAY};
    private static final int[] FU   = new int[]{BLUE, GRAY, YELLOW, GRAY, GRAY, GRAY};
    private static final int[] FRU  = new int[]{BLUE, RED, YELLOW, GRAY, GRAY, GRAY};
    
    private static final Point3D pFLD   = new Point3D(-1.04,  1.04, -1.04);
    private static final Point3D pFD    = new Point3D(   0,  1.04, -1.04);
    private static final Point3D pFRD   = new Point3D( 1.04,  1.04, -1.04);
    private static final Point3D pFL    = new Point3D(-1.04,    0, -1.04);
    private static final Point3D pF     = new Point3D(   0,    0, -1.04);
    private static final Point3D pFR    = new Point3D( 1.04,    0, -1.04);
    private static final Point3D pFLU   = new Point3D(-1.04, -1.04, -1.04);
    private static final Point3D pFU    = new Point3D(   0, -1.04, -1.04);
    private static final Point3D pFRU   = new Point3D( 1.04, -1.04, -1.04);
    
    private static final int[] CLD  = new int[]{GRAY, GRAY, GRAY, GRAY, ORANGE, WHITE};
    private static final int[] CD   = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, WHITE};
    private static final int[] CRD  = new int[]{GRAY, RED, GRAY, GRAY, GRAY, WHITE};
    private static final int[] CL   = new int[]{GRAY, GRAY, GRAY, GRAY, ORANGE, GRAY};
    private static final int[] C    = new int[]{GRAY, GRAY, GRAY, GRAY, GRAY, GRAY};
    private static final int[] CR   = new int[]{GRAY, RED, GRAY, GRAY, GRAY, GRAY};
    private static final int[] CLU  = new int[]{GRAY, GRAY, YELLOW, GRAY, ORANGE, GRAY};
    private static final int[] CU   = new int[]{GRAY, GRAY, YELLOW, GRAY, GRAY, GRAY};
    private static final int[] CRU  = new int[]{GRAY, RED, YELLOW, GRAY, GRAY, GRAY};
    
    private static final Point3D pCLD   = new Point3D(-1.04,  1.04, 0);
    private static final Point3D pCD    = new Point3D(   0,  1.04, 0);
    private static final Point3D pCRD   = new Point3D( 1.04,  1.04, 0);
    private static final Point3D pCL    = new Point3D(-1.04,    0, 0);
    private static final Point3D pC     = new Point3D(   0,    0, 0);
    private static final Point3D pCR    = new Point3D( 1.04,    0, 0);
    private static final Point3D pCLU   = new Point3D(-1.04, -1.04, 0);
    private static final Point3D pCU    = new Point3D(   0, -1.04, 0);
    private static final Point3D pCRU   = new Point3D( 1.04, -1.04, 0);
    
    private static final int[] BLD  = new int[]{GRAY, GRAY, GRAY, GREEN, ORANGE, WHITE};
    private static final int[] BD   = new int[]{GRAY, GRAY, GRAY, GREEN, GRAY, WHITE};
    private static final int[] BRD  = new int[]{GRAY, RED, GRAY, GREEN, GRAY, WHITE};
    private static final int[] BL   = new int[]{GRAY, GRAY, GRAY, GREEN, ORANGE, GRAY};
    private static final int[] B    = new int[]{GRAY, GRAY, GRAY, GREEN, GRAY, GRAY};
    private static final int[] BR   = new int[]{GRAY, RED, GRAY, GREEN, GRAY, GRAY};
    private static final int[] BLU  = new int[]{GRAY, GRAY, YELLOW, GREEN, ORANGE, GRAY};
    private static final int[] BU   = new int[]{GRAY, GRAY, YELLOW, GREEN, GRAY, GRAY};
    private static final int[] BRU  = new int[]{GRAY, RED, YELLOW, GREEN, GRAY, GRAY};
    
    private static final Point3D pBLD   = new Point3D(-1.04,  1.04, 1.04);
    private static final Point3D pBD    = new Point3D(   0,  1.04, 1.04);
    private static final Point3D pBRD   = new Point3D( 1.04,  1.04, 1.04);
    private static final Point3D pBL    = new Point3D(-1.04,    0, 1.04);
    private static final Point3D pB     = new Point3D(   0,    0, 1.04);
    private static final Point3D pBR    = new Point3D( 1.04,    0, 1.04);
    private static final Point3D pBLU   = new Point3D(-1.04, -1.04, 1.04);
    private static final Point3D pBU    = new Point3D(   0, -1.04, 1.04);
    private static final Point3D pBRU   = new Point3D( 1.04, -1.04, 1.04);
    
    private static final List<int[]> patternFaceF = Arrays.asList(
            FLD, FD, FRD, FL, F, FR, FLU, FU, FRU,
            CLD, CD, CRD, CL, C, CR, CLU, CU, CRU,
            BLD, BD, BRD, BL, B, BR, BLU, BU, BRU);
    
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