package de.darlor.dacardconv.tasks;

import de.darlor.dacardconv.DaCardConv;
import de.darlor.dacardconv.utils.CardDAVServer;
import de.darlor.dacardconv.utils.PhonerDataSet;
import java.io.File;
import java.util.concurrent.TimeUnit;
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
	private File addrBookFile;

	public CardDAVImporterTask(CardDAVServer server, String addrBook, ObservableList<PhonerDataSet> list) {
		SERV = server;
		ADDRBOOK = addrBook;
		LIST = list;
	}

	@Override
	protected Void call() throws Exception {
		addrBookFile = SERV.downloadAddressBook(ADDRBOOK);
		VCardImporterTask task = new VCardImporterTask(addrBookFile, LIST);
		Thread th = new Thread(task);
		th.start();
		while (th.isAlive()) {
			TimeUnit.SECONDS.sleep(5);
		}
		addrBookFile.delete(); //remove file when done for security reasons
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
