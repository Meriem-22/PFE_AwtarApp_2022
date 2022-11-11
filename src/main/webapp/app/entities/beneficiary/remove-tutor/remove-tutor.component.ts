import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBeneficiary } from '../beneficiary.model';
import { BeneficiaryService } from '../service/beneficiary.service';
@Component({
  selector: 'jhi-remove-tutor',
  templateUrl: './remove-tutor.component.html',
  styleUrls: ['./remove-tutor.component.scss'],
})
export class RemoveTutorComponent {
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
