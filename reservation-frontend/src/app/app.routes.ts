import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import(
        './features/reservations/pages/reservation-list/reservation-list.component'
      ).then((m) => m.ReservationListComponent),
  },
  {
    path: 'reservations/new',
    loadComponent: () =>
      import(
        './features/reservations/pages/reservation-form/reservation-form.component'
      ).then((m) => m.ReservationFormComponent),
  },
];
