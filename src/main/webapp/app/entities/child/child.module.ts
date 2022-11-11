import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChildComponent } from './list/child.component';
import { ChildDetailComponent } from './detail/child-detail.component';
import { ChildUpdateComponent } from './update/child-update.component';
import { ChildDeleteDialogComponent } from './delete/child-delete-dialog.component';
import { ChildRoutingModule } from './route/child-routing.module';
import { AddChildComponent } from './add-child/add-child.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { TableModule } from 'primeng/table';
import { FileUploadModule } from 'primeng/fileupload';

@NgModule({
  imports: [SharedModule, ChildRoutingModule, NgxPaginationModule, TableModule, FileUploadModule],
  declarations: [ChildComponent, ChildDetailComponent, ChildUpdateComponent, ChildDeleteDialogComponent, AddChildComponent],
  entryComponents: [ChildDeleteDialogComponent],
})
export class ChildModule {}
