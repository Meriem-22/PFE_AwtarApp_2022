import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBeneficiary } from '../beneficiary.model';
import { BeneficiaryService } from '../service/beneficiary.service';
@Component({
  selector: 'jhi-remove-authorizing-officer',
  templateUrl: './remove-authorizing-officer.component.html',
  styleUrls: ['./remove-authorizing-officer.component.scss'],
})
export class RemoveAuthorizingOfficerComponent {
  beneficiary?: IBeneficiary;

  constructor(protected beneficiaryService: BeneficiaryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmRemove(beneficiary: IBeneficiary): void {
    this.beneficiaryService.removeBeneficiaryAuthorizingOfficer(beneficiary).subscribe(() => {
      this.activeModal.close('removed');
    });
  }
}
