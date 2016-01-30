package de.telekom.cot.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.ObservableList;

@XmlRootElement(name = "clientData")
public class CotClientDataWrapper {

	private CotConnectionSettings cotConnectionSettingsObject;
	private List<ManagedObject> devices;
	

	public CotConnectionSettings getCotConnectionSettingsObject() {
		return cotConnectionSettingsObject;
	}

	@XmlElement(name = "cotConnectionSettings")
	public void setCotConnectionSettingsObject(CotConnectionSettings cotConnectionSettingsObject) {
		this.cotConnectionSettingsObject = cotConnectionSettingsObject;
	}

	@XmlElementWrapper(name = "devices")
	@XmlElement(name = "device")
	public void setDevices(List<ManagedObject> devices) {
		this.devices = devices;
	}

	public List<ManagedObject> getDevices() {
		return devices;
	}

}
