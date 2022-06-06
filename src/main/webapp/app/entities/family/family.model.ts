import { IParent } from 'app/entities/parent/parent.model';
import { IChild } from 'app/entities/child/child.model';

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

export function getFamilyIdentifier(family: IFamily): number | undefined {
  return family.id;
}