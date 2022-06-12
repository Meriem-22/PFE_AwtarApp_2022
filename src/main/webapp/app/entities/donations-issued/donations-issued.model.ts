import dayjs from 'dayjs/esm';
import { IDonationDetails } from 'app/entities/donation-details/donation-details.model';
import { Period } from 'app/entities/enumerations/period.model';

export interface IDonationsIssued {
  id?: number;
  model?: string;
  isValidated?: boolean | null;
  validationDate?: dayjs.Dayjs | null;
  validationUser?: number | null;
  donationsDate?: dayjs.Dayjs | null;
  canceledDonations?: boolean | null;
  canceledOn?: dayjs.Dayjs | null;
  canceledBy?: number | null;
  cancellationReason?: string | null;
  recurringDonations?: boolean | null;
  periodicity?: Period | null;
  recurrence?: number | null;
  archivated?: boolean | null;
  donationsDetails?: IDonationDetails[] | null;
  donationsDetailsN?: IDonationDetails | null;
  idsBeneficiary?: number[] | null;
}

export class DonationsIssued implements IDonationsIssued {
  constructor(
    public id?: number,
    public model?: string,
    public isValidated?: boolean | null,
    public validationDate?: dayjs.Dayjs | null,
    public validationUser?: number | null,
    public donationsDate?: dayjs.Dayjs | null,
    public canceledDonations?: boolean | null,
    public canceledOn?: dayjs.Dayjs | null,
    public canceledBy?: number | null,
    public cancellationReason?: string | null,
    public recurringDonations?: boolean | null,
    public periodicity?: Period | null,
    public recurrence?: number | null,
    public archivated?: boolean | null,
    public donationsDetails?: IDonationDetails[] | null,
    public donationsDetailsN?: IDonationDetails | null,
    public idsBeneficiary?: number[] | null
  ) {
    this.isValidated = this.isValidated ?? false;
    this.canceledDonations = this.canceledDonations ?? false;
    this.recurringDonations = this.recurringDonations ?? false;
    this.archivated = this.archivated ?? false;
  }
}

export function getDonationsIssuedIdentifier(donationsIssued: IDonationsIssued): number | undefined {
  return donationsIssued.id;
}
