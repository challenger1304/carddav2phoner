package de.darlor.dacardconv.tasks;

import de.darlor.dacardconv.DaCardConv;
import de.darlor.dacardconv.Settings;
import de.darlor.dacardconv.utils.PhonerDataSet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import net.sourceforge.cardme.engine.VCardEngine;
import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.exceptions.VCardParseException;

/**
 *
 * @author Vincent Neubauer (v.neubauer@asysgmbh.de)
 */
public class VCardImporterTask extends Task<Void> {

	private List<VCard> vcardsList;
	private final File vcardFile;
	private final ObservableList<PhonerDataSet> dataSets;
	private final ArrayList<PhonerDataSet> dataSetsLocal;

	public VCardImporterTask(File vcards, ObservableList<PhonerDataSet> list) {
		//TODO get vcards directly from CardDAV-Server
		this.vcardFile = vcards;
		this.dataSets = list;
		this.dataSetsLocal = new ArrayList();
	}

	@Override
	protected Void call() throws Exception {
		DaCardConv.LOGGER.info("started importing vcards");
		try {
			VCardEngine vcardEngine = new VCardEngine();
			vcardsList = vcardEngine.parseMultiple(this.vcardFile);
			DaCardConv.LOGGER.info("parsing vcards");
			vcardsList.forEach((VCard t) -> {

				//get a displayable name
				String name;
				if (t.getOrg() != null) {
					name = t.getOrg().getOrgName();
				} else if (t.getFN() != null) {
					name = t.getFN().getFormattedName();
				} else {
					name = Settings.getAnonymousName();
				}
				//get a description (generated from groups)
				String desc = t.getCategories() != null ? t.getCategories().getCategories().toString() : "";
				String description = desc.trim();
				//get the telephone number
				if (t.getTels() != null) {
					t.getTels().forEach((u) -> {
						String telephone = u.getTelephone();
						if (telephone != null) {
							telephone = telephone.trim().replace(" ", "").replaceFirst("^0", "+49");
							this.dataSetsLocal.add(new PhonerDataSet(name, telephone, description));
						}
					});
				}

			});
			DaCardConv.LOGGER.info("finished importing vcards");
		} catch (IOException | VCardParseException ex) {
			DaCardConv.LOGGER.severe("failed importing vcards");
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	protected void succeeded() {
		this.dataSets.addAll(dataSetsLocal);
	}

}
