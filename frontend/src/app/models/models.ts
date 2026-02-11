export interface User {
  id: string;
  username: string;
  email: string;
  twoFactorEnabled: boolean;
}

export interface Movie {
  id: string;
  title: string;
  description: string;
  releaseDate: string;
  genres: string[];
  posterUrl: string;
  averageRating: number;
  ratingCount: number;
}

export interface Rating {
  id?: string;
  userId: string;
  movieId: string;
  score: number;
  review?: string;
  createdAt?: string;
}

export interface MovieRecommendation extends Movie {
  relevanceScore: number;
}

export interface AuthResponse {
  token: string;
  userId: string;
  username: string;
  twoFactorEnabled: boolean;
  qrCode?: string;
}

export interface LoginRequest {
  username: string;
  password: string;
  twoFactorCode?: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}
