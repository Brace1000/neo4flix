import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Rating, MovieRecommendation } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  constructor(private http: HttpClient) {}

  createRating(rating: Rating): Observable<Rating> {
    return this.http.post<Rating>(`${environment.ratingServiceUrl}/ratings`, rating);
  }

  getUserRatings(userId: string): Observable<Rating[]> {
    return this.http.get<Rating[]>(`${environment.ratingServiceUrl}/ratings/user/${userId}`);
  }

  getMovieRatings(movieId: string): Observable<Rating[]> {
    return this.http.get<Rating[]>(`${environment.ratingServiceUrl}/ratings/movie/${movieId}`);
  }
}

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {
  constructor(private http: HttpClient) {}

  getRecommendations(userId: string, limit: number = 10): Observable<MovieRecommendation[]> {
    return this.http.get<MovieRecommendation[]>(
      `${environment.recommendationServiceUrl}/recommendations/user/${userId}?limit=${limit}`
    );
  }

  getRecommendationsByGenre(userId: string, genre: string, limit: number = 10): Observable<MovieRecommendation[]> {
    return this.http.get<MovieRecommendation[]>(
      `${environment.recommendationServiceUrl}/recommendations/user/${userId}/genre/${genre}?limit=${limit}`
    );
  }
}

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {
  constructor(private http: HttpClient) {}

  getWatchlist(userId: string): Observable<string[]> {
    return this.http.get<string[]>(`${environment.userServiceUrl}/users/${userId}/watchlist`);
  }

  addToWatchlist(userId: string, movieId: string): Observable<void> {
    return this.http.post<void>(`${environment.userServiceUrl}/users/${userId}/watchlist/${movieId}`, {});
  }

  removeFromWatchlist(userId: string, movieId: string): Observable<void> {
    return this.http.delete<void>(`${environment.userServiceUrl}/users/${userId}/watchlist/${movieId}`);
  }
}
