import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITeachingCurriculum, TeachingCurriculum } from '../teaching-curriculum.model';
import { TeachingCurriculumService } from '../service/teaching-curriculum.service';

@Injectable({ providedIn: 'root' })
export class TeachingCurriculumRoutingResolveService implements Resolve<ITeachingCurriculum> {
  constructor(protected service: TeachingCurriculumService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITeachingCurriculum> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((teachingCurriculum: HttpResponse<TeachingCurriculum>) => {
          if (teachingCurriculum.body) {
            return of(teachingCurriculum.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TeachingCurriculum());
  }
}
