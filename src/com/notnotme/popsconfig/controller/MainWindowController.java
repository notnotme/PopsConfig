package com.notnotme.popsconfig.controller;

import com.notnotme.popsconfig.controller.factory.FXMLController;
import com.notnotme.popsconfig.controller.factory.ControllerFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author romain
 */
public class MainWindowController extends FXMLController {

	private final static String TAG = MainWindowController.class.getSimpleName();

	private FileChooser mFileChooser;
	private boolean mFirstConfig;

	@FXML private BorderPane mRoot;
	@FXML private MenuItem mItemSave;
	@FXML private MenuItem mItemClose;
	@FXML private MenuItem mItemAbout;
	@FXML private Label mStatusLabel;

	/**
	 * A OnPadChangeListener that update the status bar when the GamePad model is saved or modified.
	 */
	private final GamePadController.OnPadChangeListener mOnPadChangeListener = new GamePadController.OnPadChangeListener() {
		@Override
		public void onChanged() {
			mStatusLabel.setText(mResources.getString("unsaved"));
			mStatusLabel.setTextFill(Paint.valueOf("orange"));
			mFirstConfig = false;
		}

		@Override
		public void onSaved() {
			mStatusLabel.setText(mResources.getString("ready"));
			mStatusLabel.setTextFill(Paint.valueOf("green"));
		}
	};

	public MainWindowController(HostServices hostServices, Stage stage) {
		super(hostServices, stage);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		Logger.getLogger(TAG).log(
				Level.INFO, "initialize() location: {0}, resources: {1}",
				new Object[]{location, resources});

		// Setup the main content
		try {
			mRoot.setCenter(FXMLLoader.load(
					getClass().getResource("/com/notnotme/popsconfig/ui/fxml/GamePadPane.fxml"),
					ResourceBundle.getBundle("com.notnotme.popsconfig.ui.fxml.ui")));
		} catch (IOException ex) {
			Logger.getLogger(TAG).log(Level.SEVERE, null, ex);

			mStatusLabel.setText(ex.getLocalizedMessage());
			mStatusLabel.setTextFill(Paint.valueOf("red"));
			return;
		}

		// Set up the menu items actions
		mItemSave.setOnAction((ActionEvent event) -> {
			saveConfig();
		});

		mItemClose.setOnAction((ActionEvent event) -> {
			mStage.hide();
		});

		mItemAbout.setOnAction((ActionEvent event) -> {
			showAboutDialog();
		});

		// Get the stage form the stage helper (also removing it)
		// - set title and scene
		// - set no resizable and center on screen
		// - set show/hide listeners and show
		mStage.setTitle(resources.getString("appname"));
		mStage.setScene(new Scene(mRoot));
		mStage.centerOnScreen();
		mStage.setResizable(false);
		mStage.setOnShown((WindowEvent event) -> {
			onEnter();
		});
		mStage.setOnHiding((WindowEvent event) -> {
			onExit();
		});

		// Application is ready so show it
		mOnPadChangeListener.onSaved();
		mStage.show();

		// prepare other data that is not shown at startup
		mFirstConfig = true;
		mFileChooser = new FileChooser();
		mFileChooser.getExtensionFilters().add(
			new FileChooser.ExtensionFilter("IN WHAT FORMAT I SAVE ? DB OR FILE ???", "*.wtf"));
	}

	private void saveConfig() {
		File file = mFileChooser.showSaveDialog(mStage);
		if (file != null) {
			GamePadController.getInstance().savePad(file);
		}
	}

	private void showAboutDialog() {
		try {
			Stage stage = new Stage();
			stage.initOwner(mStage);
			FXMLLoader.load(
					getClass().getResource("/com/notnotme/popsconfig/ui/fxml/AboutDialog.fxml"),
					ResourceBundle.getBundle("com.notnotme.popsconfig.ui.fxml.ui"),
					null,
					new ControllerFactory(mHostServices, stage));

		} catch (IOException ex) {
			Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
			mStatusLabel.setText(ex.getLocalizedMessage());
			mStatusLabel.setTextFill(Paint.valueOf("red"));
		}
	}

	/**
	 * Called when the window is initialy shown
	 */
	private void onEnter() {
		GamePadController.getInstance().addListener(mOnPadChangeListener);
	}

	/**
	 * Called when the user quit
	 * Ask for save change if needed
	 */
	private void onExit() {
		GamePadController controller = GamePadController.getInstance();
		if (!controller.isSaved() && !mFirstConfig) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(mResources.getString("warning"));
			alert.setHeaderText(mResources.getString("exit_without_saving_title"));
			alert.setContentText(mResources.getString("exit_without_saving_content"));
			alert.showAndWait();
			if (alert.getResult() != ButtonType.OK) {
				saveConfig();
			}
		}
		GamePadController.getInstance().removeListener(mOnPadChangeListener);
	}

}