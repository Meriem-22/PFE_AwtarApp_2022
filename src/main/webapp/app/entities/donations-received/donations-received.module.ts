import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DonationsReceivedComponent } from './list/donations-received.component';
import { DonationsReceivedDetailComponent } from './detail/donations-received-detail.component';
import { DonationsReceivedUpdateComponent } from './update/donations-received-update.component';
import { DonationsReceivedDeleteDialogComponent } from './delete/donations-received-delete-dialog.component';
import { DonationsReceivedRoutingModule } from './route/donations-received-routing.module';

@NgModule({
  imports: [SharedModule, DonationsReceivedRoutingModule],
  declarations: [
    DonationsReceivedComponent,
    DonationsReceivedDetailComponent,
    DonationsReceivedUpdateComponent,
    DonationsReceivedDeleteDialogComponent,
  ],
  entryComponents: [DonationsReceivedDeleteDialogComponent],
})
export class DonationsReceivedModule {}
