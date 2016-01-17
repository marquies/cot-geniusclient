package de.telekom.cot.client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ManagedObject {
	private final StringProperty name;

	public ManagedObject() {
		name = null;
	}

	public ManagedObject(String name) {
		this.name = new SimpleStringProperty(name);
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

}
