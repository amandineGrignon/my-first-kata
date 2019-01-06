package com.amandinegrignon.kata.service;

import com.amandinegrignon.kata.exception.DatabaseException;
import com.amandinegrignon.kata.exception.ResourceNotFoundException;
import com.amandinegrignon.kata.model.Movie;

import java.util.List;

public interface MovieService {

    /**
     * Obtenir la liste des films
     * @return
     */
    List<Movie> getMovies();

    /**
     * Trouver un film
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    Movie findOne(Long id) throws ResourceNotFoundException;

    /**
     * Sauvegarder un film
     * @param movie
     * @return
     */
    Movie save(Movie movie);

    /**
     * Supprimer un film
     * @param id
     * @throws ResourceNotFoundException
     */
    void deleteOne(Long id) throws ResourceNotFoundException, DatabaseException;
}
