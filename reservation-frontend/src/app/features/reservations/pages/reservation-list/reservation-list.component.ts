import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  inject,
  signal,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { RouterLink } from '@angular/router';
import {
  ReservationResponse,
  ReservationStatus,
} from '../../../../model/reservation.model';
import { ReservationService } from '../../../../service/reservation.service';

/**
 * Page component that lists reservations and allows cancellation.
 */
@Component({
  selector: 'app-reservation-list',
  imports: [RouterLink],
  templateUrl: './reservation-list.component.html',
  styleUrl: './reservation-list.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReservationListComponent {
  private readonly reservationService = inject(ReservationService);
  private readonly destroyRef = inject(DestroyRef);

  protected readonly reservations = signal<ReservationResponse[]>([]);
  protected readonly loading = signal(true);
  protected readonly errorMessage = signal<string | null>(null);
  protected readonly cancellingId = signal<number | null>(null);
  protected readonly reservationStatus = ReservationStatus;

  constructor() {
    this.loadReservations();
  }

  /**
   * Loads reservations from the API.
   */
  protected loadReservations(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    this.reservationService
      .getAllReservations()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (reservations) => {
          this.reservations.set(reservations);
          this.loading.set(false);
        },
        error: () => {
          this.errorMessage.set('Could not load reservations.');
          this.loading.set(false);
        },
      });
  }

  /**
   * Cancels a reservation and updates the local list.
   *
   * @param id reservation identifier
   */
  protected cancelReservation(id: number): void {
    if (this.cancellingId() !== null) {
      return;
    }

    this.cancellingId.set(id);
    this.errorMessage.set(null);

    this.reservationService
      .cancelReservation(id)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (updatedReservation) => {
          this.reservations.update((current) =>
            current.map((reservation) =>
              reservation.id === updatedReservation.id
                ? updatedReservation
                : reservation,
            ),
          );
          this.cancellingId.set(null);
        },
        error: () => {
          this.errorMessage.set('Could not cancel the reservation.');
          this.cancellingId.set(null);
        },
      });
  }

  /**
   * @param reservation reservation to evaluate
   * @returns whether the cancel action is available
   */
  protected canCancel(reservation: ReservationResponse): boolean {
    return reservation.status !== ReservationStatus.Cancelled;
  }

  /**
   * @param id reservation identifier under cancellation
   * @returns whether the reservation is being cancelled
   */
  protected isCancelling(id: number): boolean {
    return this.cancellingId() === id;
  }
}
