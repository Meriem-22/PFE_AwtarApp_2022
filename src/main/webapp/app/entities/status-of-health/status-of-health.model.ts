import dayjs from 'dayjs/esm';
import { IProfile } from 'app/entities/profile/profile.model';
import { IHealthStatusCategory } from 'app/entities/health-status-category/health-status-category.model';

export interface IStatusOfHealth {
  id?: number;
  healthStatusDate?: dayjs.Dayjs;
  urlDetailsAttachedContentType?: string | null;
  urlDetailsAttached?: string | null;
  archivated?: boolean | null;
  person?: IProfile;
  healthStatusCategory?: IHealthStatusCategory;
}

export class StatusOfHealth implements IStatusOfHealth {
  constructor(
    public id?: number,
    public healthStatusDate?: dayjs.Dayjs,
    public urlDetailsAttachedContentType?: string | null,
    public urlDetailsAttached?: string | null,
    public archivated?: boolean | null,
    public person?: IProfile,
    public healthStatusCategory?: IHealthStatusCategory
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getStatusOfHealthIdentifier(statusOfHealth: IStatusOfHealth): number | undefined {
  return statusOfHealth.id;
}
