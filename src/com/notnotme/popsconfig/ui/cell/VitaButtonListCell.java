package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.gamepad.VitaButton;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public class VitaButtonListCell extends ListCell<VitaButton> {

	private final ResourceBundle mResources;

	public VitaButtonListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(VitaButton item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
