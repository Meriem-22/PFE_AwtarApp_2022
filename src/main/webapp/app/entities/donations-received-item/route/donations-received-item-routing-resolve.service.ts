import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDonationsReceivedItem, DonationsReceivedItem } from '../donations-received-item.model';
import { DonationsReceivedItemService } from '../service/donations-received-item.service';

@Injectable({ providedIn: 'root' })
export class DonationsReceivedItemRoutingResolveService implements Resolve<IDonationsReceivedItem> {
  constructor(protected service: DonationsReceivedItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDonationsReceivedItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((donationsReceivedItem: HttpResponse<DonationsReceivedItem>) => {
          if (donationsReceivedItem.body) {
            return of(donationsReceivedItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DonationsReceivedItem());
  }
}
