package de.darlor.dacardconv.exceptions;

/**
 *
 * @author Vincent 'challenger1304' Neubauer (v.neubauer@darlor.de)
 */
public class WebDAVResponseBlockedException extends WebDAVException {

	public WebDAVResponseBlockedException(String message, Integer httpResponseCode) {
		super(String.format("%s; HTTP-CODE: %d", message, httpResponseCode));
	}

	public WebDAVResponseBlockedException(String message, Integer httpResponseCode, Throwable cause) {
		super(String.format("%s; HTTP-CODE: %d", message, httpResponseCode), cause);
	}
	
}
