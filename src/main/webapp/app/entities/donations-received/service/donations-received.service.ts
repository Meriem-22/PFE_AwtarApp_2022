import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDonationsReceived, getDonationsReceivedIdentifier } from '../donations-received.model';

export type EntityResponseType = HttpResponse<IDonationsReceived>;
export type EntityArrayResponseType = HttpResponse<IDonationsReceived[]>;

@Injectable({ providedIn: 'root' })
export class DonationsReceivedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/donations-receiveds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(donationsReceived: IDonationsReceived): Observable<EntityResponseType> {
    return this.http.post<IDonationsReceived>(this.resourceUrl, donationsReceived, { observe: 'response' });
  }

  update(donationsReceived: IDonationsReceived): Observable<EntityResponseType> {
    return this.http.put<IDonationsReceived>(
      `${this.resourceUrl}/${getDonationsReceivedIdentifier(donationsReceived) as number}`,
      donationsReceived,
      { observe: 'response' }
    );
  }

  partialUpdate(donationsReceived: IDonationsReceived): Observable<EntityResponseType> {
    return this.http.patch<IDonationsReceived>(
      `${this.resourceUrl}/${getDonationsReceivedIdentifier(donationsReceived) as number}`,
      donationsReceived,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDonationsReceived>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDonationsReceived[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDonationsReceivedToCollectionIfMissing(
    donationsReceivedCollection: IDonationsReceived[],
    ...donationsReceivedsToCheck: (IDonationsReceived | null | undefined)[]
  ): IDonationsReceived[] {
    const donationsReceiveds: IDonationsReceived[] = donationsReceivedsToCheck.filter(isPresent);
    if (donationsReceiveds.length > 0) {
      const donationsReceivedCollectionIdentifiers = donationsReceivedCollection.map(
        donationsReceivedItem => getDonationsReceivedIdentifier(donationsReceivedItem)!
      );
      const donationsReceivedsToAdd = donationsReceiveds.filter(donationsReceivedItem => {
        const donationsReceivedIdentifier = getDonationsReceivedIdentifier(donationsReceivedItem);
        if (donationsReceivedIdentifier == null || donationsReceivedCollectionIdentifiers.includes(donationsReceivedIdentifier)) {
          return false;
        }
        donationsReceivedCollectionIdentifiers.push(donationsReceivedIdentifier);
        return true;
      });
      return [...donationsReceivedsToAdd, ...donationsReceivedCollection];
    }
    return donationsReceivedCollection;
  }
}
