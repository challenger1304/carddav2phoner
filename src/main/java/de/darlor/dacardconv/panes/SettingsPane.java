package de.darlor.dacardconv.panes;

import de.darlor.dacardconv.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class SettingsPane {

	private static HBox settingsPane;

	public SettingsPane() {

		TextField tfPath = new TextField(Settings.getExportPath());
		tfPath.textProperty().addListener((obs, oldText, newPath) -> {
			Settings.setExportPath(newPath);
		});

		Button btExport = new Button("Export");
		btExport.setOnAction((event) -> {
			//TODO generate txt
		});

		settingsPane = new HBox();
		settingsPane.setPadding(new Insets(8));
		settingsPane.setSpacing(4);
		settingsPane.setAlignment(Pos.BASELINE_RIGHT);
		settingsPane.getChildren().addAll(tfPath, btExport);
	}

	public HBox getPane() {
		return settingsPane;
	}
}
