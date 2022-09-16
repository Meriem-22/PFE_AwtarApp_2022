import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISchoolLevelItem, getSchoolLevelItemIdentifier } from '../school-level-item.model';

export type EntityResponseType = HttpResponse<ISchoolLevelItem>;
export type EntityArrayResponseType = HttpResponse<ISchoolLevelItem[]>;

@Injectable({ providedIn: 'root' })
export class SchoolLevelItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/school-level-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(schoolLevelItem: ISchoolLevelItem): Observable<EntityResponseType> {
    return this.http.post<ISchoolLevelItem>(this.resourceUrl, schoolLevelItem, { observe: 'response' });
  }

  update(schoolLevelItem: ISchoolLevelItem): Observable<EntityResponseType> {
    return this.http.put<ISchoolLevelItem>(
      `${this.resourceUrl}/${getSchoolLevelItemIdentifier(schoolLevelItem) as number}`,
      schoolLevelItem,
      { observe: 'response' }
    );
  }

  partialUpdate(schoolLevelItem: ISchoolLevelItem): Observable<EntityResponseType> {
    return this.http.patch<ISchoolLevelItem>(
      `${this.resourceUrl}/${getSchoolLevelItemIdentifier(schoolLevelItem) as number}`,
      schoolLevelItem,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISchoolLevelItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISchoolLevelItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAllCompositeurSchoolItems(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<ISchoolLevelItem[]>(`${this.resourceUrl}/${id}` + '/compositeur-school-items', { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSchoolLevelItemToCollectionIfMissing(
    schoolLevelItemCollection: ISchoolLevelItem[],
    ...schoolLevelItemsToCheck: (ISchoolLevelItem | null | undefined)[]
  ): ISchoolLevelItem[] {
    const schoolLevelItems: ISchoolLevelItem[] = schoolLevelItemsToCheck.filter(isPresent);
    if (schoolLevelItems.length > 0) {
      const schoolLevelItemCollectionIdentifiers = schoolLevelItemCollection.map(
        schoolLevelItemItem => getSchoolLevelItemIdentifier(schoolLevelItemItem)!
      );
      const schoolLevelItemsToAdd = schoolLevelItems.filter(schoolLevelItemItem => {
        const schoolLevelItemIdentifier = getSchoolLevelItemIdentifier(schoolLevelItemItem);
        if (schoolLevelItemIdentifier == null || schoolLevelItemCollectionIdentifiers.includes(schoolLevelItemIdentifier)) {
          return false;
        }
        schoolLevelItemCollectionIdentifiers.push(schoolLevelItemIdentifier);
        return true;
      });
      return [...schoolLevelItemsToAdd, ...schoolLevelItemCollection];
    }
    return schoolLevelItemCollection;
  }
}
