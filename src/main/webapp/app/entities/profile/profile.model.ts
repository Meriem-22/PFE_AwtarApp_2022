import dayjs from 'dayjs/esm';
import { IParent } from 'app/entities/parent/parent.model';
import { IChild } from 'app/entities/child/child.model';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { IStatusOfHealth } from 'app/entities/status-of-health/status-of-health.model';
import { ICity } from 'app/entities/city/city.model';
import { Gender } from 'app/entities/enumerations/gender.model';

export interface IProfile {
  id?: number;
  firstName?: string;
  lastName?: string;
  firstNameArabic?: string | null;
  lastNameArabic?: string | null;
  gender?: Gender;
  dateOfBirth?: dayjs.Dayjs;
  cin?: string | null;
  urlPhotoContentType?: string | null;
  urlPhoto?: string | null;
  address?: string | null;
  phone?: string | null;
  email?: string | null;
  urlCinAttachedContentType?: string | null;
  urlCinAttached?: string | null;
  archivated?: boolean | null;
  parent?: IParent | null;
  child?: IChild | null;
  authorizingOfficer?: IAuthorizingOfficer | null;
  tutor?: ITutor | null;
  statusOfHealths?: IStatusOfHealth[] | null;
  birthPlace?: ICity;
  placeOfResidence?: ICity;
}

export class Profile implements IProfile {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public firstNameArabic?: string | null,
    public lastNameArabic?: string | null,
    public gender?: Gender,
    public dateOfBirth?: dayjs.Dayjs,
    public cin?: string | null,
    public urlPhotoContentType?: string | null,
    public urlPhoto?: string | null,
    public address?: string | null,
    public phone?: string | null,
    public email?: string | null,
    public urlCinAttachedContentType?: string | null,
    public urlCinAttached?: string | null,
    public archivated?: boolean | null,
    public parent?: IParent | null,
    public child?: IChild | null,
    public authorizingOfficer?: IAuthorizingOfficer | null,
    public tutor?: ITutor | null,
    public statusOfHealths?: IStatusOfHealth[] | null,
    public birthPlace?: ICity,
    public placeOfResidence?: ICity
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getProfileIdentifier(profile: IProfile): number | undefined {
  return profile.id;
}
