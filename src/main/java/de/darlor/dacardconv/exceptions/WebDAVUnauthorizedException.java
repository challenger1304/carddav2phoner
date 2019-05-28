package de.darlor.dacardconv.exceptions;

/**
 *
 * @author Vincent 'challenger1304' Neubauer (v.neubauer@darlor.de)
 */
public class WebDAVUnauthorizedException extends WebDAVException {

	public WebDAVUnauthorizedException(String message) {
		super(message);
	}

	public WebDAVUnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
