import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FamilyComponent } from './list/family.component';
import { FamilyDetailComponent } from './detail/family-detail.component';
import { FamilyUpdateComponent } from './update/family-update.component';
import { FamilyDeleteDialogComponent } from './delete/family-delete-dialog.component';
import { FamilyRoutingModule } from './route/family-routing.module';
import { AddComponent } from './add/add.component';
import { BeneficiaryUpdateComponent } from './beneficiary-update/beneficiary-update.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { TableModule } from 'primeng/table';
import { ListboxModule } from 'primeng/listbox';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';

@NgModule({
  imports: [SharedModule, FamilyRoutingModule, NgxPaginationModule, TableModule, ListboxModule, DialogModule, ButtonModule],
  declarations: [
    FamilyComponent,
    FamilyDetailComponent,
    FamilyUpdateComponent,
    FamilyDeleteDialogComponent,
    AddComponent,
    BeneficiaryUpdateComponent,
  ],
  entryComponents: [FamilyDeleteDialogComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class FamilyModule {}
