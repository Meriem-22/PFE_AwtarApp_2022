import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DonationItemDetailsComponent } from '../list/donation-item-details.component';
import { DonationItemDetailsDetailComponent } from '../detail/donation-item-details-detail.component';
import { DonationItemDetailsUpdateComponent } from '../update/donation-item-details-update.component';
import { DonationItemDetailsRoutingResolveService } from './donation-item-details-routing-resolve.service';

const donationItemDetailsRoute: Routes = [
  {
    path: '',
    component: DonationItemDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DonationItemDetailsDetailComponent,
    resolve: {
      donationItemDetails: DonationItemDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DonationItemDetailsUpdateComponent,
    resolve: {
      donationItemDetails: DonationItemDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DonationItemDetailsUpdateComponent,
    resolve: {
      donationItemDetails: DonationItemDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(donationItemDetailsRoute)],
  exports: [RouterModule],
})
export class DonationItemDetailsRoutingModule {}
