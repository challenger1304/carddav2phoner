package de.darlor.dacardconv;

import de.darlor.dacardconv.settings.BaseSettings;
import de.darlor.dacardconv.panes.ConnectionPane;
import de.darlor.dacardconv.panes.ImportExportPane;
import de.darlor.dacardconv.panes.SettingsPane;
import de.darlor.dacardconv.panes.VCardsPane;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class DaCardConv extends Application {

	public static final Logger LOGGER = Logger.getAnonymousLogger();

	private VCardsPane vcardsPane;
	private GridPane imExPane;
	private ConnectionPane connPane;
	private VBox asidePane;

	@Override
	public void start(Stage primaryStage) {
		imExPane = new ImportExportPane(this).getPane();
		vcardsPane = new VCardsPane(this);
		connPane = new ConnectionPane(this);
		asidePane = new VBox();
		asidePane.setSpacing(4);
		Button btSettings = new Button("Settings");
		btSettings.setOnAction((event) -> {
			GridPane settingsPane = new SettingsPane().getPane();
			Scene settingsScene = new Scene(settingsPane, settingsPane.getPrefWidth(), settingsPane.getPrefHeight());
			Stage settingsStage = new Stage();
			settingsStage.setTitle(BaseSettings.getAppName() + " | Settings");
			settingsStage.setScene(settingsScene);
			settingsStage.getIcons().add(new Image("de/darlor/dacardconv/assets/logo.png"));
			settingsStage.initOwner(primaryStage);
			settingsStage.initModality(Modality.APPLICATION_MODAL);
			settingsStage.showAndWait();
		});
		Region spacer01 = new Region();
		spacer01.setPrefHeight(40);
		VBox.setVgrow(spacer01, Priority.ALWAYS);
		asidePane.getChildren().addAll(connPane.getPane(), spacer01, btSettings); //TODO add btSettings
		asidePane.widthProperty().addListener((ov, oldWidth, newWidth) -> {
			btSettings.setMinWidth((double) newWidth);
		});

		BorderPane basicPane = new BorderPane();
		basicPane.setPadding(new Insets(8));
		BorderPane.setMargin(imExPane, new Insets(4, 0, 0, 0));
		BorderPane.setMargin(asidePane, new Insets(0, 4, 0, 0));
		basicPane.setBottom(imExPane);
		basicPane.setLeft(asidePane);
		basicPane.setCenter(vcardsPane.getPane());

		Scene scene = new Scene(basicPane, 700, 500);

		primaryStage.setTitle(BaseSettings.getAppName());
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("de/darlor/dacardconv/assets/logo.png"));
		primaryStage.show();
	}

	public VCardsPane getVcardsPane() {
		return vcardsPane;
	}

	/**
	 * Creates an Dialog object with predefined settings. This dialog is
	 * resizable.
	 *
	 * @param dialogName name of the dialog. will be of this scheme:
	 * <pre>applicationName | dialogName</pre>
	 *
	 * @return a new Dialog object
	 */
	public static Dialog getDialog(String dialogName) {
		return getDialog(dialogName, true);
	}

	/**
	 * Creates an Dialog object with predefined settings.
	 *
	 * @param dialogName name of the dialog. will be of this scheme:
	 * <pre>applicationName | dialogName</pre>
	 *
	 * @param resizable can this dialog shrink and grow in its size or not?
	 * @return a new Dialog object
	 */
	public static Dialog getDialog(String dialogName, boolean resizable) {
		Dialog dialog = new Dialog();
		dialog.setResizable(resizable);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.setTitle(BaseSettings.getAppName() + " | " + dialogName);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("de/darlor/dacardconv/assets/logo.png"));
		return dialog;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(DaCardConv.class, args);
	}

}
