import { ISchoolLevel } from 'app/entities/school-level/school-level.model';
import { IChild } from 'app/entities/child/child.model';
import { IEducationalInstitution } from 'app/entities/educational-institution/educational-institution.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface ITeachingCurriculum {
  id?: number;
  beginningYear?: string;
  endYear?: string;
  annualResult?: number | null;
  result?: Status;
  remarks?: string | null;
  attachedFileContentType?: string | null;
  attachedFile?: string | null;
  archivated?: boolean | null;
  schoolLevel?: ISchoolLevel;
  child?: IChild;
  educationalInstitution?: IEducationalInstitution;
}

export class TeachingCurriculum implements ITeachingCurriculum {
  constructor(
    public id?: number,
    public beginningYear?: string,
    public endYear?: string,
    public annualResult?: number | null,
    public result?: Status,
    public remarks?: string | null,
    public attachedFileContentType?: string | null,
    public attachedFile?: string | null,
    public archivated?: boolean | null,
    public schoolLevel?: ISchoolLevel,
    public child?: IChild,
    public educationalInstitution?: IEducationalInstitution
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getTeachingCurriculumIdentifier(teachingCurriculum: ITeachingCurriculum): number | undefined {
  return teachingCurriculum.id;
}
