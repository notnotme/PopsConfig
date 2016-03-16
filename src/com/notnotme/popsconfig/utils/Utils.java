package com.notnotme.popsconfig.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author romain
 */
public final class Utils {

	private final static String TAG = Utils.class.getSimpleName();

	/**
	 * Return a file that is inside a .jar archive
	 * @param loader A ClassLoader instance
	 * @param jarEntry  The jar entry to deflate into a temporary file
	 * @return A temporary file representating the one inside the jar
	 * @throws IOException If an error occur
	 */
	public static File jarEntryToFile(ClassLoader loader, JarEntry jarEntry) throws IOException {
		File file = File.createTempFile(jarEntry.getName(), null);

		try (FileOutputStream fos = new FileOutputStream(file); InputStream is = loader.getResourceAsStream(jarEntry.getName())) {
			int read;
			byte[] buffer = new byte[1024];
			while ((read = is.read(buffer)) > 0) {
				fos.write(buffer, 0, read);
			}
		}

		return file;
	}

	/**
	 * List all Files inside a .jar archive
	 * @param jarPath The path to reach the archive
	 * @return An ArrayList of entries or null if an error occur
	 */
	public static ArrayList<JarEntry> getJarEntry(String jarPath) {
		File jarRawFile = new File(jarPath);
		if(! jarRawFile.exists() || !jarRawFile.isFile()) {
			return null;
		}

		ArrayList<JarEntry> entryList = new ArrayList<>();

		try (JarFile jarFile = new JarFile(jarRawFile)) {
			Enumeration<JarEntry> entries = jarFile.entries();
			while(entries.hasMoreElements()) {
				entryList.add(entries.nextElement());
			}
		} catch (Exception ex) {
			Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
		}

		return entryList;
	}

}
