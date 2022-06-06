import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ItemValueComponent } from './list/item-value.component';
import { ItemValueDetailComponent } from './detail/item-value-detail.component';
import { ItemValueUpdateComponent } from './update/item-value-update.component';
import { ItemValueDeleteDialogComponent } from './delete/item-value-delete-dialog.component';
import { ItemValueRoutingModule } from './route/item-value-routing.module';

@NgModule({
  imports: [SharedModule, ItemValueRoutingModule],
  declarations: [ItemValueComponent, ItemValueDetailComponent, ItemValueUpdateComponent, ItemValueDeleteDialogComponent],
  entryComponents: [ItemValueDeleteDialogComponent],
})
export class ItemValueModule {}
