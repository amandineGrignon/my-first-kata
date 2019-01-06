import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MoviesService } from './../../services/movies.service';
import { Router } from '@angular/router';
import { Movie } from './../../models/movie.model';

@Component({
  selector: 'app-movie-form',
  templateUrl: './movie-form.component.html',
  styleUrls: ['./movie-form.component.scss']
})
export class MovieFormComponent implements OnInit {

  movieForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private moviesService: MoviesService,
              private router: Router) { }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    // Initialisation du formulaire et des règles
    this.movieForm = this.formBuilder.group( {
      title: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSaveMovie() {
    // Récupération des valeurs du formulaire
    const title = this.movieForm.get('title').value;
    const description = this.movieForm.get('description').value;

    // Création du nouveau film
    const newMovie = new Movie(title, description);
    console.log('newMovie = ' + newMovie);

    this.moviesService.createNewMovie(newMovie);

    // Redirection
    this.router.navigate(['/movies']);
  }
}
