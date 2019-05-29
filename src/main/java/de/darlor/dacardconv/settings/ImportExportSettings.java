package de.darlor.dacardconv.settings;

import de.darlor.dacardconv.exceptions.MalformedPatternException;
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
		String fallback = Paths.get(System.getProperty("user.home"), "Downloads", "contacts.txt").toString();
		return getImExSetting("exportPathDefault", fallback);
	}

	public static void setExportPathDefault(String value) {
		setImExSetting("exportPathDefault", value);
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
	 * @throws MalformedPatternException thrown when the cahrsequence
	 * "<b>%s</b>" isn't found exactly three times.
	 */
	public static void setExportPattern(String pattern) throws MalformedPatternException {
		if (6 == (pattern.length() - pattern.replaceAll("%s", "").length())) { //true if %s is three times in the pattern
			setImExSetting("pattern", pattern);
		} else {
			throw new MalformedPatternException("must contain \"%s\" exactly three times.");
		}
	}

	public static String getImportPathDefault() {
		return Paths.get(System.getProperty("user.home"), "Downloads", "contacts.vcf").toString();
	}

	/**
	 * Saved path to the vcf file, to import all vcards.
	 *
	 * @return String that represents the file location.
	 */
	public static String getImportPath() {
		return getImExSetting("impPath", getImportPathDefault());
	}

	public static void setImportPath(String dlPath) {
		setImExSetting("impPath", dlPath);
	}

	public static Boolean getAppendAreaCode() {
		return Boolean.valueOf(getImExSetting("appendAreaCode", "false"));
	}

	public static void setAppendAreaCode(Boolean value) {
		setImExSetting("appendAreaCode", value.toString());
	}

	public static Boolean getAppendCountryCode() {
		return Boolean.valueOf(getImExSetting("appendCountryCode", "false"));
	}

	public static void setAppendCountryCode(Boolean value) {
		setImExSetting("appendCountryCode", value.toString());
	}

	public static String getCountryPrefix() {
		return getImExSetting("countryPrefix", "+49");
	}

	public static void setCountryPrefix(String prefix) {
		setImExSetting("countryPrefix", prefix);
	}

}
