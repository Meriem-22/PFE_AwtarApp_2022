import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { IDonationsReceivedItem } from 'app/entities/donations-received-item/donations-received-item.model';

export interface IDonationsReceived {
  id?: number;
  urlPhotoContentType?: string | null;
  urlPhoto?: string | null;
  archivated?: boolean | null;
  authorizingOfficer?: IAuthorizingOfficer;
  items?: IDonationsReceivedItem[] | null;
}

export class DonationsReceived implements IDonationsReceived {
  constructor(
    public id?: number,
    public urlPhotoContentType?: string | null,
    public urlPhoto?: string | null,
    public archivated?: boolean | null,
    public authorizingOfficer?: IAuthorizingOfficer,
    public items?: IDonationsReceivedItem[] | null
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getDonationsReceivedIdentifier(donationsReceived: IDonationsReceived): number | undefined {
  return donationsReceived.id;
}
