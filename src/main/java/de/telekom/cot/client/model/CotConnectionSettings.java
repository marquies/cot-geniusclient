package de.telekom.cot.client.model;

import org.omg.CORBA.PUBLIC_MEMBER;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class CotConnectionSettings {
	
	StringProperty baseUrl;
	StringProperty tenant;
	StringProperty connectionMethod;
	
	public CotConnectionSettings() {
		baseUrl = new SimpleStringProperty();
		tenant = new SimpleStringProperty();
		connectionMethod = new SimpleStringProperty();
	}
	
	public CotConnectionSettings(String baseUrl, String tenant, String connectionMethod) {
		this.baseUrl = new SimpleStringProperty(baseUrl);
		this.tenant = new SimpleStringProperty(tenant);
		this.connectionMethod = new SimpleStringProperty(connectionMethod);
	}
	
	public String getBaseUrl() {
		return baseUrl.getValue();
	}
	
	public String getTenant() {
		return tenant.getValue();
	}
	
	public String getConnectionMethod() {
		return connectionMethod.getValue();
	}
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl.set(baseUrl);
	}
	public void setTenant(String tenant) {
		this.tenant.set(tenant);
	}
	public void setConnectionMethod(String connectionMethod) {
		this.connectionMethod.set(connectionMethod);
	}
	
	

}
