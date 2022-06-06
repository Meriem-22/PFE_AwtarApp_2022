import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHealthStatusCategory, getHealthStatusCategoryIdentifier } from '../health-status-category.model';

export type EntityResponseType = HttpResponse<IHealthStatusCategory>;
export type EntityArrayResponseType = HttpResponse<IHealthStatusCategory[]>;

@Injectable({ providedIn: 'root' })
export class HealthStatusCategoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/health-status-categories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(healthStatusCategory: IHealthStatusCategory): Observable<EntityResponseType> {
    return this.http.post<IHealthStatusCategory>(this.resourceUrl, healthStatusCategory, { observe: 'response' });
  }

  update(healthStatusCategory: IHealthStatusCategory): Observable<EntityResponseType> {
    return this.http.put<IHealthStatusCategory>(
      `${this.resourceUrl}/${getHealthStatusCategoryIdentifier(healthStatusCategory) as number}`,
      healthStatusCategory,
      { observe: 'response' }
    );
  }

  partialUpdate(healthStatusCategory: IHealthStatusCategory): Observable<EntityResponseType> {
    return this.http.patch<IHealthStatusCategory>(
      `${this.resourceUrl}/${getHealthStatusCategoryIdentifier(healthStatusCategory) as number}`,
      healthStatusCategory,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHealthStatusCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHealthStatusCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHealthStatusCategoryToCollectionIfMissing(
    healthStatusCategoryCollection: IHealthStatusCategory[],
    ...healthStatusCategoriesToCheck: (IHealthStatusCategory | null | undefined)[]
  ): IHealthStatusCategory[] {
    const healthStatusCategories: IHealthStatusCategory[] = healthStatusCategoriesToCheck.filter(isPresent);
    if (healthStatusCategories.length > 0) {
      const healthStatusCategoryCollectionIdentifiers = healthStatusCategoryCollection.map(
        healthStatusCategoryItem => getHealthStatusCategoryIdentifier(healthStatusCategoryItem)!
      );
      const healthStatusCategoriesToAdd = healthStatusCategories.filter(healthStatusCategoryItem => {
        const healthStatusCategoryIdentifier = getHealthStatusCategoryIdentifier(healthStatusCategoryItem);
        if (healthStatusCategoryIdentifier == null || healthStatusCategoryCollectionIdentifiers.includes(healthStatusCategoryIdentifier)) {
          return false;
        }
        healthStatusCategoryCollectionIdentifiers.push(healthStatusCategoryIdentifier);
        return true;
      });
      return [...healthStatusCategoriesToAdd, ...healthStatusCategoryCollection];
    }
    return healthStatusCategoryCollection;
  }
}
