import { IItemValue } from 'app/entities/item-value/item-value.model';
import { INature } from 'app/entities/nature/nature.model';
import { IDonationItemDetails } from 'app/entities/donation-item-details/donation-item-details.model';
import { ICompositeItem } from 'app/entities/composite-item/composite-item.model';
import { IDonationsReceivedItem } from 'app/entities/donations-received-item/donations-received-item.model';
import { IChildStatusItem } from 'app/entities/child-status-item/child-status-item.model';
import { ISchoolLevelItem } from 'app/entities/school-level-item/school-level-item.model';
import { ItemGender } from 'app/entities/enumerations/item-gender.model';

export interface IItem {
  id?: number;
  name?: string;
  urlPhotoContentType?: string | null;
  urlPhoto?: string | null;
  gender?: ItemGender;
  composed?: boolean | null;
  archivated?: boolean | null;
  itemValues?: IItemValue[] | null;
  nature?: INature;
  donationDetails?: IDonationItemDetails[] | null;
  composers?: ICompositeItem[] | null;
  composants?: ICompositeItem[] | null;
  donationsReceiveds?: IDonationsReceivedItem[] | null;
  childStatuses?: IChildStatusItem[] | null;
  schoolLevels?: ISchoolLevelItem[] | null;
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public name?: string,
    public urlPhotoContentType?: string | null,
    public urlPhoto?: string | null,
    public gender?: ItemGender,
    public composed?: boolean | null,
    public archivated?: boolean | null,
    public itemValues?: IItemValue[] | null,
    public nature?: INature,
    public donationDetails?: IDonationItemDetails[] | null,
    public composers?: ICompositeItem[] | null,
    public composants?: ICompositeItem[] | null,
    public donationsReceiveds?: IDonationsReceivedItem[] | null,
    public childStatuses?: IChildStatusItem[] | null,
    public schoolLevels?: ISchoolLevelItem[] | null
  ) {
    this.composed = this.composed ?? false;
    this.archivated = this.archivated ?? false;
  }
}

export function getItemIdentifier(item: IItem): number | undefined {
  return item.id;
}
