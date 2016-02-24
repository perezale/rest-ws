package entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoDetectionCollection {

	@JsonProperty("detector_id")
	private long detectorId;
	private List<VideoDetection> detections;
	
	public VideoDetectionCollection(){
		
	}
	
	public VideoDetectionCollection(long scannerId) {
		this.detectorId = scannerId;
	}
	
	public long getDetectorId() {
		return detectorId;
	}

	public void setDetectorId(long detectorId) {
		this.detectorId = detectorId;
	}
	
	public List<VideoDetection> getDetections() {
		return detections;
	}

	public void setDetections(List<VideoDetection> detections) {
		this.detections = detections;
	}
	
}
