package de.darlor.dacardconv.panes;

import de.darlor.dacardconv.utils.PhonerDataSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class VCardsPane {

	private final TableView<PhonerDataSet> dataTable;
	private final ObservableList<PhonerDataSet> dataTableList;

	public VCardsPane() {
		dataTableList = FXCollections.observableArrayList();
		dataTable = new TableView<>(dataTableList);
		dataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		dataTable.setEditable(true);
		TableColumn<PhonerDataSet, String> nameCol = new TableColumn<>("Display Name");
		nameCol.setCellValueFactory(new PropertyValueFactory("name"));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn<PhonerDataSet, String> telNoCol = new TableColumn<>("Telephone Number");
		telNoCol.setCellValueFactory(new PropertyValueFactory("telNo"));
		telNoCol.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn<PhonerDataSet, String> descCol = new TableColumn<>("Description");
		descCol.setCellValueFactory(new PropertyValueFactory("desc"));
		descCol.setCellFactory(TextFieldTableCell.forTableColumn());

		dataTable.getColumns().setAll(nameCol, telNoCol, descCol);
	}

	public TableView getPane() {
		return dataTable;
	}

	public ObservableList<PhonerDataSet> getDataTableList() {
		return dataTableList;
	}

}
