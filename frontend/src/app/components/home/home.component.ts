import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/models';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  movies: Movie[] = [];
  filteredMovies: Movie[] = [];
  searchQuery = '';
  selectedGenre = '';
  genres = ['Action', 'Comedy', 'Drama', 'Horror', 'Sci-Fi', 'Romance', 'Thriller', 'Documentary'];
  loading = true;

  constructor(private movieService: MovieService, private router: Router) {}

  ngOnInit(): void {
    this.loadMovies();
  }

  loadMovies(): void {
    this.movieService.getAllMovies().subscribe({
      next: (movies) => {
        this.movies = movies;
        this.filteredMovies = movies;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  onSearch(): void {
    if (this.searchQuery.trim()) {
      this.loading = true;
      // Search both local database and TMDB
      this.movieService.searchMovies(this.searchQuery).subscribe({
        next: (localMovies) => {
          // Also search TMDB
          this.movieService.searchTMDB(this.searchQuery).subscribe({
            next: (tmdbResponse) => {
              const tmdbMovies = tmdbResponse.results || [];
              // Convert TMDB movies to our format
              const convertedTmdbMovies = tmdbMovies.slice(0, 10).map((m: any) => ({
                id: String(m.id),
                title: m.title,
                description: m.overview,
                releaseDate: m.release_date,
                genres: this.mapGenres(m.genre_ids || []),
                posterUrl: m.poster_path ? `https://image.tmdb.org/t/p/w500${m.poster_path}` : '',
                averageRating: m.vote_average ? m.vote_average / 2 : 0,
                ratingCount: 0,
                fromTMDB: true
              }));
              // Combine local and TMDB results
              this.filteredMovies = [...localMovies, ...convertedTmdbMovies];
              this.loading = false;
            },
            error: () => {
              this.filteredMovies = localMovies;
              this.loading = false;
            }
          });
        },
        error: () => {
          this.loading = false;
        }
      });
    } else {
      this.filteredMovies = this.movies;
    }
  }

  mapGenres(genreIds: number[]): string[] {
    const genreMap: {[key: number]: string} = {
      28: 'Action', 12: 'Adventure', 16: 'Animation', 35: 'Comedy',
      80: 'Crime', 99: 'Documentary', 18: 'Drama', 10751: 'Family',
      14: 'Fantasy', 36: 'History', 27: 'Horror', 10402: 'Music',
      9648: 'Mystery', 10749: 'Romance', 878: 'Sci-Fi', 53: 'Thriller',
      10752: 'War', 37: 'Western'
    };
    return genreIds.slice(0, 3).map(id => genreMap[id] || 'Unknown');
  }

  filterByGenre(): void {
    if (this.selectedGenre) {
      this.movieService.searchByGenre(this.selectedGenre).subscribe({
        next: (movies) => {
          this.filteredMovies = movies;
        }
      });
    } else {
      this.filteredMovies = this.movies;
    }
  }

  viewMovie(id: string): void {
    const movie = this.filteredMovies.find(m => m.id === id);
    if (movie && (movie as any).fromTMDB) {
      // Add movie from TMDB first
      this.movieService.addFromTMDB(movie).subscribe({
        next: (addedMovie) => {
          this.router.navigate(['/movie', addedMovie.id]);
        },
        error: () => {
          alert('Failed to add movie. Please try again.');
        }
      });
    } else {
      this.router.navigate(['/movie', id]);
    }
  }
}
