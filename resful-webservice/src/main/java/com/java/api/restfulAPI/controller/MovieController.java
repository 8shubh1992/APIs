package com.java.api.restfulAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.api.restfulAPI.exception.ResourceNotFoundException;
import com.java.api.restfulAPI.model.Movie;
import com.java.api.restfulAPI.repository.MovieRepository;

@RestController
@RequestMapping("/api/v1")
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;

	public void setMovieRepository(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	//create all movies api
	@GetMapping("/movies")
	public List<Movie> getALLMovies(){
		return movieRepository.findAll();	
	}

	//add movie
	@PostMapping("/movies")
	public Movie addMovie(@Validated @RequestBody Movie movie) {
		return movieRepository.save(movie);
	}

	//get movie by id
	@GetMapping("/movies/{id}")
	public Movie getMovieById(@PathVariable(value = "id") long movieId) throws ResourceNotFoundException{
		return movieRepository.findById(movieId)
				.orElseThrow(() -> new ResourceNotFoundException("Movie not found by this id :: " + movieId));
	}

	//update movie
	@PutMapping("/movies/{id}")
	public Movie updateMovie(@PathVariable(value = "id") long movieId,
			@RequestBody Movie movieDetails) throws ResourceNotFoundException{
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie not found by this id :: " + movieId));
		movie.setCategory(movieDetails.getCategory());
		movie.setStarRating(movieDetails.getStarRating());
		movie.setTitle(movieDetails.getTitle());
		movieRepository.save(movie);
		return movie;
	}

	//delete movie by id
	@DeleteMapping("/movies/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable(value = "id") long movieId) throws ResourceNotFoundException {
		movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie not found by this id :: " + movieId));
		movieRepository.deleteById(movieId);
		return ResponseEntity.ok().build();
	}

}
