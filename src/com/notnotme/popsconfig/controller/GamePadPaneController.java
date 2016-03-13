package com.notnotme.popsconfig.controller;

import com.notnotme.popsconfig.model.SoundVolume;
import com.notnotme.popsconfig.model.DiscLoading;
import com.notnotme.popsconfig.model.gamepad.GamePadMapping;
import com.notnotme.popsconfig.model.gamepad.GamePadMode;
import com.notnotme.popsconfig.model.gamepad.GamePadPort;
import com.notnotme.popsconfig.model.gamepad.PsxButton;
import com.notnotme.popsconfig.model.gamepad.PsxTouchButton;
import com.notnotme.popsconfig.model.gamepad.VitaButton;
import com.notnotme.popsconfig.model.gamepad.VitaTouchButton;
import com.notnotme.popsconfig.model.screen.ScreenFilter;
import com.notnotme.popsconfig.model.screen.ScreenMode;
import com.notnotme.popsconfig.ui.factory.ListCellFactory;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;

/**
 * @author romain
 */
public class GamePadPaneController implements Initializable {

	private final static String TAG = GamePadPaneController.class.getSimpleName();

	@FXML private Tab mCustomButtonsTab;
	@FXML private Tab mCustomScreenTab;

	@FXML private ComboBox<GamePadMapping> mButtonsMappingCombo;
	@FXML private ComboBox<GamePadPort> mControllersCombo;
	@FXML private ComboBox<GamePadMode> mControllerModeCombo;
	@FXML private ComboBox<ScreenMode> mScreenModeCombo;
	@FXML private ComboBox<ScreenFilter> mScreenFilterCombo;
	@FXML private ComboBox<DiscLoading> mDiscLoadingCombo;
	@FXML private ComboBox<SoundVolume> mAudioBoostCombo;

	@FXML private ComboBox<PsxTouchButton> mTouchUpperLeftCombo;
	@FXML private ComboBox<PsxTouchButton> mTouchUpperRightCombo;
	@FXML private ComboBox<PsxTouchButton> mTouchBottomLeftCombo;
	@FXML private ComboBox<PsxTouchButton> mTouchBottomRightCombo;

	@FXML private ComboBox<PsxTouchButton> mRearTouchUpperLeftCombo;
	@FXML private ComboBox<PsxTouchButton> mRearTouchUpperRightCombo;
	@FXML private ComboBox<PsxTouchButton> mRearTouchBottomLeftCombo;
	@FXML private ComboBox<PsxTouchButton> mRearTouchBottomRightCombo;

	@FXML private ComboBox<PsxButton> mUpButtonCombo;
	@FXML private ComboBox<PsxButton> mLeftButtonCombo;
	@FXML private ComboBox<PsxButton> mRightButtonCombo;
	@FXML private ComboBox<PsxButton> mDownButtonCombo;
	@FXML private ComboBox<PsxButton> mLButtonCombo;
	@FXML private ComboBox<PsxButton> mRButtonCombo;
	@FXML private ComboBox<PsxButton> mSquareButtonCombo;
	@FXML private ComboBox<PsxButton> mCrossButtonCombo;
	@FXML private ComboBox<PsxButton> mCircleButtonCombo;
	@FXML private ComboBox<PsxButton> mTriangleButtonCombo;
	@FXML private ComboBox<PsxButton> mLStickLeftButtonCombo;
	@FXML private ComboBox<PsxButton> mLStickRightButtonCombo;
	@FXML private ComboBox<PsxButton> mLStickUpButtonCombo;
	@FXML private ComboBox<PsxButton> mLStickDownButtonCombo;
	@FXML private ComboBox<PsxButton> mRStickLeftButtonCombo;
	@FXML private ComboBox<PsxButton> mRStickRightButtonCombo;
	@FXML private ComboBox<PsxButton> mRStickUpButtonCombo;
	@FXML private ComboBox<PsxButton> mRStickDownButtonCombo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Logger.getLogger(TAG).log(
				Level.INFO, "initialize() location: {0}, resources: {1}",
				new Object[]{location, resources});

