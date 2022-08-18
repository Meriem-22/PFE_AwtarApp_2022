import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProfile, getProfileIdentifier } from '../profile.model';

export type EntityResponseType = HttpResponse<IProfile>;
export type EntityArrayResponseType = HttpResponse<IProfile[]>;
export type EntityArrayResponse = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class ProfileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/profiles');
  protected Url = this.applicationConfigService.getEndpointFor('api/profiles/parents');
  protected UrlChildren = this.applicationConfigService.getEndpointFor('api/profiles/children');
  protected resourceUrlA = this.applicationConfigService.getEndpointFor('api/profiles/x');
  protected resourceA = this.applicationConfigService.getEndpointFor('api/profiles/authorizing-officers');
  protected resourceT = this.applicationConfigService.getEndpointFor('api/profiles/tutors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(profile: IProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(profile);
    return this.http
      .post<IProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(profile: IProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(profile);
    return this.http
      .put<IProfile>(`${this.resourceUrl}/${getProfileIdentifier(profile) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(profile: IProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(profile);
    return this.http
      .patch<IProfile>(`${this.resourceUrl}/${getProfileIdentifier(profile) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findProfile(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProfile>(`${this.resourceUrlA}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findOthersTutorsProfiles(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IProfile[]>(`${this.resourceT}/${id}`, { observe: 'response' });
  }

  findOthersAuthaurizingOfficersProfiles(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IProfile[]>(`${this.resourceA}/${id}`, { observe: 'response' });
  }

  findProfiles(): Observable<EntityArrayResponseType> {
    return this.http.get<IProfile[]>(this.resourceUrl + '/children', { observe: 'response' });
  }

  findTutorsProfiles(): Observable<EntityArrayResponseType> {
    return this.http.get<IProfile[]>(this.resourceUrl + '/tutors', { observe: 'response' });
  }

  findAuthorizingOfficerProfiles(): Observable<EntityArrayResponseType> {
    return this.http.get<IProfile[]>(this.resourceUrl + '/authorizingOfficers', { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAllParentsProfile(id: number): Observable<EntityArrayResponse> {
    return this.http
      .get<any[]>(`${this.Url}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponse) => this.convertDateArrayFromServer(res)));
  }

  getAllChildrenProfile(id: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IProfile[]>(`${this.UrlChildren}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addProfileToCollectionIfMissing(profileCollection: IProfile[], ...profilesToCheck: (IProfile | null | undefined)[]): IProfile[] {
    const profiles: IProfile[] = profilesToCheck.filter(isPresent);
    if (profiles.length > 0) {
      const profileCollectionIdentifiers = profileCollection.map(profileItem => getProfileIdentifier(profileItem)!);
      const profilesToAdd = profiles.filter(profileItem => {
        const profileIdentifier = getProfileIdentifier(profileItem);
        if (profileIdentifier == null || profileCollectionIdentifiers.includes(profileIdentifier)) {
          return false;
        }
        profileCollectionIdentifiers.push(profileIdentifier);
        return true;
      });
      return [...profilesToAdd, ...profileCollection];
    }
    return profileCollection;
  }

  protected convertDateFromClient(profile: IProfile): IProfile {
    return Object.assign({}, profile, {
      dateOfBirth: profile.dateOfBirth?.isValid() ? profile.dateOfBirth.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? dayjs(res.body.dateOfBirth) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((profile: IProfile) => {
        profile.dateOfBirth = profile.dateOfBirth ? dayjs(profile.dateOfBirth) : undefined;
      });
    }
    return res;
  }
}
