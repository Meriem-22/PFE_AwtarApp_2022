import dayjs from 'dayjs/esm';
import { IItem } from 'app/entities/item/item.model';
import { IDonationsReceived } from 'app/entities/donations-received/donations-received.model';

export interface IDonationsReceivedItem {
  id?: number;
  quantity?: number;
  date?: dayjs.Dayjs;
  archivated?: boolean | null;
  item?: IItem;
  donationsReceived?: IDonationsReceived;
}

export class DonationsReceivedItem implements IDonationsReceivedItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public date?: dayjs.Dayjs,
    public archivated?: boolean | null,
    public item?: IItem,
    public donationsReceived?: IDonationsReceived
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getDonationsReceivedItemIdentifier(donationsReceivedItem: IDonationsReceivedItem): number | undefined {
  return donationsReceivedItem.id;
}
