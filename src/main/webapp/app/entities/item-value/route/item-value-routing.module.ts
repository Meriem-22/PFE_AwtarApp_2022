import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ItemValueComponent } from '../list/item-value.component';
import { ItemValueDetailComponent } from '../detail/item-value-detail.component';
import { ItemValueUpdateComponent } from '../update/item-value-update.component';
import { ItemValueRoutingResolveService } from './item-value-routing-resolve.service';

const itemValueRoute: Routes = [
  {
    path: '',
    component: ItemValueComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemValueDetailComponent,
    resolve: {
      itemValue: ItemValueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemValueUpdateComponent,
    resolve: {
      itemValue: ItemValueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemValueUpdateComponent,
    resolve: {
      itemValue: ItemValueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(itemValueRoute)],
  exports: [RouterModule],
})
export class ItemValueRoutingModule {}
