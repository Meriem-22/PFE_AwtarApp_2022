import { Route } from '@angular/router';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DashboardTemplateComponent } from './dashboard-template.component';

export const DASHBOARD_Template_ROUTE: Route = {
  path: 'dashboard',
  data: {
    authorities: [Authority.ADMIN, Authority.USER],
  },

  canActivate: [UserRouteAccessService],
  component: DashboardTemplateComponent,
};
