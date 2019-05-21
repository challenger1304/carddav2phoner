package de.darlor.dacardconv;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class DaCardConv extends Application {

	@Override
	public void start(Stage primaryStage) {

		BorderPane basicPane = new BorderPane();

		Scene scene = new Scene(basicPane, 300, 250);

		primaryStage.setTitle("CardDAV 2 Phoner");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}