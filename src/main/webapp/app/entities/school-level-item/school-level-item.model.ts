import { IItem } from 'app/entities/item/item.model';
import { ISchoolLevel } from 'app/entities/school-level/school-level.model';

export interface ISchoolLevelItem {
  id?: number;
  isSchoolItem?: boolean | null;
  quantityNeeded?: number | null;
  archivated?: boolean | null;
  item?: IItem;
  schoolLevel?: ISchoolLevel;
}

export class SchoolLevelItem implements ISchoolLevelItem {
  constructor(
    public id?: number,
    public isSchoolItem?: boolean | null,
    public quantityNeeded?: number | null,
    public archivated?: boolean | null,
    public item?: IItem,
    public schoolLevel?: ISchoolLevel
  ) {
    this.isSchoolItem = this.isSchoolItem ?? false;
    this.archivated = this.archivated ?? false;
  }
}

export function getSchoolLevelItemIdentifier(schoolLevelItem: ISchoolLevelItem): number | undefined {
  return schoolLevelItem.id;
}
