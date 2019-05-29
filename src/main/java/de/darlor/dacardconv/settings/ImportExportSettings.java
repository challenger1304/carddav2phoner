package de.darlor.dacardconv.settings;

import java.nio.file.Paths;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class ImportExportSettings extends BaseSettings {

	protected static String getImExSetting(String key, String fallback) {
		return getSetting("IMPORT-EXPORT", key, fallback);
	}

	protected static void setImExSetting(String key, String value) {
		setSetting("IMPORT-EXPORT", key, value);
	}

	public static String getExportPathDefault() {
		return Paths.get(System.getProperty("user.home"), "Downloads", "contacts.txt").toString();
		//TODO return the following when for PhonerLite
//		return Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "PhonerLite", "phonebook.csv").toString();
	}

	public static String getExportPath() {
		return getImExSetting("exportPath", getExportPathDefault());
	}

	public static void setExportPath(String dlPath) {
		setImExSetting("exportPath", dlPath);
	}

	public static String getExportPattern() {
		return getImExSetting("pattern", "%s;%s;%s");
	}

	/**
	 * Sets the pattern used for exporting into a file. Must contain "<b>%s</b>"
	 * exactly three times.
	 *
	 * @param pattern the new pattern. common pattern are:
	 * <br><code>"%s;%s;%s"</code> for Phoner, or <br><code>"%s;%s;;%s"</code>
	 * for PhonerLite.
	 */
	public static void setExportPattern(String pattern) {
		//TODO check pattern. must contain exactly three times "%s"
		setImExSetting("pattern", pattern);
	}

	public static String getImportPathDefault() {
		return Paths.get(System.getProperty("user.home"), "Downloads", "contacts.vcf").toString();
	}

	/**
	 * Saved path to the vcf file, to import all vcards.
	 * @return String that represents the file location.
	 */
	public static String getImportPath() {
		return getImExSetting("impPath", getImportPathDefault());
	}

	public static void setImportPath(String dlPath) {
		setImExSetting("impPath", dlPath);
	}
}
