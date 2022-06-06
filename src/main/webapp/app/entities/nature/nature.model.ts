import { IItem } from 'app/entities/item/item.model';
import { IDonationDetails } from 'app/entities/donation-details/donation-details.model';
import { Beneficiaries } from 'app/entities/enumerations/beneficiaries.model';

export interface INature {
  id?: number;
  name?: string;
  destinedTo?: Beneficiaries;
  necessityValue?: boolean | null;
  archivated?: boolean | null;
  items?: IItem[] | null;
  donationdetails?: IDonationDetails[] | null;
}

export class Nature implements INature {
  constructor(
    public id?: number,
    public name?: string,
    public destinedTo?: Beneficiaries,
    public necessityValue?: boolean | null,
    public archivated?: boolean | null,
    public items?: IItem[] | null,
    public donationdetails?: IDonationDetails[] | null
  ) {
    this.necessityValue = this.necessityValue ?? false;
    this.archivated = this.archivated ?? false;
  }
}

export function getNatureIdentifier(nature: INature): number | undefined {
  return nature.id;
}
