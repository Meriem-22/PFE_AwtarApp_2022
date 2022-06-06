import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SchoolLevelComponent } from './list/school-level.component';
import { SchoolLevelDetailComponent } from './detail/school-level-detail.component';
import { SchoolLevelUpdateComponent } from './update/school-level-update.component';
import { SchoolLevelDeleteDialogComponent } from './delete/school-level-delete-dialog.component';
import { SchoolLevelRoutingModule } from './route/school-level-routing.module';

@NgModule({
  imports: [SharedModule, SchoolLevelRoutingModule],
  declarations: [SchoolLevelComponent, SchoolLevelDetailComponent, SchoolLevelUpdateComponent, SchoolLevelDeleteDialogComponent],
  entryComponents: [SchoolLevelDeleteDialogComponent],
})
export class SchoolLevelModule {}
