import { Injectable } from '@angular/core';
import { Movie } from './../models/movie.model';
import { HttpClient } from '@angular/common/http';
import { Headers } from '@angular/http';
import { environment } from './../../environments/environment';

@Injectable()
export class MoviesService {

  private headers = new Headers( { 'Content-Type': 'application/json' } );
  movies: Movie[] = [];

  baseUrl = environment.urlServeur + '/api';

  constructor(private http: HttpClient) { }

  getMovies(): Promise<any> {
    return this.http.get(this.baseUrl + '/movies').toPromise()
        .then(response => response)
        .catch( this.handleError);
  }

  getSingleMovie(id: number): Promise<any> {
    return this.http.get(this.baseUrl + '/movies/' + id).toPromise()
        .then(response => response)
        .catch(this.handleError);
  }

  createNewMovie(newMovie: Movie): Promise<any> {
    return this.http.post(this.baseUrl + '/movies', newMovie).toPromise()
        .then(response => response)
        .catch(this.handleError);
  }

  removeMovie(id: number): Promise<any> {
    return this.http.delete(this.baseUrl + '/movies/' + id).toPromise()
        .then(response => response)
        .catch(this.handleError );
  }

  private handleError( error: any ): Promise<any> {
    console.error('Erreur lors de l\'appel http', error);
    return Promise.reject( error.message || error );
  }
}
