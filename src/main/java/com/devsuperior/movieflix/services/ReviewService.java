package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepsitory;
import com.devsuperior.movieflix.services.exceptions.DatabaseException;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepsitory repsitory;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		try {
			User user = authService.authenticated();
			Movie movie = movieRepository.getOne(dto.getMovieId());
			
			Review review = new Review();
			review.setText(dto.getText());
			review.setMovie(movie);
			review.setUser(user);
			review = repsitory.save(review);
			return new ReviewDTO(review);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Movie id not exists");
		}
			
	}
}
