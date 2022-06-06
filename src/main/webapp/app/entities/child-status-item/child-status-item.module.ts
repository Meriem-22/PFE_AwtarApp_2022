import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChildStatusItemComponent } from './list/child-status-item.component';
import { ChildStatusItemDetailComponent } from './detail/child-status-item-detail.component';
import { ChildStatusItemUpdateComponent } from './update/child-status-item-update.component';
import { ChildStatusItemDeleteDialogComponent } from './delete/child-status-item-delete-dialog.component';
import { ChildStatusItemRoutingModule } from './route/child-status-item-routing.module';

@NgModule({
  imports: [SharedModule, ChildStatusItemRoutingModule],
  declarations: [
    ChildStatusItemComponent,
    ChildStatusItemDetailComponent,
    ChildStatusItemUpdateComponent,
    ChildStatusItemDeleteDialogComponent,
  ],
  entryComponents: [ChildStatusItemDeleteDialogComponent],
})
export class ChildStatusItemModule {}
