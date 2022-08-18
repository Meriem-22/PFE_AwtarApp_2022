import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstablishment, getEstablishmentIdentifier } from '../establishment.model';

export type EntityResponseType = HttpResponse<IEstablishment>;
export type EntityArrayResponseType = HttpResponse<IEstablishment[]>;

@Injectable({ providedIn: 'root' })
export class EstablishmentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/establishments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(establishment: IEstablishment): Observable<EntityResponseType> {
    return this.http.post<IEstablishment>(this.resourceUrl, establishment, { observe: 'response' });
  }

  update(establishment: IEstablishment): Observable<EntityResponseType> {
    return this.http.put<IEstablishment>(`${this.resourceUrl}/${getEstablishmentIdentifier(establishment) as number}`, establishment, {
      observe: 'response',
    });
  }

  partialUpdate(establishment: IEstablishment): Observable<EntityResponseType> {
    return this.http.patch<IEstablishment>(`${this.resourceUrl}/${getEstablishmentIdentifier(establishment) as number}`, establishment, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstablishment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findEstablishments(): Observable<EntityArrayResponseType> {
    return this.http.get<IEstablishment[]>(this.resourceUrl + '/all', { observe: 'response' });
  }

  add(establishment: IEstablishment): Observable<EntityResponseType> {
    return this.http.post<IEstablishment>(this.resourceUrl + '/add', establishment, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstablishment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEstablishmentToCollectionIfMissing(
    establishmentCollection: IEstablishment[],
    ...establishmentsToCheck: (IEstablishment | null | undefined)[]
  ): IEstablishment[] {
    const establishments: IEstablishment[] = establishmentsToCheck.filter(isPresent);
    if (establishments.length > 0) {
      const establishmentCollectionIdentifiers = establishmentCollection.map(
        establishmentItem => getEstablishmentIdentifier(establishmentItem)!
      );
      const establishmentsToAdd = establishments.filter(establishmentItem => {
        const establishmentIdentifier = getEstablishmentIdentifier(establishmentItem);
        if (establishmentIdentifier == null || establishmentCollectionIdentifiers.includes(establishmentIdentifier)) {
          return false;
        }
        establishmentCollectionIdentifiers.push(establishmentIdentifier);
        return true;
      });
      return [...establishmentsToAdd, ...establishmentCollection];
    }
    return establishmentCollection;
  }
}
