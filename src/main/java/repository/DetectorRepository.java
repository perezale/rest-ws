package repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import entity.Detector;

public interface DetectorRepository extends PagingAndSortingRepository<Detector, Long> {
		 
}
