import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ItemComponent } from './list/item.component';
import { ItemDetailComponent } from './detail/item-detail.component';
import { ItemUpdateComponent } from './update/item-update.component';
import { ItemDeleteDialogComponent } from './delete/item-delete-dialog.component';
import { ItemRoutingModule } from './route/item-routing.module';
import { AddItemComponent } from './add-item/add-item.component';
import { TableModule } from 'primeng/table';
import { MultiSelectModule } from 'primeng/multiselect';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { RadioButtonModule } from 'primeng/radiobutton';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { ProgressBarModule } from 'primeng/progressbar';

@NgModule({
  imports: [
    SharedModule,
    ItemRoutingModule,
    TableModule,
    MultiSelectModule,
    FileUploadModule,
    InputTextModule,
    ProgressBarModule,
    AutoCompleteModule,
    RadioButtonModule,
    MessagesModule,
    MessageModule,
    ButtonModule,
    DropdownModule,
    InputNumberModule,
  ],
  declarations: [ItemComponent, ItemDetailComponent, ItemUpdateComponent, ItemDeleteDialogComponent, AddItemComponent],
  entryComponents: [ItemDeleteDialogComponent],
})
export class ItemModule {}
