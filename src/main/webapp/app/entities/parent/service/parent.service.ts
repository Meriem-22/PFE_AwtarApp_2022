import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParent, getParentIdentifier, IParentAllDetails } from '../parent.model';

export type EntityResponseType = HttpResponse<IParent>;
export type EntityArrayResponseType = HttpResponse<IParent[]>;
export type EntityResponseTyp = HttpResponse<IParentAllDetails>;

@Injectable({ providedIn: 'root' })
export class ParentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(parent: IParent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parent);
    return this.http
      .post<IParent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(parent: IParent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parent);
    return this.http
      .put<IParent>(`${this.resourceUrl}/${getParentIdentifier(parent) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(parent: IParent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parent);
    return this.http
      .patch<IParent>(`${this.resourceUrl}/${getParentIdentifier(parent) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  findA(id: number): Observable<EntityResponseTyp> {
    return this.http
      .get<IParent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addParent(parent: IParentAllDetails): Observable<EntityResponseType> {
    return this.http.post<IParentAllDetails>(this.resourceUrl + '/add', parent, { observe: 'response' });
  }

  addParentToCollectionIfMissing(parentCollection: IParent[], ...parentsToCheck: (IParent | null | undefined)[]): IParent[] {
    const parents: IParent[] = parentsToCheck.filter(isPresent);
    if (parents.length > 0) {
      const parentCollectionIdentifiers = parentCollection.map(parentItem => getParentIdentifier(parentItem)!);
      const parentsToAdd = parents.filter(parentItem => {
        const parentIdentifier = getParentIdentifier(parentItem);
        if (parentIdentifier == null || parentCollectionIdentifiers.includes(parentIdentifier)) {
          return false;
        }
        parentCollectionIdentifiers.push(parentIdentifier);
        return true;
      });
      return [...parentsToAdd, ...parentCollection];
    }
    return parentCollection;
  }

  protected convertDateFromClient(parent: IParent): IParent {
    return Object.assign({}, parent, {
      dateOfDeath: parent.dateOfDeath?.isValid() ? parent.dateOfDeath.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfDeath = res.body.dateOfDeath ? dayjs(res.body.dateOfDeath) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((parent: IParent) => {
        parent.dateOfDeath = parent.dateOfDeath ? dayjs(parent.dateOfDeath) : undefined;
      });
    }
    return res;
  }
}
