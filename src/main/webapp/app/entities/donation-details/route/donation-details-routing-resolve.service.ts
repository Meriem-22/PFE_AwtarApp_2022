import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDonationDetails, DonationDetails } from '../donation-details.model';
import { DonationDetailsService } from '../service/donation-details.service';

@Injectable({ providedIn: 'root' })
export class DonationDetailsRoutingResolveService implements Resolve<IDonationDetails> {
  constructor(protected service: DonationDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDonationDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((donationDetails: HttpResponse<DonationDetails>) => {
          if (donationDetails.body) {
            return of(donationDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DonationDetails());
  }
}
