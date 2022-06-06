import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChildStatusItem, ChildStatusItem } from '../child-status-item.model';
import { ChildStatusItemService } from '../service/child-status-item.service';

@Injectable({ providedIn: 'root' })
export class ChildStatusItemRoutingResolveService implements Resolve<IChildStatusItem> {
  constructor(protected service: ChildStatusItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChildStatusItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((childStatusItem: HttpResponse<ChildStatusItem>) => {
          if (childStatusItem.body) {
            return of(childStatusItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ChildStatusItem());
  }
}
