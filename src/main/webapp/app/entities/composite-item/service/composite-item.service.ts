import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompositeItem, getCompositeItemIdentifier } from '../composite-item.model';

export type EntityResponseType = HttpResponse<ICompositeItem>;
export type EntityArrayResponseType = HttpResponse<ICompositeItem[]>;

@Injectable({ providedIn: 'root' })
export class CompositeItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/composite-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(compositeItem: ICompositeItem): Observable<EntityResponseType> {
    return this.http.post<ICompositeItem>(this.resourceUrl, compositeItem, { observe: 'response' });
  }

  update(compositeItem: ICompositeItem): Observable<EntityResponseType> {
    return this.http.put<ICompositeItem>(`${this.resourceUrl}/${getCompositeItemIdentifier(compositeItem) as number}`, compositeItem, {
      observe: 'response',
    });
  }

  partialUpdate(compositeItem: ICompositeItem): Observable<EntityResponseType> {
    return this.http.patch<ICompositeItem>(`${this.resourceUrl}/${getCompositeItemIdentifier(compositeItem) as number}`, compositeItem, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompositeItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompositeItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCompositeItemToCollectionIfMissing(
    compositeItemCollection: ICompositeItem[],
    ...compositeItemsToCheck: (ICompositeItem | null | undefined)[]
  ): ICompositeItem[] {
    const compositeItems: ICompositeItem[] = compositeItemsToCheck.filter(isPresent);
    if (compositeItems.length > 0) {
      const compositeItemCollectionIdentifiers = compositeItemCollection.map(
        compositeItemItem => getCompositeItemIdentifier(compositeItemItem)!
      );
      const compositeItemsToAdd = compositeItems.filter(compositeItemItem => {
        const compositeItemIdentifier = getCompositeItemIdentifier(compositeItemItem);
        if (compositeItemIdentifier == null || compositeItemCollectionIdentifiers.includes(compositeItemIdentifier)) {
          return false;
        }
        compositeItemCollectionIdentifiers.push(compositeItemIdentifier);
        return true;
      });
      return [...compositeItemsToAdd, ...compositeItemCollection];
    }
    return compositeItemCollection;
  }
}
