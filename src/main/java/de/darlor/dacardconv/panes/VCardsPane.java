package de.darlor.dacardconv.panes;

import de.darlor.dacardconv.utils.PhonerDataSet;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class VCardsPane {

	private static TableView<PhonerDataSet> dataTable;

	public VCardsPane() {
		dataTable = new TableView<>();
		TableColumn<PhonerDataSet, String> nameCol = new TableColumn<>("Display Name");
		nameCol.setCellValueFactory(new PropertyValueFactory("name"));
		TableColumn<PhonerDataSet, String> telNoCol = new TableColumn<>("Telephone Number");
		telNoCol.setCellValueFactory(new PropertyValueFactory("telNo"));
		TableColumn<PhonerDataSet, String> descCol = new TableColumn<>("Description");
		descCol.setCellValueFactory(new PropertyValueFactory("desc"));

		dataTable.getColumns().setAll(nameCol, telNoCol, descCol);
	}

	public TableView getPane() {
		return dataTable;
	}

	public static void setTableItems(ObservableList<PhonerDataSet> list) {
		dataTable.setItems(list);
	}

}
