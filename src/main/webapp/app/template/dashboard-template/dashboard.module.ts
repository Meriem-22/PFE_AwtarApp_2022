import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EntityRoutingModule } from 'app/entities/entity-routing.module';

import { SharedModule } from 'app/shared/shared.module';
import { DASHBOARD_Template_ROUTE } from './dashboard.route';

@NgModule({
  imports: [SharedModule, EntityRoutingModule, RouterModule.forChild([DASHBOARD_Template_ROUTE])],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class DashboardTemplateModule {}