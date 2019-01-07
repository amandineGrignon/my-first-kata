package com.amandinegrignon.kata.controller;

import com.amandinegrignon.kata.dto.MovieDto;
import com.amandinegrignon.kata.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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
    public MovieDto save(@RequestBody MovieDto movieDto) {
        return movieService.save(movieDto);
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
