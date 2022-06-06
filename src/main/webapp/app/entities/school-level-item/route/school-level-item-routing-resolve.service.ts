import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISchoolLevelItem, SchoolLevelItem } from '../school-level-item.model';
import { SchoolLevelItemService } from '../service/school-level-item.service';

@Injectable({ providedIn: 'root' })
export class SchoolLevelItemRoutingResolveService implements Resolve<ISchoolLevelItem> {
  constructor(protected service: SchoolLevelItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchoolLevelItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((schoolLevelItem: HttpResponse<SchoolLevelItem>) => {
          if (schoolLevelItem.body) {
            return of(schoolLevelItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SchoolLevelItem());
  }
}
