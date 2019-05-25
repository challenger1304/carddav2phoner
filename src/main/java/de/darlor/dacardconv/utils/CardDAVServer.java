package de.darlor.dacardconv.utils;

import de.darlor.dacardconv.Settings;
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
 *
 * @author Vincent 'challenger1304' Neubauer (v.neubauer@darlor.de)
 */
public class CardDAVServer {

	private final String SERV;
	private final String USER;
	private final String PASS;

	/**
	 * Connection to CardDAV-Server
	 *
	 * @param server URL of the Server
	 * @param username username for login
	 * @param password password for login
	 * @throws MalformedURLException throws exception when host-url isn't correctly set
	 */
	public CardDAVServer(String server, String username, String password) throws MalformedURLException {
		this.SERV = server;
		this.USER = username;
		this.PASS = password;
		URL testurl = new URL(SERV); //to check for malformed urls
		//TODO need to fix: empty url with only protocoll are accepted
		//TODO check username and password
	}

	private void connect() {

		//TODO implement this function
	}
	
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
