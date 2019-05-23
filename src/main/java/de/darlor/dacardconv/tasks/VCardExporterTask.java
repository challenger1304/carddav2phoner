package de.darlor.dacardconv.tasks;

import de.darlor.dacardconv.Settings;
import de.darlor.dacardconv.utils.PhonerDataSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Vincent Neubauer (v.neubauer@asysgmbh.de)
 */
public class VCardExporterTask extends Task<Void> {

	private ObservableList<PhonerDataSet> dataSets = null;
	private final Dialog DIALOG;

	public VCardExporterTask(ObservableList<PhonerDataSet> vcards) {
		this.DIALOG = new Dialog();
		this.dataSets = vcards;
	}

	@Override
	protected Void call() throws Exception {
		DIALOG.getDialogPane().getButtonTypes().add(ButtonType.OK);
		DIALOG.setTitle(Settings.getAppName() + " - Exporter");
		Stage stage = (Stage) DIALOG.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("de/darlor/dacardconv/assets/logo.png"));

		File file = new File(Settings.getExportPath());
		file.getParentFile().mkdirs();

		try (PrintWriter writer = new PrintWriter(file, StandardCharsets.ISO_8859_1)) { //Charset needed by phoner
			dataSets.forEach((t) -> {
				writer.println(String.format(Settings.getExportPattern(), t.getName(), t.getTelNo(), t.getDesc()));
			});
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}

	@Override
	protected void succeeded() {
		DIALOG.setContentText("Export was successful");
		DIALOG.show();
	}

	@Override
	protected void failed() {
		this.getException().printStackTrace();
		DIALOG.setContentText("Export failed!");
		DIALOG.show();
	}

}
