import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ItemComponent } from './list/item.component';
import { ItemDetailComponent } from './detail/item-detail.component';
import { ItemUpdateComponent } from './update/item-update.component';
import { ItemDeleteDialogComponent } from './delete/item-delete-dialog.component';
import { ItemRoutingModule } from './route/item-routing.module';
import { AddItemComponent } from './add-item/add-item.component';

@NgModule({
  imports: [SharedModule, ItemRoutingModule],
  declarations: [ItemComponent, ItemDetailComponent, ItemUpdateComponent, ItemDeleteDialogComponent, AddItemComponent],
  entryComponents: [ItemDeleteDialogComponent],
})
export class ItemModule {}
