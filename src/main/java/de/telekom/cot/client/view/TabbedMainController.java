package de.telekom.cot.client.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import de.telekom.cot.client.MainApp;
import de.telekom.cot.client.model.ManagedObject;

import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

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
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		tableView.setItems(mainApp.getDeviceData());
//		mainApp.getDeviceData().addListener(new ListChangeListener<String>() {
//
//			@Override
//			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
//				updateTreeList();
//			}
//
//		});
	}

	private void updateTreeList() {
		
//		ObservableList<String> list = mainApp.getDeviceData();
//		tableView.getRoot().getChildren().clear();
//		for (String string : list) {
//			TreeItem<String> item = new TreeItem<String>(string);
//			tableView.getRoot().getChildren().add(item);
//		}
	}

	public void showDevice(ManagedObject newValue) {
		if (newValue != null) {
			deviceName.setText(newValue.getName());
			
		} else {
			deviceName.setText("");
		}
	}

	@FXML
	private void handleDeleteDevice() {
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
//		rootItem.getChildren().remove(selectedIndex - 1);
	}

}
