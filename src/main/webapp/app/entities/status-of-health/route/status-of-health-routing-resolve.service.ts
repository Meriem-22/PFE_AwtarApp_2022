import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStatusOfHealth, StatusOfHealth } from '../status-of-health.model';
import { StatusOfHealthService } from '../service/status-of-health.service';

@Injectable({ providedIn: 'root' })
export class StatusOfHealthRoutingResolveService implements Resolve<IStatusOfHealth> {
  constructor(protected service: StatusOfHealthService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusOfHealth> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((statusOfHealth: HttpResponse<StatusOfHealth>) => {
          if (statusOfHealth.body) {
            return of(statusOfHealth.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StatusOfHealth());
  }
}
