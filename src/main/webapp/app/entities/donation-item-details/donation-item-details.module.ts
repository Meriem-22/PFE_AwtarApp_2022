import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DonationItemDetailsComponent } from './list/donation-item-details.component';
import { DonationItemDetailsDetailComponent } from './detail/donation-item-details-detail.component';
import { DonationItemDetailsUpdateComponent } from './update/donation-item-details-update.component';
import { DonationItemDetailsDeleteDialogComponent } from './delete/donation-item-details-delete-dialog.component';
import { DonationItemDetailsRoutingModule } from './route/donation-item-details-routing.module';

@NgModule({
  imports: [SharedModule, DonationItemDetailsRoutingModule],
  declarations: [
    DonationItemDetailsComponent,
    DonationItemDetailsDetailComponent,
    DonationItemDetailsUpdateComponent,
    DonationItemDetailsDeleteDialogComponent,
  ],
  entryComponents: [DonationItemDetailsDeleteDialogComponent],
})
export class DonationItemDetailsModule {}
