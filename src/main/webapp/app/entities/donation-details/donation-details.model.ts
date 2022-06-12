import { IDonationsIssued } from 'app/entities/donations-issued/donations-issued.model';
import { INature } from 'app/entities/nature/nature.model';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { IDonationItemDetails } from 'app/entities/donation-item-details/donation-item-details.model';

export interface IDonationDetails {
  id?: number;
  description?: string;
  archivated?: boolean | null;
  donationsIssued?: IDonationsIssued;
  nature?: INature;
  beneficiary?: IBeneficiary;
  items?: IDonationItemDetails[] | null;
  donationItemDetails?: IDonationItemDetails[] | null;
}

export class DonationDetails implements IDonationDetails {
  constructor(
    public id?: number,
    public description?: string,
    public archivated?: boolean | null,
    public donationsIssued?: IDonationsIssued,
    public nature?: INature,
    public beneficiary?: IBeneficiary,
    public items?: IDonationItemDetails[] | null,
    public donationItemDetails?: IDonationItemDetails[] | null
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getDonationDetailsIdentifier(donationDetails: IDonationDetails): number | undefined {
  return donationDetails.id;
}
