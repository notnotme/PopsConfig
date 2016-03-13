package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.screen.ScreenFilter;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public class ScreenFilterListCell extends ListCell<ScreenFilter> {

	private final ResourceBundle mResources;

	public ScreenFilterListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(ScreenFilter item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
