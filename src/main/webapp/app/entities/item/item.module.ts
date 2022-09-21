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
import { AddCompositeItemComponent } from './add-composite-item/add-composite-item.component';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { AddCompositeSchoolItemComponent } from './add-composite-school-item/add-composite-school-item.component';
import { AddSimpleItemComponent } from './add-simple-item/add-simple-item.component';

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
    ToastModule,
    ToolbarModule,
    ConfirmDialogModule,
    DialogModule,
  ],
  providers: [ConfirmationService, MessageService],
  declarations: [
    ItemComponent,
    ItemDetailComponent,
    ItemUpdateComponent,
    ItemDeleteDialogComponent,
    AddItemComponent,
    AddCompositeItemComponent,
    AddCompositeSchoolItemComponent,
    AddSimpleItemComponent,
  ],
  entryComponents: [ItemDeleteDialogComponent],
})
export class ItemModule {}
