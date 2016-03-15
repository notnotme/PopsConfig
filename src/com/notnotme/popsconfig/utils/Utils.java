package com.notnotme.popsconfig.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author romain
 */
public final class Utils {

	public static File jarEntryToFile(ClassLoader loader, String path) throws IOException {
		File file = File.createTempFile(path, null);

		try (FileOutputStream fos = new FileOutputStream(file); InputStream is = loader.getResourceAsStream(path)) {
			int read;
			byte[] buffer = new byte[1024];
			while ((read = is.read(buffer)) > 0) {
				fos.write(buffer, 0, read);
			}
		}

		return file;
	}

}
