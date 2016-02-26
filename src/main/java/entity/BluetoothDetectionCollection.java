package entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BluetoothDetectionCollection {

	@JsonProperty("detector_id")
	private long detectorId;
	private List<BluetoothDetection> detections;
	
	public BluetoothDetectionCollection(){
		
	}
	
	public BluetoothDetectionCollection(long scannerId) {
		this.detectorId = scannerId;
	}
	
	public long getDetectorId() {
		return detectorId;
	}

	public void setDetectorId(long detectorId) {
		this.detectorId = detectorId;
	}

	public List<BluetoothDetection> getDetections() {
		return detections;
	}
	public void setDetections(List<BluetoothDetection> detecciones) {
		this.detections = detecciones;
	}
	
	
}
