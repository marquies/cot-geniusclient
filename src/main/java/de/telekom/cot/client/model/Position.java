package de.telekom.cot.client.model;

/**
 * Created by breucking on 05.02.16.
 */
public class Position {

	float lng;
	float lat;
	float alt;

	public float getAlt() {
		return alt;
	}

	public void setAlt(float alt) {
		this.alt = alt;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

}
