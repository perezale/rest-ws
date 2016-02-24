package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import entity.Detector;
import entity.VideoDetection;
import entity.VideoDetectionCollection;
import repository.DetectorRepository;
import repository.VideoDetectionRepository;

@RestController
@RequestMapping("/videoDetections")
public class VideoDetectionController {
    
	@Autowired
	private VideoDetectionRepository videoDetectionRepository;
	
	@Autowired
	private DetectorRepository detectorRepository;

	//@RequestMapping(method = RequestMethod.GET)
	public Iterable<VideoDetection> getUbertooth() {
		return videoDetectionRepository.findAll();
	}

	//@RequestMapping(value = "/{detectionId}", method = RequestMethod.GET)
	VideoDetection readVideoDetection(@PathVariable Long detectionId) {
		return this.videoDetectionRepository.findOne(detectionId);
	}

	//@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody VideoDetection input) {
		VideoDetection result = videoDetectionRepository
				.save(new VideoDetection(input.getDetector(), input.getTime(), input.getCount()));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);

	}

	@RequestMapping(path = "/collection", method = RequestMethod.POST)
	ResponseEntity<?> addCollection(@RequestBody VideoDetectionCollection input) {
		long detectorId = input.getDetectorId();
		Detector detector = detectorRepository.findOne(detectorId);
		if(detector == null){
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("X-Status-Reason", String.format("Detector with id=%s not found",detectorId));
			return new ResponseEntity<>(null, httpHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		List<VideoDetection> detections = input.getDetections();
		List<Long> results = new ArrayList<Long>();
		for (VideoDetection detection : detections) {			
			VideoDetection result = videoDetectionRepository
					.save(new VideoDetection(detector, detection.getTime(), detection.getCount()));
			results.add(result.getId());
		}
		return new ResponseEntity<>(Arrays.toString(results.toArray()), null, HttpStatus.CREATED);

	}
   
}
