package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.gamepad.GamePadMapping;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public final class ControllerControlsListCell extends ListCell<GamePadMapping> {

	private final ResourceBundle mResources;

	public ControllerControlsListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(GamePadMapping item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
