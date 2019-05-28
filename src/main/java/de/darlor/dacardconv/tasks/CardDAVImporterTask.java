package de.darlor.dacardconv.tasks;

import de.darlor.dacardconv.DaCardConv;
import de.darlor.dacardconv.utils.CardDAVServer;
import de.darlor.dacardconv.utils.PhonerDataSet;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Dialog;

/**
 *
 * @author Vincent 'challenger1304' Neubauer (v.neubauer@darlor.de)
 */
public class CardDAVImporterTask extends Task<Void> {

	private final CardDAVServer SERV;
	private final String ADDRBOOK;
	private final ObservableList<PhonerDataSet> LIST;

	public CardDAVImporterTask(CardDAVServer server, String addrBook, ObservableList<PhonerDataSet> list) {
		SERV = server;
		ADDRBOOK = addrBook;
		LIST = list;
	}

	@Override
	protected Void call() throws Exception {
		VCardImporterTask task = new VCardImporterTask(SERV.downloadAddressBook(ADDRBOOK), LIST);
		Thread th = new Thread(task);
		th.start();
		return null;
	}

	@Override
	protected void failed() {
		Dialog dialog = DaCardConv.getDialog("Remote Importer");
		dialog.setContentText(this.getException().getMessage());
		dialog.show();
		this.getException().printStackTrace();

	}

}
