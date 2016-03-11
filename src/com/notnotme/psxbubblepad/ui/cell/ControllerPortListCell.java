package com.notnotme.psxbubblepad.ui.cell;

import com.notnotme.psxbubblepad.model.gamepad.PsxControllerPort;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public class ControllerPortListCell extends ListCell<PsxControllerPort> {

	private final ResourceBundle mResources;

	public ControllerPortListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(PsxControllerPort item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
