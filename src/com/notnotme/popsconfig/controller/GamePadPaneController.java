package com.notnotme.popsconfig.controller;

import com.notnotme.popsconfig.model.gamepad.GamePadMapping;
import com.notnotme.popsconfig.model.gamepad.GamePadMode;
import com.notnotme.popsconfig.model.gamepad.GamePadPort;
import com.notnotme.popsconfig.model.gamepad.PsxButton;
import com.notnotme.popsconfig.model.gamepad.VitaButton;
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

	@FXML private Tab mCustomControlsTab;

	@FXML private ComboBox<GamePadMapping> mCustomButtonsCombo;
	@FXML private ComboBox<GamePadPort> mControllersCombo;
	@FXML private ComboBox<GamePadMode> mModesCombo;

	@FXML private ComboBox<PsxButton> mTouchUpperLeftCombo;
	@FXML private ComboBox<PsxButton> mTouchUpperRightCombo;
	@FXML private ComboBox<PsxButton> mTouchBottomLeftCombo;
	@FXML private ComboBox<PsxButton> mTouchBottomRightCombo;

	@FXML private ComboBox<PsxButton> mRearTouchUpperLeftCombo;
	@FXML private ComboBox<PsxButton> mRearTouchUpperRightCombo;
	@FXML private ComboBox<PsxButton> mRearTouchBottomLeftCombo;
	@FXML private ComboBox<PsxButton> mRearTouchBottomRightCombo;

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

		setupControlsTab(resources);
		setupCustomControlsTab(resources);
		setupTouchPadTab(resources);
		setupRearTouchPadTab(resources);
	}

	private void setupControlsTab(ResourceBundle resources) {
		// Set datas for the controls selection (custom/defaut) and handle the values change
		// - Disable the custom controls option and tab at startup
		// - Enable the custom controls tab if custom controls is selected if value change
		mCustomControlsTab.setDisable(true);
		mCustomButtonsCombo.setCellFactory(ListCellFactory.getControllerControlsCellFactory(resources));
		mCustomButtonsCombo.setButtonCell(ListCellFactory.getControllerControlsListCell(resources));
		mCustomButtonsCombo.setItems(FXCollections.observableArrayList(GamePadMapping.values()));
		mCustomButtonsCombo.setValue(GamePadMapping.DEFAULT);
		mCustomButtonsCombo.valueProperty().addListener((ObservableValue<? extends GamePadMapping> observable, GamePadMapping oldValue, GamePadMapping newValue) -> {
			mCustomControlsTab.setDisable(newValue == GamePadMapping.DEFAULT);
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
		mModesCombo.setCellFactory(ListCellFactory.getControllerModeCellFactory(resources));
		mModesCombo.setButtonCell(ListCellFactory.getControllerModeListCell(resources));
		mModesCombo.setItems(FXCollections.observableArrayList(GamePadMode.values()));
		mModesCombo.setValue(GamePadMode.NUMERIC);
		mModesCombo.valueProperty().addListener((ObservableValue<? extends GamePadMode> observable, GamePadMode oldValue, GamePadMode newValue) -> {
			GamePadController.getInstance().setControllerMode(newValue);
		});
	}

	private void setupCustomControlsTab(ResourceBundle resources) {
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
		ObservableList<PsxButton> buttons = FXCollections.observableArrayList(PsxButton.UNUSED,
				PsxButton.UP, PsxButton.DOWN, PsxButton.LEFT, PsxButton.RIGHT, PsxButton.START, PsxButton.SELECT,
				PsxButton.CROSS, PsxButton.SQUARE, PsxButton.CIRCLE, PsxButton.TRIANGLE, PsxButton.L1, PsxButton.L2,
				PsxButton.L3, PsxButton.R1, PsxButton.R2, PsxButton.R3);

		mTouchUpperLeftCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mTouchUpperLeftCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mTouchUpperLeftCombo.setItems(buttons);
		mTouchUpperLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.TOUCH_UPPER_LEFT, newValue);
		});

		mTouchUpperRightCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mTouchUpperRightCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mTouchUpperRightCombo.setItems(buttons);
		mTouchUpperRightCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.TOUCH_UPPER_RIGHT, newValue);
		});

		mTouchBottomLeftCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mTouchBottomLeftCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mTouchBottomLeftCombo.setItems(buttons);
		mTouchBottomLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.TOUCH_BOTTOM_LEFT, newValue);
		});

		mTouchBottomRightCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mTouchBottomRightCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mTouchBottomRightCombo.setItems(buttons);
		mTouchBottomRightCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.TOUCH_BOTTOM_RIGHT, newValue);
		});

		mTouchUpperLeftCombo.setValue(PsxButton.UNUSED);
		mTouchUpperRightCombo.setValue(PsxButton.UNUSED);
		mTouchBottomLeftCombo.setValue(PsxButton.UNUSED);
		mTouchBottomRightCombo.setValue(PsxButton.UNUSED);
	}

	private void setupRearTouchPadTab(ResourceBundle resources) {
		ObservableList<PsxButton> rButtons = FXCollections.observableArrayList(PsxButton.UNUSED, PsxButton.R1, PsxButton.R2, PsxButton.R3);
		ObservableList<PsxButton> lButtons = FXCollections.observableArrayList(PsxButton.UNUSED, PsxButton.L1, PsxButton.L2, PsxButton.L3);

		mRearTouchUpperLeftCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRearTouchUpperLeftCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRearTouchUpperLeftCombo.setItems(lButtons);
		mRearTouchUpperLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.REAR_TOUCH_UPPER_LEFT, newValue);
		});

		mRearTouchUpperRightCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRearTouchUpperRightCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRearTouchUpperRightCombo.setItems(rButtons);
		mRearTouchBottomRightCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.REAR_TOUCH_UPPER_RIGHT, newValue);
		});

		mRearTouchBottomLeftCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRearTouchBottomLeftCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRearTouchBottomLeftCombo.setItems(lButtons);
		mRearTouchBottomLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.REAR_TOUCH_BOTTOM_LEFT, newValue);
		});

		mRearTouchBottomRightCombo.setCellFactory(ListCellFactory.getPsxButtonCellFactory(resources));
		mRearTouchBottomRightCombo.setButtonCell(ListCellFactory.getPsxButtonListCell(resources));
		mRearTouchBottomRightCombo.setItems(rButtons);
		mRearTouchBottomRightCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.REAR_TOUCH_BOTTOM_RIGHT, newValue);
		});

		mRearTouchUpperLeftCombo.setValue(PsxButton.UNUSED);
		mRearTouchUpperRightCombo.setValue(PsxButton.UNUSED);
		mRearTouchBottomLeftCombo.setValue(PsxButton.UNUSED);
		mRearTouchBottomRightCombo.setValue(PsxButton.UNUSED);
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

}
