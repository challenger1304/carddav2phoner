package de.darlor.dacardconv.tasks;

import de.darlor.dacardconv.DaCardConv;
import de.darlor.dacardconv.Settings;
import de.darlor.dacardconv.panes.VCardsPane;
import de.darlor.dacardconv.utils.PhonerDataSet;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import net.sourceforge.cardme.engine.VCardEngine;
import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.exceptions.VCardParseException;

/**
 *
 * @author Vincent Neubauer (v.neubauer@asysgmbh.de)
 */
public class VCardImporterTask extends Task<ObservableList<PhonerDataSet>> {

	private List<VCard> vcardsList;
	private final File vcardFile;
	private ObservableList<PhonerDataSet> dataSets = null;

	public VCardImporterTask(File vcards) {
		//TODO get vcards directly from CardDAV-Server
		this.vcardFile = vcards;
		this.dataSets = FXCollections.observableArrayList();
	}

	@Override
	protected ObservableList<PhonerDataSet> call() throws Exception {
		DaCardConv.LOGGER.info("started importing vcards");
		try {
			VCardEngine vcardEngine = new VCardEngine();
			vcardsList = vcardEngine.parseMultiple(this.vcardFile);
			DaCardConv.LOGGER.info("parsing vcards");
			vcardsList.forEach((VCard t) -> {
				String name;
				String telephone;
				String description;

				//get a displayable name
				if (t.getOrg() != null) {
					name = t.getOrg().getOrgName();
				} else if (t.getFN() != null) {
					name = t.getFN().getFormattedName();
				} else {
					name = Settings.getAnonymousName();
				}
				//get a description (generated from groups)
				description = t.getCategories() != null ? t.getCategories().getCategories().toString(): "";
				//get the telephone number
				telephone = t.getTels() != null ? t.getTels().get(0).getTelephone(): "";
				//TODO maybe foreach telephoneNo. a separate PhonerDataSet?

				this.dataSets.add(new PhonerDataSet(name, telephone, description));
			});
			DaCardConv.LOGGER.info("finished importing vcards");
		} catch (IOException | VCardParseException ex) {
			DaCardConv.LOGGER.severe("failed importing vcards");
			ex.printStackTrace();
		}
		return this.dataSets;
	}

	@Override
	protected void succeeded() {
		VCardsPane.setTableItems(this.dataSets);
	}

}
