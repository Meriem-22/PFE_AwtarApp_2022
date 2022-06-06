import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDonationsReceivedItem } from '../donations-received-item.model';
import { DonationsReceivedItemService } from '../service/donations-received-item.service';

@Component({
  templateUrl: './donations-received-item-delete-dialog.component.html',
})
export class DonationsReceivedItemDeleteDialogComponent {
  donationsReceivedItem?: IDonationsReceivedItem;

  constructor(protected donationsReceivedItemService: DonationsReceivedItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donationsReceivedItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
