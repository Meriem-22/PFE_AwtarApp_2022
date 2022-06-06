import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDonationsIssued, DonationsIssued } from '../donations-issued.model';
import { DonationsIssuedService } from '../service/donations-issued.service';

@Injectable({ providedIn: 'root' })
export class DonationsIssuedRoutingResolveService implements Resolve<IDonationsIssued> {
  constructor(protected service: DonationsIssuedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDonationsIssued> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((donationsIssued: HttpResponse<DonationsIssued>) => {
          if (donationsIssued.body) {
            return of(donationsIssued.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DonationsIssued());
  }
}
