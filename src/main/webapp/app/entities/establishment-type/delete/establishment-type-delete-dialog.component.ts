import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstablishmentType } from '../establishment-type.model';
import { EstablishmentTypeService } from '../service/establishment-type.service';

@Component({
  templateUrl: './establishment-type-delete-dialog.component.html',
})
export class EstablishmentTypeDeleteDialogComponent {
  establishmentType?: IEstablishmentType;

  constructor(protected establishmentTypeService: EstablishmentTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.establishmentTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
