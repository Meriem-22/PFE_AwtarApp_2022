import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FamilyComponent } from './list/family.component';
import { FamilyDetailComponent } from './detail/family-detail.component';
import { FamilyUpdateComponent } from './update/family-update.component';
import { FamilyDeleteDialogComponent } from './delete/family-delete-dialog.component';
import { FamilyRoutingModule } from './route/family-routing.module';
import { AddComponent } from './add/add.component';

@NgModule({
  imports: [SharedModule, FamilyRoutingModule],
  declarations: [FamilyComponent, FamilyDetailComponent, FamilyUpdateComponent, FamilyDeleteDialogComponent, AddComponent],
  entryComponents: [FamilyDeleteDialogComponent],
})
export class FamilyModule {}
