import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDonationDetails, getDonationDetailsIdentifier } from '../donation-details.model';
import dayjs from 'dayjs';

export type EntityResponseType = HttpResponse<IDonationDetails>;
export type EntityArrayResponseType = HttpResponse<IDonationDetails[]>;
export type EntityArrayResponse = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class DonationDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/donation-details');
  protected Url = this.applicationConfigService.getEndpointFor('api/donation-details/beneficiary');
  protected Url2 = this.applicationConfigService.getEndpointFor('api/donation-details/beneficiary/donation-list');
  protected Url3 = this.applicationConfigService.getEndpointFor('api/donation-details/families-beneficiaries');
  protected Url4 = this.applicationConfigService.getEndpointFor('api/donation-details/establishments-beneficiaries');
  protected Url5 = this.applicationConfigService.getEndpointFor('api/donation-details/children-beneficiaries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(donationDetails: IDonationDetails): Observable<EntityResponseType> {
    return this.http.post<IDonationDetails>(this.resourceUrl, donationDetails, { observe: 'response' });
  }

  update(donationDetails: IDonationDetails): Observable<EntityResponseType> {
    return this.http.put<IDonationDetails>(
      `${this.resourceUrl}/${getDonationDetailsIdentifier(donationDetails) as number}`,
      donationDetails,
      { observe: 'response' }
    );
  }

  partialUpdate(donationDetails: IDonationDetails): Observable<EntityResponseType> {
    return this.http.patch<IDonationDetails>(
      `${this.resourceUrl}/${getDonationDetailsIdentifier(donationDetails) as number}`,
      donationDetails,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDonationDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDonationDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFamiliesDonationDetails(id: number): Observable<EntityArrayResponse> {
    return this.http.get<any[]>(`${this.Url3}/${id}`, { observe: 'response' });
  }

  getEstablishmentsDonationDetails(id: number): Observable<EntityArrayResponse> {
    return this.http.get<any[]>(`${this.Url4}/${id}`, { observe: 'response' });
  }

  getChildrenDonationDetails(id: number): Observable<EntityArrayResponse> {
    return this.http.get<any[]>(`${this.Url5}/${id}`, { observe: 'response' });
  }

  addDonationDetailsToCollectionIfMissing(
    donationDetailsCollection: IDonationDetails[],
    ...donationDetailsToCheck: (IDonationDetails | null | undefined)[]
  ): IDonationDetails[] {
    const donationDetails: IDonationDetails[] = donationDetailsToCheck.filter(isPresent);
    if (donationDetails.length > 0) {
      const donationDetailsCollectionIdentifiers = donationDetailsCollection.map(
        donationDetailsItem => getDonationDetailsIdentifier(donationDetailsItem)!
      );
      const donationDetailsToAdd = donationDetails.filter(donationDetailsItem => {
        const donationDetailsIdentifier = getDonationDetailsIdentifier(donationDetailsItem);
        if (donationDetailsIdentifier == null || donationDetailsCollectionIdentifiers.includes(donationDetailsIdentifier)) {
          return false;
        }
        donationDetailsCollectionIdentifiers.push(donationDetailsIdentifier);
        return true;
      });
      return [...donationDetailsToAdd, ...donationDetailsCollection];
    }
    return donationDetailsCollection;
  }

  getAllDonationsIssuedOfFamily(id: number): Observable<EntityArrayResponse> {
    return this.http
      .get<any[]>(`${this.Url2}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponse) => this.convertDateArrayFromServer(res)));
  }

  querygetAllDonationsIssuedOfFamily(id: number, req?: any): Observable<EntityArrayResponse> {
    const options = createRequestOption(req);
    return this.http.get<any[]>(`${this.Url}/${id}`, { params: options, observe: 'response' });
  }

  getAll(params: any): Observable<any> {
    return this.http.get<any>(this.Url, { params });
  }

  protected convertDateArrayFromServer(res: EntityArrayResponse): EntityArrayResponse {
    if (res.body) {
      res.body.forEach((donationsIssued: any) => {
        donationsIssued.validationDate = donationsIssued.validationDate ? dayjs(donationsIssued.validationDate) : undefined;
        donationsIssued.donationsDate = donationsIssued.donationsDate ? dayjs(donationsIssued.donationsDate) : undefined;
      });
    }
    return res;
  }
}
