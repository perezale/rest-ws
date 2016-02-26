package entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import app.FullDateSerializer;

@Entity
public class BluetoothDetection extends Detection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Date time;
	private String lap;
	private int signal;
	@ManyToOne
	private Detector detector;

	public BluetoothDetection() {

	}

	public BluetoothDetection(Detector detector, Date time, String lap, int signal) {
		this.detector = detector;
		this.time = time;
		this.lap = lap;
		this.signal = signal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonSerialize(using = FullDateSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getLap() {
		return lap;
	}

	public void setLap(String lap) {
		this.lap = lap;
	}

	public int getSignal() {
		return signal;
	}

	public void setSignal(int signal) {
		this.signal = signal;
	}

	public Detector getDetector() {
		return detector;
	}

	public void setDetector(Detector detector) {
		this.detector = detector;
	}
	
	@JsonProperty("detector_id")
	public long getDetectorId(){
		if(detector!=null)
			return detector.getId();
		return 0;
	}

	@Override
	public String toString() {
		return String.format("BluetoothDetection [id=%s, lap=%s, signal=%s]", id, lap, signal);
	}

}
