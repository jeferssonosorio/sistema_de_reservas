import { Injectable, signal } from '@angular/core';

/** Toast notification types. */
export type ToastType = 'error' | 'success' | 'info';

/** Single toast notification. */
export interface ToastMessage {
  id: number;
  message: string;
  type: ToastType;
}

const DEFAULT_DURATION_MS = 5000;

/**
 * Manages global toast notifications.
 */
@Injectable({
  providedIn: 'root',
})
export class ToastService {
  private nextId = 0;

  /** Active toast messages. */
  readonly toasts = signal<ToastMessage[]>([]);

  /**
   * Shows an error toast.
   *
   * @param message error text to display
   */
  showError(message: string): void {
    this.show(message, 'error');
  }

  /**
   * Removes a toast by id.
   *
   * @param id toast identifier
   */
  dismiss(id: number): void {
    this.toasts.update((current) => current.filter((toast) => toast.id !== id));
  }

  /**
   * Shows a toast and auto-dismisses it after a delay.
   *
   * @param message text to display
   * @param type toast category
   */
  private show(message: string, type: ToastType): void {
    const id = ++this.nextId;

    this.toasts.update((current) => [...current, { id, message, type }]);

    setTimeout(() => this.dismiss(id), DEFAULT_DURATION_MS);
  }
}
