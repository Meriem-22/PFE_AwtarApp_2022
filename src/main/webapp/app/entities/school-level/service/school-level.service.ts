import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISchoolLevel, getSchoolLevelIdentifier } from '../school-level.model';

export type EntityResponseType = HttpResponse<ISchoolLevel>;
export type EntityArrayResponseType = HttpResponse<ISchoolLevel[]>;

@Injectable({ providedIn: 'root' })
export class SchoolLevelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/school-levels');
  protected Url = this.applicationConfigService.getEndpointFor('api/school-levels/alls');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(schoolLevel: ISchoolLevel): Observable<EntityResponseType> {
    return this.http.post<ISchoolLevel>(this.resourceUrl, schoolLevel, { observe: 'response' });
  }

  update(schoolLevel: ISchoolLevel): Observable<EntityResponseType> {
    return this.http.put<ISchoolLevel>(`${this.resourceUrl}/${getSchoolLevelIdentifier(schoolLevel) as number}`, schoolLevel, {
      observe: 'response',
    });
  }

  partialUpdate(schoolLevel: ISchoolLevel): Observable<EntityResponseType> {
    return this.http.patch<ISchoolLevel>(`${this.resourceUrl}/${getSchoolLevelIdentifier(schoolLevel) as number}`, schoolLevel, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISchoolLevel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAllSchoolLevel(): Observable<EntityArrayResponseType> {
    return this.http.get<ISchoolLevel[]>(this.Url, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISchoolLevel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSchoolLevelToCollectionIfMissing(
    schoolLevelCollection: ISchoolLevel[],
    ...schoolLevelsToCheck: (ISchoolLevel | null | undefined)[]
  ): ISchoolLevel[] {
    const schoolLevels: ISchoolLevel[] = schoolLevelsToCheck.filter(isPresent);
    if (schoolLevels.length > 0) {
      const schoolLevelCollectionIdentifiers = schoolLevelCollection.map(schoolLevelItem => getSchoolLevelIdentifier(schoolLevelItem)!);
      const schoolLevelsToAdd = schoolLevels.filter(schoolLevelItem => {
        const schoolLevelIdentifier = getSchoolLevelIdentifier(schoolLevelItem);
        if (schoolLevelIdentifier == null || schoolLevelCollectionIdentifiers.includes(schoolLevelIdentifier)) {
          return false;
        }
        schoolLevelCollectionIdentifiers.push(schoolLevelIdentifier);
        return true;
      });
      return [...schoolLevelsToAdd, ...schoolLevelCollection];
    }
    return schoolLevelCollection;
  }
}
