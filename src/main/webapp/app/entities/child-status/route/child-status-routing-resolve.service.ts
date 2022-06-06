import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChildStatus, ChildStatus } from '../child-status.model';
import { ChildStatusService } from '../service/child-status.service';

@Injectable({ providedIn: 'root' })
export class ChildStatusRoutingResolveService implements Resolve<IChildStatus> {
  constructor(protected service: ChildStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChildStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((childStatus: HttpResponse<ChildStatus>) => {
          if (childStatus.body) {
            return of(childStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ChildStatus());
  }
}
