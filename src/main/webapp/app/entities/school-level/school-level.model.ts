import { ITeachingCurriculum } from 'app/entities/teaching-curriculum/teaching-curriculum.model';
import { ISchoolLevelItem } from 'app/entities/school-level-item/school-level-item.model';

export interface ISchoolLevel {
  id?: number;
  schoolLevel?: string;
  archivated?: boolean | null;
  teachingCurricula?: ITeachingCurriculum[] | null;
  items?: ISchoolLevelItem[] | null;
}

export class SchoolLevel implements ISchoolLevel {
  constructor(
    public id?: number,
    public schoolLevel?: string,
    public archivated?: boolean | null,
    public teachingCurricula?: ITeachingCurriculum[] | null,
    public items?: ISchoolLevelItem[] | null
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getSchoolLevelIdentifier(schoolLevel: ISchoolLevel): number | undefined {
  return schoolLevel.id;
}
