package averagebyalgs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class MainRedesigned extends Application {
	
	public static String edgeBuf;
	public static String cornerBuf;
	
	private Image icon = new Image("/resources/FYP-Logo-FINAL3.png");
	private ImageView loading = new ImageView(new Image("/resources/loadingGIF.gif"));
	private ImageView cstimer = new ImageView(new Image("/resources/cstimer-output.png"));
	private ImageView csv = new ImageView(new Image("/resources/CSV-output.png"));
	private ImageView helpIcon = new ImageView(new Image("/resources/HelpIconSmall.png"));

	static boolean graphic = true;
	public static boolean paritySwap = false;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage primaryStage) {
    	
    	Pane p = new Pane();
    	
    	primaryStage.setTitle("AverageByAlgs - BLD Tool");
    	Scene scene = new Scene(p, 1500, 750);
    	String image = MainRedesigned.class.getResource("/resources/AverageByAlgs-BG.png").toExternalForm();
    	p.setStyle("-fx-background-image: url('" + image + "'); " +
    	           "-fx-background-position: center center; " +
    	           "-fx-background-repeat: stretch;");
    	primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.getIcons().add(icon);
		primaryStage.setResizable(false);
    	
		TextArea userTextField = new TextArea();
        userTextField.setLayoutX(90);
        userTextField.setLayoutY(595);
        userTextField.setMinWidth(605);
        userTextField.setMinHeight(110);
        userTextField.setMaxHeight(110);
        p.getChildren().add(userTextField);
		
        Label loadingGif = new Label("");
        loadingGif.setGraphic(loading);
        loadingGif.setLayoutX(596);
        loadingGif.setLayoutY(63.5);
        p.getChildren().add(loadingGif);
        loadingGif.setVisible(true);
        
        Label modeSelect = new Label("");
        modeSelect.setGraphic(cstimer);
        modeSelect.setLayoutX(315);
        modeSelect.setLayoutY(537);
        p.getChildren().add(modeSelect);
        
        Label inputHelp = new Label("");
        inputHelp.setGraphic(helpIcon);
        inputHelp.setLayoutX(600);
        inputHelp.setLayoutY(537);
        Tooltip ttco = new Tooltip();
        ttco.setShowDelay(Duration.seconds(0.1));
        ttco.setPrefSize(400,100);
        ttco.setWrapText(true);
        ttco.setText("Data Input Format\n\nCSTimer Output: If you have your results in a CSTimer session, you can import them by clicking the "
        		+ "solve/mean box above your time list. Copy all of this text and paste it into the text field below.");
        ttco.setGraphic(new ImageView(new Image("/resources/HelpIcon.png")));
        inputHelp.setTooltip(ttco);
        p.getChildren().add(inputHelp);
        
        Button swapMode = new Button("Swap");
        swapMode.setMaxHeight(30);
        swapMode.setMaxWidth(30);
        swapMode.setMinWidth(30);
        swapMode.setMinHeight(30);
        swapMode.setLayoutX(560);
        swapMode.setLayoutY(535);
        swapMode.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (MainRedesigned.graphic) {
					modeSelect.setGraphic(csv);
					MainRedesigned.graphic = false;
					ttco.setText("Data Input Format\n\nCSV: In this format you can enter your results in CSV form of time, scramble, time, scramble etc."
							+ " Please make sure you enter this correctly to avoid any errors.");
				} else {
					modeSelect.setGraphic(cstimer);
					MainRedesigned.graphic = true;
					ttco.setText("Data Input Format\n\nCSTimer Output: If you have your results in a CSTimer session, you can import them by clicking the "
			        		+ "solve/mean box above your time list. Copy all of this text and paste it into the text field below.");
				}
			}
        	
        });
        p.getChildren().add(swapMode);
        
        
        Button settings = new Button("");
        settings.setGraphic(new ImageView(new Image("/resources/settings.png")));
        settings.setMaxHeight(45);
        settings.setMaxWidth(45);
        settings.setMinHeight(45);
        settings.setMinWidth(45);
        settings.setLayoutX(655);
        settings.setLayoutY(535);
        settings.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) {
				VBox v = new VBox();
				Scene settingsScene = new Scene(v, 200,200);
				Stage settingsWindow = new Stage();
				settingsWindow.setScene(settingsScene);
				settingsWindow.show();
			}
        	
        });
        p.getChildren().add(settings);
        
        NetManager nm = new NetManager();
        p = nm.init(p);

    }

}
