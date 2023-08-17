
package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieMinDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Transactional(readOnly = true)
	public Page<MovieMinDTO> find(Long genreId, Pageable pageable){
		pageable = PageRequest.of(0, pageable.getPageSize(), Sort.by("title"));
		
		Page<Movie> page = genreId != 0 ? repository.findMoviesByGenre(genreId, pageable) : repository.findAll(pageable);
		
		return page.map(x -> new MovieMinDTO(x));
	}
	
	@Transactional
	public MovieDTO findById(Long id) {
		Optional<Movie> optional = repository.findById(id);
		Movie movie = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new MovieDTO(movie);		
	}
}