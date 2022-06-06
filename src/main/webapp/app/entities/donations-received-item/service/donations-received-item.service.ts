import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDonationsReceivedItem, getDonationsReceivedItemIdentifier } from '../donations-received-item.model';

export type EntityResponseType = HttpResponse<IDonationsReceivedItem>;
export type EntityArrayResponseType = HttpResponse<IDonationsReceivedItem[]>;

@Injectable({ providedIn: 'root' })
export class DonationsReceivedItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/donations-received-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(donationsReceivedItem: IDonationsReceivedItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationsReceivedItem);
    return this.http
      .post<IDonationsReceivedItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(donationsReceivedItem: IDonationsReceivedItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationsReceivedItem);
    return this.http
      .put<IDonationsReceivedItem>(`${this.resourceUrl}/${getDonationsReceivedItemIdentifier(donationsReceivedItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(donationsReceivedItem: IDonationsReceivedItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationsReceivedItem);
    return this.http
      .patch<IDonationsReceivedItem>(`${this.resourceUrl}/${getDonationsReceivedItemIdentifier(donationsReceivedItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDonationsReceivedItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDonationsReceivedItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDonationsReceivedItemToCollectionIfMissing(
    donationsReceivedItemCollection: IDonationsReceivedItem[],
    ...donationsReceivedItemsToCheck: (IDonationsReceivedItem | null | undefined)[]
  ): IDonationsReceivedItem[] {
    const donationsReceivedItems: IDonationsReceivedItem[] = donationsReceivedItemsToCheck.filter(isPresent);
    if (donationsReceivedItems.length > 0) {
      const donationsReceivedItemCollectionIdentifiers = donationsReceivedItemCollection.map(
        donationsReceivedItemItem => getDonationsReceivedItemIdentifier(donationsReceivedItemItem)!
      );
      const donationsReceivedItemsToAdd = donationsReceivedItems.filter(donationsReceivedItemItem => {
        const donationsReceivedItemIdentifier = getDonationsReceivedItemIdentifier(donationsReceivedItemItem);
        if (
          donationsReceivedItemIdentifier == null ||
          donationsReceivedItemCollectionIdentifiers.includes(donationsReceivedItemIdentifier)
        ) {
          return false;
        }
        donationsReceivedItemCollectionIdentifiers.push(donationsReceivedItemIdentifier);
        return true;
      });
      return [...donationsReceivedItemsToAdd, ...donationsReceivedItemCollection];
    }
    return donationsReceivedItemCollection;
  }

  protected convertDateFromClient(donationsReceivedItem: IDonationsReceivedItem): IDonationsReceivedItem {
    return Object.assign({}, donationsReceivedItem, {
      date: donationsReceivedItem.date?.isValid() ? donationsReceivedItem.date.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((donationsReceivedItem: IDonationsReceivedItem) => {
        donationsReceivedItem.date = donationsReceivedItem.date ? dayjs(donationsReceivedItem.date) : undefined;
      });
    }
    return res;
  }
}
