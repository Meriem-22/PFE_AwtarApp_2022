import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AuthorizingOfficerComponent } from './list/authorizing-officer.component';
import { AuthorizingOfficerDetailComponent } from './detail/authorizing-officer-detail.component';
import { AuthorizingOfficerUpdateComponent } from './update/authorizing-officer-update.component';
import { AuthorizingOfficerDeleteDialogComponent } from './delete/authorizing-officer-delete-dialog.component';
import { AuthorizingOfficerRoutingModule } from './route/authorizing-officer-routing.module';
import { AddComponent } from './add/add.component';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  imports: [SharedModule, AuthorizingOfficerRoutingModule, NgxPaginationModule],
  declarations: [
    AuthorizingOfficerComponent,
    AuthorizingOfficerDetailComponent,
    AuthorizingOfficerUpdateComponent,
    AuthorizingOfficerDeleteDialogComponent,
    AddComponent,
  ],
  entryComponents: [AuthorizingOfficerDeleteDialogComponent],
})
export class AuthorizingOfficerModule {}
