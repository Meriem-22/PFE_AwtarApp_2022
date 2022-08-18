import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { IProfile } from 'app/entities/profile/profile.model';

export interface ITutor {
  id?: number;
  activity?: string | null;
  manager?: string | null;
  managerCin?: string | null;
  beneficiaries?: IBeneficiary[] | null;
  tutorProfile?: IProfile | null;
  profile?: IProfile | null;
}

export class Tutor implements ITutor {
  constructor(
    public id?: number,
    public activity?: string | null,
    public manager?: string | null,
    public managerCin?: string | null,
    public beneficiaries?: IBeneficiary[] | null,
    public tutorProfile?: IProfile | null,
    public profile?: IProfile | null
  ) {}
}

export function getTutorIdentifier(tutor: ITutor): number | undefined {
  return tutor.id;
}
