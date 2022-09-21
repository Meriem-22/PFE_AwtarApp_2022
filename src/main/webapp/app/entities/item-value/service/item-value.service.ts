import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IItemValue, getItemValueIdentifier, getItemValueIdentifierFromItem } from '../item-value.model';
import { IItem } from 'app/entities/item/item.model';

export type EntityResponseType = HttpResponse<IItemValue>;
export type EntityArrayResponseType = HttpResponse<IItemValue[]>;

@Injectable({ providedIn: 'root' })
export class ItemValueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/item-values');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(itemValue: IItemValue): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemValue);
    return this.http
      .post<IItemValue>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(itemValue: IItemValue): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemValue);
    return this.http
      .put<IItemValue>(`${this.resourceUrl}/${getItemValueIdentifier(itemValue) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updatePrice(itemValue: IItemValue): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemValue);
    return this.http.put<IItemValue>(`${this.resourceUrl}/${getItemValueIdentifier(itemValue) as number}` + '/update-price', copy, {
      observe: 'response',
    });
  }

  partialUpdate(itemValue: IItemValue): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemValue);
    return this.http
      .patch<IItemValue>(`${this.resourceUrl}/${getItemValueIdentifier(itemValue) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IItemValue>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findItem(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IItemValue>(`${this.resourceUrl + '/item'}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IItemValue[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addItemValueToCollectionIfMissing(
    itemValueCollection: IItemValue[],
    ...itemValuesToCheck: (IItemValue | null | undefined)[]
  ): IItemValue[] {
    const itemValues: IItemValue[] = itemValuesToCheck.filter(isPresent);
    if (itemValues.length > 0) {
      const itemValueCollectionIdentifiers = itemValueCollection.map(itemValueItem => getItemValueIdentifier(itemValueItem)!);
      const itemValuesToAdd = itemValues.filter(itemValueItem => {
        const itemValueIdentifier = getItemValueIdentifier(itemValueItem);
        if (itemValueIdentifier == null || itemValueCollectionIdentifiers.includes(itemValueIdentifier)) {
          return false;
        }
        itemValueCollectionIdentifiers.push(itemValueIdentifier);
        return true;
      });
      return [...itemValuesToAdd, ...itemValueCollection];
    }
    return itemValueCollection;
  }

  protected convertDateFromClient(itemValue: IItemValue): IItemValue {
    return Object.assign({}, itemValue, {
      priceDate: itemValue.priceDate?.isValid() ? itemValue.priceDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.priceDate = res.body.priceDate ? dayjs(res.body.priceDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((itemValue: IItemValue) => {
        itemValue.priceDate = itemValue.priceDate ? dayjs(itemValue.priceDate) : undefined;
      });
    }
    return res;
  }
}
