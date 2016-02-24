package repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import entity.VideoDetection;

public interface VideoDetectionRepository extends PagingAndSortingRepository<VideoDetection, Long> {
		 
}
