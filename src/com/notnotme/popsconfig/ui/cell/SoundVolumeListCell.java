package com.notnotme.popsconfig.ui.cell;

import com.notnotme.popsconfig.model.SoundVolume;
import java.util.ResourceBundle;
import javafx.scene.control.ListCell;

/**
 * @author romain
 */
public class SoundVolumeListCell extends ListCell<SoundVolume> {

	private final ResourceBundle mResources;

	public SoundVolumeListCell(ResourceBundle mResources) {
		this.mResources = mResources;
	}

	@Override
	protected void updateItem(SoundVolume item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(mResources.getString(item.getName()));
		}
	}

}
