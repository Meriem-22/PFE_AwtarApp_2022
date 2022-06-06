import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatusOfHealth } from '../status-of-health.model';
import { StatusOfHealthService } from '../service/status-of-health.service';

@Component({
  templateUrl: './status-of-health-delete-dialog.component.html',
})
export class StatusOfHealthDeleteDialogComponent {
  statusOfHealth?: IStatusOfHealth;

  constructor(protected statusOfHealthService: StatusOfHealthService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statusOfHealthService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
