import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEducationalInstitution, EducationalInstitution } from '../educational-institution.model';
import { EducationalInstitutionService } from '../service/educational-institution.service';

@Injectable({ providedIn: 'root' })
export class EducationalInstitutionRoutingResolveService implements Resolve<IEducationalInstitution> {
  constructor(protected service: EducationalInstitutionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEducationalInstitution> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((educationalInstitution: HttpResponse<EducationalInstitution>) => {
          if (educationalInstitution.body) {
            return of(educationalInstitution.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EducationalInstitution());
  }
}
