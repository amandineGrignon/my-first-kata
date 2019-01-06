import { Component, OnInit } from '@angular/core';
import { Movie } from './../../models/movie.model';
import { ActivatedRoute, Router } from '@angular/router';
import { MoviesService } from './../../services/movies.service';

@Component({
  selector: 'app-single-movie',
  templateUrl: './single-movie.component.html',
  styleUrls: ['./single-movie.component.scss']
})
export class SingleMovieComponent implements OnInit {

  movie: Movie;

  constructor(private route: ActivatedRoute,
              private moviesService: MoviesService,
              private router: Router) { }

  ngOnInit() {
    // Création d'un film vide temporaire, le temps de récupérer celui du serveur
    this.movie = new Movie('', '');

    const id = this.route.snapshot.params['id'];

    console.log('id movie: ' + id);

    // +id pour le cast en tant que nombre
    // this.moviesService.getSingleMovie(+id).then(
    //  (movie: Movie) => {
    //    this.movie = movie;
    //  }
    // );
  }

  onBack() {
    this.router.navigate(['/movies']);
  }

}
