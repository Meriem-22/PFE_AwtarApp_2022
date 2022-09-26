import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IItem, getItemIdentifier } from '../item.model';
import { DATE_FORMAT } from 'app/config/input.constants';

export type EntityResponseType = HttpResponse<IItem>;
export type EntityArrayResponseType = HttpResponse<IItem[]>;
export type EntityArrayResponseType1 = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class ItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(item: IItem): Observable<EntityResponseType> {
    return this.http.post<IItem>(this.resourceUrl, item, { observe: 'response' });
  }

  addSimpleItem(item: IItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(item);
    return this.http.post<IItem>(this.resourceUrl + '/simple-item', copy, { observe: 'response' });
  }

  addSimpleSchoolItem(item: IItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(item);
    return this.http.post<IItem>(this.resourceUrl + '/simple-school-item', copy, { observe: 'response' });
  }

  update(item: IItem): Observable<EntityResponseType> {
    return this.http.put<IItem>(`${this.resourceUrl}/${getItemIdentifier(item) as number}`, item, { observe: 'response' });
  }

  partialUpdate(item: IItem): Observable<EntityResponseType> {
    return this.http.patch<IItem>(`${this.resourceUrl}/${getItemIdentifier(item) as number}`, item, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAllNormalItems(): Observable<EntityArrayResponseType1> {
    return this.http.get<any[]>(this.resourceUrl + '/normal/allDetails', { observe: 'response' });
  }

  findAllSchoolItems(): Observable<EntityArrayResponseType1> {
    return this.http.get<IItem[]>(this.resourceUrl + '/schoolItems/allDetails', { observe: 'response' });
  }

  findAllCompositeurItems(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IItem[]>(`${this.resourceUrl}/${id}` + '/compositeur-items/all', { observe: 'response' });
  }

  findAllItems(): Observable<EntityArrayResponseType> {
    return this.http.get<IItem[]>(this.resourceUrl + '/all', { observe: 'response' });
  }

  findItemWithNature(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IItem[]>(`${this.resourceUrl + '/nature'}/${id}`, { observe: 'response' });
  }

  findItemsDetailsWithNature(id: number): Observable<EntityArrayResponseType1> {
    return this.http.get<any[]>(`${this.resourceUrl + '/price-details/with-nature'}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addItemToCollectionIfMissing(itemCollection: IItem[], ...itemsToCheck: (IItem | null | undefined)[]): IItem[] {
    const items: IItem[] = itemsToCheck.filter(isPresent);
    if (items.length > 0) {
      const itemCollectionIdentifiers = itemCollection.map(itemItem => getItemIdentifier(itemItem)!);
      const itemsToAdd = items.filter(itemItem => {
        const itemIdentifier = getItemIdentifier(itemItem);
        if (itemIdentifier == null || itemCollectionIdentifiers.includes(itemIdentifier)) {
          return false;
        }
        itemCollectionIdentifiers.push(itemIdentifier);
        return true;
      });
      return [...itemsToAdd, ...itemCollection];
    }
    return itemCollection;
  }

  protected convertDateFromClient(itemValue: IItem): IItem {
    return Object.assign({}, itemValue, {
      priceDate: itemValue.priceDate?.isValid() ? itemValue.priceDate.format(DATE_FORMAT) : undefined,
    });
  }
}
