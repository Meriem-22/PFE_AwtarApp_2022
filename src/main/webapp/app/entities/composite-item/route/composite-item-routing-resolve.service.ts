import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompositeItem, CompositeItem } from '../composite-item.model';
import { CompositeItemService } from '../service/composite-item.service';

@Injectable({ providedIn: 'root' })
export class CompositeItemRoutingResolveService implements Resolve<ICompositeItem> {
  constructor(protected service: CompositeItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompositeItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((compositeItem: HttpResponse<CompositeItem>) => {
          if (compositeItem.body) {
            return of(compositeItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompositeItem());
  }
}
