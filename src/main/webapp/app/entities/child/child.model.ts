import { ITeachingCurriculum } from 'app/entities/teaching-curriculum/teaching-curriculum.model';
import { IProfile } from 'app/entities/profile/profile.model';
import { IFamily } from 'app/entities/family/family.model';

export interface IChild {
  id?: number;
  teachingCurricula?: ITeachingCurriculum[] | null;
  childProfile?: IProfile | null;
  family?: IFamily | null;
}

export class Child implements IChild {
  constructor(
    public id?: number,
    public teachingCurricula?: ITeachingCurriculum[] | null,
    public childProfile?: IProfile | null,
    public family?: IFamily | null
  ) {}
}

export function getChildIdentifier(child: IChild): number | undefined {
  return child.id;
}
