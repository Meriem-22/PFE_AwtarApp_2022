import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChildStatusItem } from '../child-status-item.model';
import { ChildStatusItemService } from '../service/child-status-item.service';

@Component({
  templateUrl: './child-status-item-delete-dialog.component.html',
})
export class ChildStatusItemDeleteDialogComponent {
  childStatusItem?: IChildStatusItem;

  constructor(protected childStatusItemService: ChildStatusItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.childStatusItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
