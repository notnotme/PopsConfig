package com.notnotme.psxbubblepad.controller;

import com.notnotme.psxbubblepad.model.PsxControllerMapping;
import com.notnotme.psxbubblepad.model.PsxControllerMode;
import com.notnotme.psxbubblepad.model.PsxControllerPort;
import com.notnotme.psxbubblepad.model.PsxButton;
import com.notnotme.psxbubblepad.model.VitaButton;
import com.notnotme.psxbubblepad.ui.cell.ControllerControlsListCell;
import com.notnotme.psxbubblepad.ui.cell.ControllerModeListCell;
import com.notnotme.psxbubblepad.ui.cell.ControllerPortListCell;
import com.notnotme.psxbubblepad.ui.cell.PsxButtonListCell;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

/**
 * @author romain
 */
public class GamePadPaneController implements Initializable {

	private final static String TAG = GamePadPaneController.class.getSimpleName();

	@FXML private Tab mCustomControlsTab;

	@FXML private ComboBox<PsxControllerMapping> mCustomButtonsCombo;
	@FXML private ComboBox<PsxControllerPort> mControllersCombo;
	@FXML private ComboBox<PsxControllerMode> mModesCombo;

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
		mCustomButtonsCombo.setCellFactory((ListView<PsxControllerMapping> param) -> {
				return new ControllerControlsListCell(resources);
		});
		mCustomButtonsCombo.setButtonCell(new ControllerControlsListCell(resources));
		mCustomButtonsCombo.setItems(FXCollections.observableArrayList(PsxControllerMapping.values()));
		mCustomButtonsCombo.setValue(PsxControllerMapping.DEFAULT);
		mCustomButtonsCombo.valueProperty().addListener((ObservableValue<? extends PsxControllerMapping> observable, PsxControllerMapping oldValue, PsxControllerMapping newValue) -> {
			mCustomControlsTab.setDisable(newValue == PsxControllerMapping.DEFAULT);
			GamePadController.getInstance().setControls(newValue);
		});

		// Set datas for the controllers selection (player 1 / player 2) and handle the values change
		// - Select player 1 by default at startup
		mControllersCombo.setCellFactory((ListView<PsxControllerPort> param) -> {
				return new ControllerPortListCell(resources);
		});
		mControllersCombo.setButtonCell(new ControllerPortListCell(resources));
		mControllersCombo.setItems(FXCollections.observableArrayList(PsxControllerPort.values()));
		mControllersCombo.setValue(PsxControllerPort.PORT_1);
		mControllersCombo.valueProperty().addListener((ObservableValue<? extends PsxControllerPort> observable, PsxControllerPort oldValue, PsxControllerPort newValue) -> {
			GamePadController.getInstance().setControllerPort(newValue);
		});

