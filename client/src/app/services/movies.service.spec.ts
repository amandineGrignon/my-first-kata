import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { MoviesService } from './movies.service';

describe('MoviesService', () => {
  let injector: TestBed;
  let service: MoviesService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MoviesService]
    });
    injector = getTestBed();
    service = injector.get(MoviesService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    // to make sure that there are no outstanding requests
    httpMock.verify();
  });

  describe('#getMovies', () => {
    it('should return an Promise<any> with movies', () => {
      const dummyMovies = [
        { title: 'Iron Man' },
        { title: 'Thor',
          description: 'Sorti en 2011, le film suit les aventures de Thor, fils d’Odin, dépouillé de ses pouvoirs et exilé sur terre ...'
        }
      ];

      service.getMovies().then(movies => {
        expect(movies.length).toBe(2);
        expect(movies).toEqual(dummyMovies);
      });

      const req = httpMock.expectOne(`${service.baseUrl}/movies`);
      expect(req.request.method).toBe('GET');
      req.flush(dummyMovies);
    });
  });

  describe('#getSingleMovie', () => {
    it('should return an Promise<any> with a movie', () => {
      const dummyMovie = [
        { title: 'Thor',
          description: 'Sorti en 2011, le film suit les aventures de Thor, fils d’Odin, dépouillé de ses pouvoirs et exilé sur terre ...'
        }
      ];

      const id = 1;

      service.getSingleMovie(id).then(movie => {
        expect(movie.length).toBe(1);
        expect(movie).toEqual(dummyMovie);
      });

      const req = httpMock.expectOne(`${service.baseUrl}/movies/${id}`);
      expect(req.request.method).toBe('GET');
      req.flush(dummyMovie);
    });
  });


  it('should be created', inject([MoviesService], (serviceM: MoviesService) => {
    expect(serviceM).toBeTruthy();
  }));
});
