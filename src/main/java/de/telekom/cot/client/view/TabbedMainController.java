package de.telekom.cot.client.view;

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

import de.telekom.cot.client.MainApp;
import de.telekom.cot.client.model.ManagedObject;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class TabbedMainController implements Initializable, MapComponentInitializedListener {

	@FXML
	GoogleMapView mapView;

	GoogleMap map;

	@FXML
	private Label idLabel;

	@FXML
	private TabPane tabPane;

	@FXML
	private TableColumn<ManagedObject, String> deviceNameColumn;

	@FXML
	private TextField deviceName;

	private MainApp mainApp;

	@FXML
	private TableView<ManagedObject> tableView;

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

		tableView.setRowFactory(new Callback<TableView<ManagedObject>, TableRow<ManagedObject>>() {
			@Override
			public TableRow<ManagedObject> call(TableView<ManagedObject> tableView) {
				final TableRow<ManagedObject> row = new TableRow<>();
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

	public void showDevice(ManagedObject newValue) {
		if (newValue != null) {
			deviceName.setText(newValue.getName());

		} else {
			deviceName.setText("");
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
		System.out.println("Not implemented yet");
	}

	/**
	 * Tries to do all the stuff to create a Device.
	 */
	@FXML
	private void handleRegisterDeviceStep2() {
		System.out.println("Not implemented yet");
	}

	@FXML
	private void handleDeleteDevice() {
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		mainApp.getDeviceData().remove(selectedIndex);
	}

}
