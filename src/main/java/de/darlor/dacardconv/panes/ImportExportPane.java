package de.darlor.dacardconv.panes;

import de.darlor.dacardconv.DaCardConv;
import de.darlor.dacardconv.Settings;
import de.darlor.dacardconv.tasks.VCardExporterTask;
import de.darlor.dacardconv.tasks.VCardImporterTask;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class ImportExportPane {

	private static GridPane settingsPane;
	private final Integer buttonSize = 64;

	public ImportExportPane(DaCardConv app) {

		TextField tfImportPath = new TextField(Settings.getImportPath());
		tfImportPath.textProperty().addListener((obs, oldText, newPath) -> {
			Settings.setImportPath(newPath);
		});
		Button btImportPath = new Button("Select");
		btImportPath.setPrefWidth(buttonSize);
		btImportPath.setOnAction((event) -> {
			FileChooser fc = new FileChooser();
			File file = new File(Settings.getExportPath());
			if (file.getParentFile().isDirectory()) { //check if directory exists
				fc.setInitialDirectory(file.getParentFile());
				fc.setInitialFileName(file.getName());
			}
			File f = fc.showOpenDialog(btImportPath.getScene().getWindow());
			if (f != null) {tfImportPath.setText(f.toString());}
		});
		Button btImportPathDefault = new Button("Default");
		btImportPathDefault.setPrefWidth(buttonSize);
		btImportPathDefault.setOnAction((event) -> {
			tfImportPath.setText(Settings.getImportPathDefault());
		});

		Button btImport = new Button("Import");
		btImport.setPrefWidth(buttonSize);
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
		Button btExportPath = new Button("Select");
		btExportPath.setPrefWidth(buttonSize);
		btExportPath.setOnAction((event) -> {
			FileChooser fc = new FileChooser();
			File file = new File(Settings.getExportPath());
			if (file.getParentFile().isDirectory()) { //check if directory exists
				fc.setInitialDirectory(file.getParentFile());
				fc.setInitialFileName(file.getName());
			}
			File f = fc.showSaveDialog(btExportPath.getScene().getWindow());
			if (f != null) {tfExportPath.setText(f.toString());}
		});
		Button btExportPathDefault = new Button("Default");
		btExportPathDefault.setPrefWidth(buttonSize);
		btExportPathDefault.setOnAction((event) -> {
			tfExportPath.setText(Settings.getExportPathDefault());
		});

		Button btExport = new Button("Export");
		btExport.setPrefWidth(buttonSize);
		btExport.setOnAction((event) -> {
			VCardExporterTask exporterTask;
			exporterTask = new VCardExporterTask(app.getVcardsPane().getDataTableList());
			Thread th = new Thread(exporterTask);
			th.start();
		});

		ColumnConstraints cc = new ColumnConstraints();
		cc.setFillWidth(true);
		cc.setHgrow(Priority.ALWAYS);

		settingsPane = new GridPane();
		settingsPane.setVgap(4);
		settingsPane.setHgap(4);
		settingsPane.setAlignment(Pos.BASELINE_RIGHT);
		settingsPane.getColumnConstraints().addAll(cc, new ColumnConstraints(buttonSize), new ColumnConstraints(buttonSize));
		//TODO add buttons to retrieve default paths
		settingsPane.addRow(0, tfImportPath, btImportPath, btImportPathDefault, btImport);
		settingsPane.addRow(1, tfExportPath, btExportPath, btExportPathDefault, btExport);
	}

	public GridPane getPane() {
		return settingsPane;
	}
}
