import { Component, OnInit } from '@angular/core';
import { Movie } from './../../models/movie.model';
import { ActivatedRoute, Router } from '@angular/router';
import { MoviesService } from './../../services/movies.service';
import { NumberValueAccessor } from '@angular/forms/src/directives';

@Component({
  selector: 'app-single-movie',
  templateUrl: './single-movie.component.html',
  styleUrls: ['./single-movie.component.scss']
})
export class SingleMovieComponent implements OnInit {

  movie: any;
  displaySpinner = false;

  constructor(private route: ActivatedRoute,
              private moviesService: MoviesService,
              private router: Router) { }

  ngOnInit() {
    // Création d'un film vide temporaire, le temps de récupérer celui du serveur
    this.movie = new Movie('', '');

    // Récupération de l'id dans la route
    const id = this.route.snapshot.params['id'];
    console.log('id movie: ' + id);

    // Récupération du film depuis le serveur
    this.getMovie(id);
  }

  getMovie(id: number) {
    this.displaySpinner = true;

    try {
      this.moviesService.getSingleMovie(id).then(
        data => {
         this.displaySpinner = false;
         console.log(data, 'Récupération du film');
         this.movie = data;
       });
     } catch (e) {
        this.displaySpinner = false;
        console.log(e, 'Exception lors de la récupération du film');
     }
  }

  onBack() {
    this.router.navigate(['/movies']);
  }

}
