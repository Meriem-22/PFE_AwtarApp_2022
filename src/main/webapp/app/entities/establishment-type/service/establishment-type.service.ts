import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstablishmentType, getEstablishmentTypeIdentifier } from '../establishment-type.model';

export type EntityResponseType = HttpResponse<IEstablishmentType>;
export type EntityArrayResponseType = HttpResponse<IEstablishmentType[]>;

@Injectable({ providedIn: 'root' })
export class EstablishmentTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/establishment-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(establishmentType: IEstablishmentType): Observable<EntityResponseType> {
    return this.http.post<IEstablishmentType>(this.resourceUrl, establishmentType, { observe: 'response' });
  }

  update(establishmentType: IEstablishmentType): Observable<EntityResponseType> {
    return this.http.put<IEstablishmentType>(
      `${this.resourceUrl}/${getEstablishmentTypeIdentifier(establishmentType) as number}`,
      establishmentType,
      { observe: 'response' }
    );
  }

  partialUpdate(establishmentType: IEstablishmentType): Observable<EntityResponseType> {
    return this.http.patch<IEstablishmentType>(
      `${this.resourceUrl}/${getEstablishmentTypeIdentifier(establishmentType) as number}`,
      establishmentType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstablishmentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstablishmentType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEstablishmentTypeToCollectionIfMissing(
    establishmentTypeCollection: IEstablishmentType[],
    ...establishmentTypesToCheck: (IEstablishmentType | null | undefined)[]
  ): IEstablishmentType[] {
    const establishmentTypes: IEstablishmentType[] = establishmentTypesToCheck.filter(isPresent);
    if (establishmentTypes.length > 0) {
      const establishmentTypeCollectionIdentifiers = establishmentTypeCollection.map(
        establishmentTypeItem => getEstablishmentTypeIdentifier(establishmentTypeItem)!
      );
      const establishmentTypesToAdd = establishmentTypes.filter(establishmentTypeItem => {
        const establishmentTypeIdentifier = getEstablishmentTypeIdentifier(establishmentTypeItem);
        if (establishmentTypeIdentifier == null || establishmentTypeCollectionIdentifiers.includes(establishmentTypeIdentifier)) {
          return false;
        }
        establishmentTypeCollectionIdentifiers.push(establishmentTypeIdentifier);
        return true;
      });
      return [...establishmentTypesToAdd, ...establishmentTypeCollection];
    }
    return establishmentTypeCollection;
  }
}
