package com.notnotme.psxbubblepad;

import com.sun.javafx.stage.StageHelper;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * @author romain
 */
public class PsxBubblePad extends Application {

	public final static String VERSION = "0.1";

	@Override
	public void start(Stage stage) throws Exception {
		// Create first stage then show it
		// - Add it to the stage helper stack so we can get it in controller
		StageHelper.getStages().add(0, stage);
		FXMLLoader.load(
				getClass().getResource("/com/notnotme/psxbubblepad/ui/fxml/MainWindow.fxml"),
				ResourceBundle.getBundle("com.notnotme.psxbubblepad.ui.fxml.ui"));
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
