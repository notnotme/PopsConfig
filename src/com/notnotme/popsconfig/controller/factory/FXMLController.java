package com.notnotme.popsconfig.controller.factory;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * @author romain
 *
 * A controller class intended to be used for new windows.
 * It will make an instance to the Stage and/or HostServices
 * available in the controller.
 *
 * Inherit your controller from this class and pass a new
 * ControllerFactory to the loader to make it work.
 *
 * If you pass a resource bundle to the loader it will make it available too but
 * only once the init method (via super) is called.
 *
 * You must make one of those contructor available in you controller:
 *
 * Constructor(HostServices hostServices, Stage stage);
 * Constructor(Stage stage, HostServices hostServices);
 *
 * Both parameters can be null but in this case you would use a loader
 * without custom factory and a controller with the default ctor.
 *
 * Constructor();
 */
public class FXMLController implements Initializable {

	protected final HostServices mHostServices;
	protected final Stage mStage;
	protected ResourceBundle mResources;

	public FXMLController(HostServices hostServices, Stage stage) {
		mStage = stage;
		mHostServices = hostServices;
		mResources = null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mResources = resources;
	}

}
