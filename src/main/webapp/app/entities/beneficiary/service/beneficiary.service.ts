import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBeneficiary, getBeneficiaryIdentifier } from '../beneficiary.model';

export type EntityResponseType = HttpResponse<IBeneficiary>;
export type EntityArrayResponseType = HttpResponse<IBeneficiary[]>;

@Injectable({ providedIn: 'root' })
export class BeneficiaryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/beneficiaries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(beneficiary: IBeneficiary): Observable<EntityResponseType> {
    return this.http.post<IBeneficiary>(this.resourceUrl, beneficiary, { observe: 'response' });
  }

  update(beneficiary: IBeneficiary): Observable<EntityResponseType> {
    return this.http.put<IBeneficiary>(`${this.resourceUrl}/${getBeneficiaryIdentifier(beneficiary) as number}`, beneficiary, {
      observe: 'response',
    });
  }

  partialUpdate(beneficiary: IBeneficiary): Observable<EntityResponseType> {
    return this.http.patch<IBeneficiary>(`${this.resourceUrl}/${getBeneficiaryIdentifier(beneficiary) as number}`, beneficiary, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBeneficiary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeneficiary[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBeneficiaryToCollectionIfMissing(
    beneficiaryCollection: IBeneficiary[],
    ...beneficiariesToCheck: (IBeneficiary | null | undefined)[]
  ): IBeneficiary[] {
    const beneficiaries: IBeneficiary[] = beneficiariesToCheck.filter(isPresent);
    if (beneficiaries.length > 0) {
      const beneficiaryCollectionIdentifiers = beneficiaryCollection.map(beneficiaryItem => getBeneficiaryIdentifier(beneficiaryItem)!);
      const beneficiariesToAdd = beneficiaries.filter(beneficiaryItem => {
        const beneficiaryIdentifier = getBeneficiaryIdentifier(beneficiaryItem);
        if (beneficiaryIdentifier == null || beneficiaryCollectionIdentifiers.includes(beneficiaryIdentifier)) {
          return false;
        }
        beneficiaryCollectionIdentifiers.push(beneficiaryIdentifier);
        return true;
      });
      return [...beneficiariesToAdd, ...beneficiaryCollection];
    }
    return beneficiaryCollection;
  }
}
