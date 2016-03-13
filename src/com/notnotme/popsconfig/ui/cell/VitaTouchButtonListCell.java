package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.gamepad.VitaTouchButton;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public class VitaTouchButtonListCell extends ListCell<VitaTouchButton> {

	private final ResourceBundle mResources;

	public VitaTouchButtonListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(VitaTouchButton item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
