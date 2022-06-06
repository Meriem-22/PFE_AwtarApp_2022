import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBeneficiary, Beneficiary } from '../beneficiary.model';
import { BeneficiaryService } from '../service/beneficiary.service';

@Injectable({ providedIn: 'root' })
export class BeneficiaryRoutingResolveService implements Resolve<IBeneficiary> {
  constructor(protected service: BeneficiaryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBeneficiary> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((beneficiary: HttpResponse<Beneficiary>) => {
          if (beneficiary.body) {
            return of(beneficiary.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Beneficiary());
  }
}
