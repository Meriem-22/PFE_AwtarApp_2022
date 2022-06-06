import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDonationsIssued } from '../donations-issued.model';
import { DonationsIssuedService } from '../service/donations-issued.service';

@Component({
  templateUrl: './donations-issued-delete-dialog.component.html',
})
export class DonationsIssuedDeleteDialogComponent {
  donationsIssued?: IDonationsIssued;

  constructor(protected donationsIssuedService: DonationsIssuedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donationsIssuedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
