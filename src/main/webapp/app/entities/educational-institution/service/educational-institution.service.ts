import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEducationalInstitution, getEducationalInstitutionIdentifier } from '../educational-institution.model';

export type EntityResponseType = HttpResponse<IEducationalInstitution>;
export type EntityArrayResponseType = HttpResponse<IEducationalInstitution[]>;

@Injectable({ providedIn: 'root' })
export class EducationalInstitutionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/educational-institutions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(educationalInstitution: IEducationalInstitution): Observable<EntityResponseType> {
    return this.http.post<IEducationalInstitution>(this.resourceUrl, educationalInstitution, { observe: 'response' });
  }

  update(educationalInstitution: IEducationalInstitution): Observable<EntityResponseType> {
    return this.http.put<IEducationalInstitution>(
      `${this.resourceUrl}/${getEducationalInstitutionIdentifier(educationalInstitution) as number}`,
      educationalInstitution,
      { observe: 'response' }
    );
  }

  partialUpdate(educationalInstitution: IEducationalInstitution): Observable<EntityResponseType> {
    return this.http.patch<IEducationalInstitution>(
      `${this.resourceUrl}/${getEducationalInstitutionIdentifier(educationalInstitution) as number}`,
      educationalInstitution,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEducationalInstitution>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEducationalInstitution[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEducationalInstitutionToCollectionIfMissing(
    educationalInstitutionCollection: IEducationalInstitution[],
    ...educationalInstitutionsToCheck: (IEducationalInstitution | null | undefined)[]
  ): IEducationalInstitution[] {
    const educationalInstitutions: IEducationalInstitution[] = educationalInstitutionsToCheck.filter(isPresent);
    if (educationalInstitutions.length > 0) {
      const educationalInstitutionCollectionIdentifiers = educationalInstitutionCollection.map(
        educationalInstitutionItem => getEducationalInstitutionIdentifier(educationalInstitutionItem)!
      );
      const educationalInstitutionsToAdd = educationalInstitutions.filter(educationalInstitutionItem => {
        const educationalInstitutionIdentifier = getEducationalInstitutionIdentifier(educationalInstitutionItem);
        if (
          educationalInstitutionIdentifier == null ||
          educationalInstitutionCollectionIdentifiers.includes(educationalInstitutionIdentifier)
        ) {
          return false;
        }
        educationalInstitutionCollectionIdentifiers.push(educationalInstitutionIdentifier);
        return true;
      });
      return [...educationalInstitutionsToAdd, ...educationalInstitutionCollection];
    }
    return educationalInstitutionCollection;
  }
}
