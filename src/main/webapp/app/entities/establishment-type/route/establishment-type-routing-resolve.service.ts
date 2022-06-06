import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEstablishmentType, EstablishmentType } from '../establishment-type.model';
import { EstablishmentTypeService } from '../service/establishment-type.service';

@Injectable({ providedIn: 'root' })
export class EstablishmentTypeRoutingResolveService implements Resolve<IEstablishmentType> {
  constructor(protected service: EstablishmentTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstablishmentType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((establishmentType: HttpResponse<EstablishmentType>) => {
          if (establishmentType.body) {
            return of(establishmentType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstablishmentType());
  }
}
