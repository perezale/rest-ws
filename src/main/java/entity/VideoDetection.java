package entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import app.DateSerializer;

@Entity
public class VideoDetection extends Detection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Date time;
	private int count;
	@ManyToOne
	private Detector detector;

	public VideoDetection() {

	}

	public VideoDetection(Detector detector, Date time, int count) {
		this.detector = detector;
		this.time = time;
		this.count = count;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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
		return String.format("VideoDetection [id=%s, count=%s]", id, count);
	}

}
