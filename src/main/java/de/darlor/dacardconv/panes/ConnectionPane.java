package de.darlor.dacardconv.panes;

import de.darlor.dacardconv.Settings;
import de.darlor.dacardconv.utils.CardDAVServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class ConnectionPane {

	private static VBox connPane;
	private final ObservableList<String> remoteAddrBooks;
	private CardDAVServer server;

	public ConnectionPane() {
		remoteAddrBooks = FXCollections.observableArrayList();

		TextField tfWebdavUrl = new TextField(Settings.getWebdavAddress());
		tfWebdavUrl.textProperty().addListener((obs, oldText, newUrl) -> {
			Settings.setWebdavAddress(newUrl);
		});
		TextField tfWebdavUser = new TextField(Settings.getWebdavUsername());
		tfWebdavUser.textProperty().addListener((obs, oldText, newUsername) -> {
			Settings.setWebdavUsername(newUsername);
		});
		PasswordField tfWebdavPass = new PasswordField();
		tfWebdavPass.textProperty().addListener((obs, oldText, newPassword) -> {
			Settings.setWebdavPassword(newPassword);
		});
		tfWebdavPass.setText(Settings.getwebdavPassword());

		Button btConnect = new Button("Connect");

		ComboBox cbAddrBook = new ComboBox();
		cbAddrBook.setDisable(true);
		cbAddrBook.setItems(remoteAddrBooks);

		Button btImport = new Button("Import");
		btImport.setDisable(true);

		connPane = new VBox();

		//styles
		connPane.setSpacing(4);
		connPane.widthProperty().addListener((ov, oldWidth, newWidth) -> {
			btConnect.setMinWidth((double) newWidth);
			btImport.setMinWidth((double) newWidth);
			cbAddrBook.setMinWidth((double) newWidth);
		});

		//button-event-handler
		btConnect.setOnAction((t) -> {
			server = new CardDAVServer(tfWebdavUrl.getText(),
					tfWebdavUser.getText(), tfWebdavPass.getText());
			remoteAddrBooks.clear();
			remoteAddrBooks.addAll(server.getAddrBooks());
			cbAddrBook.setDisable(false); //TODO only on success
			btImport.setDisable(false); //TODO only on success
		});
		btImport.setOnAction((t) -> {
			//TODO import address book from CardDAV-Server
		});

		connPane.getChildren().addAll(tfWebdavUrl, tfWebdavUser, tfWebdavPass,
				btConnect, new Separator(), cbAddrBook, btImport);
	}

	public VBox getPane() {
		return connPane;
	}

}
