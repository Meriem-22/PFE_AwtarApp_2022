import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EducationalInstitutionComponent } from '../list/educational-institution.component';
import { EducationalInstitutionDetailComponent } from '../detail/educational-institution-detail.component';
import { EducationalInstitutionUpdateComponent } from '../update/educational-institution-update.component';
import { EducationalInstitutionRoutingResolveService } from './educational-institution-routing-resolve.service';

const educationalInstitutionRoute: Routes = [
  {
    path: '',
    component: EducationalInstitutionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EducationalInstitutionDetailComponent,
    resolve: {
      educationalInstitution: EducationalInstitutionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EducationalInstitutionUpdateComponent,
    resolve: {
      educationalInstitution: EducationalInstitutionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EducationalInstitutionUpdateComponent,
    resolve: {
      educationalInstitution: EducationalInstitutionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(educationalInstitutionRoute)],
  exports: [RouterModule],
})
export class EducationalInstitutionRoutingModule {}