		setupMainTab(resources);
		setupTouchPadTab(resources);
		setupRearTouchPadTab(resources);
		setupCustomButtonsTab(resources);
	}

	private void setupMainTab(ResourceBundle resources) {
		// Set datas for the controls selection (custom/defaut) and handle the values change
		// - Disable the custom controls option and tab at startup
		// - Enable the custom controls tab if custom controls is selected if value change
		mCustomButtonsTab.setDisable(true);
		mButtonsMappingCombo.setCellFactory(ListCellFactory.getControllerControlsCellFactory(resources));
		mButtonsMappingCombo.setButtonCell(ListCellFactory.getControllerControlsListCell(resources));
		mButtonsMappingCombo.setItems(FXCollections.observableArrayList(GamePadMapping.values()));
		mButtonsMappingCombo.setValue(GamePadMapping.DEFAULT);
		mButtonsMappingCombo.valueProperty().addListener((ObservableValue<? extends GamePadMapping> observable, GamePadMapping oldValue, GamePadMapping newValue) -> {
			mCustomButtonsTab.setDisable(newValue == GamePadMapping.DEFAULT);
			GamePadController.getInstance().setControls(newValue);
		});

		// Set datas for the controllers selection (player 1 / player 2) and handle the values change
		// - Select player 1 by default at startup
		mControllersCombo.setCellFactory(ListCellFactory.getControllerPortCellFactory(resources));
		mControllersCombo.setButtonCell(ListCellFactory.getControllerPortListCell(resources));
		mControllersCombo.setItems(FXCollections.observableArrayList(GamePadPort.values()));
		mControllersCombo.setValue(GamePadPort.PORT_1);
		mControllersCombo.valueProperty().addListener((ObservableValue<? extends GamePadPort> observable, GamePadPort oldValue, GamePadPort newValue) -> {
			GamePadController.getInstance().setControllerPort(newValue);
		});

		// Set datas for the controllers mode selection (analog/numeric) and handle the values change
		// - Select numeric by default at startup
		mControllerModeCombo.setCellFactory(ListCellFactory.getControllerModeCellFactory(resources));
		mControllerModeCombo.setButtonCell(ListCellFactory.getControllerModeListCell(resources));
		mControllerModeCombo.setItems(FXCollections.observableArrayList(GamePadMode.values()));
		mControllerModeCombo.setValue(GamePadMode.NUMERIC);
		mControllerModeCombo.valueProperty().addListener((ObservableValue<? extends GamePadMode> observable, GamePadMode oldValue, GamePadMode newValue) -> {
			GamePadController.getInstance().setControllerMode(newValue);
		});

		// Screen mode
		mCustomScreenTab.setDisable(true);
		mScreenModeCombo.setCellFactory(ListCellFactory.getScreenModeCellFactory(resources));
		mScreenModeCombo.setButtonCell(ListCellFactory.getScreenModeListCell(resources));
		mScreenModeCombo.setItems(FXCollections.observableArrayList(ScreenMode.values()));
		mScreenModeCombo.setValue(ScreenMode.ORIGINAL);
		mScreenModeCombo.valueProperty().addListener((ObservableValue<? extends ScreenMode> observable, ScreenMode oldValue, ScreenMode newValue) -> {
			mCustomScreenTab.setDisable(newValue != ScreenMode.CUSTOM);
			// todo: controller interaction
		});

		// Screen filtering
		mScreenFilterCombo.setCellFactory(ListCellFactory.getScreenFilterCellFactory(resources));
		mScreenFilterCombo.setButtonCell(ListCellFactory.getScreenFilterListCell(resources));
		mScreenFilterCombo.setItems(FXCollections.observableArrayList(ScreenFilter.values()));
		mScreenFilterCombo.setValue(ScreenFilter.NONE);
		mScreenFilterCombo.valueProperty().addListener((ObservableValue<? extends ScreenFilter> observable, ScreenFilter oldValue, ScreenFilter newValue) -> {
			// todo: controller interaction
		});

		// Disc loading
		mDiscLoadingCombo.setCellFactory(ListCellFactory.getDiscLoadingCellFactory(resources));
		mDiscLoadingCombo.setButtonCell(ListCellFactory.getDiscLoadingListCell(resources));
		mDiscLoadingCombo.setItems(FXCollections.observableArrayList(DiscLoading.values()));
		mDiscLoadingCombo.setValue(DiscLoading.NORMAL);
		mDiscLoadingCombo.valueProperty().addListener((ObservableValue<? extends DiscLoading> observable, DiscLoading oldValue, DiscLoading newValue) -> {
			// todo: controller interaction
		});

		// Audio boost
		mAudioBoostCombo.setCellFactory(ListCellFactory.getSoundVolumeCellFactory(resources));
		mAudioBoostCombo.setButtonCell(ListCellFactory.getSoundVolumeListCell(resources));
		mAudioBoostCombo.setItems(FXCollections.observableArrayList(SoundVolume.values()));
		mAudioBoostCombo.setValue(SoundVolume.NORMAL);
		mAudioBoostCombo.valueProperty().addListener((ObservableValue<? extends SoundVolume> observable, SoundVolume oldValue, SoundVolume newValue) -> {
			// todo: controller interaction
		});
	}

	private void setupCustomButtonsTab(ResourceBundle resources) {
		ObservableList<PsxButton> buttons = FXCollections.observableArrayList(PsxButton.UNUSED,
				PsxButton.UP, PsxButton.DOWN, PsxButton.LEFT, PsxButton.RIGHT, PsxButton.START, PsxButton.SELECT,
				PsxButton.CROSS, PsxButton.SQUARE, PsxButton.CIRCLE, PsxButton.TRIANGLE, PsxButton.L1, PsxButton.L2,
				PsxButton.L3, PsxButton.R1, PsxButton.R2, PsxButton.R3, PsxButton.L1_R1, PsxButton.L2_R2);

		mUpButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mUpButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mUpButtonCombo.setItems(buttons);
		mUpButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.UP, newValue);
		});

		mLeftButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mLeftButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mLeftButtonCombo.setItems(buttons);
		mLeftButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT, newValue);
		});

		mRightButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRightButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRightButtonCombo.setItems(buttons);
		mRightButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.RIGHT, newValue);
		});

		mDownButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mDownButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mDownButtonCombo.setItems(buttons);
		mDownButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.DOWN, newValue);
		});

		mLButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mLButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mLButtonCombo.setItems(buttons);
		mLButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.L, newValue);
		});

		mRButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRButtonCombo.setItems(buttons);
		mRButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.R, newValue);
		});

		mSquareButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mSquareButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mSquareButtonCombo.setItems(buttons);
		mSquareButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.SQUARE, newValue);
		});

		mCrossButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mCrossButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mCrossButtonCombo.setItems(buttons);
		mCrossButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.CROSS, newValue);
		});

		mCircleButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mCircleButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mCircleButtonCombo.setItems(buttons);
		mCircleButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.CIRCLE, newValue);
		});

		mTriangleButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mTriangleButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mTriangleButtonCombo.setItems(buttons);
		mTriangleButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.TRIANGLE, newValue);
		});

		mLStickLeftButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mLStickLeftButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mLStickLeftButtonCombo.setItems(buttons);
		mLStickLeftButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT_ANALOG_LEFT, newValue);
		});

		mLStickRightButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mLStickRightButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mLStickRightButtonCombo.setItems(buttons);
		mLStickRightButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT_LANALOG_RIGHT, newValue);
		});

		mLStickUpButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mLStickUpButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mLStickUpButtonCombo.setItems(buttons);
		mLStickUpButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT_ANALOG_UP, newValue);
		});

		mLStickDownButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mLStickDownButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mLStickDownButtonCombo.setItems(buttons);
		mLStickDownButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT_ANALOG_DOWN, newValue);
		});

		mRStickLeftButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRStickLeftButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRStickLeftButtonCombo.setItems(buttons);
		mRStickLeftButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.RIGHT_ANALOG_LEFT, newValue);
		});

		mRStickRightButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRStickRightButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRStickRightButtonCombo.setItems(buttons);
		mRStickRightButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.RIGHT_ANALOG_RIGHT, newValue);
		});

		mRStickUpButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRStickUpButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRStickUpButtonCombo.setItems(buttons);
		mRStickUpButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.RIGHT_ANALOG_UP, newValue);
		});

		mRStickDownButtonCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRStickDownButtonCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRStickDownButtonCombo.setItems(buttons);
		mRStickDownButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.RIGHT_ANALOG_DOWN, newValue);
		});

		mUpButtonCombo.setValue(PsxButton.UP);
		mLeftButtonCombo.setValue(PsxButton.LEFT);
		mRightButtonCombo.setValue(PsxButton.RIGHT);
		mDownButtonCombo.setValue(PsxButton.DOWN);
		mLButtonCombo.setValue(PsxButton.L1);
		mRButtonCombo.setValue(PsxButton.R1);
		mSquareButtonCombo.setValue(PsxButton.SQUARE);
		mCrossButtonCombo.setValue(PsxButton.CROSS);
		mCircleButtonCombo.setValue(PsxButton.CIRCLE);
		mTriangleButtonCombo.setValue(PsxButton.TRIANGLE);
		mLStickLeftButtonCombo.setValue(PsxButton.UNUSED);
		mLStickRightButtonCombo.setValue(PsxButton.UNUSED);
		mLStickUpButtonCombo.setValue(PsxButton.UNUSED);
		mLStickDownButtonCombo.setValue(PsxButton.UNUSED);
		mRStickLeftButtonCombo.setValue(PsxButton.UNUSED);
		mRStickRightButtonCombo.setValue(PsxButton.UNUSED);
		mRStickUpButtonCombo.setValue(PsxButton.UNUSED);
		mRStickDownButtonCombo.setValue(PsxButton.UNUSED);
	}

	private void setupTouchPadTab(ResourceBundle resources) {
		ObservableList<PsxTouchButton> buttons = FXCollections.observableArrayList(PsxTouchButton.UNUSED,
				PsxTouchButton.UP, PsxTouchButton.DOWN, PsxTouchButton.LEFT, PsxTouchButton.RIGHT, PsxTouchButton.START, PsxTouchButton.SELECT,
				PsxTouchButton.CROSS, PsxTouchButton.SQUARE, PsxTouchButton.CIRCLE, PsxTouchButton.TRIANGLE, PsxTouchButton.L1, PsxTouchButton.L2,
				PsxTouchButton.L3, PsxTouchButton.R1, PsxTouchButton.R2, PsxTouchButton.R3);

		mTouchUpperLeftCombo.setCellFactory(ListCellFactory.getPsxTouchButtonCellFactory(resources));
		mTouchUpperLeftCombo.setButtonCell(ListCellFactory.getPsxTouchButtonListCell(resources));
		mTouchUpperLeftCombo.setItems(buttons);
		mTouchUpperLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxTouchButton> observable, PsxTouchButton oldValue, PsxTouchButton newValue) -> {
			bindButton(VitaTouchButton.TOUCH_UPPER_LEFT, newValue);
		});

		mTouchUpperRightCombo.setCellFactory(ListCellFactory.getPsxTouchButtonCellFactory(resources));
		mTouchUpperRightCombo.setButtonCell(ListCellFactory.getPsxTouchButtonListCell(resources));
		mTouchUpperRightCombo.setItems(buttons);
		mTouchUpperRightCombo.valueProperty().addListener((ObservableValue<? extends PsxTouchButton> observable, PsxTouchButton oldValue, PsxTouchButton newValue) -> {
			bindButton(VitaTouchButton.TOUCH_UPPER_RIGHT, newValue);
		});

		mTouchBottomLeftCombo.setCellFactory(ListCellFactory.getPsxTouchButtonCellFactory(resources));
		mTouchBottomLeftCombo.setButtonCell(ListCellFactory.getPsxTouchButtonListCell(resources));
		mTouchBottomLeftCombo.setItems(buttons);
		mTouchBottomLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxTouchButton> observable, PsxTouchButton oldValue, PsxTouchButton newValue) -> {
			bindButton(VitaTouchButton.TOUCH_BOTTOM_LEFT, newValue);
		});

		mTouchBottomRightCombo.setCellFactory(ListCellFactory.getPsxTouchButtonCellFactory(resources));
		mTouchBottomRightCombo.setButtonCell(ListCellFactory.getPsxTouchButtonListCell(resources));
		mTouchBottomRightCombo.setItems(buttons);
		mTouchBottomRightCombo.valueProperty().addListener((ObservableValue<? extends PsxTouchButton> observable, PsxTouchButton oldValue, PsxTouchButton newValue) -> {
			bindButton(VitaTouchButton.TOUCH_BOTTOM_RIGHT, newValue);
		});

		mTouchUpperLeftCombo.setValue(PsxTouchButton.UNUSED);
		mTouchUpperRightCombo.setValue(PsxTouchButton.UNUSED);
		mTouchBottomLeftCombo.setValue(PsxTouchButton.UNUSED);
		mTouchBottomRightCombo.setValue(PsxTouchButton.UNUSED);
	}

	private void setupRearTouchPadTab(ResourceBundle resources) {
		ObservableList<PsxTouchButton> rButtons = FXCollections.observableArrayList(PsxTouchButton.UNUSED, PsxTouchButton.R1, PsxTouchButton.R2, PsxTouchButton.R3);
		ObservableList<PsxTouchButton> lButtons = FXCollections.observableArrayList(PsxTouchButton.UNUSED, PsxTouchButton.L1, PsxTouchButton.L2, PsxTouchButton.L3);

		mRearTouchUpperLeftCombo.setCellFactory(ListCellFactory.getPsxTouchButtonCellFactory(resources));
		mRearTouchUpperLeftCombo.setButtonCell(ListCellFactory.getPsxTouchButtonListCell(resources));
		mRearTouchUpperLeftCombo.setItems(lButtons);
		mRearTouchUpperLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxTouchButton> observable, PsxTouchButton oldValue, PsxTouchButton newValue) -> {
			bindButton(VitaTouchButton.REAR_TOUCH_UPPER_LEFT, newValue);
		});

		mRearTouchUpperRightCombo.setCellFactory(ListCellFactory.getPsxTouchButtonCellFactory(resources));
		mRearTouchUpperRightCombo.setButtonCell(ListCellFactory.getPsxTouchButtonListCell(resources));
		mRearTouchUpperRightCombo.setItems(rButtons);
		mRearTouchBottomRightCombo.valueProperty().addListener((ObservableValue<? extends PsxTouchButton> observable, PsxTouchButton oldValue, PsxTouchButton newValue) -> {
			bindButton(VitaTouchButton.REAR_TOUCH_UPPER_RIGHT, newValue);
		});

		mRearTouchBottomLeftCombo.setCellFactory(ListCellFactory.getPsxTouchButtonCellFactory(resources));
		mRearTouchBottomLeftCombo.setButtonCell(ListCellFactory.getPsxTouchButtonListCell(resources));
		mRearTouchBottomLeftCombo.setItems(lButtons);
		mRearTouchBottomLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxTouchButton> observable, PsxTouchButton oldValue, PsxTouchButton newValue) -> {
			bindButton(VitaTouchButton.REAR_TOUCH_BOTTOM_LEFT, newValue);
		});

		mRearTouchBottomRightCombo.setCellFactory(ListCellFactory.getPsxTouchButtonCellFactory(resources));
		mRearTouchBottomRightCombo.setButtonCell(ListCellFactory.getPsxTouchButtonListCell(resources));
		mRearTouchBottomRightCombo.setItems(rButtons);
		mRearTouchBottomRightCombo.valueProperty().addListener((ObservableValue<? extends PsxTouchButton> observable, PsxTouchButton oldValue, PsxTouchButton newValue) -> {
			bindButton(VitaTouchButton.REAR_TOUCH_BOTTOM_RIGHT, newValue);
		});

		mRearTouchUpperLeftCombo.setValue(PsxTouchButton.UNUSED);
		mRearTouchUpperRightCombo.setValue(PsxTouchButton.UNUSED);
		mRearTouchBottomLeftCombo.setValue(PsxTouchButton.UNUSED);
		mRearTouchBottomRightCombo.setValue(PsxTouchButton.UNUSED);
	}

	private void bindButton(VitaButton vitaButton, PsxButton psxButton) {
		try {
			GamePadController.getInstance().assign(vitaButton, psxButton);
		} catch (Exception e) {
			Logger.getLogger(TAG).log(Level.INFO, null, e);
			Logger.getLogger(TAG).log(
				Level.INFO, "Bind button fail: VitaButton: {0}, PsxButton: {1}",
				new Object[]{vitaButton, psxButton});
		}
	}

	private void bindButton(VitaTouchButton vitaButton, PsxTouchButton psxButton) {
		try {
			GamePadController.getInstance().assign(vitaButton, psxButton);
		} catch (Exception e) {
			Logger.getLogger(TAG).log(Level.INFO, null, e);
			Logger.getLogger(TAG).log(
				Level.INFO, "Bind button fail: VitaButton: {0}, PsxButton: {1}",
				new Object[]{vitaButton, psxButton});
		}
	}

}
