import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDonationDetails } from '../donation-details.model';
import { DonationDetailsService } from '../service/donation-details.service';

@Component({
  templateUrl: './donation-details-delete-dialog.component.html',
})
export class DonationDetailsDeleteDialogComponent {
  donationDetails?: IDonationDetails;

  constructor(protected donationDetailsService: DonationDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donationDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
