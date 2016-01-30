package de.telekom.cot.client;

import java.io.IOException;

import de.telekom.cot.client.model.CotConnectionSettingsObject;
import de.telekom.cot.client.model.ManagedObject;
import de.telekom.cot.client.view.CotSettingsDialogController;
import de.telekom.cot.client.view.RootLayoutController;
import de.telekom.cot.client.view.TabbedMainController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private TreeItem<String> rootItem;
	private ObservableList<ManagedObject> deviceData = FXCollections.observableArrayList();
	private TabbedMainController tabbedMainController;
	private CotConnectionSettingsObject cotConnectionSettingsObject;

	public ObservableList<ManagedObject> getDeviceData() {
		return deviceData;
	}
	
	public CotConnectionSettingsObject getConnectionSettings() {
		return cotConnectionSettingsObject;
	}
	

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			RootLayoutController controller = (RootLayoutController) loader.getController();
			controller.setMainApp(this);
	
			rootItem = new TreeItem<>(new String("Devices"));

			rootItem.setExpanded(true);
			

			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Cloud of Things - Genius Client");
		
		cotConnectionSettingsObject = new CotConnectionSettingsObject("test-ram.m2m.telekom.com", "testing", "http");

		initRootLayout();

		showPersonOverview();

	}

	private void showPersonOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TabbedMain.fxml"));
			AnchorPane tabbedMain = (AnchorPane) loader.load();
			
			rootLayout.setCenter(tabbedMain);

			tabbedMainController = loader.getController();
			tabbedMainController.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean showCotSettingsDialog() {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/CotSettingsDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("CoT Connection Settings");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        CotSettingsDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setCotConnectionSettings(cotConnectionSettingsObject);
	        
	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	public static void main(String[] args) {
		launch(args);
	}


	public void showDevice(ManagedObject newValue) {
		tabbedMainController.showDevice(newValue);
	}
}
