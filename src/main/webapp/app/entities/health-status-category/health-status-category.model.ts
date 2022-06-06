import { IStatusOfHealth } from 'app/entities/status-of-health/status-of-health.model';

export interface IHealthStatusCategory {
  id?: number;
  name?: string;
  archivated?: boolean | null;
  stateOfHealths?: IStatusOfHealth[] | null;
}

export class HealthStatusCategory implements IHealthStatusCategory {
  constructor(
    public id?: number,
    public name?: string,
    public archivated?: boolean | null,
    public stateOfHealths?: IStatusOfHealth[] | null
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getHealthStatusCategoryIdentifier(healthStatusCategory: IHealthStatusCategory): number | undefined {
  return healthStatusCategory.id;
}
