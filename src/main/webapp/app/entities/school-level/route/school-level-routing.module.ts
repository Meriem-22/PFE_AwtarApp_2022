import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SchoolLevelComponent } from '../list/school-level.component';
import { SchoolLevelDetailComponent } from '../detail/school-level-detail.component';
import { SchoolLevelUpdateComponent } from '../update/school-level-update.component';
import { SchoolLevelRoutingResolveService } from './school-level-routing-resolve.service';

const schoolLevelRoute: Routes = [
  {
    path: '',
    component: SchoolLevelComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SchoolLevelDetailComponent,
    resolve: {
      schoolLevel: SchoolLevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SchoolLevelUpdateComponent,
    resolve: {
      schoolLevel: SchoolLevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SchoolLevelUpdateComponent,
    resolve: {
      schoolLevel: SchoolLevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(schoolLevelRoute)],
  exports: [RouterModule],
})
export class SchoolLevelRoutingModule {}
