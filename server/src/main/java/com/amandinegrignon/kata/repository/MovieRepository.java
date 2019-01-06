package com.amandinegrignon.kata.repository;

import com.amandinegrignon.kata.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
