import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISchoolLevel, SchoolLevel } from '../school-level.model';
import { SchoolLevelService } from '../service/school-level.service';

@Injectable({ providedIn: 'root' })
export class SchoolLevelRoutingResolveService implements Resolve<ISchoolLevel> {
  constructor(protected service: SchoolLevelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchoolLevel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((schoolLevel: HttpResponse<SchoolLevel>) => {
          if (schoolLevel.body) {
            return of(schoolLevel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SchoolLevel());
  }
}
