import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompositeItemComponent } from './list/composite-item.component';
import { CompositeItemDetailComponent } from './detail/composite-item-detail.component';
import { CompositeItemUpdateComponent } from './update/composite-item-update.component';
import { CompositeItemDeleteDialogComponent } from './delete/composite-item-delete-dialog.component';
import { CompositeItemRoutingModule } from './route/composite-item-routing.module';

@NgModule({
  imports: [SharedModule, CompositeItemRoutingModule],
  declarations: [CompositeItemComponent, CompositeItemDetailComponent, CompositeItemUpdateComponent, CompositeItemDeleteDialogComponent],
  entryComponents: [CompositeItemDeleteDialogComponent],
})
export class CompositeItemModule {}
