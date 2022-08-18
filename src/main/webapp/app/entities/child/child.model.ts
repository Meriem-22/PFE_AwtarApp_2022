import { ITeachingCurriculum } from 'app/entities/teaching-curriculum/teaching-curriculum.model';
import { IProfile } from 'app/entities/profile/profile.model';
import { IFamily } from 'app/entities/family/family.model';
import { IAuthorizingOfficer } from '../authorizing-officer/authorizing-officer.model';
import { ITutor } from '../tutor/tutor.model';

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

export interface IChildAllDetails {
  id?: number;
  teachingCurricula?: ITeachingCurriculum[] | null;
  childProfile?: IProfile | null;
  family?: IFamily | null;
  authorizingOfficer?: IAuthorizingOfficer | null;
  tutor?: ITutor | null;
  profile?: IProfile;
}

export class ChildAllDetails implements IChildAllDetails {
  constructor(
    public id?: number,
    public teachingCurricula?: ITeachingCurriculum[] | null,
    public childProfile?: IProfile | null,
    public family?: IFamily | null,
    public authorizingOfficer?: IAuthorizingOfficer | null,
    public tutor?: ITutor | null,
    public profile?: IProfile
  ) {}
}

export function getChildIdentifier(child: IChild): number | undefined {
  return child.id;
}
