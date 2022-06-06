import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchoolLevel } from '../school-level.model';
import { SchoolLevelService } from '../service/school-level.service';

@Component({
  templateUrl: './school-level-delete-dialog.component.html',
})
export class SchoolLevelDeleteDialogComponent {
  schoolLevel?: ISchoolLevel;

  constructor(protected schoolLevelService: SchoolLevelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schoolLevelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
