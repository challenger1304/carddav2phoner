package de.darlor.dacardconv.panes;

import de.darlor.dacardconv.Settings;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class ConnectionPane {

	private static VBox connPane;

	public ConnectionPane() {
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
		//TODO load from remote server

		connPane = new VBox();
		connPane.getChildren().addAll(tfWebdavUrl, tfWebdavUser, tfWebdavPass,
			btConnect, new Label("Select Address-Book"), cbAddrBook);
	}

	public VBox getPane() {
		return connPane;
	}

}
