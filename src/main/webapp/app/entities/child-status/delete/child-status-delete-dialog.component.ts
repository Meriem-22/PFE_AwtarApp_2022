import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChildStatus } from '../child-status.model';
import { ChildStatusService } from '../service/child-status.service';

@Component({
  templateUrl: './child-status-delete-dialog.component.html',
})
export class ChildStatusDeleteDialogComponent {
  childStatus?: IChildStatus;

  constructor(protected childStatusService: ChildStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.childStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
