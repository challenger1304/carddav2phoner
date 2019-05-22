package de.darlor.dacardconv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.ini4j.Ini;

/**
 *
 * @author Vincent 'challenger1304' Neubauer <v.neubauer@darlor.de>
 */
public class Settings {

	private static final Path SETTINGSFOLDER = Paths.get(System.getProperty("user.home"), ".config", "darlor");
	private static final File SETTINGSFILE = Paths.get(SETTINGSFOLDER.toString(), "daCardConv.conf").toFile();
	private static String webdavPassword;

	private static String getSetting(String section, String key, String returnOnFailure) {
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

	private static void setSetting(String section, String key, String value) {
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

	public static String getExportPath() {
		String fallback = Paths.get(System.getProperty("user.home"), "Downloads", "contacts.txt").toString();
		return getSetting("SETTINGS", "expPath", fallback);
	}

	public static void setExportPath(String dlPath) {
		Settings.setSetting("SETTINGS", "expPath", dlPath);
	}

	public static String getImportPath() {
		String fallback = Paths.get(System.getProperty("user.home"), "Downloads", "contacts.vcf").toString();
		return getSetting("SETTINGS", "impPath", fallback);
	}

	public static void setImportPath(String dlPath) {
		Settings.setSetting("SETTINGS", "impPath", dlPath);
	}

	public static String getAnonymousName() {
		return getSetting("SETTINGS", "anonymousName", "Unknown");
	}

	public static void setAnonymousName(String name) {
		Settings.setSetting("SETTINGS", "anonymousName", name);
	}

	public static String getWebdavAddress() {
		String fallback = "https://";
		return getSetting("SETTINGS", "webdavUrl", fallback);
	}

	public static void setWebdavAddress(String url) {
		Settings.setSetting("SETTINGS", "webdavUrl", url);
	}

	public static String getWebdavUsername() {
		String fallback = "Anonymous";
		return getSetting("SETTINGS", "webdavUser", fallback);
	}

	public static void setWebdavUsername(String name) {
		Settings.setSetting("SETTINGS", "webdavUser", name);
	}

	public static String getwebdavPassword() {
		if (webdavPassword == null) {
			return "";
		} else {
			return webdavPassword;
		}
	}

	public static void setWebdavPassword(String password) {
		webdavPassword = password; //Do NOT store in a file!
	}
}
