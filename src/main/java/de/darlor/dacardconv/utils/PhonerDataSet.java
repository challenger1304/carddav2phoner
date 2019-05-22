package de.darlor.dacardconv.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class PhonerDataSet {

	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty telNo = new SimpleStringProperty();
	private final StringProperty desc = new SimpleStringProperty();

	public PhonerDataSet(String name, String telNo, String desc) {
		this.name.set(name);
		this.telNo.set(telNo);
		this.desc.set(desc);
	}

	public String getName() {
		return name.getValueSafe();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public String getTelNo() {
		return telNo.getValueSafe();
	}

	public void setTelNo(String telNo) {
		this.telNo.set(telNo);
	}

	public StringProperty telNoProperty() {
		return this.telNo;
	}

	public String getDesc() {
		return desc.getValueSafe();
	}

	public void setDesc(String desc) {
		this.desc.set(desc);
	}

	public StringProperty descProperty() {
		return this.desc;
	}

}
