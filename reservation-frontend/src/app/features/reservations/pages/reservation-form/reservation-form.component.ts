import { HttpErrorResponse } from '@angular/common/http';
import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  inject,
  signal,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ToastService } from '../../../../core/services/toast.service';
import { CreateReservationRequest } from '../../../../model/reservation.model';
import { ReservationService } from '../../../../service/reservation.service';

/** Reactive form shape for creating a reservation. */
interface ReservationFormValue {
  customerName: string;
  date: string;
  time: string;
}

/**
 * Page component with a reactive form to create a reservation.
 */
@Component({
  selector: 'app-reservation-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './reservation-form.component.html',
  styleUrl: './reservation-form.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReservationFormComponent {
  private readonly formBuilder = inject(FormBuilder);
  private readonly reservationService = inject(ReservationService);
  private readonly toastService = inject(ToastService);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);

  protected readonly submitting = signal(false);

  protected readonly form = this.formBuilder.group({
    customerName: this.formBuilder.nonNullable.control('', Validators.required),
    date: this.formBuilder.nonNullable.control('', Validators.required),
    time: this.formBuilder.nonNullable.control('', Validators.required),
  });

  /**
   * Validates and submits the reservation form.
   */
  protected onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const request = this.buildRequest(this.form.getRawValue());
    this.submitting.set(true);

    this.reservationService
      .createReservation(request)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.submitting.set(false);
          this.router.navigate(['/']);
        },
        error: (error: HttpErrorResponse) => {
          this.submitting.set(false);
          this.toastService.showError(this.resolveErrorMessage(error));
        },
      });
  }

  /**
   * @param field form control name
   * @returns whether the field is invalid and was touched
   */
  protected isInvalid(field: keyof ReservationFormValue): boolean {
    const control = this.form.controls[field];
    return control.invalid && control.touched;
  }

  /**
   * Maps form values to the API request DTO.
   *
   * @param value raw form value
   * @returns create reservation request
   */
  private buildRequest(value: ReservationFormValue): CreateReservationRequest {
    return {
      customerName: value.customerName.trim(),
      date: value.date,
      time: value.time,
    };
  }

  /**
   * Extracts a user-facing error message from an HTTP error.
   *
   * @param error HTTP error response
   * @returns message for the toast
   */
  private resolveErrorMessage(error: HttpErrorResponse): string {
    if (typeof error.error === 'string' && error.error.length > 0) {
      return error.error;
    }

    if (
      error.error &&
      typeof error.error === 'object' &&
      'message' in error.error &&
      typeof error.error.message === 'string'
    ) {
      return error.error.message;
    }

    return 'No se pudo guardar la reserva. Inténtalo de nuevo.';
  }
}
