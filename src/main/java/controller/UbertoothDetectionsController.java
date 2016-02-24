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
import entity.UbertoothDetection;
import entity.UbertoothDetectionCollection;
import repository.DetectorRepository;
import repository.UbertoothDetectionRepository;

@RestController
@RequestMapping("/ubertoothDetections")
public class UbertoothDetectionsController {

	@Autowired
	private UbertoothDetectionRepository ubertoothDetectionRepository;
	
	@Autowired
	private DetectorRepository detectorRepository;

	//@RequestMapping(method = RequestMethod.GET)
	public Iterable<UbertoothDetection> getUbertooth() {
		return ubertoothDetectionRepository.findAll();
	}

	//@RequestMapping(value = "/{detectionId}", method = RequestMethod.GET)
	UbertoothDetection readUbertoothDetection(@PathVariable Long detectionId) {
		return this.ubertoothDetectionRepository.findOne(detectionId);
	}

	//@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody UbertoothDetection input) {
		UbertoothDetection result = ubertoothDetectionRepository
				.save(new UbertoothDetection(input.getDetector(),input.getTime(), input.getLap(), input.getSignal()));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);

	}

	@RequestMapping(path = "/collection", method = RequestMethod.POST)
	ResponseEntity<?> addCollection(@RequestBody UbertoothDetectionCollection input) {
		long detectorId = input.getDetectorId();
		Detector detector = detectorRepository.findOne(detectorId);
		if(detector == null){
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("X-Status-Reason", String.format("Detector with id=%s not found",detectorId));
			return new ResponseEntity<>(null, httpHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		List<UbertoothDetection> detections = input.getDetections();
		List<Long> results = new ArrayList<Long>();
		for (UbertoothDetection detection : detections) {			
			UbertoothDetection result = ubertoothDetectionRepository
					.save(new UbertoothDetection(detector, detection.getTime(), detection.getLap(), detection.getSignal()));
			results.add(result.getId());
		}
		return new ResponseEntity<>(Arrays.toString(results.toArray()), null, HttpStatus.CREATED);

	}

}
