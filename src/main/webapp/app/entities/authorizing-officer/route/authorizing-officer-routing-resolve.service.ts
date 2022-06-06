import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuthorizingOfficer, AuthorizingOfficer } from '../authorizing-officer.model';
import { AuthorizingOfficerService } from '../service/authorizing-officer.service';

@Injectable({ providedIn: 'root' })
export class AuthorizingOfficerRoutingResolveService implements Resolve<IAuthorizingOfficer> {
  constructor(protected service: AuthorizingOfficerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuthorizingOfficer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((authorizingOfficer: HttpResponse<AuthorizingOfficer>) => {
          if (authorizingOfficer.body) {
            return of(authorizingOfficer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AuthorizingOfficer());
  }
}
