package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.gamepad.PsxTouchButton;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public final class PsxTouchButtonListCell extends ListCell<PsxTouchButton> {

	private final ResourceBundle mResources;

	public PsxTouchButtonListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(PsxTouchButton item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
