import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HealthStatusCategoryComponent } from '../list/health-status-category.component';
import { HealthStatusCategoryDetailComponent } from '../detail/health-status-category-detail.component';
import { HealthStatusCategoryUpdateComponent } from '../update/health-status-category-update.component';
import { HealthStatusCategoryRoutingResolveService } from './health-status-category-routing-resolve.service';

const healthStatusCategoryRoute: Routes = [
  {
    path: '',
    component: HealthStatusCategoryComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HealthStatusCategoryDetailComponent,
    resolve: {
      healthStatusCategory: HealthStatusCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HealthStatusCategoryUpdateComponent,
    resolve: {
      healthStatusCategory: HealthStatusCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HealthStatusCategoryUpdateComponent,
    resolve: {
      healthStatusCategory: HealthStatusCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(healthStatusCategoryRoute)],
  exports: [RouterModule],
})
export class HealthStatusCategoryRoutingModule {}
