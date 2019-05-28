package de.darlor.dacardconv.exceptions;

/**
 *
 * @author Vincent 'challenger1304' Neubauer (v.neubauer@darlor.de)
 */
public class WebDAVMalformedURLException extends WebDAVException {

	public WebDAVMalformedURLException(String message) {
		super(message);
	}

	public WebDAVMalformedURLException(String message, Throwable cause) {
		super(message, cause);
	}
}
