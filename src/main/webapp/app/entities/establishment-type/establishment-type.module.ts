import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EstablishmentTypeComponent } from './list/establishment-type.component';
import { EstablishmentTypeDetailComponent } from './detail/establishment-type-detail.component';
import { EstablishmentTypeUpdateComponent } from './update/establishment-type-update.component';
import { EstablishmentTypeDeleteDialogComponent } from './delete/establishment-type-delete-dialog.component';
import { EstablishmentTypeRoutingModule } from './route/establishment-type-routing.module';

@NgModule({
  imports: [SharedModule, EstablishmentTypeRoutingModule],
  declarations: [
    EstablishmentTypeComponent,
    EstablishmentTypeDetailComponent,
    EstablishmentTypeUpdateComponent,
    EstablishmentTypeDeleteDialogComponent,
  ],
  entryComponents: [EstablishmentTypeDeleteDialogComponent],
})
export class EstablishmentTypeModule {}
