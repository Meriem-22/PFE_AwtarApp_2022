import { IDonationsReceived } from 'app/entities/donations-received/donations-received.model';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { IProfile } from 'app/entities/profile/profile.model';

export interface IAuthorizingOfficer {
  id?: number;
  abbreviation?: string;
  activity?: string | null;
  manager?: string | null;
  managerCin?: string | null;
  donations?: IDonationsReceived[] | null;
  beneficiaries?: IBeneficiary[] | null;
  authorizingOfficerProfile?: IProfile | null;
}

export class AuthorizingOfficer implements IAuthorizingOfficer {
  constructor(
    public id?: number,
    public abbreviation?: string,
    public activity?: string | null,
    public manager?: string | null,
    public managerCin?: string | null,
    public donations?: IDonationsReceived[] | null,
    public beneficiaries?: IBeneficiary[] | null,
    public authorizingOfficerProfile?: IProfile | null
  ) {}
}

export function getAuthorizingOfficerIdentifier(authorizingOfficer: IAuthorizingOfficer): number | undefined {
  return authorizingOfficer.id;
}
