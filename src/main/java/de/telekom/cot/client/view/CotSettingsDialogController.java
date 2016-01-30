package de.telekom.cot.client.view;

import java.util.regex.Pattern;

import de.telekom.cot.client.model.CotConnectionSettingsObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CotSettingsDialogController {

	ObservableList<String> serverBackendList = FXCollections.observableArrayList("test-ram.m2m.telekom.com",
			"ram.m2m.telekom.com");

	ObservableList<String> connectionMethodList = FXCollections.observableArrayList("HTTP", "MQTT");

	CotConnectionSettingsObject cotConnectionSettingsObject;

	private Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);;

	@FXML
	private ComboBox<String> serverBackend;

	@FXML
	private ComboBox<String> connectionMethod;

	@FXML
	private TextField tenant;

	@FXML
	private void initialize() {
		connectionMethod.setValue(connectionMethodList.get(0));
		connectionMethod.setItems(connectionMethodList);

		serverBackend.setValue(serverBackendList.get(0));
		serverBackend.setItems(serverBackendList);
	}

	private Stage dialogStage;
	private boolean okClicked = false;

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			cotConnectionSettingsObject.setBaseUrl(serverBackend.getValue());
			cotConnectionSettingsObject.setConnnectionMethod(connectionMethod.getValue());
			cotConnectionSettingsObject.setTenant(tenant.getText());
			okClicked = true;
			dialogStage.close();
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (tenant.getText() == null || tenant.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		} else {

			if (p.matcher(tenant.getText()).find())
				errorMessage += "There is a special character in Tenant";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	public void setCotConnectionSettings(CotConnectionSettingsObject cotConnectionSettingsObject) {

		this.cotConnectionSettingsObject = cotConnectionSettingsObject;

		serverBackend.setValue(cotConnectionSettingsObject.getBaseUrl());
		connectionMethod.setValue(cotConnectionSettingsObject.getConnectionMethod());
		tenant.setText(cotConnectionSettingsObject.getTenant());

	}

}
