import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBeneficiary, getBeneficiaryIdentifier } from '../beneficiary.model';
import { IProfile } from 'app/entities/profile/profile.model';

export type EntityResponseType = HttpResponse<IBeneficiary>;
export type EntityArrayResponseType = HttpResponse<IBeneficiary[]>;
export type EntityArrayResponseTypex = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class BeneficiaryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/beneficiaries');
  protected UrlA = this.applicationConfigService.getEndpointFor('api/beneficiaries/contributor/authorizing-officer');
  protected UrlT = this.applicationConfigService.getEndpointFor('api/beneficiaries/contributor/tutor');
  protected UrlF = this.applicationConfigService.getEndpointFor('api/beneficiaries/families');
  protected UrlE = this.applicationConfigService.getEndpointFor('api/beneficiaries/establishments');
  protected UrlC = this.applicationConfigService.getEndpointFor('api/beneficiaries/children');
  protected UrlRA = this.applicationConfigService.getEndpointFor('api/beneficiaries/remove-authorizing-officer');
  protected UrlRT = this.applicationConfigService.getEndpointFor('api/beneficiaries/tutor');

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

  updateAuthorizingOfficer(beneficiary: IBeneficiary): Observable<EntityResponseType> {
    return this.http.put<IBeneficiary>(`${this.UrlA}/${getBeneficiaryIdentifier(beneficiary) as number}`, beneficiary, {
      observe: 'response',
    });
  }

  updateTutor(beneficiary: IBeneficiary): Observable<EntityResponseType> {
    return this.http.put<IBeneficiary>(`${this.UrlT}/${getBeneficiaryIdentifier(beneficiary) as number}`, beneficiary, {
      observe: 'response',
    });
  }

  findBeneficiaryP(id: number): Observable<HttpResponse<IProfile>> {
    return this.http.get<IBeneficiary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAllFamilies(id: number): Observable<EntityArrayResponseTypex> {
    return this.http.get<any[]>(`${this.UrlF}/${id}`, { observe: 'response' });
  }

  findAllChildren(id: number): Observable<EntityArrayResponseTypex> {
    return this.http.get<any[]>(`${this.UrlC}/${id}`, { observe: 'response' });
  }

  findAllEstablishments(id: number): Observable<EntityArrayResponseTypex> {
    return this.http.get<any[]>(`${this.UrlE}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeneficiary[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  removeBeneficiaryAuthorizingOfficer(beneficiary: IBeneficiary): Observable<EntityResponseType> {
    return this.http.put<IBeneficiary>(`${this.UrlRA}/${getBeneficiaryIdentifier(beneficiary) as number}`, beneficiary, {
      observe: 'response',
    });
  }

  removeBeneficiaryTutor(beneficiary: IBeneficiary): Observable<EntityResponseType> {
    return this.http.put<IBeneficiary>(`${this.UrlRT}/${getBeneficiaryIdentifier(beneficiary) as number}`, beneficiary, {
      observe: 'response',
    });
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
