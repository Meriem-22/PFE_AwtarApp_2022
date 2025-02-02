import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EstablishmentComponent } from '../list/establishment.component';
import { EstablishmentDetailComponent } from '../detail/establishment-detail.component';
import { EstablishmentUpdateComponent } from '../update/establishment-update.component';
import { EstablishmentRoutingResolveService } from './establishment-routing-resolve.service';
import { AddComponent } from '../add/add.component';

const establishmentRoute: Routes = [
  {
    path: '',
    component: EstablishmentComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstablishmentDetailComponent,
    resolve: {
      establishment: EstablishmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstablishmentUpdateComponent,
    resolve: {
      establishment: EstablishmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstablishmentUpdateComponent,
    resolve: {
      establishment: EstablishmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'add',
    component: AddComponent,
    resolve: {
      establishment: EstablishmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(establishmentRoute)],
  exports: [RouterModule],
})
export class EstablishmentRoutingModule {}
