import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Movie } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  constructor(private http: HttpClient) {}

  getAllMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${environment.movieServiceUrl}/movies`);
  }

  getMovie(id: string): Observable<Movie> {
    return this.http.get<Movie>(`${environment.movieServiceUrl}/movies/${id}`);
  }

  searchMovies(query: string): Observable<Movie[]> {
    const params = new HttpParams().set('query', query);
    return this.http.get<Movie[]>(`${environment.movieServiceUrl}/movies/search`, { params });
  }

  searchByGenre(genre: string): Observable<Movie[]> {
    const params = new HttpParams().set('genre', genre);
    return this.http.get<Movie[]>(`${environment.movieServiceUrl}/movies/search/genre`, { params });
  }

  getTopRated(limit: number = 10): Observable<Movie[]> {
    const params = new HttpParams().set('limit', limit.toString());
    return this.http.get<Movie[]>(`${environment.movieServiceUrl}/movies/top-rated`, { params });
  }

  createMovie(movie: Movie): Observable<Movie> {
    return this.http.post<Movie>(`${environment.movieServiceUrl}/movies`, movie);
  }

  searchTMDB(query: string): Observable<any> {
    const params = new HttpParams().set('query', query);
    return this.http.get<any>(`${environment.movieServiceUrl}/movies/search/tmdb`, { params });
  }

  addFromTMDB(tmdbMovie: any): Observable<Movie> {
    return this.http.post<Movie>(`${environment.movieServiceUrl}/movies/add-from-tmdb`, tmdbMovie);
  }
}
