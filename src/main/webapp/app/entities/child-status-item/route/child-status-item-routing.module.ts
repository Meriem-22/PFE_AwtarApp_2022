import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChildStatusItemComponent } from '../list/child-status-item.component';
import { ChildStatusItemDetailComponent } from '../detail/child-status-item-detail.component';
import { ChildStatusItemUpdateComponent } from '../update/child-status-item-update.component';
import { ChildStatusItemRoutingResolveService } from './child-status-item-routing-resolve.service';

const childStatusItemRoute: Routes = [
  {
    path: '',
    component: ChildStatusItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChildStatusItemDetailComponent,
    resolve: {
      childStatusItem: ChildStatusItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChildStatusItemUpdateComponent,
    resolve: {
      childStatusItem: ChildStatusItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChildStatusItemUpdateComponent,
    resolve: {
      childStatusItem: ChildStatusItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(childStatusItemRoute)],
  exports: [RouterModule],
})
export class ChildStatusItemRoutingModule {}
