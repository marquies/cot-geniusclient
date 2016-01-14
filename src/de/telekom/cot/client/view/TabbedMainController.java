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
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

public class TabbedMainController implements Initializable, MapComponentInitializedListener {
	
    @FXML
	GoogleMapView mapView;
    
    
	GoogleMap map;

	@FXML
	private Label idLabel;

	@FXML
	private TabPane tabPane;


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

				
	}

}
