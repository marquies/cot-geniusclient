package de.telekom.cot.client.view;

import java.net.URL;
import java.util.ResourceBundle;

import de.telekom.cot.client.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class RootLayoutController implements Initializable {

	@FXML
	private TreeView treeView;

	// Reference to the main application.
	private MainApp mainApp;

	public RootLayoutController() {
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		TreeItem<String> rootItem = new TreeItem<>(new String("Devices"));

		rootItem.setExpanded(true);

		String device1 = new String("Device 1");

		TreeItem<String> link1 = new TreeItem<String>(device1);
		treeView.setRoot(rootItem);
		rootItem.getChildren().add(link1);
	}

}
