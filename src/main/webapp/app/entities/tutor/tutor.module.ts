import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TutorComponent } from './list/tutor.component';
import { TutorDetailComponent } from './detail/tutor-detail.component';
import { TutorUpdateComponent } from './update/tutor-update.component';
import { TutorDeleteDialogComponent } from './delete/tutor-delete-dialog.component';
import { TutorRoutingModule } from './route/tutor-routing.module';
import { AddComponent } from './add/add.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { TableModule } from 'primeng/table';

@NgModule({
  imports: [SharedModule, TutorRoutingModule, NgxPaginationModule, TableModule],
  declarations: [TutorComponent, TutorDetailComponent, TutorUpdateComponent, TutorDeleteDialogComponent, AddComponent],
  entryComponents: [TutorDeleteDialogComponent],
})
export class TutorModule {}
