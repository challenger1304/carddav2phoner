package de.darlor.dacardconv.exceptions;

/**
 *
 * @author Vincent Neubauer (v.neubauer@asysgmbh.de)
 */
public class MalformedPatternException extends Exception {

	public MalformedPatternException() {
	}

	public MalformedPatternException(String message) {
		super(message);
	}

	public MalformedPatternException(String message, Throwable cause) {
		super(message, cause);
	}

	public MalformedPatternException(Throwable cause) {
		super(cause);
	}


}
