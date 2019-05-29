package de.darlor.dacardconv.settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.ini4j.Ini;

/**
 *
 * @author Vincent 'challenger1304' Neubauer <v.neubauer@darlor.de>
 */
public class BaseSettings {

	private static final Path SETTINGSFOLDER = Paths.get(System.getProperty("user.home"), ".config", "darlor", "daCardConv");
	private static final File SETTINGSFILE = Paths.get(SETTINGSFOLDER.toString(), "daCardConv.conf").toFile();
	private static String webdavPassword;

	protected static String getSetting(String section, String key, String returnOnFailure) {
		try {
			Ini settings;
			if (!SETTINGSFILE.exists()) {
				SETTINGSFOLDER.toFile().mkdirs();
				SETTINGSFILE.createNewFile();
			}
			settings = new Ini(SETTINGSFILE);
			String value = settings.get(section, key);
			if (value == null) {
				setSetting(section, key, returnOnFailure);
				return returnOnFailure;
			} else {
				return value;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return returnOnFailure;
	}

	protected static void setSetting(String section, String key, String value) {
		try {
			Ini settings;
			if (!SETTINGSFILE.exists()) {
				SETTINGSFILE.createNewFile();
			}
			settings = new Ini(SETTINGSFILE);
			settings.put(section, key, value);
			settings.store();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static String getAppName() {
		return "CardDAV 2 Phoner";
	}

	public static Path getSettingsFolder() {
		return SETTINGSFOLDER;
	}

	public static String getAnonymousName() {
		return getSetting("APPLICATION", "anonymousName", "Unknown");
	}

	public static void setAnonymousName(String name) {
		setSetting("APPLICATION", "anonymousName", name);
	}


}
