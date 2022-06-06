import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TeachingCurriculumComponent } from '../list/teaching-curriculum.component';
import { TeachingCurriculumDetailComponent } from '../detail/teaching-curriculum-detail.component';
import { TeachingCurriculumUpdateComponent } from '../update/teaching-curriculum-update.component';
import { TeachingCurriculumRoutingResolveService } from './teaching-curriculum-routing-resolve.service';

const teachingCurriculumRoute: Routes = [
  {
    path: '',
    component: TeachingCurriculumComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TeachingCurriculumDetailComponent,
    resolve: {
      teachingCurriculum: TeachingCurriculumRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TeachingCurriculumUpdateComponent,
    resolve: {
      teachingCurriculum: TeachingCurriculumRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TeachingCurriculumUpdateComponent,
    resolve: {
      teachingCurriculum: TeachingCurriculumRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(teachingCurriculumRoute)],
  exports: [RouterModule],
})
export class TeachingCurriculumRoutingModule {}
