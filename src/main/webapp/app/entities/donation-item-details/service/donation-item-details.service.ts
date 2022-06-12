import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDonationItemDetails, getDonationItemDetailsIdentifier } from '../donation-item-details.model';

export type EntityResponseType = HttpResponse<IDonationItemDetails>;
export type EntityArrayResponseType = HttpResponse<IDonationItemDetails[]>;

@Injectable({ providedIn: 'root' })
export class DonationItemDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/donation-item-details');
  protected Url = this.applicationConfigService.getEndpointFor('api/donation-item-details/donation-issued');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(donationItemDetails: IDonationItemDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationItemDetails);
    return this.http
      .post<IDonationItemDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(donationItemDetails: IDonationItemDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationItemDetails);
    return this.http
      .put<IDonationItemDetails>(`${this.resourceUrl}/${getDonationItemDetailsIdentifier(donationItemDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(donationItemDetails: IDonationItemDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationItemDetails);
    return this.http
      .patch<IDonationItemDetails>(`${this.resourceUrl}/${getDonationItemDetailsIdentifier(donationItemDetails) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  getAllDonationItemDetails(id: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IDonationItemDetails[]>(`${this.Url}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDonationItemDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDonationItemDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDonationItemDetailsToCollectionIfMissing(
    donationItemDetailsCollection: IDonationItemDetails[],
    ...donationItemDetailsToCheck: (IDonationItemDetails | null | undefined)[]
  ): IDonationItemDetails[] {
    const donationItemDetails: IDonationItemDetails[] = donationItemDetailsToCheck.filter(isPresent);
    if (donationItemDetails.length > 0) {
      const donationItemDetailsCollectionIdentifiers = donationItemDetailsCollection.map(
        donationItemDetailsItem => getDonationItemDetailsIdentifier(donationItemDetailsItem)!
      );
      const donationItemDetailsToAdd = donationItemDetails.filter(donationItemDetailsItem => {
        const donationItemDetailsIdentifier = getDonationItemDetailsIdentifier(donationItemDetailsItem);
        if (donationItemDetailsIdentifier == null || donationItemDetailsCollectionIdentifiers.includes(donationItemDetailsIdentifier)) {
          return false;
        }
        donationItemDetailsCollectionIdentifiers.push(donationItemDetailsIdentifier);
        return true;
      });
      return [...donationItemDetailsToAdd, ...donationItemDetailsCollection];
    }
    return donationItemDetailsCollection;
  }

  protected convertDateFromClient(donationItemDetails: IDonationItemDetails): IDonationItemDetails {
    return Object.assign({}, donationItemDetails, {
      date: donationItemDetails.date?.isValid() ? donationItemDetails.date.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((donationItemDetails: IDonationItemDetails) => {
        donationItemDetails.date = donationItemDetails.date ? dayjs(donationItemDetails.date) : undefined;
      });
    }
    return res;
  }
}
