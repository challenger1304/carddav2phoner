package de.darlor.dacardconv.exceptions;

/**
 *
 * @author Vincent 'challenger1304' Neubauer (v.neubauer@darlor.de)
 */
public class WebDAVException extends Exception {

	public WebDAVException(String message) {
		super(message);
	}

	public WebDAVException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
