import { Component, OnInit, OnDestroy } from '@angular/core';
import { Movie } from './../models/movie.model';
import { MoviesService } from '../services/movies.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.scss']
})
export class MovieListComponent implements OnInit {

  displaySpinner = false;
  movies: any = [];

  constructor(private moviesService: MoviesService,
              private router: Router) { }

  ngOnInit() {
    this.displaySpinner = true;
    this.getMovies();
  }

  getMovies() {
    try {
      this.moviesService.getMovies().then(data => {
        this.displaySpinner = false;
        console.log('Succès lors de la récupération des films');
        this.movies = data;
      });
    } catch (e) {
      console.log(e, 'Exception lors de la récupération des films');
    }
  }

  // Redirection vers la route de création de film
  onNewMovie() {
    this.router.navigate(['movies', 'new']);
  }

  onDeleteMovie(movie: Movie) {
    if (movie !== undefined && confirm('Voulez-vous supprimer le film ' + movie.title + ' ?')) {
      this.deleteMovie(movie.id);
    }
  }

  deleteMovie(id: number) {
    try {
      this.displaySpinner = true;

      this.moviesService.removeMovie(id)
        .then(data => {
          this.displaySpinner = false;
          console.log(data, 'Succès lors de la suppression du film');

          const index = this.movies.indexOf(data);
          this.movies.splice(index, 1);
        });
    } catch (e) {
      this.displaySpinner = false;
      console.log(e, 'Exception lors de la suppression du film');
    }
  }

  onViewMovie(id: number) {
    this.router.navigate(['/movies', 'view', id]);
  }
}
