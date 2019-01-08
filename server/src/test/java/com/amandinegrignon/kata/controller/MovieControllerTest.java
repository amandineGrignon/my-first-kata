package com.amandinegrignon.kata.controller;

import com.amandinegrignon.kata.model.Movie;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.core.IsEqual.equalTo;

import static io.restassured.RestAssured.*;

@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MovieControllerTest {

    @Before
    public void setBaseUri() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    @Sql("classpath:sql/Test_movies_empty.sql")
    public void testGetMovies_Empty() {
        //@formatter:off
        get("/api/movies")
        .then()
                .statusCode(HttpStatus.OK.value())
        .body("isEmpty()", Matchers.is(true))
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testGetMovies_SeveralResults() {
        //@formatter:off
        get("/api/movies")
        .then()
                .statusCode(HttpStatus.OK.value())
        .body("$", Matchers.hasSize(3))
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testGetMovie_ResourceNotFound() {
        String idNoExist = "999";

        //@formatter:off
        get("/api/movies/" + idNoExist)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testGetMovie_Success() {
        String id = "1";

        //@formatter:off
        get("/api/movies/" + id)
        .then()
                .statusCode(HttpStatus.OK.value())
        .assertThat()
              .body("id", equalTo(1))
              .body("title", equalTo("Iron Man"))
              .body("description", equalTo("Tout commence en 2008 avec Iron Man où le Milliardaire Tony Stark, génie un poil mégalomane"))
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testSaveMovie_Success() {
        Movie movie = new Movie();
        movie.setTitle("Thor");
        movie.setDescription("Sorti en 2011, le film suit les aventures de Thor, fils d’Odin, dépouillé de ses pouvoirs et exilé sur terre ...");

        //@formatter:off
        with().body(movie)
                .contentType(ContentType.JSON)
        .post("/api")
        .then()
                .statusCode(HttpStatus.OK.value())
        .assertThat()
              .body("id", equalTo(4))
              .body("title", equalTo(movie.getTitle()))
              .body("description", equalTo(movie.getDescription()))
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testDeleteMovie_Success() {
        String id = "3";

        //@formatter:off
        delete("/api/" + id)
        .then()
                .statusCode(HttpStatus.OK.value())
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_movies.sql")
    public void testDeleteMovie_ResourceNotFound() {
        String id = "999";

        //@formatter:off
        delete("/api/" + id)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
        ;
        //@formatter:on
    }
}
