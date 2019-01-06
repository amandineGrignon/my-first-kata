package com.amandinegrignon.kata.service.impl;

import com.amandinegrignon.kata.exception.DatabaseException;
import com.amandinegrignon.kata.exception.ResourceNotFoundException;
import com.amandinegrignon.kata.model.Movie;
import com.amandinegrignon.kata.repository.MovieRepository;
import com.amandinegrignon.kata.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findOne(Long id) throws ResourceNotFoundException {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        String msg = MessageFormat.format("Le film avec l''id {0} est introuvable.", id);
        return movieOptional.orElseThrow(() -> new ResourceNotFoundException(msg));
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteOne(Long id) throws ResourceNotFoundException, DatabaseException {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if (movieOptional.isPresent()) {
            try {
                movieRepository.delete(movieOptional.get());
            } catch (Exception ex) {
                String msg = MessageFormat.format("Une exception a été rencontrée lors de la suppression du film avec l''id {0}.", id);
                throw new DatabaseException(msg, ex);
            }
        } else {
            String msg = MessageFormat.format("Le film avec l''id {0} est introuvable.", id);
            throw new ResourceNotFoundException(msg);
        }
    }
}
