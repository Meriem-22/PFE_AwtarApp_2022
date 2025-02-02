import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ItemComponent } from '../list/item.component';
import { ItemDetailComponent } from '../detail/item-detail.component';
import { ItemUpdateComponent } from '../update/item-update.component';
import { ItemRoutingResolveService } from './item-routing-resolve.service';
import { AddItemComponent } from '../add-item/add-item.component';
import { AddCompositeItemComponent } from '../add-composite-item/add-composite-item.component';
import { AddCompositeSchoolItemComponent } from '../add-composite-school-item/add-composite-school-item.component';
import { AddSimpleItemComponent } from '../add-simple-item/add-simple-item.component';

const itemRoute: Routes = [
  {
    path: '',
    component: ItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemDetailComponent,
    resolve: {
      item: ItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemUpdateComponent,
    resolve: {
      item: ItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemUpdateComponent,
    resolve: {
      item: ItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'add',
    component: AddItemComponent,
    resolve: {
      item: ItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },

  {
    path: 'add/composite',
    component: AddCompositeItemComponent,
    resolve: {
      item: ItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },

  {
    path: 'add/composite-school-item',
    component: AddCompositeSchoolItemComponent,
    resolve: {
      item: ItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'add/simple-item/:id',
    component: AddSimpleItemComponent,
    resolve: {
      item: ItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(itemRoute)],
  exports: [RouterModule],
})
export class ItemRoutingModule {}
