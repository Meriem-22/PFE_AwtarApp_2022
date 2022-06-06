import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IItemValue } from '../item-value.model';
import { ItemValueService } from '../service/item-value.service';

@Component({
  templateUrl: './item-value-delete-dialog.component.html',
})
export class ItemValueDeleteDialogComponent {
  itemValue?: IItemValue;

  constructor(protected itemValueService: ItemValueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemValueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
