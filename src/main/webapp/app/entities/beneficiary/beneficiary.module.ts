import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BeneficiaryComponent } from './list/beneficiary.component';
import { BeneficiaryDetailComponent } from './detail/beneficiary-detail.component';
import { BeneficiaryUpdateComponent } from './update/beneficiary-update.component';
import { BeneficiaryDeleteDialogComponent } from './delete/beneficiary-delete-dialog.component';
import { BeneficiaryRoutingModule } from './route/beneficiary-routing.module';
import { EditAuthorizingOfficerComponent } from './edit-authorizing-officer/edit-authorizing-officer.component';
import { EditTutorComponent } from './edit-tutor/edit-tutor.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { TableModule } from 'primeng/table';
import { MultiSelectModule } from 'primeng/multiselect';
import { RemoveAuthorizingOfficerComponent } from './remove-authorizing-officer/remove-authorizing-officer.component';
import { RemoveTutorComponent } from './remove-tutor/remove-tutor.component';

@NgModule({
  imports: [SharedModule, BeneficiaryRoutingModule, NgxPaginationModule, TableModule, MultiSelectModule],
  declarations: [
    BeneficiaryComponent,
    BeneficiaryDetailComponent,
    BeneficiaryUpdateComponent,
    BeneficiaryDeleteDialogComponent,
    EditAuthorizingOfficerComponent,
    EditTutorComponent,
    RemoveAuthorizingOfficerComponent,
    RemoveTutorComponent,
  ],
  entryComponents: [BeneficiaryDeleteDialogComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class BeneficiaryModule {}
