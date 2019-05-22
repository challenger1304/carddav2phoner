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

/**
 *
 * @author Vincent Neubauer (v.neubauer@asysgmbh.de)
 */
public class VCardExporterTask extends Task<Void> {

	private ObservableList<PhonerDataSet> dataSets = null;

	public VCardExporterTask(ObservableList<PhonerDataSet> vcards) {
		this.dataSets = vcards;
	}

	@Override
	protected Void call() throws Exception {
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
		Dialog dial = new Dialog();
		dial.setContentText("Export was successful");
		dial.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dial.show();
	}

	@Override
	protected void failed() {
		this.getException().printStackTrace();
		Dialog dial = new Dialog();
		dial.setContentText("Export failed!");
		dial.setTitle("Error");
		dial.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dial.show();
	}

}
