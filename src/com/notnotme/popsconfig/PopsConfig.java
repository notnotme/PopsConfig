package com.notnotme.popsconfig;

import com.notnotme.popsconfig.controller.factory.ControllerFactory;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * @author romain
 */
public final class PopsConfig extends Application {

	public final static String TAG = PopsConfig.class.getSimpleName();

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
		ResourceBundle resources = ResourceBundle.getBundle("com.notnotme.popsconfig.ui.fxml.ui");
		try {
			Logger.getLogger(TAG).log(Level.INFO, "start()");
			FXMLLoader.load(
					getClass().getResource("/com/notnotme/popsconfig/ui/fxml/MainWindow.fxml"),
					resources,
					null,
					new ControllerFactory(getHostServices(), stage));
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle(resources.getString("error"));
			alert.setHeaderText(null);
			if (e.getCause() != null) {
				alert.setContentText(e.getCause().getLocalizedMessage());
			} else {
				alert.setContentText(e.getLocalizedMessage());
			}
			alert.showAndWait();
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
