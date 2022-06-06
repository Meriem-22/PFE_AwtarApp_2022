import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DonationsReceivedComponent } from '../list/donations-received.component';
import { DonationsReceivedDetailComponent } from '../detail/donations-received-detail.component';
import { DonationsReceivedUpdateComponent } from '../update/donations-received-update.component';
import { DonationsReceivedRoutingResolveService } from './donations-received-routing-resolve.service';

const donationsReceivedRoute: Routes = [
  {
    path: '',
    component: DonationsReceivedComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DonationsReceivedDetailComponent,
    resolve: {
      donationsReceived: DonationsReceivedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DonationsReceivedUpdateComponent,
    resolve: {
      donationsReceived: DonationsReceivedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DonationsReceivedUpdateComponent,
    resolve: {
      donationsReceived: DonationsReceivedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(donationsReceivedRoute)],
  exports: [RouterModule],
})
export class DonationsReceivedRoutingModule {}
