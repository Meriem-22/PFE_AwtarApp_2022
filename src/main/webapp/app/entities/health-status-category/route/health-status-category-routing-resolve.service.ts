import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHealthStatusCategory, HealthStatusCategory } from '../health-status-category.model';
import { HealthStatusCategoryService } from '../service/health-status-category.service';

@Injectable({ providedIn: 'root' })
export class HealthStatusCategoryRoutingResolveService implements Resolve<IHealthStatusCategory> {
  constructor(protected service: HealthStatusCategoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHealthStatusCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((healthStatusCategory: HttpResponse<HealthStatusCategory>) => {
          if (healthStatusCategory.body) {
            return of(healthStatusCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HealthStatusCategory());
  }
}
