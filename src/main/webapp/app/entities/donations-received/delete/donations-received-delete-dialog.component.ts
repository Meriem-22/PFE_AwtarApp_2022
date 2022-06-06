import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDonationsReceived } from '../donations-received.model';
import { DonationsReceivedService } from '../service/donations-received.service';

@Component({
  templateUrl: './donations-received-delete-dialog.component.html',
})
export class DonationsReceivedDeleteDialogComponent {
  donationsReceived?: IDonationsReceived;

  constructor(protected donationsReceivedService: DonationsReceivedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donationsReceivedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
