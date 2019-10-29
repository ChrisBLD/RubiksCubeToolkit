package main;

import javafx.application.Application;
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

public class Home extends Application {

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
		g.getChildren().add(rubikstut);
	}
}
