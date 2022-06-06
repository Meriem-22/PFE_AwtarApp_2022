import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDonationDetails, getDonationDetailsIdentifier } from '../donation-details.model';

export type EntityResponseType = HttpResponse<IDonationDetails>;
export type EntityArrayResponseType = HttpResponse<IDonationDetails[]>;

@Injectable({ providedIn: 'root' })
export class DonationDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/donation-details');

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
}
