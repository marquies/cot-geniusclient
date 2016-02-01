package de.telekom.cot.client.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import de.telekom.cot.client.MainApp;
import de.telekom.cot.client.model.InternalManagedObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;

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
	private void addNewDevice() {
		String device1 = new String("New Device");
		mainApp.getDeviceData().add(new InternalManagedObject("", device1, "", "", ""));
	}
	
	@FXML
	private void openCotSettingsDialog() {
		mainApp.showCotSettingsDialog();
	}
	
	   /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadClientDataFromFile(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.saveClientDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveClientDataToFile(file);
        }
    }

    
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
		Platform.exit();
    }
}
