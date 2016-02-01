package de.telekom.cot.client.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.telekom.m2m.cot.restsdk.CloudOfThingsPlatform;
import com.telekom.m2m.cot.restsdk.devicecontrol.DeviceControlApi;
import com.telekom.m2m.cot.restsdk.devicecontrol.DeviceCredentials;
import com.telekom.m2m.cot.restsdk.devicecontrol.DeviceCredentialsApi;
import com.telekom.m2m.cot.restsdk.identity.IdentityApi;
import com.telekom.m2m.cot.restsdk.inventory.InventoryApi;
import com.telekom.m2m.cot.restsdk.inventory.ManagedObject;

import de.telekom.cot.client.MainApp;
import de.telekom.cot.client.model.InternalManagedObject;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;

public class TabbedMainController implements Initializable, MapComponentInitializedListener {

	@FXML
	GoogleMapView mapView;

	GoogleMap map;

	@FXML
	private Label idLabel;

	@FXML
	private Label deviceUsernameLabel;

	@FXML
	private Label devicePasswordLabel;

	@FXML
	private TabPane tabPane;

	@FXML
	private TableColumn<InternalManagedObject, String> deviceNameColumn;

	@FXML
	private TextField deviceName;

	private MainApp mainApp;

	@FXML
	private TableView<InternalManagedObject> tableView;

	@Override
	public void mapInitialized() {
		// Set the initial properties of the map.
		MapOptions mapOptions = new MapOptions();

		mapOptions.center(new LatLong(47.6097, -122.3331)).mapType(MapTypeIdEnum.ROADMAP).overviewMapControl(false)
				.panControl(false).rotateControl(false).scaleControl(false).streetViewControl(false).zoomControl(false)
				.zoom(12);

		map = mapView.createMap(mapOptions);

		// Add a marker to the map
		MarkerOptions markerOptions = new MarkerOptions();

		markerOptions.position(new LatLong(47.6, -122.3)).visible(Boolean.TRUE).title("My Marker");

		Marker marker = new Marker(markerOptions);
		marker.setDraggable(true);

		map.addMarker(marker);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mapView.addMapInializedListener(this);

		deviceNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		tableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDevice(newValue));

		deviceName.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
				int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
				mainApp.getDeviceData().get(selectedIndex).setName(s2);
			}
		});

		tableView.setRowFactory(new Callback<TableView<InternalManagedObject>, TableRow<InternalManagedObject>>() {
			@Override
			public TableRow<InternalManagedObject> call(TableView<InternalManagedObject> tableView) {
				final TableRow<InternalManagedObject> row = new TableRow<>();
				final ContextMenu rowMenu = new ContextMenu();

				MenuItem removeItem = new MenuItem("Delete");
				removeItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						handleDeleteDevice();
					}
				});
				rowMenu.getItems().addAll(removeItem);

				// only display context menu for non-null items:
				row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu)
						.otherwise((ContextMenu) null));
				return row;
			}
		});
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		tableView.setItems(mainApp.getDeviceData());
	}

	public void showDevice(InternalManagedObject newValue) {
		if (newValue != null) {
			idLabel.setText(newValue.getId());
			deviceName.setText(newValue.getName());
			deviceUsernameLabel.setText(newValue.getDeviceUsername());
			devicePasswordLabel.setText(newValue.getDevicePassword());
		} else {
			idLabel.setText("");
			deviceName.setText("");
			deviceUsernameLabel.setText("");
			devicePasswordLabel.setText("");
		}
	}

	/**
	 * Removes the device from the Cloud of Things.
	 */
	@FXML
	private void handleUnregisterDevice() {
		System.out.println("Not implemented yet");
	}

	/**
	 * Check if device is already in the register devices list.
	 */
	@FXML
	private void handleRegisterDeviceStep1() {
		try {
			CloudOfThingsPlatform platform = CloudOfThingsPlatform.getPlatformToRegisterDevice();
			DeviceCredentialsApi deviceCredentialsApi = platform.getDeviceCredentialsApi();
			DeviceCredentials result = deviceCredentialsApi.getCredentials(deviceName.getText());

			deviceUsernameLabel.setText(result.getUsername());
			devicePasswordLabel.setText(result.getPassword());

			int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
			mainApp.getDeviceData().get(selectedIndex).setDeviceTenant(result.getTenantId());
			mainApp.getDeviceData().get(selectedIndex).setDeviceUsername(result.getUsername());
			mainApp.getDeviceData().get(selectedIndex).setDevicePassword(result.getPassword());

		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error registering device");
			alert.setContentText(e.getMessage());
			e.printStackTrace();
			alert.showAndWait();
		}
	}

	/**
	 * Tries to do all the stuff to create a Device.
	 */
	@FXML
	private void handleRegisterDeviceStep2() {
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		InternalManagedObject imo = mainApp.getDeviceData().get(selectedIndex);
		try {
			CloudOfThingsPlatform platform = new CloudOfThingsPlatform(imo.getDeviceTenant(), imo.getDeviceUsername(),
					imo.getDevicePassword());
			InventoryApi inventoryApi = platform.getInventoryApi();
			ManagedObject mo = new ManagedObject();
			mo.setName(imo.getName());
	        mo.set("c8y_IsDevice", new Object());
			ManagedObject resultedMo = inventoryApi.create(mo);
			imo.setId(resultedMo.getId());
			idLabel.setText(resultedMo.getId());
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error registering device");
			alert.setContentText(e.getMessage());
			e.printStackTrace();
			alert.showAndWait();
		}

	}

	@FXML
	private void handleDeleteDevice() {
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		mainApp.getDeviceData().remove(selectedIndex);
	}

}
