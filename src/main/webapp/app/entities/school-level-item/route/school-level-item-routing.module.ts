import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SchoolLevelItemComponent } from '../list/school-level-item.component';
import { SchoolLevelItemDetailComponent } from '../detail/school-level-item-detail.component';
import { SchoolLevelItemUpdateComponent } from '../update/school-level-item-update.component';
import { SchoolLevelItemRoutingResolveService } from './school-level-item-routing-resolve.service';

const schoolLevelItemRoute: Routes = [
  {
    path: '',
    component: SchoolLevelItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SchoolLevelItemDetailComponent,
    resolve: {
      schoolLevelItem: SchoolLevelItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SchoolLevelItemUpdateComponent,
    resolve: {
      schoolLevelItem: SchoolLevelItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SchoolLevelItemUpdateComponent,
    resolve: {
      schoolLevelItem: SchoolLevelItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(schoolLevelItemRoute)],
  exports: [RouterModule],
})
export class SchoolLevelItemRoutingModule {}
