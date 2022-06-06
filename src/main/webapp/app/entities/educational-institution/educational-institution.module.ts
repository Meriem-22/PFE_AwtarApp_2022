import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EducationalInstitutionComponent } from './list/educational-institution.component';
import { EducationalInstitutionDetailComponent } from './detail/educational-institution-detail.component';
import { EducationalInstitutionUpdateComponent } from './update/educational-institution-update.component';
import { EducationalInstitutionDeleteDialogComponent } from './delete/educational-institution-delete-dialog.component';
import { EducationalInstitutionRoutingModule } from './route/educational-institution-routing.module';

@NgModule({
  imports: [SharedModule, EducationalInstitutionRoutingModule],
  declarations: [
    EducationalInstitutionComponent,
    EducationalInstitutionDetailComponent,
    EducationalInstitutionUpdateComponent,
    EducationalInstitutionDeleteDialogComponent,
  ],
  entryComponents: [EducationalInstitutionDeleteDialogComponent],
})
export class EducationalInstitutionModule {}
