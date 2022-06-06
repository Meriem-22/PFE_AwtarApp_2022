import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DonationsReceivedItemComponent } from '../list/donations-received-item.component';
import { DonationsReceivedItemDetailComponent } from '../detail/donations-received-item-detail.component';
import { DonationsReceivedItemUpdateComponent } from '../update/donations-received-item-update.component';
import { DonationsReceivedItemRoutingResolveService } from './donations-received-item-routing-resolve.service';

const donationsReceivedItemRoute: Routes = [
  {
    path: '',
    component: DonationsReceivedItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DonationsReceivedItemDetailComponent,
    resolve: {
      donationsReceivedItem: DonationsReceivedItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DonationsReceivedItemUpdateComponent,
    resolve: {
      donationsReceivedItem: DonationsReceivedItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DonationsReceivedItemUpdateComponent,
    resolve: {
      donationsReceivedItem: DonationsReceivedItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(donationsReceivedItemRoute)],
  exports: [RouterModule],
})
export class DonationsReceivedItemRoutingModule {}
