package main;

import averagebyalgs.MainRedesigned;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tutorial.UserInterface;
import virtualcube.RubiksCube;

public class Home extends Application {
	
	static UserInterface tut;
	static RubiksCube vc;
	static MainRedesigned aba;
	static boolean doubleTut = false;
	static boolean doubleCube = false;
	static boolean doubleABA = false;

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) {
		
		Group g = new Group();
		Scene scene = new Scene(g, 800, 500, Color.valueOf("#424242"));
        primaryStage.setScene(scene);
        //primaryStage.set
		primaryStage.setTitle("Rubik's Cube Multi-Purpose Tool and Interactive Tutorial");
		primaryStage.getIcons().add(new Image("/resources/FYP-Logo-FINAL3.png"));
		primaryStage.setResizable(false);
		primaryStage.show();
		
		Label cubeGif = new Label("");
		cubeGif.setGraphic(new ImageView(new Image("/resources/FYP-Logo-Anim-S.gif")));
		cubeGif.setLayoutX(30);
		cubeGif.setLayoutY(200);
		g.getChildren().add(cubeGif);
		
		Label sceneTitle = new Label("");
		sceneTitle.setGraphic(new ImageView(new Image("/resources/FYP-Title.png")));
		sceneTitle.setLayoutX(70);
		sceneTitle.setLayoutY(10);
		g.getChildren().add(sceneTitle);
		
		ImageView rubtut = new ImageView(new Image("/resources/3x3tut-title.gif"));
		
		ImageView virtualcube = new ImageView(new Image("/resources/virtualcube-title.gif"));
		
		ImageView averagebyalgs = new ImageView(new Image("/resources/aba-title.gif"));
		
		
		Button rubikstut = new Button("");
		rubikstut.setGraphic(rubtut);
		rubikstut.setLayoutX(400);
		rubikstut.setLayoutY(140);
		rubikstut.setMinSize(333,161);
		rubikstut.setMaxSize(333,161);
		rubikstut.setOnMouseEntered(new EventHandler<MouseEvent>
	    () {

	        @Override
	        public void handle(MouseEvent t) {
	           rubikstut.setGraphic(new ImageView(new Image("/resources/3x3tut-title-hover.gif")));
	        }
	    });
		rubikstut.setOnMouseExited(new EventHandler<MouseEvent>
	    () {

	        @Override
	        public void handle(MouseEvent t) {
	           rubikstut.setGraphic(rubtut);
	        }
	    });
		
		rubikstut.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		if (doubleTut) {
        			System.out.println("not opening two cunty");
        		} else {
            		(new UserInterface()).start(new Stage());
            		doubleTut = true;
        		}
        	}
		});

		g.getChildren().add(rubikstut);
		
		Button virtcube = new Button("");
		virtcube.setGraphic(virtualcube);
		virtcube.setLayoutX(400);
		virtcube.setLayoutY(330);
		virtcube.setMinSize(132,125);
		virtcube.setMaxSize(132,125);
		
		virtcube.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		if (doubleCube) {
        			System.out.println("not opening two cunty");
        		} else {
        			vc = new RubiksCube();
            		vc.start(new Stage());
            		doubleCube = true;
        		}
        	}
		});
		
		
		g.getChildren().add(virtcube);
		
		Button ababut = new Button("");
		ababut.setGraphic(averagebyalgs);
		ababut.setLayoutX(600);
		ababut.setLayoutY(330);
		ababut.setMinSize(132,125);
		ababut.setMaxSize(132,125);
		
		ababut.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		if (doubleABA) {
        			System.out.println("not opening two cunty");
        		} else {
        			aba = new MainRedesigned();
            		aba.start(new Stage());
            		doubleABA = true;
        		}
        	}
		});
		
		
		g.getChildren().add(ababut);
	}
	
	public static void onCloseTut() {
		doubleTut = false;
	}
	
	public static void onCloseVirt() {
		doubleCube = false;
	}
	
	public static void onCloseABA() {
		doubleABA = false;
	}
	
	
}
