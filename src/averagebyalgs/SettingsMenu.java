package averagebyalgs;

import java.util.ArrayList;

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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SettingsMenu {

	Group g;
	Scene settingsScene;
	Stage settingsWindow;
	CheckBox paritySwap, quadFlip, dtBox, otBox;
	ComboBox<String> eBuff, cBuff;
	ObservableList<String> edgeBufList = FXCollections.observableArrayList("UF", "DF");
	ObservableList<String> cornerBufList = FXCollections.observableArrayList("UBL","UFR");

	
	public SettingsMenu() {
		g = new Group();
		settingsScene = new Scene(g, 400,600, Color.rgb(66,66,66));
		settingsWindow = new Stage();
		settingsWindow.setScene(settingsScene);
		
		int initX = 10;
		int initY = 40;
		
        Label parAvd = new Label("Parity Swap:");
        parAvd.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 30));
        parAvd.setLayoutX(initX);
        parAvd.setTextFill(Color.rgb(213,225,227));
        parAvd.setLayoutY(initY);
        g.getChildren().add(parAvd);
        paritySwap = new CheckBox();
        paritySwap.setLayoutX(initX+240);
        paritySwap.setLayoutY(initY+3);
        g.getChildren().add(paritySwap);
        
        Label quad = new Label("Quad Flip:");
        quad.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 30));
        quad.setLayoutX(initX);
        quad.setTextFill(Color.rgb(213,225,227));
        quad.setLayoutY(initY+40);
        g.getChildren().add(quad);
        quadFlip = new CheckBox();
        quadFlip.setLayoutX(initX+240);
        quadFlip.setLayoutY(initY+43);
        g.getChildren().add(quadFlip);
        
        Label adjacentTwists = new Label("Adj. Double Twists:");
        adjacentTwists.setTextFill(Color.rgb(213,225,227));
        adjacentTwists.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 30));
        adjacentTwists.setLayoutX(initX);
        adjacentTwists.setLayoutY(initY+80);
        g.getChildren().add(adjacentTwists);
        dtBox = new CheckBox();
        dtBox.setLayoutX(initX+240);
        dtBox.setLayoutY(initY+83);
        g.getChildren().add(dtBox);
        
        Label oppositeTwists = new Label("Opp. Double Twists:");
        oppositeTwists.setTextFill(Color.rgb(213,225,227));
        oppositeTwists.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 30));
        oppositeTwists.setLayoutX(initX);
        oppositeTwists.setLayoutY(initY+120);
        g.getChildren().add(oppositeTwists);
        otBox = new CheckBox();
        otBox.setLayoutX(initX+240);
        otBox.setLayoutY(initY+123);
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
        
        Button save = new Button("Save");
        save.setLayoutX(10);
        save.setLayoutY(500);
        
        save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				settingsGive();
				
			}
        	
        });
        g.getChildren().add(save);
        
        Label cornerBuffer = new Label("Corner Buffer:");
        cornerBuffer.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 30));
        cornerBuffer.setTextFill(Color.rgb(213,225,227));
        cornerBuffer.setLayoutX(initX);
        cornerBuffer.setLayoutY(initY+160);
        g.getChildren().add(cornerBuffer);
        cBuff = new ComboBox<String>(cornerBufList);
        cBuff.setValue("UFR");
        cBuff.setLayoutX(initX+190);
        cBuff.setLayoutY(initY+163);
        g.getChildren().add(cBuff);
        
        Label edgeBuffer = new Label("Edge Buffer:");
        edgeBuffer.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/ihfont.otf"), 30));
        edgeBuffer.setLayoutX(initX);
        edgeBuffer.setTextFill(Color.rgb(213,225,227));
        edgeBuffer.setLayoutY(initY+200);
        g.getChildren().add(edgeBuffer);
        eBuff = new ComboBox<String>(edgeBufList);
        eBuff.setValue("UF");
        eBuff.setLayoutX(initX+190);
        eBuff.setLayoutY(initY+203);
        g.getChildren().add(eBuff);
        
        
        
        
        
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
	
	void show() {
		settingsWindow.show();
	}
	
	void settingsGive() {
		if (paritySwap.isSelected()) {
			MainRedesigned.paritySwap = true;
		} else {
			MainRedesigned.paritySwap = false;
		}
		if (quadFlip.isSelected()) {
			MainRedesigned.quadFlip = true;
		} else {
			MainRedesigned.quadFlip = false;
		}
		if (dtBox.isSelected()) {
			MainRedesigned.dt = true;
		} else {
			MainRedesigned.dt = false;
		}
		if (otBox.isSelected()) {
			MainRedesigned.ot = true;
		} else {
			MainRedesigned.ot = false;
		}
		MainRedesigned.edgeBuf = eBuff.getValue();
		MainRedesigned.cornerBuf = cBuff.getValue();
	}
}
