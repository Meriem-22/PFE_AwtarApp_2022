import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChildStatusComponent } from './list/child-status.component';
import { ChildStatusDetailComponent } from './detail/child-status-detail.component';
import { ChildStatusUpdateComponent } from './update/child-status-update.component';
import { ChildStatusDeleteDialogComponent } from './delete/child-status-delete-dialog.component';
import { ChildStatusRoutingModule } from './route/child-status-routing.module';

@NgModule({
  imports: [SharedModule, ChildStatusRoutingModule],
  declarations: [ChildStatusComponent, ChildStatusDetailComponent, ChildStatusUpdateComponent, ChildStatusDeleteDialogComponent],
  entryComponents: [ChildStatusDeleteDialogComponent],
})
export class ChildStatusModule {}
