package com.amandinegrignon.kata.service.impl;

import com.amandinegrignon.kata.dto.FactoryDto;
import com.amandinegrignon.kata.dto.MovieDto;
import com.amandinegrignon.kata.exception.DatabaseException;
import com.amandinegrignon.kata.exception.ResourceNotFoundException;
import com.amandinegrignon.kata.model.Movie;
import com.amandinegrignon.kata.repository.MovieRepository;
import com.amandinegrignon.kata.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDto> getMovies() {
        List<Movie> movies = movieRepository.findAll();

        if (movies != null && !movies.isEmpty())
        {
            // Convertir en DTO
            return movies.stream().map(m -> FactoryDto.convertToDto(m)).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public MovieDto findOne(Long id) throws ResourceNotFoundException {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if (movieOptional.isPresent()) {
            return FactoryDto.convertToDto(movieOptional.get());
        } else {
            String msg = MessageFormat.format("Le film avec l''id {0} est introuvable.", id);
            throw new ResourceNotFoundException(msg);
        }
    }

    @Override
    public MovieDto save(MovieDto movieDto) {
        Movie movie = null;
        boolean isExist = false;

        if (movieDto.getId() != null) {
            Optional<Movie> movieOptional = movieRepository.findById(movieDto.getId());

            // Modifier
            if (movieOptional.isPresent()) {
                isExist = true;
                movie = movieOptional.get();
            }
        }

        if (!isExist) {
            // Créer
            movie = new Movie();
        }

        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());

        movie = movieRepository.save(movie);
        return FactoryDto.convertToDto(movie);
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
