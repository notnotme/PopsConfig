package com.notnotme.psxbubblepad.controller;

import com.sun.javafx.stage.StageHelper;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author romain
 */
public class AboutDialogController implements Initializable {

	private final static String TAG = AboutDialogController.class.getSimpleName();

	private Stage mStage;

	@FXML private AnchorPane mRoot;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Logger.getLogger(TAG).log(
				Level.INFO, "initialize() location: {0}, resources: {1}",
				new Object[]{location, resources});

		mStage = StageHelper.getStages().remove(0);
		mStage.initModality(Modality.APPLICATION_MODAL);
		mStage.setTitle(resources.getString("about"));
		mStage.setScene(new Scene(mRoot));
		mStage.setResizable(false);
		mStage.setOnShown((WindowEvent event) -> {
			// todo: start music & anim
		});
		mStage.setOnCloseRequest((WindowEvent event) -> {
			// todo: stop music & anim
		});

		mRoot.setOnMouseClicked((MouseEvent event) -> {
			mStage.hide();
		});

		mStage.show();
	}

}
