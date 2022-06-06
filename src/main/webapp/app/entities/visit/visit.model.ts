import dayjs from 'dayjs/esm';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';

export interface IVisit {
  id?: number;
  visitDate?: dayjs.Dayjs;
  realizedBy?: number | null;
  description?: string;
  attachedFileContentType?: string | null;
  attachedFile?: string | null;
  archivated?: boolean | null;
  beneficiary?: IBeneficiary;
}

export class Visit implements IVisit {
  constructor(
    public id?: number,
    public visitDate?: dayjs.Dayjs,
    public realizedBy?: number | null,
    public description?: string,
    public attachedFileContentType?: string | null,
    public attachedFile?: string | null,
    public archivated?: boolean | null,
    public beneficiary?: IBeneficiary
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getVisitIdentifier(visit: IVisit): number | undefined {
  return visit.id;
}
