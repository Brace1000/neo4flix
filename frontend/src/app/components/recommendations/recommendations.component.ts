import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { RecommendationService } from '../../services/services';
import { AuthService } from '../../services/auth.service';
import { MovieRecommendation } from '../../models/models';

@Component({
  selector: 'app-recommendations',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './recommendations.component.html'
})
export class RecommendationsComponent implements OnInit {
  recommendations: MovieRecommendation[] = [];
  selectedGenre = '';
  genres = ['Action', 'Comedy', 'Drama', 'Horror', 'Sci-Fi', 'Romance', 'Thriller', 'Documentary'];
  loading = true;

  constructor(
    private recommendationService: RecommendationService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadRecommendations();
  }

  loadRecommendations(): void {
    const user = this.authService.getCurrentUser();
    if (user) {
      this.recommendationService.getRecommendations(user.id, 20).subscribe({
        next: (recommendations) => {
          this.recommendations = recommendations;
          this.loading = false;
        },
        error: () => {
          this.loading = false;
        }
      });
    }
  }

  filterByGenre(): void {
    const user = this.authService.getCurrentUser();
    if (user) {
      if (this.selectedGenre) {
        this.recommendationService.getRecommendationsByGenre(user.id, this.selectedGenre, 20).subscribe({
          next: (recommendations) => {
            this.recommendations = recommendations;
          }
        });
      } else {
        this.loadRecommendations();
      }
    }
  }

  viewMovie(id: string): void {
    this.router.navigate(['/movie', id]);
  }
}
