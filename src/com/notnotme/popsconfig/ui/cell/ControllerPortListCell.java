package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.gamepad.GamePadPort;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public class ControllerPortListCell extends ListCell<GamePadPort> {

	private final ResourceBundle mResources;

	public ControllerPortListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(GamePadPort item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
