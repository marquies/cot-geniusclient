package de.telekom.cot.client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InternalManagedObject {
	private final StringProperty name;
	private final StringProperty id;
	private final StringProperty deviceUsername;
	private final StringProperty devicePassword;
	private final StringProperty deviceTenant;

	public InternalManagedObject() {
		id = new SimpleStringProperty();
		name = new SimpleStringProperty();
		deviceUsername = new SimpleStringProperty();
		devicePassword = new SimpleStringProperty();
		deviceTenant = new SimpleStringProperty();
	}

	public InternalManagedObject(String id, String name, String deviceUsername, String devicePassword, String deviceTenant) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.deviceUsername = new SimpleStringProperty(deviceUsername);
		this.devicePassword = new SimpleStringProperty(devicePassword);
		this.deviceTenant= new SimpleStringProperty(deviceTenant);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setDeviceUsername(String username) {
		this.deviceUsername.set(username);
	}

	public String getDeviceUsername() {
		return deviceUsername.get();
	}

	public void setDevicePassword(String password) {
		this.devicePassword.set(password);
	}

	public String getDevicePassword() {
		return devicePassword.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}
	
	public String getId() {
		return id.get();
	}

	public void setDeviceTenant(String tenant) {
		this.deviceTenant.set(tenant);
	}
	
	public String getDeviceTenant() {
		return deviceTenant.get();
	}

}
