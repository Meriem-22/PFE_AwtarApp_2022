import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IItemValue, ItemValue } from '../item-value.model';
import { ItemValueService } from '../service/item-value.service';

@Injectable({ providedIn: 'root' })
export class ItemValueRoutingResolveService implements Resolve<IItemValue> {
  constructor(protected service: ItemValueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemValue> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((itemValue: HttpResponse<ItemValue>) => {
          if (itemValue.body) {
            return of(itemValue.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemValue());
  }
}
