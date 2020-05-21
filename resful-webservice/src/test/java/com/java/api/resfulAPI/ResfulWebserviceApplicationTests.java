package com.java.api.resfulAPI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.java.api.restfulAPI.controller.MovieController;
import com.java.api.restfulAPI.exception.ResourceNotFoundException;
import com.java.api.restfulAPI.model.Movie;
import com.java.api.restfulAPI.repository.MovieRepository;

@SpringBootTest
public class ResfulWebserviceApplicationTests {

	MovieController movieController;

	MovieRepository movieRepository;

	@Before
	public void setUp() {
		movieRepository = Mockito.mock(MovieRepository.class);
		movieController = new MovieController();
		movieController.setMovieRepository(movieRepository);
	}

	@Test
	public void editMovieTest() throws ResourceNotFoundException {
		Movie movie = new Movie();
		movie.setCategory("Test Category");
		movie.setStarRating("2d");
		movie.setTitle("Movie Title");
		movie.setMovieId(2l);
		when(movieRepository.findById(2l)).thenReturn(Optional.of(movie));
		// rating updated
		movie.setStarRating("3d");
		when(movieRepository.save(movie)).thenReturn(movie);
		// ASSERTIONS
		Movie updatedMovie = movieController.updateMovie(2l, movie);
		assertEquals(Double.valueOf(3), Double.valueOf(updatedMovie.getStarRating()));
	}

	@Test
	public void addMovieTest() {
		Movie movie = new Movie();
		movie.setCategory("Test Category");
		movie.setStarRating("2d");
		movie.setTitle("Movie Title");
		when(movieRepository.save(movie)).thenReturn(movie);
		Movie addedMovie = movieController.addMovie(movie);
		assertNotNull(addedMovie);
	}

	@Test
	public void getMovieById() throws ResourceNotFoundException {
		Movie movie = new Movie();
		movie.setCategory("Test Category");
		movie.setStarRating("2d");
		movie.setTitle("Movie Title");
		movie.setMovieId(2l);
		when(movieRepository.findById(2l)).thenReturn(Optional.of(movie));
		movie = movieController.getMovieById(2l);
		assertNotNull(movie);
	}

	@Test
	public void deleteMovies() throws ResourceNotFoundException {
		Movie movie = new Movie();
		movie.setMovieId(2l);
		when(movieRepository.findById(2l)).thenReturn(Optional.of(movie));
		movieController.deleteMovie(2l);
		// verifying that the method is called once
		verify(movieRepository, times(1)).findById(2l);
		// No movie should return with id 2
		when(movieRepository.findById(2l)).thenReturn(Optional.empty());
		// asserting that movie not found exception thrown
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			movieController.getMovieById(2l);
		});
	}

	@Test(expected = ResourceNotFoundException.class)
	public void deleteMoviesException() throws ResourceNotFoundException {
		when(movieRepository.findById(2l)).thenReturn(Optional.empty());
		movieController.deleteMovie(2l);
	}

}
