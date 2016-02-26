package app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import entity.BluetoothDetection;
import entity.Detector;
import repository.BluetoothDetectionRepository;
import repository.DetectorRepository;
import repository.VideoDetectionRepository;

@Component
@Profile("development")
public class InitDb {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Bean
	public CommandLineRunner demo(DetectorRepository detectorRepository,
			VideoDetectionRepository videoDetectionRepository,
			BluetoothDetectionRepository ubertoothDetectionRepository) {
		return (args) -> {
			// save a couple of detectors
			detectorRepository.save(new Detector(-37.320750f, -59.081938f));
			detectorRepository.save(new Detector(-37.321531f, -59.083338f));

			// fetch all detectors
			log.info("Detectors found with findAll():");
			log.info("-------------------------------");
			for (Detector detector : detectorRepository.findAll()) {
				log.info(detector.toString());
			}
			log.info("");

			// fetch an individual detectors by ID
			Detector detector = detectorRepository.findOne(1L);
			log.info("Detector found with findOne(1L):");
			log.info("--------------------------------");
			log.info(detector.toString());
			log.info("");

			// save a couple of Ubertooth Detections
			ubertoothDetectionRepository.save(new BluetoothDetection(detector, new Date(), "349e4a", -50));
			ubertoothDetectionRepository.save(new BluetoothDetection(detector, new Date(), "349e4a", -55));
			ubertoothDetectionRepository.save(new BluetoothDetection(detector, new Date(), "349e4a", -60));

			// fetch all detections
			log.info("Ubertooth detections found with findAll():");
			log.info("-------------------------------");
			for (BluetoothDetection detection : ubertoothDetectionRepository.findAll()) {
				log.info(detection.toString());
			}
			log.info("");

			// fetch an individual detectors by ID
			BluetoothDetection udetection = ubertoothDetectionRepository.findOne(1L);
			log.info("Ubertooth detection found with findOne(1L):");
			log.info("--------------------------------");
			log.info(udetection.toString());
			log.info("");
		};
	}

}
