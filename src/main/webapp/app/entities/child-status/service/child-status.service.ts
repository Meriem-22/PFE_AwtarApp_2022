import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChildStatus, getChildStatusIdentifier } from '../child-status.model';

export type EntityResponseType = HttpResponse<IChildStatus>;
export type EntityArrayResponseType = HttpResponse<IChildStatus[]>;

@Injectable({ providedIn: 'root' })
export class ChildStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/child-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(childStatus: IChildStatus): Observable<EntityResponseType> {
    return this.http.post<IChildStatus>(this.resourceUrl, childStatus, { observe: 'response' });
  }

  update(childStatus: IChildStatus): Observable<EntityResponseType> {
    return this.http.put<IChildStatus>(`${this.resourceUrl}/${getChildStatusIdentifier(childStatus) as number}`, childStatus, {
      observe: 'response',
    });
  }

  partialUpdate(childStatus: IChildStatus): Observable<EntityResponseType> {
    return this.http.patch<IChildStatus>(`${this.resourceUrl}/${getChildStatusIdentifier(childStatus) as number}`, childStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChildStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChildStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addChildStatusToCollectionIfMissing(
    childStatusCollection: IChildStatus[],
    ...childStatusesToCheck: (IChildStatus | null | undefined)[]
  ): IChildStatus[] {
    const childStatuses: IChildStatus[] = childStatusesToCheck.filter(isPresent);
    if (childStatuses.length > 0) {
      const childStatusCollectionIdentifiers = childStatusCollection.map(childStatusItem => getChildStatusIdentifier(childStatusItem)!);
      const childStatusesToAdd = childStatuses.filter(childStatusItem => {
        const childStatusIdentifier = getChildStatusIdentifier(childStatusItem);
        if (childStatusIdentifier == null || childStatusCollectionIdentifiers.includes(childStatusIdentifier)) {
          return false;
        }
        childStatusCollectionIdentifiers.push(childStatusIdentifier);
        return true;
      });
      return [...childStatusesToAdd, ...childStatusCollection];
    }
    return childStatusCollection;
  }
}
