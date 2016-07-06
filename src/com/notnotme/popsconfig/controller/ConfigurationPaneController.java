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
import com.notnotme.popsconfig.model.screen.Screen;
import com.notnotme.popsconfig.model.screen.ScreenFilter;
import com.notnotme.popsconfig.model.screen.ScreenMode;
import com.notnotme.popsconfig.ui.factory.ListCellFactory;
import com.sun.javafx.geom.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

/**
 * @author romain
 */
public final class ConfigurationPaneController implements Initializable {

	private final static String TAG = ConfigurationPaneController.class.getSimpleName();
	private final static int EMU_WIDTH = 320;
	private final static int EMU_HEIGHT = 240;
	private final static int SCREEN_HEIGHT = 272;

	@FXML private TabPane mTabPane;
	@FXML private Tab mCustomButtonsTab;
	@FXML private Tab mCustomScreenTab;

	@FXML private ComboBox<GamePadMapping> mButtonsMappingCombo;
	@FXML private ComboBox<GamePadPort> mControllersCombo;
	@FXML private ComboBox<GamePadMode> mControllerModeCombo;
	@FXML private ComboBox<ScreenMode> mScreenModeCombo;
	@FXML private ComboBox<ScreenFilter> mScreenFilterCombo;
	@FXML private ComboBox<DiscLoading> mDiscLoadingCombo;
	@FXML private ComboBox<SoundVolume> mAudioBoostCombo;
	@FXML private ComboBox<Integer> mDiscNumberCombo;

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

	@FXML private Spinner<Integer> mCustomScreenXSpinner;
	@FXML private Spinner<Integer> mCustomScreenYSpinner;
	@FXML private Spinner<Integer> mCustomScreenWidthSpinner;
	@FXML private Spinner<Integer> mCustomScreenHeightSpinner;
	@FXML private StackPane mScreenStackPane;
	@FXML private javafx.scene.shape.Rectangle mScreenRect;
	@FXML private Button mResetScreenButton;

	/**
	 * A OnChangeListener that update the status bar when the GamePad model is saved or modified.
	 */
	private final ConfigController.OnChangeListener mOnChangeListener = new ConfigController.OnChangeListener() {
		@Override
		public void onChanged() {
		}

		@Override
		public void onSaved() {
		}

		@Override
		public void onLoaded() {
			refreshDatas();
			mTabPane.getSelectionModel().select(0);
		}
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Logger.getLogger(TAG).log(
				Level.INFO, "initialize() location: {0}, resources: {1}",
				new Object[]{location, resources});

		setupMainTab(resources);
		setupTouchPadTab(resources);
		setupRearTouchPadTab(resources);
		setupCustomButtonsTab(resources);
		setupCustomScreenTab(resources);
		refreshDatas();

		ConfigController.getInstance().addListener(mOnChangeListener);
	}

