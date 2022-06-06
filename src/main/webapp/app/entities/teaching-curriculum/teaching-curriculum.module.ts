import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TeachingCurriculumComponent } from './list/teaching-curriculum.component';
import { TeachingCurriculumDetailComponent } from './detail/teaching-curriculum-detail.component';
import { TeachingCurriculumUpdateComponent } from './update/teaching-curriculum-update.component';
import { TeachingCurriculumDeleteDialogComponent } from './delete/teaching-curriculum-delete-dialog.component';
import { TeachingCurriculumRoutingModule } from './route/teaching-curriculum-routing.module';

@NgModule({
  imports: [SharedModule, TeachingCurriculumRoutingModule],
  declarations: [
    TeachingCurriculumComponent,
    TeachingCurriculumDetailComponent,
    TeachingCurriculumUpdateComponent,
    TeachingCurriculumDeleteDialogComponent,
  ],
  entryComponents: [TeachingCurriculumDeleteDialogComponent],
})
export class TeachingCurriculumModule {}
