import { Injectable } from '@angular/core';
import { Movie } from './../models/movie.model';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class MoviesService {

  movies: Movie[] = [];
  movieSubject = new Subject<Movie[]>();

  constructor() { }

  emitMovies() {
    this.movieSubject.next(this.movies);
  }

  getMovies() {

  }

  saveMovies() {

  }

  getSingleMovie(id: number) {
  }

  createNewMovie(newMovie: Movie) {

  }

  removeMovie(movie: Movie) {
  }
}
