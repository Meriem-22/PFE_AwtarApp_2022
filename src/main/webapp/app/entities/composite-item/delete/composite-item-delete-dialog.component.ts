import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompositeItem } from '../composite-item.model';
import { CompositeItemService } from '../service/composite-item.service';

@Component({
  templateUrl: './composite-item-delete-dialog.component.html',
})
export class CompositeItemDeleteDialogComponent {
  compositeItem?: ICompositeItem;

  constructor(protected compositeItemService: CompositeItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.compositeItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
