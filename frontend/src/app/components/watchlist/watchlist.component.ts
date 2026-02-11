import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { WatchlistService } from '../../services/services';
import { MovieService } from '../../services/movie.service';
import { AuthService } from '../../services/auth.service';
import { Movie } from '../../models/models';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-watchlist',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './watchlist.component.html'
})
export class WatchlistComponent implements OnInit {
  movies: Movie[] = [];
  loading = true;

  constructor(
    private watchlistService: WatchlistService,
    private movieService: MovieService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadWatchlist();
  }

  loadWatchlist(): void {
    const user = this.authService.getCurrentUser();
    if (user) {
      this.watchlistService.getWatchlist(user.id).subscribe({
        next: (movieIds) => {
          if (movieIds.length > 0) {
            const movieRequests = movieIds.map(id => this.movieService.getMovie(id));
            forkJoin(movieRequests).subscribe({
              next: (movies) => {
                this.movies = movies;
                this.loading = false;
              },
              error: () => {
                this.loading = false;
              }
            });
          } else {
            this.loading = false;
          }
        },
        error: () => {
          this.loading = false;
        }
      });
    }
  }

  removeFromWatchlist(movieId: string): void {
    const user = this.authService.getCurrentUser();
    if (user) {
      this.watchlistService.removeFromWatchlist(user.id, movieId).subscribe({
        next: () => {
          this.movies = this.movies.filter(m => m.id !== movieId);
        }
      });
    }
  }

  viewMovie(id: string): void {
    this.router.navigate(['/movie', id]);
  }
}
