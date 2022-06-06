import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StatusOfHealthComponent } from './list/status-of-health.component';
import { StatusOfHealthDetailComponent } from './detail/status-of-health-detail.component';
import { StatusOfHealthUpdateComponent } from './update/status-of-health-update.component';
import { StatusOfHealthDeleteDialogComponent } from './delete/status-of-health-delete-dialog.component';
import { StatusOfHealthRoutingModule } from './route/status-of-health-routing.module';

@NgModule({
  imports: [SharedModule, StatusOfHealthRoutingModule],
  declarations: [
    StatusOfHealthComponent,
    StatusOfHealthDetailComponent,
    StatusOfHealthUpdateComponent,
    StatusOfHealthDeleteDialogComponent,
  ],
  entryComponents: [StatusOfHealthDeleteDialogComponent],
})
export class StatusOfHealthModule {}
