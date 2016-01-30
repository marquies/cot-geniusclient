package de.telekom.cot.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cotConnectionSettings")
public class CotConnectionSettingsWrapper {

	private CotConnectionSettings cotConnectionSettingsObject;

	public CotConnectionSettings getCotConnectionSettingsObject() {
		return cotConnectionSettingsObject;
	}

	@XmlElement(name = "settings")
	public void setCotConnectionSettingsObject(CotConnectionSettings cotConnectionSettingsObject) {
		this.cotConnectionSettingsObject = cotConnectionSettingsObject;
	}

}
