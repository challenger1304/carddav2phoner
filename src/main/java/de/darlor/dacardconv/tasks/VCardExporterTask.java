package de.darlor.dacardconv.tasks;

import de.darlor.dacardconv.DaCardConv;
import de.darlor.dacardconv.settings.ImportExportSettings;
import de.darlor.dacardconv.utils.PhonerDataSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Dialog;

/**
 *
 * @author Vincent Neubauer (v.neubauer@asysgmbh.de)
 */
public class VCardExporterTask extends Task<Void> {

	private ObservableList<PhonerDataSet> dataSets = null;
	private final Dialog DIALOG;

	public VCardExporterTask(ObservableList<PhonerDataSet> vcards) {
		this.DIALOG = DaCardConv.getDialog("Exporter");
		this.dataSets = vcards;
	}

	@Override
	protected Void call() throws Exception {
		File file = new File(ImportExportSettings.getExportPath());
		file.getParentFile().mkdirs();

		try (PrintWriter writer = new PrintWriter(file, StandardCharsets.ISO_8859_1)) { //Charset needed by phoner
			dataSets.forEach((t) -> {
				writer.println(String.format(ImportExportSettings.getExportPattern(), t.getName(), t.getTelNo(), t.getDesc()));
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
