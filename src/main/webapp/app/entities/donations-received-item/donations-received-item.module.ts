import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DonationsReceivedItemComponent } from './list/donations-received-item.component';
import { DonationsReceivedItemDetailComponent } from './detail/donations-received-item-detail.component';
import { DonationsReceivedItemUpdateComponent } from './update/donations-received-item-update.component';
import { DonationsReceivedItemDeleteDialogComponent } from './delete/donations-received-item-delete-dialog.component';
import { DonationsReceivedItemRoutingModule } from './route/donations-received-item-routing.module';

@NgModule({
  imports: [SharedModule, DonationsReceivedItemRoutingModule],
  declarations: [
    DonationsReceivedItemComponent,
    DonationsReceivedItemDetailComponent,
    DonationsReceivedItemUpdateComponent,
    DonationsReceivedItemDeleteDialogComponent,
  ],
  entryComponents: [DonationsReceivedItemDeleteDialogComponent],
})
export class DonationsReceivedItemModule {}
