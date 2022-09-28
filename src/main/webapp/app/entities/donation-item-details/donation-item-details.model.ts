import dayjs from 'dayjs/esm';
import { IDonationDetails } from 'app/entities/donation-details/donation-details.model';
import { IItem } from 'app/entities/item/item.model';

export interface IDonationItemDetails {
  id?: number;
  quantity?: number;
  date?: dayjs.Dayjs;
  archivated?: boolean | null;
  donationDetails?: IDonationDetails;
  item?: IItem;
  itemsWithQuantitys?: number[];
  quantityOfItems?: number[];
  itemsWithoutQuantitys?: number[];
}

export class DonationItemDetails implements IDonationItemDetails {
  constructor(
    public id?: number,
    public quantity?: number,
    public date?: dayjs.Dayjs,
    public archivated?: boolean | null,
    public donationDetails?: IDonationDetails,
    public item?: IItem,
    public itemsWithQuantitys?: number[],
    public quantityOfItems?: number[],
    public itemsWithoutQuantitys?: number[]
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getDonationItemDetailsIdentifier(donationItemDetails: IDonationItemDetails): number | undefined {
  return donationItemDetails.id;
}
