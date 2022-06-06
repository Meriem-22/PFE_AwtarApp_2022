import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompositeItemComponent } from '../list/composite-item.component';
import { CompositeItemDetailComponent } from '../detail/composite-item-detail.component';
import { CompositeItemUpdateComponent } from '../update/composite-item-update.component';
import { CompositeItemRoutingResolveService } from './composite-item-routing-resolve.service';

const compositeItemRoute: Routes = [
  {
    path: '',
    component: CompositeItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompositeItemDetailComponent,
    resolve: {
      compositeItem: CompositeItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompositeItemUpdateComponent,
    resolve: {
      compositeItem: CompositeItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompositeItemUpdateComponent,
    resolve: {
      compositeItem: CompositeItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(compositeItemRoute)],
  exports: [RouterModule],
})
export class CompositeItemRoutingModule {}
