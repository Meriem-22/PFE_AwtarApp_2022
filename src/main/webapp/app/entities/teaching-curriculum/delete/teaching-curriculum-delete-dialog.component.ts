import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeachingCurriculum } from '../teaching-curriculum.model';
import { TeachingCurriculumService } from '../service/teaching-curriculum.service';

@Component({
  templateUrl: './teaching-curriculum-delete-dialog.component.html',
})
export class TeachingCurriculumDeleteDialogComponent {
  teachingCurriculum?: ITeachingCurriculum;

  constructor(protected teachingCurriculumService: TeachingCurriculumService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.teachingCurriculumService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
