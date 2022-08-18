import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AuthorizingOfficerComponent } from '../list/authorizing-officer.component';
import { AuthorizingOfficerDetailComponent } from '../detail/authorizing-officer-detail.component';
import { AuthorizingOfficerUpdateComponent } from '../update/authorizing-officer-update.component';
import { AuthorizingOfficerRoutingResolveService } from './authorizing-officer-routing-resolve.service';
import { AddComponent } from '../add/add.component';

const authorizingOfficerRoute: Routes = [
  {
    path: '',
    component: AuthorizingOfficerComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuthorizingOfficerDetailComponent,
    resolve: {
      authorizingOfficer: AuthorizingOfficerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuthorizingOfficerUpdateComponent,
    resolve: {
      authorizingOfficer: AuthorizingOfficerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuthorizingOfficerUpdateComponent,
    resolve: {
      authorizingOfficer: AuthorizingOfficerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'add',
    component: AddComponent,
    resolve: {
      authorizingOfficer: AuthorizingOfficerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(authorizingOfficerRoute)],
  exports: [RouterModule],
})
export class AuthorizingOfficerRoutingModule {}
