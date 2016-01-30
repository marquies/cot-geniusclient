package de.telekom.cot.client;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.telekom.cot.client.model.CotConnectionSettings;
import de.telekom.cot.client.model.CotClientDataWrapper;
import de.telekom.cot.client.model.ManagedObject;
import de.telekom.cot.client.view.CotSettingsDialogController;
import de.telekom.cot.client.view.RootLayoutController;
import de.telekom.cot.client.view.TabbedMainController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	private BorderPane rootLayout;
	private TreeItem<String> rootItem;
	private ObservableList<ManagedObject> deviceData = FXCollections.observableArrayList();
	private TabbedMainController tabbedMainController;
	private CotConnectionSettings cotConnectionSettingsObject;

	public ObservableList<ManagedObject> getDeviceData() {
		return deviceData;
	}
	
	public CotConnectionSettings getConnectionSettings() {
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
		this.primaryStage.setTitle("Deutsche Telekom - Cloud of Things - Genius Client");
		
		cotConnectionSettingsObject = new CotConnectionSettings("test-ram.m2m.telekom.com", "testing", "http");

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
	        dialogStage.setTitle("Cloud of Things Connection Settings");
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
	
	public File getPersonFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	public void setPersonFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("Cloud of Things - Genius Client - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("Cloud of Things - Genius Client");
	    }
	}


	public static void main(String[] args) {
		launch(args);
	}


	public void showDevice(ManagedObject newValue) {
		tabbedMainController.showDevice(newValue);
	}
	
	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadClientDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(CotClientDataWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        CotClientDataWrapper wrapper = (CotClientDataWrapper) um.unmarshal(file);
	        cotConnectionSettingsObject = wrapper.getCotConnectionSettingsObject();
	        deviceData.clear();
	        deviceData.addAll(wrapper.getDevices());
	        

	        // Save the file path to the registry.
	        setPersonFilePath(file);

	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + file.getPath());
	        e.printStackTrace();
	        alert.showAndWait();
	    }
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void saveClientDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(CotClientDataWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        CotClientDataWrapper wrapper = new CotClientDataWrapper();
	        wrapper.setCotConnectionSettingsObject(cotConnectionSettingsObject);
	        wrapper.setDevices(deviceData);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	        // Save the file path to the registry.
	        setPersonFilePath(file);
	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());
	        e.printStackTrace();
	        alert.showAndWait();
	    }
	}
	
	
}
