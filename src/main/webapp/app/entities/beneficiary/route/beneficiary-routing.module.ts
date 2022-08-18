import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BeneficiaryComponent } from '../list/beneficiary.component';
import { BeneficiaryDetailComponent } from '../detail/beneficiary-detail.component';
import { BeneficiaryUpdateComponent } from '../update/beneficiary-update.component';
import { BeneficiaryRoutingResolveService } from './beneficiary-routing-resolve.service';
import { EditContributorsComponent } from '../edit-contributors/edit-contributors.component';
import { EditAuthorizingOfficerComponent } from '../edit-authorizing-officer/edit-authorizing-officer.component';
import { EditTutorComponent } from '../edit-tutor/edit-tutor.component';

const beneficiaryRoute: Routes = [
  {
    path: '',
    component: BeneficiaryComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BeneficiaryDetailComponent,
    resolve: {
      beneficiary: BeneficiaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BeneficiaryUpdateComponent,
    resolve: {
      beneficiary: BeneficiaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BeneficiaryUpdateComponent,
    resolve: {
      beneficiary: BeneficiaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/contributor/authorizing-officer',
    component: EditAuthorizingOfficerComponent,
    resolve: {
      beneficiary: BeneficiaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/contributor/tutor',
    component: EditTutorComponent,
    resolve: {
      beneficiary: BeneficiaryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(beneficiaryRoute)],
  exports: [RouterModule],
})
export class BeneficiaryRoutingModule {}
