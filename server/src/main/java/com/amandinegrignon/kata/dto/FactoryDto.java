package com.amandinegrignon.kata.dto;

import com.amandinegrignon.kata.model.Movie;

public final class FactoryDto {

    public static Movie convertToModel(MovieDto movieDto) {
        Movie movie = new Movie();

        movie.setId(movieDto.getId());
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());

        return movie;
    }

    public static MovieDto convertToDto(Movie movie) {
        MovieDto movieDto = new MovieDto();

        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDescription(movie.getDescription());

        return movieDto;
    }

}
