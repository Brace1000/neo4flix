import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { MovieService } from '../../services/movie.service';
import { RatingService, WatchlistService } from '../../services/services';
import { AuthService } from '../../services/auth.service';
import { Movie, Rating } from '../../models/models';

@Component({
  selector: 'app-movie-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './movie-detail.component.html'
})
export class MovieDetailComponent implements OnInit {
  movie: Movie | null = null;
  userRating: number = 0;
  userReview: string = '';
  loading = true;
  inWatchlist = false;

  constructor(
    private route: ActivatedRoute,
    private movieService: MovieService,
    private ratingService: RatingService,
    private watchlistService: WatchlistService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadMovie(id);
      this.checkWatchlist(id);
    }
  }

  loadMovie(id: string): void {
    this.movieService.getMovie(id).subscribe({
      next: (movie) => {
        this.movie = movie;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  checkWatchlist(movieId: string): void {
    const user = this.authService.getCurrentUser();
    if (user) {
      this.watchlistService.getWatchlist(user.id).subscribe({
        next: (watchlist) => {
          this.inWatchlist = watchlist.includes(movieId);
        }
      });
    }
  }

  submitRating(): void {
    const user = this.authService.getCurrentUser();
    if (user && this.movie && this.userRating > 0) {
      const rating: Rating = {
        userId: user.id,
        movieId: this.movie.id,
        score: this.userRating,
        review: this.userReview
      };
      
      this.ratingService.createRating(rating).subscribe({
        next: () => {
          alert('✅ Rating submitted successfully!');
          this.userRating = 0;
          this.userReview = '';
          this.loadMovie(this.movie!.id);
        },
        error: (err) => {
          console.error('Rating error:', err);
          alert('❌ Failed to submit rating. Please try again.');
        }
      });
    } else if (this.userRating === 0) {
      alert('⚠️ Please select a rating (1-5 stars)');
    }
  }

  toggleWatchlist(): void {
    const user = this.authService.getCurrentUser();
    if (user && this.movie) {
      if (this.inWatchlist) {
        this.watchlistService.removeFromWatchlist(user.id, this.movie.id).subscribe({
          next: () => {
            this.inWatchlist = false;
            alert('✅ Removed from watchlist');
          },
          error: (err) => {
            console.error('Remove from watchlist error:', err);
            alert('❌ Failed to remove from watchlist');
          }
        });
      } else {
        this.watchlistService.addToWatchlist(user.id, this.movie.id).subscribe({
          next: () => {
            this.inWatchlist = true;
            alert('✅ Added to watchlist!');
          },
          error: (err) => {
            console.error('Add to watchlist error:', err);
            alert('❌ Failed to add to watchlist');
          }
        });
      }
    } else {
      alert('⚠️ Please login to use watchlist');
    }
  }

  setRating(rating: number): void {
    this.userRating = rating;
  }
}
