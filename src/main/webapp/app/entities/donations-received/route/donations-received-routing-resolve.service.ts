import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDonationsReceived, DonationsReceived } from '../donations-received.model';
import { DonationsReceivedService } from '../service/donations-received.service';

@Injectable({ providedIn: 'root' })
export class DonationsReceivedRoutingResolveService implements Resolve<IDonationsReceived> {
  constructor(protected service: DonationsReceivedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDonationsReceived> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((donationsReceived: HttpResponse<DonationsReceived>) => {
          if (donationsReceived.body) {
            return of(donationsReceived.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DonationsReceived());
  }
}
