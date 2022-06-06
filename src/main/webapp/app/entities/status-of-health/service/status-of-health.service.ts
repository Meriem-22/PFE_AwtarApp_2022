import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStatusOfHealth, getStatusOfHealthIdentifier } from '../status-of-health.model';

export type EntityResponseType = HttpResponse<IStatusOfHealth>;
export type EntityArrayResponseType = HttpResponse<IStatusOfHealth[]>;

@Injectable({ providedIn: 'root' })
export class StatusOfHealthService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/status-of-healths');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(statusOfHealth: IStatusOfHealth): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(statusOfHealth);
    return this.http
      .post<IStatusOfHealth>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(statusOfHealth: IStatusOfHealth): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(statusOfHealth);
    return this.http
      .put<IStatusOfHealth>(`${this.resourceUrl}/${getStatusOfHealthIdentifier(statusOfHealth) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(statusOfHealth: IStatusOfHealth): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(statusOfHealth);
    return this.http
      .patch<IStatusOfHealth>(`${this.resourceUrl}/${getStatusOfHealthIdentifier(statusOfHealth) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStatusOfHealth>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStatusOfHealth[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStatusOfHealthToCollectionIfMissing(
    statusOfHealthCollection: IStatusOfHealth[],
    ...statusOfHealthsToCheck: (IStatusOfHealth | null | undefined)[]
  ): IStatusOfHealth[] {
    const statusOfHealths: IStatusOfHealth[] = statusOfHealthsToCheck.filter(isPresent);
    if (statusOfHealths.length > 0) {
      const statusOfHealthCollectionIdentifiers = statusOfHealthCollection.map(
        statusOfHealthItem => getStatusOfHealthIdentifier(statusOfHealthItem)!
      );
      const statusOfHealthsToAdd = statusOfHealths.filter(statusOfHealthItem => {
        const statusOfHealthIdentifier = getStatusOfHealthIdentifier(statusOfHealthItem);
        if (statusOfHealthIdentifier == null || statusOfHealthCollectionIdentifiers.includes(statusOfHealthIdentifier)) {
          return false;
        }
        statusOfHealthCollectionIdentifiers.push(statusOfHealthIdentifier);
        return true;
      });
      return [...statusOfHealthsToAdd, ...statusOfHealthCollection];
    }
    return statusOfHealthCollection;
  }

  protected convertDateFromClient(statusOfHealth: IStatusOfHealth): IStatusOfHealth {
    return Object.assign({}, statusOfHealth, {
      healthStatusDate: statusOfHealth.healthStatusDate?.isValid() ? statusOfHealth.healthStatusDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.healthStatusDate = res.body.healthStatusDate ? dayjs(res.body.healthStatusDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((statusOfHealth: IStatusOfHealth) => {
        statusOfHealth.healthStatusDate = statusOfHealth.healthStatusDate ? dayjs(statusOfHealth.healthStatusDate) : undefined;
      });
    }
    return res;
  }
}
