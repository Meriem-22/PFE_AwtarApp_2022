import { IParent, IParentAllDetails } from 'app/entities/parent/parent.model';
import { IChild, IChildAllDetails } from 'app/entities/child/child.model';
import { IBeneficiary } from '../beneficiary/beneficiary.model';
import { IAuthorizingOfficer } from '../authorizing-officer/authorizing-officer.model';
import { ITutor } from '../tutor/tutor.model';

export interface IFamily {
  id?: number;
  familyName?: string;
  dwelling?: string;
  area?: string | null;
  notebookOfPoverty?: boolean | null;
  notebookOfHandicap?: boolean | null;
  archivated?: boolean | null;
  parents?: IParent[] | null;
  children?: IChild[] | null;
  head?: IParent | null;
}

export class Family implements IFamily {
  constructor(
    public id?: number,
    public familyName?: string,
    public dwelling?: string,
    public area?: string | null,
    public notebookOfPoverty?: boolean | null,
    public notebookOfHandicap?: boolean | null,
    public archivated?: boolean | null,
    public parents?: IParent[] | null,
    public children?: IChild[] | null,
    public head?: IParent | null
  ) {
    this.notebookOfPoverty = this.notebookOfPoverty ?? false;
    this.notebookOfHandicap = this.notebookOfHandicap ?? false;
    this.archivated = this.archivated ?? false;
  }
}

export interface IFamilyAllDetails {
  id?: number;
  familyName?: string;
  dwelling?: string;
  area?: string | null;
  notebookOfPoverty?: boolean | null;
  notebookOfHandicap?: boolean | null;
  archivated?: boolean | null;
  authorizingOfficer?: IAuthorizingOfficer | null;
  tutor?: ITutor | null;
  parents?: IParent[] | null;
  children?: IChild[] | null;
  head?: IParentAllDetails | null;
  beneficiary?: IBeneficiary;
  parentsDetails?: IParentAllDetails[] | null;
  childrenDetails?: IChildAllDetails[] | null;
}

export class FamilyAllDetails implements IFamilyAllDetails {
  constructor(
    public id?: number,
    public familyName?: string,
    public dwelling?: string,
    public area?: string | null,
    public notebookOfPoverty?: boolean | null,
    public notebookOfHandicap?: boolean | null,
    public archivated?: boolean | null,
    public authorizingOfficer?: IAuthorizingOfficer | null,
    public tutor?: ITutor | null,
    public parents?: IParent[] | null,
    public children?: IChild[] | null,
    public head?: IParentAllDetails | null,
    public beneficiary?: IBeneficiary,
    public parentsDetails?: IParentAllDetails[] | null,
    public childrenDetails?: IChildAllDetails[] | null
  ) {
    this.notebookOfPoverty = this.notebookOfPoverty ?? false;
    this.notebookOfHandicap = this.notebookOfHandicap ?? false;
    this.archivated = this.archivated ?? false;
  }
}

export function getFamilyIdentifier(family: IFamily): number | undefined {
  return family.id;
}

export function getFamilyAllDetailsIdentifier(family: IFamilyAllDetails): number | undefined {
  return family.id;
}
