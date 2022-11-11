import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EstablishmentComponent } from './list/establishment.component';
import { EstablishmentDetailComponent } from './detail/establishment-detail.component';
import { EstablishmentUpdateComponent } from './update/establishment-update.component';
import { EstablishmentDeleteDialogComponent } from './delete/establishment-delete-dialog.component';
import { EstablishmentRoutingModule } from './route/establishment-routing.module';
import { AddComponent } from './add/add.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { TableModule } from 'primeng/table';

@NgModule({
  imports: [SharedModule, EstablishmentRoutingModule, NgxPaginationModule, TableModule],
  declarations: [
    EstablishmentComponent,
    EstablishmentDetailComponent,
    EstablishmentUpdateComponent,
    EstablishmentDeleteDialogComponent,
    AddComponent,
  ],
  entryComponents: [EstablishmentDeleteDialogComponent],
})
export class EstablishmentModule {}
