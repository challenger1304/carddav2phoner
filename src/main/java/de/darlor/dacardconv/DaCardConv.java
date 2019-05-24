package de.darlor.dacardconv;

import de.darlor.dacardconv.panes.ConnectionPane;
import de.darlor.dacardconv.panes.ImportExportPane;
import de.darlor.dacardconv.panes.VCardsPane;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class DaCardConv extends Application {

	public static final Logger LOGGER = Logger.getAnonymousLogger();

	private VCardsPane vcardsPane;
	private GridPane settingsPane;
	private ConnectionPane connPane;

	@Override
	public void start(Stage primaryStage) {
		settingsPane = new ImportExportPane(this).getPane();
		vcardsPane = new VCardsPane();
		connPane = new ConnectionPane();

		BorderPane basicPane = new BorderPane();
		basicPane.setPadding(new Insets(8));
		BorderPane.setMargin(settingsPane, new Insets(4, 0, 0, 0));
		BorderPane.setMargin(connPane.getPane(), new Insets(0, 4, 0, 0));
		basicPane.setBottom(settingsPane);
		basicPane.setLeft(connPane.getPane());
		basicPane.setCenter(vcardsPane.getPane());

		Scene scene = new Scene(basicPane, 700, 500);

		primaryStage.setTitle(Settings.getAppName());
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("de/darlor/dacardconv/assets/logo.png"));
		primaryStage.show();
	}

	public VCardsPane getVcardsPane() {
		return vcardsPane;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(DaCardConv.class, args);
	}

}
