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
import entity.BluetoothDetection;
import entity.BluetoothDetectionCollection;
import repository.DetectorRepository;
import repository.BluetoothDetectionRepository;

@RestController
@RequestMapping("/bluetoothDetections")
public class BluetoothDetectionsController {

	@Autowired
	private BluetoothDetectionRepository bluetoothDetectionRepository;
	
	@Autowired
	private DetectorRepository detectorRepository;

	//@RequestMapping(method = RequestMethod.GET)
	public Iterable<BluetoothDetection> getUbertooth() {
		return bluetoothDetectionRepository.findAll();
	}

	//@RequestMapping(value = "/{detectionId}", method = RequestMethod.GET)
	BluetoothDetection readUbertoothDetection(@PathVariable Long detectionId) {
		return this.bluetoothDetectionRepository.findOne(detectionId);
	}

	//@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody BluetoothDetection input) {
		BluetoothDetection result = bluetoothDetectionRepository
				.save(new BluetoothDetection(input.getDetector(),input.getTime(), input.getLap(), input.getSignal()));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);

	}

	@RequestMapping(path = "/collection", method = RequestMethod.POST)
	ResponseEntity<?> addCollection(@RequestBody BluetoothDetectionCollection input) {
		long detectorId = input.getDetectorId();
		Detector detector = detectorRepository.findOne(detectorId);
		if(detector == null){
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("X-Status-Reason", String.format("Detector with id=%s not found",detectorId));
			return new ResponseEntity<>(null, httpHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		List<BluetoothDetection> detections = input.getDetections();
		List<Long> results = new ArrayList<Long>();
		for (BluetoothDetection detection : detections) {			
			BluetoothDetection result = bluetoothDetectionRepository
					.save(new BluetoothDetection(detector, detection.getTime(), detection.getLap(), detection.getSignal()));
			results.add(result.getId());
		}
		return new ResponseEntity<>(Arrays.toString(results.toArray()), null, HttpStatus.CREATED);

	}

}
