import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAuthorizingOfficer, getAuthorizingOfficerIdentifier } from '../authorizing-officer.model';

export type EntityResponseType = HttpResponse<IAuthorizingOfficer>;
export type EntityArrayResponseType = HttpResponse<IAuthorizingOfficer[]>;

@Injectable({ providedIn: 'root' })
export class AuthorizingOfficerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/authorizing-officers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(authorizingOfficer: IAuthorizingOfficer): Observable<EntityResponseType> {
    return this.http.post<IAuthorizingOfficer>(this.resourceUrl, authorizingOfficer, { observe: 'response' });
  }

  update(authorizingOfficer: IAuthorizingOfficer): Observable<EntityResponseType> {
    return this.http.put<IAuthorizingOfficer>(
      `${this.resourceUrl}/${getAuthorizingOfficerIdentifier(authorizingOfficer) as number}`,
      authorizingOfficer,
      { observe: 'response' }
    );
  }

  partialUpdate(authorizingOfficer: IAuthorizingOfficer): Observable<EntityResponseType> {
    return this.http.patch<IAuthorizingOfficer>(
      `${this.resourceUrl}/${getAuthorizingOfficerIdentifier(authorizingOfficer) as number}`,
      authorizingOfficer,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAuthorizingOfficer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAuthorizingOfficer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  add(AuthorizingOfficer: IAuthorizingOfficer): Observable<EntityResponseType> {
    return this.http.post<IAuthorizingOfficer>(this.resourceUrl + '/add', AuthorizingOfficer, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAuthorizingOfficerToCollectionIfMissing(
    authorizingOfficerCollection: IAuthorizingOfficer[],
    ...authorizingOfficersToCheck: (IAuthorizingOfficer | null | undefined)[]
  ): IAuthorizingOfficer[] {
    const authorizingOfficers: IAuthorizingOfficer[] = authorizingOfficersToCheck.filter(isPresent);
    if (authorizingOfficers.length > 0) {
      const authorizingOfficerCollectionIdentifiers = authorizingOfficerCollection.map(
        authorizingOfficerItem => getAuthorizingOfficerIdentifier(authorizingOfficerItem)!
      );
      const authorizingOfficersToAdd = authorizingOfficers.filter(authorizingOfficerItem => {
        const authorizingOfficerIdentifier = getAuthorizingOfficerIdentifier(authorizingOfficerItem);
        if (authorizingOfficerIdentifier == null || authorizingOfficerCollectionIdentifiers.includes(authorizingOfficerIdentifier)) {
          return false;
        }
        authorizingOfficerCollectionIdentifiers.push(authorizingOfficerIdentifier);
        return true;
      });
      return [...authorizingOfficersToAdd, ...authorizingOfficerCollection];
    }
    return authorizingOfficerCollection;
  }
}
