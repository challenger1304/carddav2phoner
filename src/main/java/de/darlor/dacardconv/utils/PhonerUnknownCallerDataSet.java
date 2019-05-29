/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.darlor.dacardconv.utils;

import de.darlor.dacardconv.settings.BaseSettings;

/**
 *
 * @author Vincent Neubauer (v.neubauer@darlor.de)
 */
public class PhonerUnknownCallerDataSet extends PhonerDataSet {

	public PhonerUnknownCallerDataSet() {
		super(BaseSettings.getAnonymousName(), "0", "");
		nameProperty().addListener((observable, oldValue, newValue) -> {
			BaseSettings.setAnonymousName(newValue);
		});
	}

}
