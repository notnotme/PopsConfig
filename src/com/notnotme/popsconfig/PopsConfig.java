package com.notnotme.popsconfig;

import com.notnotme.popsconfig.controller.factory.ControllerFactory;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * @author romain
 */
public class PopsConfig extends Application {

	private final static String TAG = PopsConfig.class.getSimpleName();

	@Override
	public void init() throws Exception {
		super.init();
		Logger.getLogger(TAG).log(Level.INFO, "init()");
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		Logger.getLogger(TAG).log(Level.INFO, "stop()");
	}

	@Override
	public void start(Stage stage) throws Exception {
		Logger.getLogger(TAG).log(Level.INFO, "start()");
		FXMLLoader.load(
				getClass().getResource("/com/notnotme/popsconfig/ui/fxml/MainWindow.fxml"),
				ResourceBundle.getBundle("com.notnotme.popsconfig.ui.fxml.ui"),
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
