import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChildStatusComponent } from '../list/child-status.component';
import { ChildStatusDetailComponent } from '../detail/child-status-detail.component';
import { ChildStatusUpdateComponent } from '../update/child-status-update.component';
import { ChildStatusRoutingResolveService } from './child-status-routing-resolve.service';

const childStatusRoute: Routes = [
  {
    path: '',
    component: ChildStatusComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChildStatusDetailComponent,
    resolve: {
      childStatus: ChildStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChildStatusUpdateComponent,
    resolve: {
      childStatus: ChildStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChildStatusUpdateComponent,
    resolve: {
      childStatus: ChildStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(childStatusRoute)],
  exports: [RouterModule],
})
export class ChildStatusRoutingModule {}
