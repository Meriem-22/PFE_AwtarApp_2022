import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DonationsIssuedComponent } from './list/donations-issued.component';
import { DonationsIssuedDetailComponent } from './detail/donations-issued-detail.component';
import { DonationsIssuedUpdateComponent } from './update/donations-issued-update.component';
import { DonationsIssuedDeleteDialogComponent } from './delete/donations-issued-delete-dialog.component';
import { DonationsIssuedRoutingModule } from './route/donations-issued-routing.module';

@NgModule({
  imports: [SharedModule, DonationsIssuedRoutingModule],
  declarations: [
    DonationsIssuedComponent,
    DonationsIssuedDetailComponent,
    DonationsIssuedUpdateComponent,
    DonationsIssuedDeleteDialogComponent,
  ],
  entryComponents: [DonationsIssuedDeleteDialogComponent],
})
export class DonationsIssuedModule {}
