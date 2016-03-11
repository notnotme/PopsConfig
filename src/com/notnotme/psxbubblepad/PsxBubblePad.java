package com.notnotme.psxbubblepad;

import com.notnotme.psxbubblepad.controller.factory.ControllerFactory;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * @author romain
 */
public class PsxBubblePad extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader.load(
				getClass().getResource("/com/notnotme/psxbubblepad/ui/fxml/MainWindow.fxml"),
				ResourceBundle.getBundle("com.notnotme.psxbubblepad.ui.fxml.ui"),
				null,
				new ControllerFactory(getHostServices(), stage));
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
