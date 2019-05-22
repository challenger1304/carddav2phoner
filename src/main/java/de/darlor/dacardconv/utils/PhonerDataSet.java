package de.darlor.dacardconv.utils;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class PhonerDataSet {

	private final SimpleStringProperty name = new SimpleStringProperty();
	private final SimpleStringProperty telNo = new SimpleStringProperty();
	private final SimpleStringProperty desc = new SimpleStringProperty();

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

	public SimpleStringProperty nameProperty() {
		return this.name;
	}

	public String getTelNo() {
		return telNo.getValueSafe();
	}

	public void setTelNo(String telNo) {
		this.telNo.set(telNo);
	}

	public SimpleStringProperty telNoProperty() {
		return this.telNo;
	}

	public String getDesc() {
		return desc.getValueSafe();
	}

	public void setDesc(String desc) {
		this.desc.set(desc);
	}

	public SimpleStringProperty descProperty() {
		return this.desc;
	}

}
