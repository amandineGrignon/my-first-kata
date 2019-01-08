package com.amandinegrignon.kata.controller;

import com.amandinegrignon.kata.dto.MovieDto;
import com.amandinegrignon.kata.exception.InvalidObjectException;
import com.amandinegrignon.kata.service.MovieService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin //(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Récupérer tous les films
     *
     * @return List<Movie>
     */
    @GetMapping({"", "/"})
    public List<MovieDto> getMovies() {
        return movieService.getMovies();
    }

    /**
     * Récupérer un film
     *
     * @param id
     * @return Movie
     */
    @GetMapping("/{id}")
    public MovieDto getMovie(@PathVariable Long id) {
        return movieService.findOne(id);
    }

    /**
     * Créer un film
     *
     * @param movieDto
     * @return
     */
    @PostMapping({"", "/"})
    public MovieDto save(@Valid @RequestBody MovieDto movieDto, BindingResult result) {
        if (!result.hasErrors()) {
            return movieService.save(movieDto);
        } else {
            throw new InvalidObjectException(result.getAllErrors().toString());
        }
    }

    /**
     * Supprimer un film
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public boolean delete(@PathVariable Long id) {
        movieService.deleteOne(id);
        return true;
    }
}
