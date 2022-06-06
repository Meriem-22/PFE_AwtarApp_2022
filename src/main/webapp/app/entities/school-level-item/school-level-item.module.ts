import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SchoolLevelItemComponent } from './list/school-level-item.component';
import { SchoolLevelItemDetailComponent } from './detail/school-level-item-detail.component';
import { SchoolLevelItemUpdateComponent } from './update/school-level-item-update.component';
import { SchoolLevelItemDeleteDialogComponent } from './delete/school-level-item-delete-dialog.component';
import { SchoolLevelItemRoutingModule } from './route/school-level-item-routing.module';

@NgModule({
  imports: [SharedModule, SchoolLevelItemRoutingModule],
  declarations: [
    SchoolLevelItemComponent,
    SchoolLevelItemDetailComponent,
    SchoolLevelItemUpdateComponent,
    SchoolLevelItemDeleteDialogComponent,
  ],
  entryComponents: [SchoolLevelItemDeleteDialogComponent],
})
export class SchoolLevelItemModule {}
