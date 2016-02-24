package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import entity.Detector;
import repository.DetectorRepository;

@RestController
@RequestMapping("/detectors")
public class DetectorsController {

	@Autowired
	private DetectorRepository detectorRepository;

	//@RequestMapping(method = RequestMethod.GET)
	public Iterable<Detector> getDetectors() {
		return detectorRepository.findAll();
	}
	
	//@RequestMapping(value = "/{detectorId}", method = RequestMethod.GET)
	Detector readDetector(@PathVariable Long detectorId) {		
		return this.detectorRepository.findOne(detectorId);
	}

	//@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Detector input) {
		Detector result = detectorRepository.save(new Detector(input.getLat(), input.getLon()));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);

	}
}
