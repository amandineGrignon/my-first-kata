package com.amandinegrignon.kata.service;

import com.amandinegrignon.kata.dto.MovieDto;
import com.amandinegrignon.kata.exception.DatabaseException;
import com.amandinegrignon.kata.exception.ResourceNotFoundException;
import com.amandinegrignon.kata.model.Movie;

import java.util.List;

public interface MovieService {

    /**
     * Obtenir la liste des films
     * @return
     */
    List<MovieDto> getMovies();

    /**
     * Trouver un film
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    MovieDto findOne(Long id) throws ResourceNotFoundException;

    /**
     * Sauvegarder un film
     * @param movie
     * @return
     */
    MovieDto save(MovieDto movie);

    /**
     * Supprimer un film
     * @param id
     * @throws ResourceNotFoundException
     */
    void deleteOne(Long id) throws ResourceNotFoundException, DatabaseException;
}
