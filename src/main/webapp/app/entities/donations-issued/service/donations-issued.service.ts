import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDonationsIssued, getDonationsIssuedIdentifier } from '../donations-issued.model';

export type EntityResponseType = HttpResponse<IDonationsIssued>;
export type EntityArrayResponseType = HttpResponse<IDonationsIssued[]>;
export type EntityArrayResponseTypeAny = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class DonationsIssuedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/donations-issueds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(donationsIssued: IDonationsIssued): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationsIssued);
    return this.http
      .post<IDonationsIssued>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(donationsIssued: IDonationsIssued): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationsIssued);
    return this.http
      .put<IDonationsIssued>(`${this.resourceUrl}/${getDonationsIssuedIdentifier(donationsIssued) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  createDons(donationsIssued: IDonationsIssued): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationsIssued);
    return this.http
      .post<IDonationsIssued>(this.resourceUrl + '/add', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  getAllValidatedDonationsIssued(): Observable<EntityArrayResponseType> {
    return this.http.get<IDonationsIssued[]>(this.resourceUrl + '/validated', { observe: 'response' });
  }

  Upcomingscheduleddonations(): Observable<EntityArrayResponseType> {
    return this.http.get<IDonationsIssued[]>(this.resourceUrl + '/upcoming-scheduled-donations', { observe: 'response' });
  }

  UpcomingDonationsValidated(): Observable<EntityArrayResponseType> {
    return this.http.get<IDonationsIssued[]>(this.resourceUrl + '/upcoming-validated-donations', { observe: 'response' });
  }

  DonationsIssuedOfCurrentYearByMonth(): Observable<EntityArrayResponseTypeAny> {
    return this.http.get<any[]>(this.resourceUrl + '/by-month', { observe: 'response' });
  }

  CanceledDonationsOfCurrentYearByMonth(): Observable<EntityArrayResponseTypeAny> {
    return this.http.get<any[]>(this.resourceUrl + '/canceled-by-month', { observe: 'response' });
  }

  getAll(): Observable<EntityArrayResponseTypeAny> {
    return this.http.get<any[]>(this.resourceUrl + '/get-all', { observe: 'response' });
  }

  partialUpdate(donationsIssued: IDonationsIssued): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationsIssued);
    return this.http
      .patch<IDonationsIssued>(`${this.resourceUrl}/${getDonationsIssuedIdentifier(donationsIssued) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDonationsIssued>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDonationsIssued[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDonationsIssuedToCollectionIfMissing(
    donationsIssuedCollection: IDonationsIssued[],
    ...donationsIssuedsToCheck: (IDonationsIssued | null | undefined)[]
  ): IDonationsIssued[] {
    const donationsIssueds: IDonationsIssued[] = donationsIssuedsToCheck.filter(isPresent);
    if (donationsIssueds.length > 0) {
      const donationsIssuedCollectionIdentifiers = donationsIssuedCollection.map(
        donationsIssuedItem => getDonationsIssuedIdentifier(donationsIssuedItem)!
      );
      const donationsIssuedsToAdd = donationsIssueds.filter(donationsIssuedItem => {
        const donationsIssuedIdentifier = getDonationsIssuedIdentifier(donationsIssuedItem);
        if (donationsIssuedIdentifier == null || donationsIssuedCollectionIdentifiers.includes(donationsIssuedIdentifier)) {
          return false;
        }
        donationsIssuedCollectionIdentifiers.push(donationsIssuedIdentifier);
        return true;
      });
      return [...donationsIssuedsToAdd, ...donationsIssuedCollection];
    }
    return donationsIssuedCollection;
  }

  protected convertDateFromClient(donationsIssued: IDonationsIssued): IDonationsIssued {
    return Object.assign({}, donationsIssued, {
      validationDate: donationsIssued.validationDate?.isValid() ? donationsIssued.validationDate.format(DATE_FORMAT) : undefined,
      donationsDate: donationsIssued.donationsDate?.isValid() ? donationsIssued.donationsDate.format(DATE_FORMAT) : undefined,
      canceledOn: donationsIssued.canceledOn?.isValid() ? donationsIssued.canceledOn.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validationDate = res.body.validationDate ? dayjs(res.body.validationDate) : undefined;
      res.body.donationsDate = res.body.donationsDate ? dayjs(res.body.donationsDate) : undefined;
      res.body.canceledOn = res.body.canceledOn ? dayjs(res.body.canceledOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((donationsIssued: IDonationsIssued) => {
        donationsIssued.validationDate = donationsIssued.validationDate ? dayjs(donationsIssued.validationDate) : undefined;
        donationsIssued.donationsDate = donationsIssued.donationsDate ? dayjs(donationsIssued.donationsDate) : undefined;
        donationsIssued.canceledOn = donationsIssued.canceledOn ? dayjs(donationsIssued.canceledOn) : undefined;
      });
    }
    return res;
  }
}
