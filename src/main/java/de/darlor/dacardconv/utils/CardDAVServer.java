package de.darlor.dacardconv.utils;

import de.darlor.dacardconv.settings.BaseSettings;
import de.darlor.dacardconv.exceptions.WebDAVException;
import de.darlor.dacardconv.exceptions.WebDAVMalformedURLException;
import de.darlor.dacardconv.exceptions.WebDAVResponseBlockedException;
import de.darlor.dacardconv.exceptions.WebDAVUnauthorizedException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Base64;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;

/**
 * A Class to do some basic tasks with the CardDAV-Server.
 *
 * @author Vincent 'challenger1304' Neubauer (v.neubauer@darlor.de)
 */
public class CardDAVServer {

	private final String SERV;
	private final String USER;
	private final String PASS;
	private final String AUTH;
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
		this.AUTH = Base64.getEncoder().encodeToString((USER + ":" + PASS).getBytes()); //base64 of username:password;
		this.connect();
	}

	/**
	 * Initial connection to the Server. This will retrieve all necessary data
	 * like path for the address books and checks for authorization.
	 *
	 * @throws WebDAVException When the connection to the CardDAV Server fails.
	 * This can be due to wrong login credentials or an unavailable service.
	 */
	protected void connect() throws WebDAVException {

		try {
			this.checkUrl();
			this.checkAuth();
			this.setWellKnownCardDAVPath();
		} catch (WebDAVException e) {
			throw new WebDAVException("Initial connection to CardDAV-Server failed.", e);
		}
	}

	/**
	 * Checks if there is a reachable CardDAV-Server behind the given URL.
	 *
	 * @throws WebDAVMalformedURLException when Server isn't available.
	 * @throws WebDAVResponseBlockedException when Server responds with
	 * something other than 200.
	 */
	protected void checkUrl() throws WebDAVException {
		try {
			URL baseUrl = new URL(SERV);
			HttpsURLConnection con = (HttpsURLConnection) baseUrl.openConnection();
			con.setRequestMethod("GET");
			Integer status = con.getResponseCode();
			if (200 != status) {
				throw new WebDAVResponseBlockedException("Connection refused", status);
			}
		} catch (SSLException e) {
			throw new WebDAVMalformedURLException("SSL-Certificate of the server isn't trusted", e);
		} catch (IOException e) {
			throw new WebDAVMalformedURLException("Server isn't reachable", e);
		}
	}

	/**
	 * Checks if the provided username and password-combo is authorized to
	 * request resources from this server.
	 *
	 * @throws WebDAVUnauthorizedException when login credentials aren't
	 * correct.
	 */
	protected void checkAuth() throws WebDAVException {
		try {
			URL baseUrl = new URL(String.format("%s/.well-known/carddav", SERV.replace("/$", "")));
			HttpsURLConnection con = (HttpsURLConnection) baseUrl.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Basic " + AUTH);
			Integer status = con.getResponseCode();
			switch (status) {
				case 401:
					throw new WebDAVUnauthorizedException("Wrong credentials");
				case 200:
					//ignore on success
					break;
				default:
					throw new WebDAVResponseBlockedException("Connection refused", status);
			}
		} catch (IOException e) {
			throw new WebDAVMalformedURLException("Server isn't reachable", e);
		}
	}

	/**
	 * Remembers the redirected path for address books.
	 *
	 * @throws WebDAVMalformedURLException when it can't specify the new path
	 */
	private void setWellKnownCardDAVPath() throws WebDAVMalformedURLException {
		try {
			URL baseUrl = new URL(String.format("%s/.well-known/carddav", SERV.replace("/$", "")));
			HttpsURLConnection con = (HttpsURLConnection) baseUrl.openConnection();
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Basic " + AUTH);
			String location = con.getHeaderField("Location");
			if (location == null) {
				throw new WebDAVMalformedURLException("Couldn't retrieve the base URL for address books");
			} else {
				this.cardDAVBaseURL = location;
			}
		} catch (IOException e) {
			throw new WebDAVMalformedURLException("Server isn't reachable", e);
		}
	}

	public String getWellKnownCardDAVPath() {
		return this.cardDAVBaseURL;
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
		return new URL(String.format("%s/addressbooks/users/%s/%s/?export", cardDAVBaseURL, USER, addrBook));
	}

	/**
	 * @param addrBook name of the address book
	 * @return the downloaded file
	 * @throws IOException when failed
	 */
	public File downloadAddressBook(String addrBook) throws IOException {
		File dlFile = new File(BaseSettings.getSettingsFolder().toFile(), "contacts.vcf");
		URL dlUrl = this.getAddressBookUrl(addrBook);
		HttpsURLConnection con = (HttpsURLConnection) dlUrl.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Basic " + AUTH);
		ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
		FileOutputStream fos = new FileOutputStream(dlFile);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		return dlFile;
	}

}
