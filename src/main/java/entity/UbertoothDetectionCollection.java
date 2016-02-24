package entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UbertoothDetectionCollection {

	@JsonProperty("detector_id")
	private long detectorId;
	private List<UbertoothDetection> detections;
	
	public UbertoothDetectionCollection(){
		
	}
	
	public UbertoothDetectionCollection(long scannerId) {
		this.detectorId = scannerId;
	}
	
	public long getDetectorId() {
		return detectorId;
	}

	public void setDetectorId(long detectorId) {
		this.detectorId = detectorId;
	}

	public List<UbertoothDetection> getDetections() {
		return detections;
	}
	public void setDetections(List<UbertoothDetection> detecciones) {
		this.detections = detecciones;
	}
	
	
}
