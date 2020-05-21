package com.java.api.restfulAPI.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="movies")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long movieId;
	
	@Column(name = "movie_title")
	private String title;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "star_rating")
	private String starRating;
	
	//Default Constructor
	public Movie() {
		super();
	}

	//parameterized Constructors
	public Movie(Long movieId, String title, String category, String starRating) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.category = category;
		this.starRating = starRating;
	}
	
	//Getters and Setters
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStarRating() {
		return starRating;
	}
	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}
	
	
}
