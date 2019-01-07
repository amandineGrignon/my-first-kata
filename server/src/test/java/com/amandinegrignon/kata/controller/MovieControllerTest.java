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
@PropertySource("test:application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MovieControllerTest {

    @Before
    public void setBaseUri() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost/api";
    }

    @Test
    @Sql("classpath:sql/Test_GetMovies_Empty.sql")
    public void testGetMovies_Aucun() {
        //@formatter:off
        get("/movies")
        .then()
                .statusCode(HttpStatus.OK.value())
        .body("isEmpty()", Matchers.is(true))
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_GetMovies.sql")
    public void testGetMovies_Plusieurs() {
        //@formatter:off
        get("/movies")
        .then()
                .statusCode(HttpStatus.OK.value())
        .body("$", Matchers.hasSize(3))
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_GetMovies.sql")
    public void testGetMovie_ResourceNotFound() {
        String idNoExist = "999";

        //@formatter:off
        get("/movies/" + idNoExist)
        .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_GetMovies.sql")
    public void testGetMovie_Succes() {
        String id = "1";

        //@formatter:off
        get("/movies/" + id)
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
    @Sql("classpath:sql/Test_GetMovies.sql")
    public void testSaveMovie_Succes() {
        Movie movie = new Movie();
        movie.setTitle("Thor");
        movie.setDescription("Sorti en 2011, le film suit les aventures de Thor, fils d’Odin, dépouillé de ses pouvoirs et exilé sur terre ...");
        movie.setDescription("Sorti en 2011, le film suit les aventures de Thor, fils d’Odin, dépouillé de ses pouvoirs et exilé sur terre ...");

        //@formatter:off
        with().body(movie)
                .contentType(ContentType.JSON)
        .post("")
        .then()
                .statusCode(HttpStatus.OK.value())
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_GetMovies.sql")
    public void testDeleteMovie_Succes() {
        String id = "3";

        //@formatter:off
        delete("/" + id)
        .then()
                .statusCode(HttpStatus.OK.value())
        ;
        //@formatter:on
    }

    @Test
    @Sql("classpath:sql/Test_GetMovies.sql")
    public void testDeleteMovie_ResourceNotFound() {
        String id = "999";

        //@formatter:off
        delete("/" + id)
        .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
        ;
        //@formatter:on
    }
}
