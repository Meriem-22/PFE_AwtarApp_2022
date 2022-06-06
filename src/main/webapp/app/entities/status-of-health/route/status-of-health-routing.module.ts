import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatusOfHealthComponent } from '../list/status-of-health.component';
import { StatusOfHealthDetailComponent } from '../detail/status-of-health-detail.component';
import { StatusOfHealthUpdateComponent } from '../update/status-of-health-update.component';
import { StatusOfHealthRoutingResolveService } from './status-of-health-routing-resolve.service';

const statusOfHealthRoute: Routes = [
  {
    path: '',
    component: StatusOfHealthComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatusOfHealthDetailComponent,
    resolve: {
      statusOfHealth: StatusOfHealthRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusOfHealthUpdateComponent,
    resolve: {
      statusOfHealth: StatusOfHealthRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatusOfHealthUpdateComponent,
    resolve: {
      statusOfHealth: StatusOfHealthRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(statusOfHealthRoute)],
  exports: [RouterModule],
})
export class StatusOfHealthRoutingModule {}
