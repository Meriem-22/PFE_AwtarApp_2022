import { ITeachingCurriculum } from 'app/entities/teaching-curriculum/teaching-curriculum.model';
import { ICity } from 'app/entities/city/city.model';

export interface IEducationalInstitution {
  id?: number;
  name?: string;
  adress?: string | null;
  archivated?: boolean | null;
  teachingCurricula?: ITeachingCurriculum[] | null;
  city?: ICity | null;
}

export class EducationalInstitution implements IEducationalInstitution {
  constructor(
    public id?: number,
    public name?: string,
    public adress?: string | null,
    public archivated?: boolean | null,
    public teachingCurricula?: ITeachingCurriculum[] | null,
    public city?: ICity | null
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getEducationalInstitutionIdentifier(educationalInstitution: IEducationalInstitution): number | undefined {
  return educationalInstitution.id;
}
