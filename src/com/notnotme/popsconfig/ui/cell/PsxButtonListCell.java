package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.gamepad.PsxButton;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public final class PsxButtonListCell extends ListCell<PsxButton> {

	private final ResourceBundle mResources;

	public PsxButtonListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(PsxButton item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
