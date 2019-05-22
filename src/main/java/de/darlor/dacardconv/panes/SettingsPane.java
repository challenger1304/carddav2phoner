package de.darlor.dacardconv.panes;

import de.darlor.dacardconv.DaCardConv;
import de.darlor.dacardconv.Settings;
import de.darlor.dacardconv.tasks.VCardImporterTask;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class SettingsPane {

	private static GridPane settingsPane;
	private final Integer buttonSize = 64;

	public SettingsPane(DaCardConv app) {

		TextField tfImportPath = new TextField(Settings.getImportPath());
		tfImportPath.textProperty().addListener((obs, oldText, newPath) -> {
			Settings.setImportPath(newPath);
		});

		Button btImport = new Button("Import");
		btImport.setMaxWidth(buttonSize);
		btImport.setOnAction((ActionEvent event) -> {
			VCardImporterTask importerTask;
			importerTask = new VCardImporterTask(new File(tfImportPath.getText()), app.getVcardsPane().getDataTableList());
			Thread th = new Thread(importerTask);
			th.start();
		});

		TextField tfExportPath = new TextField(Settings.getExportPath());
		tfExportPath.textProperty().addListener((obs, oldText, newPath) -> {
			Settings.setExportPath(newPath);
		});

		Button btExport = new Button("Export");
		btExport.setMaxWidth(buttonSize);
		btExport.setOnAction((event) -> {
			//TODO generate txt
		});

		ColumnConstraints cc = new ColumnConstraints();
		cc.setFillWidth(true);
		cc.setHgrow(Priority.ALWAYS);

		settingsPane = new GridPane();
		settingsPane.setVgap(4);
		settingsPane.setHgap(4);
		settingsPane.setAlignment(Pos.BASELINE_RIGHT);
		settingsPane.getColumnConstraints().addAll(cc, new ColumnConstraints(buttonSize));
		settingsPane.addRow(0, tfImportPath, btImport);
		settingsPane.addRow(1, tfExportPath, btExport);
	}

	public GridPane getPane() {
		return settingsPane;
	}
}
