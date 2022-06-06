import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuthorizingOfficer } from '../authorizing-officer.model';
import { AuthorizingOfficerService } from '../service/authorizing-officer.service';

@Component({
  templateUrl: './authorizing-officer-delete-dialog.component.html',
})
export class AuthorizingOfficerDeleteDialogComponent {
  authorizingOfficer?: IAuthorizingOfficer;

  constructor(protected authorizingOfficerService: AuthorizingOfficerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.authorizingOfficerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
