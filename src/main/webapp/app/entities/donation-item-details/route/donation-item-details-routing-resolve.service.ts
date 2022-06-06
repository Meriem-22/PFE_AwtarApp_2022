import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDonationItemDetails, DonationItemDetails } from '../donation-item-details.model';
import { DonationItemDetailsService } from '../service/donation-item-details.service';

@Injectable({ providedIn: 'root' })
export class DonationItemDetailsRoutingResolveService implements Resolve<IDonationItemDetails> {
  constructor(protected service: DonationItemDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDonationItemDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((donationItemDetails: HttpResponse<DonationItemDetails>) => {
          if (donationItemDetails.body) {
            return of(donationItemDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DonationItemDetails());
  }
}
