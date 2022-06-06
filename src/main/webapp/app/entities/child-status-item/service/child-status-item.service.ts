import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChildStatusItem, getChildStatusItemIdentifier } from '../child-status-item.model';

export type EntityResponseType = HttpResponse<IChildStatusItem>;
export type EntityArrayResponseType = HttpResponse<IChildStatusItem[]>;

@Injectable({ providedIn: 'root' })
export class ChildStatusItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/child-status-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(childStatusItem: IChildStatusItem): Observable<EntityResponseType> {
    return this.http.post<IChildStatusItem>(this.resourceUrl, childStatusItem, { observe: 'response' });
  }

  update(childStatusItem: IChildStatusItem): Observable<EntityResponseType> {
    return this.http.put<IChildStatusItem>(
      `${this.resourceUrl}/${getChildStatusItemIdentifier(childStatusItem) as number}`,
      childStatusItem,
      { observe: 'response' }
    );
  }

  partialUpdate(childStatusItem: IChildStatusItem): Observable<EntityResponseType> {
    return this.http.patch<IChildStatusItem>(
      `${this.resourceUrl}/${getChildStatusItemIdentifier(childStatusItem) as number}`,
      childStatusItem,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChildStatusItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChildStatusItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addChildStatusItemToCollectionIfMissing(
    childStatusItemCollection: IChildStatusItem[],
    ...childStatusItemsToCheck: (IChildStatusItem | null | undefined)[]
  ): IChildStatusItem[] {
    const childStatusItems: IChildStatusItem[] = childStatusItemsToCheck.filter(isPresent);
    if (childStatusItems.length > 0) {
      const childStatusItemCollectionIdentifiers = childStatusItemCollection.map(
        childStatusItemItem => getChildStatusItemIdentifier(childStatusItemItem)!
      );
      const childStatusItemsToAdd = childStatusItems.filter(childStatusItemItem => {
        const childStatusItemIdentifier = getChildStatusItemIdentifier(childStatusItemItem);
        if (childStatusItemIdentifier == null || childStatusItemCollectionIdentifiers.includes(childStatusItemIdentifier)) {
          return false;
        }
        childStatusItemCollectionIdentifiers.push(childStatusItemIdentifier);
        return true;
      });
      return [...childStatusItemsToAdd, ...childStatusItemCollection];
    }
    return childStatusItemCollection;
  }
}
