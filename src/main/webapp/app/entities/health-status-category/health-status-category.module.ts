import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HealthStatusCategoryComponent } from './list/health-status-category.component';
import { HealthStatusCategoryDetailComponent } from './detail/health-status-category-detail.component';
import { HealthStatusCategoryUpdateComponent } from './update/health-status-category-update.component';
import { HealthStatusCategoryDeleteDialogComponent } from './delete/health-status-category-delete-dialog.component';
import { HealthStatusCategoryRoutingModule } from './route/health-status-category-routing.module';

@NgModule({
  imports: [SharedModule, HealthStatusCategoryRoutingModule],
  declarations: [
    HealthStatusCategoryComponent,
    HealthStatusCategoryDetailComponent,
    HealthStatusCategoryUpdateComponent,
    HealthStatusCategoryDeleteDialogComponent,
  ],
  entryComponents: [HealthStatusCategoryDeleteDialogComponent],
})
export class HealthStatusCategoryModule {}
