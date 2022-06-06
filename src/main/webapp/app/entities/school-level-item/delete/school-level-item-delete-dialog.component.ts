import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchoolLevelItem } from '../school-level-item.model';
import { SchoolLevelItemService } from '../service/school-level-item.service';

@Component({
  templateUrl: './school-level-item-delete-dialog.component.html',
})
export class SchoolLevelItemDeleteDialogComponent {
  schoolLevelItem?: ISchoolLevelItem;

  constructor(protected schoolLevelItemService: SchoolLevelItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schoolLevelItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
