import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DonationDetailsComponent } from './list/donation-details.component';
import { DonationDetailsDetailComponent } from './detail/donation-details-detail.component';
import { DonationDetailsUpdateComponent } from './update/donation-details-update.component';
import { DonationDetailsDeleteDialogComponent } from './delete/donation-details-delete-dialog.component';
import { DonationDetailsRoutingModule } from './route/donation-details-routing.module';

@NgModule({
  imports: [SharedModule, DonationDetailsRoutingModule],
  declarations: [
    DonationDetailsComponent,
    DonationDetailsDetailComponent,
    DonationDetailsUpdateComponent,
    DonationDetailsDeleteDialogComponent,
  ],
  entryComponents: [DonationDetailsDeleteDialogComponent],
})
export class DonationDetailsModule {}
