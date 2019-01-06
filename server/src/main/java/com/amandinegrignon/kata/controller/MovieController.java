package com.amandinegrignon.kata.controller;

import com.amandinegrignon.kata.model.Movie;
import com.amandinegrignon.kata.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Récupérer tous les films
     *
     * @return List<Movie>
     */
    @GetMapping({"", "/"})
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    /**
     * Récupérer un film
     *
     * @param id
     * @return Movie
     */
    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable Long id) {
        return movieService.findOne(id);
    }

    /**
     * Créer un film
     *
     * @param movie
     * @return
     */
    @PostMapping({"", "/"})
    public Movie save(@RequestBody Movie movie) {
        return movieService.save(movie);
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
