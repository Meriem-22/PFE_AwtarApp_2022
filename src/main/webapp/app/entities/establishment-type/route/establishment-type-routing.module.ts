import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EstablishmentTypeComponent } from '../list/establishment-type.component';
import { EstablishmentTypeDetailComponent } from '../detail/establishment-type-detail.component';
import { EstablishmentTypeUpdateComponent } from '../update/establishment-type-update.component';
import { EstablishmentTypeRoutingResolveService } from './establishment-type-routing-resolve.service';

const establishmentTypeRoute: Routes = [
  {
    path: '',
    component: EstablishmentTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstablishmentTypeDetailComponent,
    resolve: {
      establishmentType: EstablishmentTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstablishmentTypeUpdateComponent,
    resolve: {
      establishmentType: EstablishmentTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstablishmentTypeUpdateComponent,
    resolve: {
      establishmentType: EstablishmentTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(establishmentTypeRoute)],
  exports: [RouterModule],
})
export class EstablishmentTypeRoutingModule {}
