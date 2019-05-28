package de.darlor.dacardconv.utils;

import de.darlor.dacardconv.Settings;
import de.darlor.dacardconv.exceptions.WebDAVException;
import de.darlor.dacardconv.exceptions.WebDAVMalformedURLException;
import de.darlor.dacardconv.exceptions.WebDAVUnauthorizedException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A Class to do some basic tasks with the CardDAV-Server.
 *
 * @author Vincent 'challenger1304' Neubauer (v.neubauer@darlor.de)
 */
public class CardDAVServer {

	private final String SERV;
	private final String USER;
	private final String PASS;
	private String cardDAVBaseURL;

	/**
	 * Connection to CardDAV-Server
	 *
	 * @param server URL of the Server
	 * @param username username for login
	 * @param password password for login
	 * @throws WebDAVException throws exception when service isn't available or
	 * credentials are wrong. correctly set
	 */
	public CardDAVServer(String server, String username, String password) throws WebDAVException {
		this.SERV = server;
		this.USER = username;
		this.PASS = password;
		this.connect();
	}

	/**
	 * Initial connection to the Server. This will retrieve all necessary data
	 * like path for the address books and checks for authorization.
	 *
	 * @throws WebDAVException When the connection to the CardDAV Server fails. This
	 * can be due to wrong login credentials or an unavailable service.
	 */
	protected void connect() throws WebDAVException {
		try {
			this.checkUrl();
			this.checkAuth();
			this.cardDAVBaseURL = this.getWellKnownCarddavPath();
		} catch (WebDAVException e) {
			throw new WebDAVException("Initial connection to CardDAV-Server failed.", e);
		}
	}

	/**
	 * Checks if there is a reachable CardDAV-Server behind the given URL.
	 *
	 * @throws WebDAVMalformedURLException when Server isn't available.
	 */
	protected void checkUrl() throws WebDAVMalformedURLException {
		//TODO implement this
		throw new WebDAVMalformedURLException("Host isn't reachable");
	}

	/**
	 * Checks if the provided username and password-combo is authorized to
	 * request resources from this server.
	 *
	 * @throws WebDAVUnauthorizedException when login credentials aren't
	 * correct.
	 */
	protected void checkAuth() throws WebDAVUnauthorizedException {
		//TODO implement this function
		throw new WebDAVUnauthorizedException("This is currently not supported.");
	}

	private String getWellKnownCarddavPath() {
		//TODO implement this function
		throw new UnsupportedOperationException("This is currently not supported.");
	}

	/**
	 * This will retrieve a list with all address books, that this user has
	 * access to.
	 *
	 * @return List of strings with the names of all address books.
	 */
	public ObservableList<String> getAddrBooks() {
		ObservableList<String> addrBooks = FXCollections.observableArrayList();
		//TODO fill list with address books from remote server
		return addrBooks;
	}

	/**
	 * @param addrBook name of the address book
	 * @return a URL for downloading the address book or null on
	 * MalformedURLException
	 */
	private URL getAddressBookUrl(String addrBook) throws MalformedURLException {
		return new URL(SERV + "/remote.php/dav/addressbooks/users/" + USER + "/" + addrBook + "/?export");
	}

	/**
	 * @param addrBook name of the address book
	 * @return the downloaded file
	 * @throws IOException when failed
	 */
	public File downloadAddressBook(String addrBook) throws IOException {
		File dlFile = new File(Settings.getSettingsFolder().toFile(), "contacts.vcf");
		URL dlUrl = this.getAddressBookUrl(addrBook);
		ReadableByteChannel rbc = Channels.newChannel(dlUrl.openStream());
		FileOutputStream fos = new FileOutputStream(dlFile);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		return dlFile;
	}

}
