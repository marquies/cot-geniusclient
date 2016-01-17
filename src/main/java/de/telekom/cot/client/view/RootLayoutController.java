package de.telekom.cot.client.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.telekom.cot.client.MainApp;
import de.telekom.cot.client.model.ManagedObject;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;

public class RootLayoutController implements Initializable {

	@FXML
	private TabPane tabPane;

	// Reference to the main application.
	private MainApp mainApp;

	public RootLayoutController() {

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void exitApplication() {
		Platform.exit();
	}

	@FXML
	private void addNewDevice() {
		String device1 = new String("New Device");
		mainApp.getDeviceData().add(new ManagedObject(device1));
	}
}
