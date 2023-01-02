import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFamily, getFamilyIdentifier, IFamilyAllDetails, getFamilyAllDetailsIdentifier } from '../family.model';

export type EntityResponseType = HttpResponse<IFamily>;
export type EntityArrayResponseType = HttpResponse<IFamily[]>;
export type EntityResponse = HttpResponse<IFamilyAllDetails>;
export type EntityResponseTypeAny = HttpResponse<any>;

@Injectable({ providedIn: 'root' })
export class FamilyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/families');
  protected resourceUrl2 = this.applicationConfigService.getEndpointFor('api/families/place-of-residence');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(family: IFamilyAllDetails): Observable<EntityResponse> {
    return this.http.post<IFamilyAllDetails>(this.resourceUrl, family, { observe: 'response' });
  }

  update(family: IFamilyAllDetails): Observable<EntityResponse> {
    return this.http.put<IFamilyAllDetails>(`${this.resourceUrl}/${getFamilyIdentifier(family) as number}`, family, {
      observe: 'response',
    });
  }

  partialUpdate(family: IFamilyAllDetails): Observable<EntityResponse> {
    return this.http.patch<IFamily>(`${this.resourceUrl}/${getFamilyIdentifier(family) as number}`, family, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponse> {
    return this.http.get<IFamilyAllDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findFamily(): Observable<EntityArrayResponseType> {
    return this.http.get<IFamily[]>(this.resourceUrl + '/all', { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFamilyAllDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findNumberOfFamiliesByCity(id: number): Observable<EntityResponseTypeAny> {
    return this.http.get<any>(`${this.resourceUrl2}/${id}`, { observe: 'response' });
  }

  addFamilyToCollectionIfMissing(
    familyCollection: IFamilyAllDetails[],
    ...familiesToCheck: (IFamilyAllDetails | null | undefined)[]
  ): IFamilyAllDetails[] {
    const families: IFamilyAllDetails[] = familiesToCheck.filter(isPresent);
    if (families.length > 0) {
      const familyCollectionIdentifiers = familyCollection.map(familyItem => getFamilyAllDetailsIdentifier(familyItem)!);
      const familiesToAdd = families.filter(familyItem => {
        const familyIdentifier = getFamilyAllDetailsIdentifier(familyItem);
        if (familyIdentifier == null || familyCollectionIdentifiers.includes(familyIdentifier)) {
          return false;
        }
        familyCollectionIdentifiers.push(familyIdentifier);
        return true;
      });
      return [...familiesToAdd, ...familyCollection];
    }
    return familyCollection;
  }

  createFromFormFamily(family: IFamilyAllDetails): Observable<EntityResponse> {
    return this.http.post<IFamilyAllDetails>(this.resourceUrl + '/add', family, { observe: 'response' });
  }
}
