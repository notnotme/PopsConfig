package com.notnotme.psxbubblepad.ui.factory;

import com.notnotme.psxbubblepad.model.gamepad.PsxButton;
import com.notnotme.psxbubblepad.model.gamepad.PsxControllerMapping;
import com.notnotme.psxbubblepad.model.gamepad.PsxControllerMode;
import com.notnotme.psxbubblepad.model.gamepad.PsxControllerPort;
import com.notnotme.psxbubblepad.ui.cell.ControllerControlsListCell;
import com.notnotme.psxbubblepad.ui.cell.ControllerModeListCell;
import com.notnotme.psxbubblepad.ui.cell.ControllerPortListCell;
import com.notnotme.psxbubblepad.ui.cell.PsxButtonListCell;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @author romain
 */
public class ListCellFactory {

	public static PsxButtonListCell getPsxButtonListCell(ResourceBundle resources) {
		return new PsxButtonListCell(resources);
	}

	public static Callback<ListView<PsxButton>, ListCell<PsxButton>> getPsxButtonCellFactory(ResourceBundle resources) {
		return (ListView<PsxButton> param) -> getPsxButtonListCell(resources);
	}

	public static ControllerControlsListCell getControllerControlsListCell(ResourceBundle resources) {
		return new ControllerControlsListCell(resources);
	}

	public static Callback<ListView<PsxControllerMapping>, ListCell<PsxControllerMapping>> getControllerControlsCellFactory(ResourceBundle resources) {
		return (ListView<PsxControllerMapping> param) -> getControllerControlsListCell(resources);
	}

	public static ControllerPortListCell getControllerPortListCell(ResourceBundle resources) {
		return new ControllerPortListCell(resources);
	}

	public static Callback<ListView<PsxControllerPort>, ListCell<PsxControllerPort>> getControllerPortCellFactory(ResourceBundle resources) {
		return (ListView<PsxControllerPort> param) -> getControllerPortListCell(resources);
	}

	public static ControllerModeListCell getControllerModeListCell(ResourceBundle resources) {
		return new ControllerModeListCell(resources);
	}

	public static Callback<ListView<PsxControllerMode>, ListCell<PsxControllerMode>> getControllerModeCellFactory(ResourceBundle resources) {
		return (ListView<PsxControllerMode> param) -> getControllerModeListCell(resources);
	}

}
