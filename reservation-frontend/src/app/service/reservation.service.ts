import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  CreateReservationRequest,
  ReservationResponse,
} from '../model/reservation.model';

/**
 * HTTP client for reservation API operations.
 */
@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/reservations';

  /**
   * Returns all reservations from the backend.
   *
   * @returns observable list of reservations
   */
  getAllReservations(): Observable<ReservationResponse[]> {
    return this.http.get<ReservationResponse[]>(this.baseUrl);
  }

  /**
   * Creates a new reservation.
   *
   * @param request reservation data
   * @returns observable created reservation
   */
  createReservation(
    request: CreateReservationRequest,
  ): Observable<ReservationResponse> {
    return this.http.post<ReservationResponse>(this.baseUrl, request);
  }

  /**
   * Cancels a reservation by id.
   *
   * @param id reservation identifier
   * @returns observable updated reservation
   */
  cancelReservation(id: number): Observable<ReservationResponse> {
    return this.http.delete<ReservationResponse>(`${this.baseUrl}/${id}`);
  }
}
