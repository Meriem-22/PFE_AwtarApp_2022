import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeachingCurriculum, getTeachingCurriculumIdentifier } from '../teaching-curriculum.model';

export type EntityResponseType = HttpResponse<ITeachingCurriculum>;
export type EntityArrayResponseType = HttpResponse<ITeachingCurriculum[]>;

@Injectable({ providedIn: 'root' })
export class TeachingCurriculumService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/teaching-curricula');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(teachingCurriculum: ITeachingCurriculum): Observable<EntityResponseType> {
    return this.http.post<ITeachingCurriculum>(this.resourceUrl, teachingCurriculum, { observe: 'response' });
  }

  update(teachingCurriculum: ITeachingCurriculum): Observable<EntityResponseType> {
    return this.http.put<ITeachingCurriculum>(
      `${this.resourceUrl}/${getTeachingCurriculumIdentifier(teachingCurriculum) as number}`,
      teachingCurriculum,
      { observe: 'response' }
    );
  }

  partialUpdate(teachingCurriculum: ITeachingCurriculum): Observable<EntityResponseType> {
    return this.http.patch<ITeachingCurriculum>(
      `${this.resourceUrl}/${getTeachingCurriculumIdentifier(teachingCurriculum) as number}`,
      teachingCurriculum,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITeachingCurriculum>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITeachingCurriculum[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTeachingCurriculumToCollectionIfMissing(
    teachingCurriculumCollection: ITeachingCurriculum[],
    ...teachingCurriculaToCheck: (ITeachingCurriculum | null | undefined)[]
  ): ITeachingCurriculum[] {
    const teachingCurricula: ITeachingCurriculum[] = teachingCurriculaToCheck.filter(isPresent);
    if (teachingCurricula.length > 0) {
      const teachingCurriculumCollectionIdentifiers = teachingCurriculumCollection.map(
        teachingCurriculumItem => getTeachingCurriculumIdentifier(teachingCurriculumItem)!
      );
      const teachingCurriculaToAdd = teachingCurricula.filter(teachingCurriculumItem => {
        const teachingCurriculumIdentifier = getTeachingCurriculumIdentifier(teachingCurriculumItem);
        if (teachingCurriculumIdentifier == null || teachingCurriculumCollectionIdentifiers.includes(teachingCurriculumIdentifier)) {
          return false;
        }
        teachingCurriculumCollectionIdentifiers.push(teachingCurriculumIdentifier);
        return true;
      });
      return [...teachingCurriculaToAdd, ...teachingCurriculumCollection];
    }
    return teachingCurriculumCollection;
  }
}
