package com.notnotme.popsconfig.ui.factory;

import com.notnotme.popsconfig.model.SoundVolume;
import com.notnotme.popsconfig.model.DiscLoading;
import com.notnotme.popsconfig.model.gamepad.PsxTouchButton;
import com.notnotme.popsconfig.model.gamepad.GamePadMapping;
import com.notnotme.popsconfig.model.gamepad.GamePadMode;
import com.notnotme.popsconfig.model.gamepad.GamePadPort;
import com.notnotme.popsconfig.model.gamepad.PsxButton;
import com.notnotme.popsconfig.model.screen.ScreenFilter;
import com.notnotme.popsconfig.model.screen.ScreenMode;
import com.notnotme.popsconfig.ui.cell.SoundVolumeListCell;
import com.notnotme.popsconfig.ui.cell.ControllerControlsListCell;
import com.notnotme.popsconfig.ui.cell.ControllerModeListCell;
import com.notnotme.popsconfig.ui.cell.ControllerPortListCell;
import com.notnotme.popsconfig.ui.cell.DiscLoadingListCell;
import com.notnotme.popsconfig.ui.cell.PsxButtonListCell;
import com.notnotme.popsconfig.ui.cell.PsxTouchButtonListCell;
import com.notnotme.popsconfig.ui.cell.ScreenFilterListCell;
import com.notnotme.popsconfig.ui.cell.ScreenModeListCell;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @author romain
 */
public final class ListCellFactory {

	public static PsxButtonListCell getPsxButtonListCell(ResourceBundle resources) {
		return new PsxButtonListCell(resources);
	}

	public static Callback<ListView<PsxButton>, ListCell<PsxButton>> getPsxButtonCellFactory(ResourceBundle resources) {
		return (ListView<PsxButton> param) -> getPsxButtonListCell(resources);
	}

	public static PsxTouchButtonListCell getPsxTouchButtonListCell(ResourceBundle resources) {
		return new PsxTouchButtonListCell(resources);
	}

	public static Callback<ListView<PsxTouchButton>, ListCell<PsxTouchButton>> getPsxTouchButtonCellFactory(ResourceBundle resources) {
		return (ListView<PsxTouchButton> param) -> getPsxTouchButtonListCell(resources);
	}

	public static ControllerControlsListCell getControllerControlsListCell(ResourceBundle resources) {
		return new ControllerControlsListCell(resources);
	}

	public static Callback<ListView<GamePadMapping>, ListCell<GamePadMapping>> getControllerControlsCellFactory(ResourceBundle resources) {
		return (ListView<GamePadMapping> param) -> getControllerControlsListCell(resources);
	}

	public static ControllerPortListCell getControllerPortListCell(ResourceBundle resources) {
		return new ControllerPortListCell(resources);
	}

	public static Callback<ListView<GamePadPort>, ListCell<GamePadPort>> getControllerPortCellFactory(ResourceBundle resources) {
		return (ListView<GamePadPort> param) -> getControllerPortListCell(resources);
	}

	public static ControllerModeListCell getControllerModeListCell(ResourceBundle resources) {
		return new ControllerModeListCell(resources);
	}

	public static Callback<ListView<GamePadMode>, ListCell<GamePadMode>> getControllerModeCellFactory(ResourceBundle resources) {
		return (ListView<GamePadMode> param) -> getControllerModeListCell(resources);
	}

	public static ScreenModeListCell getScreenModeListCell(ResourceBundle resources) {
		return new ScreenModeListCell(resources);
	}

	public static Callback<ListView<ScreenMode>, ListCell<ScreenMode>> getScreenModeCellFactory(ResourceBundle resources) {
		return (ListView<ScreenMode> param) -> getScreenModeListCell(resources);
	}

	public static ScreenFilterListCell getScreenFilterListCell(ResourceBundle resources) {
		return new ScreenFilterListCell(resources);
	}

	public static Callback<ListView<ScreenFilter>, ListCell<ScreenFilter>> getScreenFilterCellFactory(ResourceBundle resources) {
		return (ListView<ScreenFilter> param) -> getScreenFilterListCell(resources);
	}

	public static SoundVolumeListCell getSoundVolumeListCell(ResourceBundle resources) {
		return new SoundVolumeListCell(resources);
	}

	public static Callback<ListView<SoundVolume>, ListCell<SoundVolume>> getSoundVolumeCellFactory(ResourceBundle resources) {
		return (ListView<SoundVolume> param) -> getSoundVolumeListCell(resources);
	}

	public static DiscLoadingListCell getDiscLoadingListCell(ResourceBundle resources) {
		return new DiscLoadingListCell(resources);
	}

	public static Callback<ListView<DiscLoading>, ListCell<DiscLoading>> getDiscLoadingCellFactory(ResourceBundle resources) {
		return (ListView<DiscLoading> param) -> getDiscLoadingListCell(resources);
	}

}