		// Set datas for the controllers mode selection (analog/numeric) and handle the values change
		// - Select numeric by default at startup
		mModesCombo.setCellFactory((ListView<PsxControllerMode> param) -> {
				return new ControllerModeListCell(resources);
		});
		mModesCombo.setButtonCell(new ControllerModeListCell(resources));
		mModesCombo.setItems(FXCollections.observableArrayList(PsxControllerMode.values()));
		mModesCombo.setValue(PsxControllerMode.NUMERIC);
		mModesCombo.valueProperty().addListener((ObservableValue<? extends PsxControllerMode> observable, PsxControllerMode oldValue, PsxControllerMode newValue) -> {
			GamePadController.getInstance().setControllerMode(newValue);
		});
	}

	private void setupCustomControlsTab(ResourceBundle resources) {
		ObservableList<PsxButton> buttons = FXCollections.observableArrayList(PsxButton.UNUSED,
				PsxButton.UP, PsxButton.DOWN, PsxButton.LEFT, PsxButton.RIGHT, PsxButton.START, PsxButton.SELECT,
				PsxButton.CROSS, PsxButton.SQUARE, PsxButton.CIRCLE, PsxButton.TRIANGLE, PsxButton.L1, PsxButton.L2,
				PsxButton.L3, PsxButton.R1, PsxButton.R2, PsxButton.R3, PsxButton.L1_R1, PsxButton.L2_R2);

		mUpButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mUpButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mUpButtonCombo.setItems(buttons);
		mUpButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.UP, newValue);
		});

		mLeftButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mLeftButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mLeftButtonCombo.setItems(buttons);
		mLeftButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT, newValue);
		});

		mRightButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRightButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mRightButtonCombo.setItems(buttons);
		mRightButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.RIGHT, newValue);
		});

		mDownButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mDownButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mDownButtonCombo.setItems(buttons);
		mDownButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.DOWN, newValue);
		});

		mLButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mLButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mLButtonCombo.setItems(buttons);
		mLButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.L, newValue);
		});

		mRButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mRButtonCombo.setItems(buttons);
		mRButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.R, newValue);
		});

		mSquareButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mSquareButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mSquareButtonCombo.setItems(buttons);
		mSquareButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.SQUARE, newValue);
		});

		mCrossButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mCrossButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mCrossButtonCombo.setItems(buttons);
		mCrossButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.CROSS, newValue);
		});

		mCircleButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mCircleButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mCircleButtonCombo.setItems(buttons);
		mCircleButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.CIRCLE, newValue);
		});

		mTriangleButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mTriangleButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mTriangleButtonCombo.setItems(buttons);
		mTriangleButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.TRIANGLE, newValue);
		});

		mLStickLeftButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mLStickLeftButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mLStickLeftButtonCombo.setItems(buttons);
		mLStickLeftButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT_ANALOG_LEFT, newValue);
		});

		mLStickRightButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mLStickRightButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mLStickRightButtonCombo.setItems(buttons);
		mLStickRightButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT_LANALOG_RIGHT, newValue);
		});

		mLStickUpButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mLStickUpButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mLStickUpButtonCombo.setItems(buttons);
		mLStickUpButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT_ANALOG_UP, newValue);
		});

		mLStickDownButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mLStickDownButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mLStickDownButtonCombo.setItems(buttons);
		mLStickDownButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.LEFT_ANALOG_DOWN, newValue);
		});

		mRStickLeftButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRStickLeftButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mRStickLeftButtonCombo.setItems(buttons);
		mRStickLeftButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.RIGHT_ANALOG_LEFT, newValue);
		});

		mRStickRightButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRStickRightButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mRStickRightButtonCombo.setItems(buttons);
		mRStickRightButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.RIGHT_ANALOG_RIGHT, newValue);
		});

		mRStickUpButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRStickUpButtonCombo.setButtonCell(new PsxButtonListCell(resources));
		mRStickUpButtonCombo.setItems(buttons);
		mRStickUpButtonCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.RIGHT_ANALOG_UP, newValue);
		});

		mRStickDownButtonCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRStickDownButtonCombo.setButtonCell(new PsxButtonListCell(resources));
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

		mTouchUpperLeftCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mTouchUpperLeftCombo.setButtonCell(new PsxButtonListCell(resources));
		mTouchUpperLeftCombo.setItems(buttons);
		mTouchUpperLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.TOUCH_UPPER_LEFT, newValue);
		});

		mTouchUpperRightCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mTouchUpperRightCombo.setButtonCell(new PsxButtonListCell(resources));
		mTouchUpperRightCombo.setItems(buttons);
		mTouchUpperRightCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.TOUCH_UPPER_RIGHT, newValue);
		});

		mTouchBottomLeftCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mTouchBottomLeftCombo.setButtonCell(new PsxButtonListCell(resources));
		mTouchBottomLeftCombo.setItems(buttons);
		mTouchBottomLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.TOUCH_BOTTOM_LEFT, newValue);
		});

		mTouchBottomRightCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mTouchBottomRightCombo.setButtonCell(new PsxButtonListCell(resources));
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

		mRearTouchUpperLeftCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRearTouchUpperLeftCombo.setButtonCell(new PsxButtonListCell(resources));
		mRearTouchUpperLeftCombo.setItems(lButtons);
		mRearTouchUpperLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.REAR_TOUCH_UPPER_LEFT, newValue);
		});

		mRearTouchUpperRightCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRearTouchUpperRightCombo.setButtonCell(new PsxButtonListCell(resources));
		mRearTouchUpperRightCombo.setItems(rButtons);
		mRearTouchBottomRightCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.REAR_TOUCH_UPPER_RIGHT, newValue);
		});

		mRearTouchBottomLeftCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRearTouchBottomLeftCombo.setButtonCell(new PsxButtonListCell(resources));
		mRearTouchBottomLeftCombo.setItems(lButtons);
		mRearTouchBottomLeftCombo.valueProperty().addListener((ObservableValue<? extends PsxButton> observable, PsxButton oldValue, PsxButton newValue) -> {
			bindButton(VitaButton.REAR_TOUCH_BOTTOM_LEFT, newValue);
		});

		mRearTouchBottomRightCombo.setCellFactory((ListView<PsxButton> param) -> {
			return new PsxButtonListCell(resources);
		});
		mRearTouchBottomRightCombo.setButtonCell(new PsxButtonListCell(resources));
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
			Logger.getLogger(TAG).log(
				Level.INFO, "Bind button fail: VitaButton: {0}, PsxButton: {1}",
				new Object[]{vitaButton, psxButton});
		}
	}

}
