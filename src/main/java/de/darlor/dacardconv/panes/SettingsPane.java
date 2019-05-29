package de.darlor.dacardconv.panes;

import de.darlor.dacardconv.DaCardConv;
import de.darlor.dacardconv.exceptions.MalformedPatternException;
import de.darlor.dacardconv.settings.ImportExportSettings;
import java.nio.file.Paths;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author Vincent Neubauer (v.neubauer@asysgmbh.de)
 */
public class SettingsPane {

	private final GridPane PANE = new GridPane();

	public GridPane getPane()
{
		Label lbExpPattern = new Label("Export Pattern");
		ComboBox cobExpPattern = new ComboBox();
		cobExpPattern.getItems().addAll("%s,%s,%s", "%s;%s;%s", "%s;%s;;%s");
		cobExpPattern.setEditable(true);
		cobExpPattern.setMaxWidth(Double.MAX_VALUE);
		lbExpPattern.setLabelFor(cobExpPattern);

		Label lbExpPath = new Label("Default Export Path");
		ComboBox cobExpPath = new ComboBox();
		cobExpPath.getItems().addAll(
				Paths.get(System.getProperty("user.home"), "Downloads", "contacts.txt").toString(),
				Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "PhonerLite", "phonebook.csv").toString()
		);
		cobExpPath.setEditable(true);
		cobExpPath.setMaxWidth(Double.MAX_VALUE);
		lbExpPath.setLabelFor(cobExpPath);

		Label lbCountryPrefix = new Label("Country Prefix");
		TextField tfCountryPrefix = new TextField(ImportExportSettings.getCountryPrefix());
		tfCountryPrefix.setMaxWidth(Double.MAX_VALUE);
		lbCountryPrefix.setLabelFor(tfCountryPrefix);

		CheckBox chbAppendAreaCode = new CheckBox("Append Area Code (0)");
		chbAppendAreaCode.setSelected(ImportExportSettings.getAppendAreaCode());
		CheckBox chbAppendCountryCode = new CheckBox("Append Country Code (00)");
		chbAppendCountryCode.setSelected(ImportExportSettings.getAppendCountryCode());

		Button btApply = new Button("Apply");
		Button btCancel = new Button("Cancel");
		HBox controls = new HBox(btApply, btCancel);
		controls.setAlignment(Pos.BASELINE_RIGHT);
		controls.setSpacing(4);

		//create layout
		PANE.addRow(0, lbExpPattern, cobExpPattern);
		PANE.addRow(1, lbExpPath, cobExpPath);
		PANE.addRow(2, lbCountryPrefix, tfCountryPrefix);
		PANE.add(chbAppendAreaCode, 1, 3);
		PANE.add(chbAppendCountryCode, 1, 4);
		PANE.add(controls, 1, 5);

		PANE.setHgap(4);
		PANE.setVgap(4);
		PANE.setPadding(new Insets(8));
		GridPane.setHgrow(cobExpPattern, Priority.ALWAYS);
		GridPane.setHgrow(cobExpPath, Priority.ALWAYS);
		PANE.getColumnConstraints().addAll(new ColumnConstraints(96, 128, 128),
				new ColumnConstraints(128, 256, 1024, Priority.ALWAYS, HPos.LEFT, true));

		//populate defaults
		cobExpPattern.setValue(ImportExportSettings.getExportPattern());
		cobExpPath.setValue(ImportExportSettings.getExportPathDefault());

		//register eventhandler
		btApply.setOnAction((event) -> {
			try {
				ImportExportSettings.setExportPattern(cobExpPattern.getValue().toString());
				ImportExportSettings.setExportPathDefault(cobExpPath.getValue().toString());
				ImportExportSettings.setCountryPrefix(tfCountryPrefix.getText());
				ImportExportSettings.setAppendAreaCode(chbAppendAreaCode.isSelected());
				ImportExportSettings.setAppendCountryCode(chbAppendCountryCode.isSelected());
				Node source = (Node) event.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
			} catch (MalformedPatternException ex) {
				Dialog dial = DaCardConv.getDialog("Settings");
				dial.setContentText(ex.getMessage());
				dial.show();
			}
		});
		btCancel.setOnAction((event) -> {
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
		});

		return PANE;
	}

}
