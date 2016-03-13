package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.DiscLoading;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public final class DiscLoadingListCell extends ListCell<DiscLoading> {

	private final ResourceBundle mResources;

	public DiscLoadingListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(DiscLoading item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
