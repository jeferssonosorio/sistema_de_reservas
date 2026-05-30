import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { ToastService } from '../../../core/services/toast.service';

/**
 * Presentational component that renders global toast notifications.
 */
@Component({
  selector: 'app-toast',
  imports: [],
  templateUrl: './toast.component.html',
  styleUrl: './toast.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ToastComponent {
  protected readonly toastService = inject(ToastService);

  /**
   * @param id toast identifier
   */
  protected dismiss(id: number): void {
    this.toastService.dismiss(id);
  }
}
