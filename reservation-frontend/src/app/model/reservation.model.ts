export interface CreateReservationRequest {
  customerName: string;
  date: string;
  time: string;
}

export enum ReservationStatus {
  Active = 'ACTIVE',
  Pending = 'PENDING',
  Cancelled = 'CANCELLED',
  Completed = 'COMPLETED',
}

export interface ReservationResponse {
  id: number;
  customerName: string;
  date: string;
  time: string;
  status: ReservationStatus;
}
