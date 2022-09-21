import { IItemValue } from 'app/entities/item-value/item-value.model';
import { INature } from 'app/entities/nature/nature.model';
import { IDonationItemDetails } from 'app/entities/donation-item-details/donation-item-details.model';
import { IDonationsReceivedItem } from 'app/entities/donations-received-item/donations-received-item.model';
import { IChildStatusItem } from 'app/entities/child-status-item/child-status-item.model';
import { ISchoolLevelItem } from 'app/entities/school-level-item/school-level-item.model';
import { ItemGender } from 'app/entities/enumerations/item-gender.model';
import dayjs from 'dayjs/esm';
import { Status } from '../enumerations/status.model';

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

  idPrice?: number;
  price?: number;
  priceDate?: dayjs.Dayjs;
  schoolLevelDetails?: { id: number; qt: number }[];
  isSchoolItem?: boolean | null;
  quantityNeeded?: number | null;
  staus?: Status;
  allStaus?: Status[];

  simple?: boolean | null;
  compositeurSimpleItems?: ICompositeItem[] | null;
  compositeurSchoolItems?: ICompositeSchoolItem[] | null;
  quantity?: number;
  quantityToAdd?: number;
  quantitySchoolItemTab?: number[] | null;
  schoolLevelIemTab?: number[] | null;
}

export interface ICompositeItem {
  name?: string;
  urlPhotoContentType?: string | null;
  urlPhoto?: string | null;
  gender?: ItemGender;
  nature?: INature;
  price?: number;
  priceDate?: dayjs.Dayjs;
  quantity?: number;
}

export interface ICompositeSchoolItem {
  name?: string;
  urlPhotoContentType?: string | null;
  urlPhoto?: string | null;
  gender?: ItemGender;
  nature?: INature;
  price?: number;
  priceDate?: dayjs.Dayjs;
  schoolLevel?: string;
  quantityNeeded?: number | null;
  staus?: Status;
  quantity?: number;
}

export class CompositeSchoolItem implements ICompositeSchoolItem {
  constructor(
    public name?: string,
    public urlPhotoContentType?: string | null,
    public urlPhoto?: string | null,
    public gender?: ItemGender,
    public nature?: INature,
    public price?: number,
    public priceDate?: dayjs.Dayjs,
    public schoolLevel?: string,
    public quantityNeeded?: number | null,
    public staus?: Status,
    public quantity?: number
  ) {}
}
export class CompositeItem implements ICompositeItem {
  constructor(
    public name?: string,
    public urlPhotoContentType?: string | null,
    public urlPhoto?: string | null,
    public gender?: ItemGender,
    public nature?: INature,
    public price?: number,
    public priceDate?: dayjs.Dayjs,
    public quantity?: number
  ) {}
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
    public schoolLevels?: ISchoolLevelItem[] | null,
    public idPrice?: number,
    public price?: number,
    public priceDate?: dayjs.Dayjs,
    public schoolLevelDetails?: { id: number; qt: number }[],
    public isSchoolItem?: boolean | null,
    public quantityNeeded?: number | null,
    public staus?: Status,
    public allStaus?: Status[],

    public simple?: boolean | null,
    public compositeurItems?: IItem[] | null,
    public compositeurSchoolItems?: IItem[] | null,
    public quantity?: number,
    public quantityToAdd?: number,
    public quantitySchoolItemTab?: number[] | null,
    public schoolLevelIemTab?: number[] | null
  ) {
    this.composed = this.composed ?? false;
    this.archivated = this.archivated ?? false;
    this.simple = this.simple ?? false;
  }
}

export function getItemIdentifier(item: IItem): number | undefined {
  return item.id;
}
