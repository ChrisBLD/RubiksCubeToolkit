package averagebyalgs;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsMenu {

	
	public SettingsMenu() {
		VBox v = new VBox();
		Scene settingsScene = new Scene(v, 200,200);
		Stage settingsWindow = new Stage();
		settingsWindow.setScene(settingsScene);
		settingsWindow.show();
		
	}
}