	private void setupMainTab(ResourceBundle resources) {
		ConfigController controller = ConfigController.getInstance();

		// Set datas for the controls selection (custom/defaut) and handle the values change
		// - Disable the custom controls option and tab at startup
		// - Enable the custom controls tab if custom controls is selected if value change
		mButtonsMappingCombo.setCellFactory(ListCellFactory.getControllerControlsCellFactory(resources));
		mButtonsMappingCombo.setButtonCell(ListCellFactory.getControllerControlsListCell(resources));
		mButtonsMappingCombo.setItems(FXCollections.observableArrayList(GamePadMapping.values()));
		mButtonsMappingCombo.valueProperty().addListener((ObservableValue<? extends GamePadMapping> observable, GamePadMapping oldValue, GamePadMapping newValue) -> {
			mCustomButtonsTab.setDisable(newValue == GamePadMapping.DEFAULT);
			controller.setGamePadMapping(newValue);
		});
		mCustomButtonsTab.setDisable(controller.getGamePadMapping() != GamePadMapping.CUSTOM);

		// Set datas for the controllers selection (player 1 / player 2) and handle the values change
		// - Select player 1 by default at startup
		mControllersCombo.setCellFactory(ListCellFactory.getControllerPortCellFactory(resources));
		mControllersCombo.setButtonCell(ListCellFactory.getControllerPortListCell(resources));
		mControllersCombo.setItems(FXCollections.observableArrayList(GamePadPort.values()));
		mControllersCombo.valueProperty().addListener((ObservableValue<? extends GamePadPort> observable, GamePadPort oldValue, GamePadPort newValue) -> {
			controller.setGamePadPort(newValue);
		});

		// Set datas for the controllers mode selection (analog/numeric) and handle the values change
		// - Select numeric by default at startup
		mControllerModeCombo.setCellFactory(ListCellFactory.getControllerModeCellFactory(resources));
		mControllerModeCombo.setButtonCell(ListCellFactory.getControllerModeListCell(resources));
		mControllerModeCombo.setItems(FXCollections.observableArrayList(GamePadMode.values()));
		mControllerModeCombo.valueProperty().addListener((ObservableValue<? extends GamePadMode> observable, GamePadMode oldValue, GamePadMode newValue) -> {
			controller.setGamePadMode(newValue);
		});

		// Screen mode
		mScreenModeCombo.setCellFactory(ListCellFactory.getScreenModeCellFactory(resources));
		mScreenModeCombo.setButtonCell(ListCellFactory.getScreenModeListCell(resources));
		mScreenModeCombo.setItems(FXCollections.observableArrayList(ScreenMode.values()));
		mScreenModeCombo.valueProperty().addListener((ObservableValue<? extends ScreenMode> observable, ScreenMode oldValue, ScreenMode newValue) -> {
			mCustomScreenTab.setDisable(newValue != ScreenMode.CUSTOM);
			controller.setScreenMode(newValue);
		});
		mCustomScreenTab.setDisable(controller.getScreenMode() != ScreenMode.CUSTOM);

		// Screen filtering
		mScreenFilterCombo.setCellFactory(ListCellFactory.getScreenFilterCellFactory(resources));
		mScreenFilterCombo.setButtonCell(ListCellFactory.getScreenFilterListCell(resources));
		mScreenFilterCombo.setItems(FXCollections.observableArrayList(ScreenFilter.values()));
		mScreenFilterCombo.valueProperty().addListener((ObservableValue<? extends ScreenFilter> observable, ScreenFilter oldValue, ScreenFilter newValue) -> {
			controller.setScreenFilter(newValue);
		});

		// Disc loading
		mDiscLoadingCombo.setCellFactory(ListCellFactory.getDiscLoadingCellFactory(resources));
		mDiscLoadingCombo.setButtonCell(ListCellFactory.getDiscLoadingListCell(resources));
		mDiscLoadingCombo.setItems(FXCollections.observableArrayList(DiscLoading.values()));
		mDiscLoadingCombo.valueProperty().addListener((ObservableValue<? extends DiscLoading> observable, DiscLoading oldValue, DiscLoading newValue) -> {
			controller.setDiscLoading(newValue);
		});

		// Audio boost
		mAudioBoostCombo.setCellFactory(ListCellFactory.getSoundVolumeCellFactory(resources));
		mAudioBoostCombo.setButtonCell(ListCellFactory.getSoundVolumeListCell(resources));
		mAudioBoostCombo.setItems(FXCollections.observableArrayList(SoundVolume.values()));
		mAudioBoostCombo.valueProperty().addListener((ObservableValue<? extends SoundVolume> observable, SoundVolume oldValue, SoundVolume newValue) -> {
			controller.setSoundVolume(newValue);
		});

		// Disc number
		mDiscNumberCombo.setItems(FXCollections.observableArrayList(1,2,3,4));
		mDiscNumberCombo.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
			controller.setDiscNumber(newValue - 1);
		});
	}

	private void setupCustomButtonsTab(ResourceBundle resources) {
		final ObservableList<PsxButton> buttons = FXCollections.observableArrayList(PsxButton.UNUSED,
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
			bindButton(VitaButton.LEFT_ANALOG_RIGHT, newValue);
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
	}

	private void setupTouchPadTab(ResourceBundle resources) {
		final ObservableList<PsxTouchButton> buttons = FXCollections.observableArrayList(PsxTouchButton.UNUSED,
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
	}

	private void setupRearTouchPadTab(ResourceBundle resources) {
		final ObservableList<PsxTouchButton> rButtons = FXCollections.observableArrayList(PsxTouchButton.UNUSED, PsxTouchButton.R1, PsxTouchButton.R2, PsxTouchButton.R3);
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
		mRearTouchUpperRightCombo.valueProperty().addListener((ObservableValue<? extends PsxTouchButton> observable, PsxTouchButton oldValue, PsxTouchButton newValue) -> {
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
	}

	private void setupCustomScreenTab(ResourceBundle resources) {
		ConfigController controller = ConfigController.getInstance();

		mCustomScreenXSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-EMU_WIDTH/2, EMU_WIDTH/2));
		mCustomScreenXSpinner.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
			Rectangle rect = new Rectangle(controller.getScreenSize());
			rect.x = newValue;

			SpinnerValueFactory.IntegerSpinnerValueFactory factory =
					(SpinnerValueFactory.IntegerSpinnerValueFactory) mCustomScreenXSpinner.getValueFactory();

			if (rect.x < 0) {
				if ((rect.x-rect.width/2) < -EMU_WIDTH/2) {
					factory.setMin(rect.x);
					factory.setValue(rect.x);
				} else {
					factory.setMin(-EMU_WIDTH/2);
				}
			} else {
				if ((rect.x+rect.width/2) >= EMU_WIDTH/2) {
					factory.setMax(rect.x);
					factory.setValue(rect.x);
				} else {
					factory.setMax(EMU_WIDTH/2);
				}
			}

			mScreenRect.translateXProperty().set(rect.x/2);
			controller.setScreenSize(rect);
		});

		mCustomScreenYSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-SCREEN_HEIGHT,SCREEN_HEIGHT));
		mCustomScreenYSpinner.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
			Rectangle rect = new Rectangle(controller.getScreenSize());
			rect.y = newValue;

			SpinnerValueFactory.IntegerSpinnerValueFactory factory =
					(SpinnerValueFactory.IntegerSpinnerValueFactory) mCustomScreenYSpinner.getValueFactory();

			if (rect.y < 0) {
				if ((rect.y-rect.height/2) < -SCREEN_HEIGHT) {
					factory.setMin(rect.y);
					factory.setValue(rect.y);
				} else {
					factory.setMin(-SCREEN_HEIGHT);
				}
			} else {
				if ((rect.y+rect.height/2) >= SCREEN_HEIGHT) {
					factory.setMax(rect.y);
					factory.setValue(rect.y);
				} else {
					factory.setMax(SCREEN_HEIGHT);
				}
			}

			mScreenRect.translateYProperty().set(rect.y/2);
			controller.setScreenSize(rect);
		});

		mCustomScreenWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,EMU_WIDTH));
		mCustomScreenWidthSpinner.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
			Rectangle rect = new Rectangle(controller.getScreenSize());
			rect.width = newValue;

			SpinnerValueFactory.IntegerSpinnerValueFactory factory =
					(SpinnerValueFactory.IntegerSpinnerValueFactory) mCustomScreenWidthSpinner.getValueFactory();

			if (rect.x < 0) {
				if ((rect.x-rect.width/2) < -EMU_WIDTH/2) {
					factory.setMax(rect.width);
					factory.setValue(rect.width);
				} else {
					factory.setMax(EMU_WIDTH);
				}
			} else {
				if ((rect.x+rect.width/2) >= EMU_WIDTH/2) {
					factory.setMax(rect.width);
					factory.setValue(rect.width);
				} else {
					factory.setMax(EMU_WIDTH);
				}
			}

			mScreenRect.setWidth(Screen.BASE_WIDTH + rect.width/2);
			controller.setScreenSize(rect);
		});

		mCustomScreenHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,EMU_HEIGHT));
		mCustomScreenHeightSpinner.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
			Rectangle rect = new Rectangle(controller.getScreenSize());
			rect.height = newValue;

			SpinnerValueFactory.IntegerSpinnerValueFactory factory =
					(SpinnerValueFactory.IntegerSpinnerValueFactory) mCustomScreenHeightSpinner.getValueFactory();

			if (rect.y < 0) {
				if ((rect.y-rect.height/2) < -SCREEN_HEIGHT) {
					factory.setMax(rect.height);
					factory.setValue(rect.height);
				} else {
					factory.setMax(EMU_HEIGHT);
				}
			} else {
				if ((rect.y+rect.height/2) >= SCREEN_HEIGHT) {
					factory.setMax(rect.height);
					factory.setValue(rect.height);
				} else {
					factory.setMax(EMU_HEIGHT);
				}
			}

			mScreenRect.setHeight(Screen.BASE_HEIGHT + rect.height/2);
			controller.setScreenSize(rect);
		});

		mResetScreenButton.setOnAction((ActionEvent event) -> {
			mCustomScreenWidthSpinner.getValueFactory().setValue(0);
			mCustomScreenHeightSpinner.getValueFactory().setValue(0);
			mCustomScreenXSpinner.getValueFactory().setValue(0);
			mCustomScreenYSpinner.getValueFactory().setValue(0);
		});

		javafx.scene.shape.Rectangle clipRectangle = new javafx.scene.shape.Rectangle();
		mScreenStackPane.setClip(clipRectangle);
		mScreenStackPane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
			clipRectangle.setWidth(newValue.getWidth());
			clipRectangle.setHeight(newValue.getHeight());
		});
	}

	private void refreshDatas() {
		ConfigController controller = ConfigController.getInstance();

		mControllersCombo.setValue(controller.getGamePadPort());
		mButtonsMappingCombo.setValue(controller.getGamePadMapping());
		mControllerModeCombo.setValue(controller.getGamePadMode());
		mScreenModeCombo.setValue(controller.getScreenMode());
		mScreenFilterCombo.setValue(controller.getScreenFilter());
		mDiscLoadingCombo.setValue(controller.getDiscLoading());
		mAudioBoostCombo.setValue(controller.getSoundVolume());
		mDiscNumberCombo.setValue(controller.getDiscNumber()+1);

		mUpButtonCombo.setValue(controller.get(VitaButton.UP));
		mLeftButtonCombo.setValue(controller.get(VitaButton.LEFT));
		mRightButtonCombo.setValue(controller.get(VitaButton.RIGHT));
		mDownButtonCombo.setValue(controller.get(VitaButton.DOWN));
		mLButtonCombo.setValue(controller.get(VitaButton.L));
		mRButtonCombo.setValue(controller.get(VitaButton.R));
		mSquareButtonCombo.setValue(controller.get(VitaButton.SQUARE));
		mCrossButtonCombo.setValue(controller.get(VitaButton.CROSS));
		mCircleButtonCombo.setValue(controller.get(VitaButton.CIRCLE));
		mTriangleButtonCombo.setValue(controller.get(VitaButton.TRIANGLE));
		mLStickLeftButtonCombo.setValue(controller.get(VitaButton.LEFT_ANALOG_LEFT));
		mLStickRightButtonCombo.setValue(controller.get(VitaButton.LEFT_ANALOG_RIGHT));
		mLStickUpButtonCombo.setValue(controller.get(VitaButton.LEFT_ANALOG_UP));
		mLStickDownButtonCombo.setValue(controller.get(VitaButton.LEFT_ANALOG_DOWN));
		mRStickLeftButtonCombo.setValue(controller.get(VitaButton.RIGHT_ANALOG_LEFT));
		mRStickRightButtonCombo.setValue(controller.get(VitaButton.RIGHT_ANALOG_RIGHT));
		mRStickUpButtonCombo.setValue(controller.get(VitaButton.RIGHT_ANALOG_UP));
		mRStickDownButtonCombo.setValue(controller.get(VitaButton.RIGHT_ANALOG_DOWN));

		mTouchUpperLeftCombo.setValue(controller.get(VitaTouchButton.TOUCH_UPPER_LEFT));
		mTouchUpperRightCombo.setValue(controller.get(VitaTouchButton.TOUCH_UPPER_RIGHT));
		mTouchBottomLeftCombo.setValue(controller.get(VitaTouchButton.TOUCH_BOTTOM_LEFT));
		mTouchBottomRightCombo.setValue(controller.get(VitaTouchButton.TOUCH_BOTTOM_RIGHT));

		mRearTouchUpperLeftCombo.setValue(controller.get(VitaTouchButton.REAR_TOUCH_UPPER_LEFT));
		mRearTouchUpperRightCombo.setValue(controller.get(VitaTouchButton.REAR_TOUCH_UPPER_RIGHT));
		mRearTouchBottomLeftCombo.setValue(controller.get(VitaTouchButton.REAR_TOUCH_BOTTOM_LEFT));
		mRearTouchBottomRightCombo.setValue(controller.get(VitaTouchButton.REAR_TOUCH_BOTTOM_RIGHT));

		Rectangle screen = controller.getScreenSize();
		mCustomScreenXSpinner.getValueFactory().setValue(screen.x);
		mCustomScreenYSpinner.getValueFactory().setValue(screen.y);
		mCustomScreenWidthSpinner.getValueFactory().setValue(screen.width);
		mCustomScreenHeightSpinner.getValueFactory().setValue(screen.height);
	}

	private void bindButton(VitaButton vitaButton, PsxButton psxButton) {
		ConfigController.getInstance().assign(vitaButton, psxButton);
		Logger.getLogger(TAG).log(
			Level.INFO, "Bind button: VitaButton: {0}, PsxButton: {1}",
			new Object[]{vitaButton, psxButton});
	}

	private void bindButton(VitaTouchButton vitaButton, PsxTouchButton psxButton) {
		try {
			ConfigController.getInstance().assign(vitaButton, psxButton);
		} catch (Exception e) {
			Logger.getLogger(TAG).log(Level.WARNING, null, e);
			Logger.getLogger(TAG).log(
				Level.WARNING, "Bind button fail: VitaButton: {0}, PsxButton: {1}",
				new Object[]{vitaButton, psxButton});
		}

		Logger.getLogger(TAG).log(
			Level.INFO, "Bind button: VitaButton: {0}, PsxButton: {1}",
			new Object[]{vitaButton, psxButton});
	}

}
