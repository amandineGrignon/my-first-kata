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

  formErrors = {
    'title': '',
    'description': ''
  };

  validationMessages = {
    'title': {
      'required': 'Le titre est obligatoire.',
      'minlength': 'Le titre doit contenir au minimum 2 caractères',
      'maxlength': 'Le titre doit contenir au maximum 250 caractères.'
    },
    'description': {
      'maxlength': 'La description doit contenir au maximum 250 caractères.'
    }
   };

  constructor(private formBuilder: FormBuilder,
              private moviesService: MoviesService,
              private router: Router) { }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    // Initialisation du formulaire et des règles
    this.movieForm = this.formBuilder.group( {
      title: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(250)]],
      description: ['', Validators.maxLength(250)]
    });

    this.movieForm.valueChanges.subscribe(data => this.onValueChanged(data));
    this.onValueChanged();
  }

  onValueChanged(data?: any) {
    if (!this.movieForm) {
      return;
    }

    for (const field of Object.keys(this.formErrors)) {
      // Supprime les anciens messages d'erreurs
      this.formErrors[field] = '';
      const control = this.movieForm.get(field);

      if (control && control.dirty && !control.valid) {
        const messages = this.validationMessages[field];
        for (const key of Object.keys(control.errors)) {
          this.formErrors[field] += messages[key] + ' ';
        }
      }
    }
  }

  get title() { return this.movieForm.get('title'); }
  get description() { return this.movieForm.get('description'); }

  onSaveMovie() {
    if (this.movieForm.valid) {
      // Récupération des valeurs du formulaire
      const title = this.movieForm.get('title').value;
      const description = this.movieForm.get('description').value;
      console.log('createNewMovie Form title = ' + title + ', ' + description);

      // Création du nouveau film
      const newMovie = new Movie(title, description);

      this.createMovie(newMovie);
    }
  }

  createMovie(newMovie: Movie) {
    try {
      this.moviesService.createNewMovie(newMovie)
        .then(data => {
           console.log('Création du film réussie');

           // Redirection
           this.router.navigate(['/movies']);
        });
    } catch (e) {
      console.log(e, 'Exception lors de la création du film');
    }
  }
}
