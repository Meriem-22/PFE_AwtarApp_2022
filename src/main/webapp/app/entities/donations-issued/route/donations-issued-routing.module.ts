import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DonationsIssuedComponent } from '../list/donations-issued.component';
import { DonationsIssuedDetailComponent } from '../detail/donations-issued-detail.component';
import { DonationsIssuedUpdateComponent } from '../update/donations-issued-update.component';
import { DonationsIssuedRoutingResolveService } from './donations-issued-routing-resolve.service';
import { AddDonationsIssuedComponent } from '../add-donations-issued/add-donations-issued.component';
import { AddAnyDonationsComponent } from '../add-any-donations/add-any-donations.component';
import { DonationIssuedDetailsComponent } from '../donation-issued-details/donation-issued-details.component';

const donationsIssuedRoute: Routes = [
  {
    path: '',
    component: DonationsIssuedComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DonationsIssuedDetailComponent,
    resolve: {
      donationsIssued: DonationsIssuedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DonationsIssuedUpdateComponent,
    resolve: {
      donationsIssued: DonationsIssuedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DonationsIssuedUpdateComponent,
    resolve: {
      donationsIssued: DonationsIssuedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'add',
    component: AddDonationsIssuedComponent,
    resolve: {
      donationsIssued: DonationsIssuedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'add-any-donations',
    component: AddAnyDonationsComponent,
    resolve: {
      donationsIssued: DonationsIssuedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(donationsIssuedRoute)],
  exports: [RouterModule],
})
export class DonationsIssuedRoutingModule {}
