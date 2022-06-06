import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DonationDetailsComponent } from '../list/donation-details.component';
import { DonationDetailsDetailComponent } from '../detail/donation-details-detail.component';
import { DonationDetailsUpdateComponent } from '../update/donation-details-update.component';
import { DonationDetailsRoutingResolveService } from './donation-details-routing-resolve.service';

const donationDetailsRoute: Routes = [
  {
    path: '',
    component: DonationDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DonationDetailsDetailComponent,
    resolve: {
      donationDetails: DonationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DonationDetailsUpdateComponent,
    resolve: {
      donationDetails: DonationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DonationDetailsUpdateComponent,
    resolve: {
      donationDetails: DonationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(donationDetailsRoute)],
  exports: [RouterModule],
})
export class DonationDetailsRoutingModule {}
