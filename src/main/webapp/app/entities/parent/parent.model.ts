import dayjs from 'dayjs/esm';
import { IFamily } from 'app/entities/family/family.model';
import { IProfile } from 'app/entities/profile/profile.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';

export interface IParent {
  id?: number;
  annualRevenue?: number;
  cnss?: number | null;
  maritalStatus?: MaritalStatus;
  occupation?: string;
  deceased?: boolean | null;
  dateOfDeath?: dayjs.Dayjs | null;
  familyHead?: IFamily | null;
  parentProfile?: IProfile | null;
  family?: IFamily;
}

export class Parent implements IParent {
  constructor(
    public id?: number,
    public annualRevenue?: number,
    public cnss?: number | null,
    public maritalStatus?: MaritalStatus,
    public occupation?: string,
    public deceased?: boolean | null,
    public dateOfDeath?: dayjs.Dayjs | null,
    public familyHead?: IFamily | null,
    public parentProfile?: IProfile | null,
    public family?: IFamily
  ) {
    this.deceased = this.deceased ?? false;
  }
}

export interface IParentAllDetails {
  id?: number;
  annualRevenue?: number;
  cnss?: number | null;
  maritalStatus?: MaritalStatus;
  occupation?: string;
  deceased?: boolean | null;
  dateOfDeath?: dayjs.Dayjs | null;
  familyHead?: IFamily | null;
  parentProfile?: IProfile | null;
  family?: IFamily;
  head?: boolean | null;
  profile?: IProfile;
}

export class ParentAllDetails implements IParentAllDetails {
  constructor(
    public id?: number,
    public annualRevenue?: number,
    public cnss?: number | null,
    public maritalStatus?: MaritalStatus,
    public occupation?: string,
    public deceased?: boolean | null,
    public dateOfDeath?: dayjs.Dayjs | null,
    public familyHead?: IFamily | null,
    public parentProfile?: IProfile | null,
    public family?: IFamily,
    public head?: boolean | null,
    public profile?: IProfile
  ) {
    this.deceased = this.deceased ?? false;
    this.head = this.head ?? false;
  }
}

export function getParentIdentifier(parent: IParent): number | undefined {
  return parent.id;
}
