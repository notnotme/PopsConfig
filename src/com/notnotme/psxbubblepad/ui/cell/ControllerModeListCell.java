package com.notnotme.psxbubblepad.ui.cell;

import com.notnotme.psxbubblepad.model.gamepad.PsxControllerMode;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public class ControllerModeListCell extends ListCell<PsxControllerMode> {

	private final ResourceBundle mResources;

	public ControllerModeListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(PsxControllerMode item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
