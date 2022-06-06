import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBeneficiary } from '../beneficiary.model';
import { BeneficiaryService } from '../service/beneficiary.service';

@Component({
  templateUrl: './beneficiary-delete-dialog.component.html',
})
export class BeneficiaryDeleteDialogComponent {
  beneficiary?: IBeneficiary;

  constructor(protected beneficiaryService: BeneficiaryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.beneficiaryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
