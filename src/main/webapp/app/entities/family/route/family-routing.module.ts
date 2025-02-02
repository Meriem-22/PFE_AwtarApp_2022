import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FamilyComponent } from '../list/family.component';
import { FamilyDetailComponent } from '../detail/family-detail.component';
import { FamilyUpdateComponent } from '../update/family-update.component';
import { FamilyRoutingResolveService } from './family-routing-resolve.service';
import { AddComponent } from '../add/add.component';
import { BeneficiaryUpdateComponent } from '../beneficiary-update/beneficiary-update.component';

const familyRoute: Routes = [
  {
    path: '',
    component: FamilyComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FamilyDetailComponent,
    resolve: {
      family: FamilyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FamilyUpdateComponent,
    resolve: {
      family: FamilyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FamilyUpdateComponent,
    resolve: {
      family: FamilyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },

  {
    path: 'add',
    component: AddComponent,
    resolve: {
      family: FamilyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'beneficiary',
    component: BeneficiaryUpdateComponent,
    resolve: {
      family: FamilyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(familyRoute)],
  exports: [RouterModule],
})
export class FamilyRoutingModule {}
