import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Movie } from './../models/movie.model';
import { MoviesService } from '../services/movies.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.scss']
})
export class MovieListComponent implements OnInit, OnDestroy {

  movies: Movie[];
  movieSubscription: Subscription;

  constructor(private moviesService: MoviesService, private router: Router) { }

  ngOnInit() {
    this.movieSubscription = this.moviesService.movieSubject.subscribe(
        (movies: Movie[]) => {
          this.movies = movies;
        }
    );

    // Afficher la liste de films
    this.moviesService.getMovies();
    this.moviesService.emitMovies();
  }

  // Redirection vers la route de création de film
  onNewMovie() {
    this.router.navigate(['movies', 'new']);
  }

  onDeleteMovie(movie: Movie) {
    this.moviesService.removeMovie(movie);
  }

  onViewMovie(id: number) {
    this.router.navigate(['/movies', 'view', id]);
  }

  ngOnDestroy() {
    this.movieSubscription.unsubscribe();
  }
}
