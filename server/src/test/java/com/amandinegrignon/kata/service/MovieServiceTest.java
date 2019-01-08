package com.amandinegrignon.kata.service;

import com.amandinegrignon.kata.dto.MovieDto;
import com.amandinegrignon.kata.exception.ResourceNotFoundException;
import com.amandinegrignon.kata.model.Movie;
import com.amandinegrignon.kata.repository.MovieRepository;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.delete;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MovieServiceTest {

    @Autowired
    MovieService movieService;

    @Autowired
    MovieRepository movieRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testGetMovies_Success() {

        List<MovieDto> movies = movieService.getMovies();

        Assert.assertFalse(movies.isEmpty());
        Assert.assertEquals(3, movies.size());
    }

    @Test
    @Sql("classpath:sql/Test_movies_empty.sql")
    public void testGetMovies_Empty() {
        List<MovieDto> movies = movieService.getMovies();

        Assert.assertTrue(movies.isEmpty());
        Assert.assertEquals(0, movies.size());
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testGetMovie_ResourceNotFound() {
        Long idNoExist = 999L;

        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(JUnitMatchers.containsString("Le film avec l'id " + idNoExist + " est introuvable."));

        movieService.findOne(idNoExist);
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testGetMovie_Success() {
        Long id = 1l;

        MovieDto movie = movieService.findOne(id);

        Assert.assertNotNull(movie);
        Assert.assertEquals(movie.getId(), id);
        Assert.assertEquals(movie.getTitle(), "Iron Man");
        Assert.assertEquals(movie.getDescription(), "Tout commence en 2008 avec Iron Man où le Milliardaire Tony Stark, génie un poil mégalomane");
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testSaveMovie_Success() {
        Long newId = 4L;

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Thor");
        movieDto.setDescription("Sorti en 2011, le film suit les aventures de Thor, fils d’Odin, dépouillé de ses pouvoirs et exilé sur terre ...");

        MovieDto movie = movieService.save(movieDto);

        Assert.assertNotNull(movie);
        Assert.assertEquals(movie.getId(), newId);
        Assert.assertEquals(movie.getTitle(), movieDto.getTitle());
        Assert.assertEquals(movie.getDescription(), movieDto.getDescription());
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testDeleteMovie_Success() {
        Long id = 3L;

        List<Movie> moviesBefore = movieRepository.findAll();

        movieService.deleteOne(id);

        List<Movie> moviesAfter = movieRepository.findAll();

        Assert.assertEquals(3, moviesBefore.size());
        Assert.assertEquals(2, moviesAfter.size());
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testDeleteMovie_ResourceNotFound() {
        Long idNoExist = 999L;

        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(JUnitMatchers.containsString("Le film avec l'id " + idNoExist + " est introuvable."));

        movieService.deleteOne(idNoExist);
    }

}
