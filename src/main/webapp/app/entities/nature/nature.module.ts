import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureComponent } from './list/nature.component';
import { NatureDetailComponent } from './detail/nature-detail.component';
import { NatureUpdateComponent } from './update/nature-update.component';
import { NatureDeleteDialogComponent } from './delete/nature-delete-dialog.component';
import { NatureRoutingModule } from './route/nature-routing.module';
import { TableModule } from 'primeng/table';
import { MultiSelectModule } from 'primeng/multiselect';

@NgModule({
  imports: [SharedModule, NatureRoutingModule, TableModule, MultiSelectModule],
  declarations: [NatureComponent, NatureDetailComponent, NatureUpdateComponent, NatureDeleteDialogComponent],
  entryComponents: [NatureDeleteDialogComponent],
})
export class NatureModule {}
