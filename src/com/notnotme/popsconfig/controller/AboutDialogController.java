package com.notnotme.popsconfig.controller;

import com.notnotme.popsconfig.controller.factory.FXMLController;
import com.notnotme.popsconfig.sound.ModulePlayer;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author romain
 */
public final class AboutDialogController extends FXMLController {

	private final static String TAG = AboutDialogController.class.getSimpleName();

	private ModulePlayer mPlayer;

	@FXML private VBox mRoot;
	@FXML private Hyperlink mWebsiteLink;
	@FXML private Hyperlink mGithubLink;
	@FXML private Hyperlink mSongLink;
	@FXML private Hyperlink mIconsLink;
	@FXML private Hyperlink mMuxmLink;

	public AboutDialogController(HostServices hostServices, Stage stage) {
		super(hostServices, stage);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		Logger.getLogger(TAG).log(
				Level.INFO, "initialize() location: {0}, resources: {1}",
				new Object[]{location, resources});

		mRoot.setOnMouseClicked((MouseEvent event) -> {
			mStage.hide();
		});
		mWebsiteLink.setOnAction((ActionEvent event) -> {
			mHostServices.showDocument(mWebsiteLink.getText());
		});
		mGithubLink.setOnAction((ActionEvent event) -> {
			mHostServices.showDocument(mGithubLink.getText());
		});
		mMuxmLink.setOnAction((ActionEvent event) -> {
			mHostServices.showDocument("http://musound.sourceforge.net/muxm/index.html");
		});
		mSongLink.setOnAction((ActionEvent event) -> {
			mHostServices.showDocument("http://ultrasyd.free.fr/");
		});
		mIconsLink.setOnAction((ActionEvent event) -> {
			mHostServices.showDocument("http://github.com/damieng/silk-companion/");
		});

		mStage.initModality(Modality.APPLICATION_MODAL);
		mStage.setTitle(resources.getString("about_title"));
		mStage.setScene(new Scene(mRoot));
		mStage.setResizable(false);
		mStage.centerOnScreen();
		mStage.setOnShown((WindowEvent event) -> {
			onEnter();
		});
		mStage.setOnHiding((WindowEvent event) -> {
			onExit();
		});
		mStage.addEventHandler(KeyEvent.KEY_PRESSED, (Event event) -> {
			mStage.hide();
		});
		mStage.show();
	}

	private void onEnter() {
		try {
			mPlayer = new ModulePlayer(getClass().getResource("/com/notnotme/popsconfig/res/Ultrasyd-Groovy Elisa.xm").toURI().toURL());
			new Thread(mPlayer).start();
		} catch (NullPointerException | IOException | URISyntaxException ex) {
			// can be null if file not found or lib missing
			Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
		}
	}

	private void onExit() {
		if (mPlayer != null) {
			mPlayer.stop();
		}
	}

}
