package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Detector {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private float lat;
	private float lon;

	public Detector() {

	}

	public Detector(float lat, float lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return String.format("Detector [id=%s, lat=%s, lon=%s]", id, lat, lon);
	}

}
