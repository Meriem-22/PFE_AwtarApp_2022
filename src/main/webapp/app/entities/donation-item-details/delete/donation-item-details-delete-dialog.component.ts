import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDonationItemDetails } from '../donation-item-details.model';
import { DonationItemDetailsService } from '../service/donation-item-details.service';

@Component({
  templateUrl: './donation-item-details-delete-dialog.component.html',
})
export class DonationItemDetailsDeleteDialogComponent {
  donationItemDetails?: IDonationItemDetails;

  constructor(protected donationItemDetailsService: DonationItemDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donationItemDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
