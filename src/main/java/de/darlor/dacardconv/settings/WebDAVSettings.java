package de.darlor.dacardconv.settings;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class WebDAVSettings extends BaseSettings {

	private static String webdavPassword;

	protected static String getWebDAVSetting(String key, String fallback) {
		return getSetting("WEBDAV", key, fallback);
	}

	protected static void setWebDAVSetting(String key, String value) {
		setSetting("WEBDAV", key, value);
	}

	public static String getURL() {
		return getWebDAVSetting("webdavUrl", "https://");
	}

	public static void setURL(String url) {
		setWebDAVSetting("webdavUrl", url);
	}

	public static String getUsername() {
		return getWebDAVSetting("webdavUser", "Anonymous");
	}

	public static void setUsername(String name) {
		setWebDAVSetting("webdavUser", name);
	}

	public static String getPassword() {
		if (webdavPassword == null) {
			return "";
		} else {
			return webdavPassword;
		}
	}

	public static void setPassword(String password) {
		webdavPassword = password; //Do NOT store in a file!
	}

	public static String getAddressBook() {
		return getWebDAVSetting("webdavAddr", "contacts");
	}

	public static void setAddressBook(String addrBook) {
		setWebDAVSetting("webdavAddr", addrBook);
	}

}
