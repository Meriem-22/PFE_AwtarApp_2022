import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEducationalInstitution } from '../educational-institution.model';
import { EducationalInstitutionService } from '../service/educational-institution.service';

@Component({
  templateUrl: './educational-institution-delete-dialog.component.html',
})
export class EducationalInstitutionDeleteDialogComponent {
  educationalInstitution?: IEducationalInstitution;

  constructor(protected educationalInstitutionService: EducationalInstitutionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.educationalInstitutionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
