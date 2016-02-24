package app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.google.common.base.Predicates;

import entity.Detector;
import entity.UbertoothDetection;
import repository.DetectorRepository;
import repository.UbertoothDetectionRepository;
import repository.VideoDetectionRepository;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = { "controller" })
@EnableJpaRepositories(basePackages = { "repository" })
@EntityScan(basePackages = { "entity" })
@EnableSwagger2
public class Application extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(DetectorRepository detectorRepository,
			VideoDetectionRepository videoDetectionRepository,
			UbertoothDetectionRepository ubertoothDetectionRepository) {
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
			ubertoothDetectionRepository.save(new UbertoothDetection(detector, new Date(), "349e4a", -50));
			ubertoothDetectionRepository.save(new UbertoothDetection(detector, new Date(), "349e4a", -55));
			ubertoothDetectionRepository.save(new UbertoothDetection(detector, new Date(), "349e4a", -60));

			// fetch all detections
			log.info("Ubertooth detections found with findAll():");
			log.info("-------------------------------");
			for (UbertoothDetection detection : ubertoothDetectionRepository.findAll()) {
				log.info(detection.toString());
			}
			log.info("");

			// fetch an individual detectors by ID
			UbertoothDetection udetection = ubertoothDetectionRepository.findOne(1L);
			log.info("Ubertooth detection found with findOne(1L):");
			log.info("--------------------------------");
			log.info(udetection.toString());
			log.info("");
		};
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Bean
	public CorsFilter corsFilter() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // you USUALLY want this
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public Docket swaggerSpringMvcPlugin() {
		// http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Pladema REST WS", "Servicios REST para acceder a datos del instituto Pladema", "1.0",
				"http://wsonline.pladema.net/rest-ws/terms.html", "ajperez@exa.unicen.edu.ar", "Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0.html");
	}

}
