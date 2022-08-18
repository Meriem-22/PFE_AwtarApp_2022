import { IDonationDetails } from 'app/entities/donation-details/donation-details.model';
import { IVisit } from 'app/entities/visit/visit.model';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { Beneficiaries } from 'app/entities/enumerations/beneficiaries.model';

export interface IBeneficiary {
  id?: number;
  beneficiaryReference?: string | null;
  beneficiaryType?: Beneficiaries;
  archivated?: boolean | null;
  donationdetails?: IDonationDetails[] | null;
  visits?: IVisit[] | null;
  authorizingOfficer?: IAuthorizingOfficer | null;
  tutor?: ITutor | null;
  idContributor?: number | null;
}

export class Beneficiary implements IBeneficiary {
  constructor(
    public id?: number,
    public beneficiaryReference?: string | null,
    public beneficiaryType?: Beneficiaries,
    public archivated?: boolean | null,
    public donationdetails?: IDonationDetails[] | null,
    public visits?: IVisit[] | null,
    public authorizingOfficer?: IAuthorizingOfficer | null,
    public tutor?: ITutor | null,
    public idContributor?: number | null
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getBeneficiaryIdentifier(beneficiary: IBeneficiary): number | undefined {
  return beneficiary.id;
}
