import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHealthStatusCategory } from '../health-status-category.model';
import { HealthStatusCategoryService } from '../service/health-status-category.service';

@Component({
  templateUrl: './health-status-category-delete-dialog.component.html',
})
export class HealthStatusCategoryDeleteDialogComponent {
  healthStatusCategory?: IHealthStatusCategory;

  constructor(protected healthStatusCategoryService: HealthStatusCategoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.healthStatusCategoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
