package de.telekom.cot.client.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.telekom.cot.client.MainApp;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class RootLayoutController implements Initializable {

	@FXML
	private TreeView treeView;

	@FXML
	private TabPane tabPane;
	
	@FXML
	private TreeItem<String> treeItem;

	// Reference to the main application.
	private MainApp mainApp;


	public RootLayoutController() {

	}
	
	  /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    private void initialize() {
    	
        // Initialize the person table with the two columns.
//        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
//        
//        // Clear person details.
//        showPersonDetails(null);
//
//        // Listen for selection changes and show the person details when changed.
//        personTable.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }


	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		mainApp.getDeviceData().addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				updateTreeList();
			}

		});
	}
	private void updateTreeList() {
		ObservableList<String> list = mainApp.getDeviceData();
		treeView.getRoot().getChildren().clear();
		for (String string : list) {
			TreeItem<String> item = new TreeItem<String>(string);
			treeView.getRoot().getChildren().add(item);
		}
	}

	@FXML
	private void exitApplication() {
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TreeItem<String> rootItem = new TreeItem<>(new String("Devices"));
		rootItem.setExpanded(true);
		treeView.setRoot(rootItem);
	}

	@FXML
	private void addNewDevice() {
		String device1 = new String("New Device");
		mainApp.getDeviceData().add(device1);

	}

}
