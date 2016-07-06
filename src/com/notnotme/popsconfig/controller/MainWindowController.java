package com.notnotme.popsconfig.controller;

import com.notnotme.popsconfig.PopsConfig;
import com.notnotme.popsconfig.controller.factory.ControllerFactory;
import com.notnotme.popsconfig.controller.factory.FXMLController;
import com.notnotme.popsconfig.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author romain
 */
public final class MainWindowController extends FXMLController {

	private final static String TAG = MainWindowController.class.getSimpleName();

	private FileChooser mFileChooser;
	private boolean mFirstConfig;

	@FXML private BorderPane mRoot;
	@FXML private MenuItem mItemSave;
	@FXML private MenuItem mItemClose;
	@FXML private MenuItem mItemAbout;
	@FXML private MenuItem mItemLoad;
	@FXML private Label mStatusLabel;
	@FXML private Menu mMenuSystemPreset;
	@FXML private Menu mMenuUserPreset;

	/**
	 * A OnChangeListener that update the status bar when the GamePad model is saved or modified.
	 */
	private final ConfigController.OnChangeListener mOnChangeListener = new ConfigController.OnChangeListener() {
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

		@Override
		public void onLoaded() {
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
					getClass().getResource("/com/notnotme/popsconfig/ui/fxml/ConfigurationPane.fxml"),
					ResourceBundle.getBundle("com.notnotme.popsconfig.ui.fxml.ui")));
		} catch (IOException ex) {
			Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
			if (ex.getCause() != null) {
				showErrorDialog(null, ex.getCause().getLocalizedMessage());
			} else {
				showErrorDialog(null, ex.getLocalizedMessage());
			}
			Platform.exit();
		}

		// Set up the menu items actions
		mItemLoad.setOnAction((ActionEvent event) -> {
			loadConfig();
		});

		mItemSave.setOnAction((ActionEvent event) -> {
			saveConfig();
		});

		mItemClose.setOnAction((ActionEvent event) -> {
			mStage.hide();
		});

		mItemAbout.setOnAction((ActionEvent event) -> {
			showAboutDialog();
		});
		loadSystemPreset();
		loadUserPreset();

		// - set title and scene
		// - set no resizable and center on screen
		// - set show/hide listeners and show the app window
		mStage.setTitle(resources.getString("appname"));
		mStage.setScene(new Scene(mRoot));
		mStage.centerOnScreen();
		mStage.setResizable(false);
		mStage.setOnShown((WindowEvent event) -> {
			ConfigController.getInstance().addListener(mOnChangeListener);
		});
		mStage.setOnHiding((WindowEvent event) -> {
			ConfigController controller = ConfigController.getInstance();
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
		});

		// Application is ready so show it
		mOnChangeListener.onLoaded();
		mStage.show();

		// prepare other data that is not shown at startup
		mFirstConfig = true;
		mFileChooser = new FileChooser();
		mFileChooser.setInitialFileName("__sce_menuinfo");
		mFileChooser.getExtensionFilters().add(
			new FileChooser.ExtensionFilter("Pops Configuration", "*"));
	}

	private void saveConfig() {
		mFileChooser.setTitle(mResources.getString("save_title"));
		File file = mFileChooser.showSaveDialog(mStage);
		if (file != null) {
			mFileChooser.setInitialDirectory(file.getParentFile());
			try {
				ConfigController.getInstance().saveConfig(file);
			} catch (Exception ex) {
				Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
				showErrorDialog(mResources.getString("error_saving_title"),
						ex.getLocalizedMessage());
			}
		}
	}

	private void loadConfig() {
		mFileChooser.setTitle(mResources.getString("load_title"));
		File file = mFileChooser.showOpenDialog(mStage);
		if (file != null) {
			mFileChooser.setTitle(mResources.getString("load"));
			mFileChooser.setInitialDirectory(file.getParentFile());
			try {
				ConfigController.getInstance().loadConfig(file);
			} catch (Exception ex) {
				Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
				showErrorDialog(mResources.getString("error_loading_title"),
						ex.getLocalizedMessage());
			}
		}
	}

	/**
	 * Prepare all preset file found in the package res/preset
	 * This can be used to quick edit or reset the configuration
	 */
	private void loadSystemPreset() {
		String path = null;

		try {
			path = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (URISyntaxException ex) {
			Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
			mMenuSystemPreset.getItems().add(
				new MenuItem(mResources.getString("preset.empty")));
		}

		// List file inside our jar
		if (path == null) return;
		Utils.getJarEntry(path).stream().forEach((entry) -> {
			String name = entry.getName();
			if (name.startsWith("com/notnotme/popsconfig/res/preset") && !name.endsWith("/")) {
				// We got a preset file, add it to the menu
				MenuItem menuItem = new MenuItem(name.substring(name.lastIndexOf('/') + 1));
				menuItem.setMnemonicParsing(false);
				menuItem.setOnAction((ActionEvent event) -> {
					try {
						ConfigController.getInstance().loadConfig(
								Utils.jarEntryToFile(getClass().getClassLoader(), entry));
					} catch (Exception ex) {
						Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
						showErrorDialog(mResources.getString("error_loading_title"),
							ex.getLocalizedMessage());
					}
				});
				mMenuSystemPreset.getItems().add(menuItem);
			}
		});
	}

	/**
	 * Load the preset from the current directory (look for "preset")
	 * or in the user directory (look for ~/.popsconfig/preset)
	 */
	private void loadUserPreset() {
		// Get the user directory
		String path = System.getProperty("user.home");
		File userDirectory = new File(path + File.separator + "." + PopsConfig.TAG + File.separator + "preset");

		if (userDirectory.isDirectory()) {
			for (File currentFile : userDirectory.listFiles()) {
				MenuItem menuItem = new MenuItem(currentFile.getName());
					menuItem.setMnemonicParsing(false);
					menuItem.setOnAction((ActionEvent event) -> {
					try {
						ConfigController.getInstance().loadConfig(currentFile);
					} catch (Exception ex) {
						Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
						showErrorDialog(mResources.getString("error_loading_title"),
							ex.getLocalizedMessage());
					}
				});
				mMenuUserPreset.getItems().add(menuItem);
			}
		}

		// Try to get from current directory
		try {
			path = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			path = path.substring(0, path.lastIndexOf(File.separator) + 1) + "preset";
		} catch (URISyntaxException ex) {
			Logger.getLogger(TAG).log(Level.WARNING, null, ex);
		}

		userDirectory = new File(path);
		if (userDirectory.isDirectory()) {
			for (File currentFile : userDirectory.listFiles()) {
				MenuItem menuItem = new MenuItem(currentFile.getName());
				menuItem.setMnemonicParsing(false);
				menuItem.setOnAction((ActionEvent event) -> {
					try {
						ConfigController.getInstance().loadConfig(currentFile);
					} catch (Exception ex) {
						Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
						showErrorDialog(mResources.getString("error_loading_title"),
							ex.getLocalizedMessage());
					}
				});
				mMenuUserPreset.getItems().add(menuItem);
			}
		}

		// If none was found add an item to show that nothing is here
		if (mMenuUserPreset.getItems().isEmpty()) {
			mMenuUserPreset.getItems().add(
					new MenuItem(mResources.getString("preset.empty")));
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
			if (ex.getCause() != null) {
				showErrorDialog(null, ex.getCause().getLocalizedMessage());
			} else {
				showErrorDialog(null, ex.getLocalizedMessage());
			}
		}
	}

	private void showErrorDialog(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
