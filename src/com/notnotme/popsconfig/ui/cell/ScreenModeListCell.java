package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.screen.ScreenMode;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public class ScreenModeListCell extends ListCell<ScreenMode> {

	private final ResourceBundle mResources;

	public ScreenModeListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(ScreenMode item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
