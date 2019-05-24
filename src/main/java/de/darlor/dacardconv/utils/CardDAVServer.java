package de.darlor.dacardconv.utils;

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
	 * @param server URL of the Server
	 * @param username username for login
	 * @param password password for login
	 */
	public CardDAVServer(String server, String username, String password) {
		this.SERV = server;
		this.USER = username;
		this.PASS = password;
	}
	
	private void connect() {
		//TODO implement this function
	}
	
	public ObservableList<String> getAddrBooks() {
		ObservableList<String> addrBooks = FXCollections.observableArrayList();
		//TODO fill list with address books from remote server
		return addrBooks;
	}
	
}
