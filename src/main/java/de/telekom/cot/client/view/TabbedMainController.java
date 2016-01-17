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

import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
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
	private TextField deviceName;

	private MainApp mainApp;

	@FXML
	private TreeView treeView;

	@FXML
	private TreeItem<String> treeItem;

	private TreeItem<String> rootItem;

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
		
		rootItem = new TreeItem<>(new String("Devices"));
		rootItem.setExpanded(true);
		treeView.setRoot(rootItem);
		
		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDevice(newValue));
	}

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


	public void showDevice(Object newValue) {
		System.out.println("Value " + newValue);
		if (newValue != rootItem)
		deviceName.setText(((TreeItem<String>)newValue).getValue());
	}
	
	@FXML
	private void handleDeleteDevice() {
		
	}
	
	

}
