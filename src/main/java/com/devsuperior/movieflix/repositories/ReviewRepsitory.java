package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;

public interface ReviewRepsitory extends JpaRepository<Review, Long>{

	@Query("SELECT new com.devsuperior.movieflix.dto.ReviewDTO(obj) "
			+ "FROM Review obj "
			+ "WHERE obj.movie.id = :movieId")
	List<ReviewDTO> findReviewByMovie(Long movieId);
}
