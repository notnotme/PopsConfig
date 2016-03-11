package com.notnotme.psxbubblepad.controller.factory;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * @author romain
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
