import dayjs from 'dayjs/esm';
import { IEstablishment } from 'app/entities/establishment/establishment.model';

export interface IReport {
  id?: number;
  nature?: string;
  date?: dayjs.Dayjs;
  urlEnclosedContentType?: string;
  urlEnclosed?: string;
  archivated?: boolean | null;
  establishment?: IEstablishment;
}

export class Report implements IReport {
  constructor(
    public id?: number,
    public nature?: string,
    public date?: dayjs.Dayjs,
    public urlEnclosedContentType?: string,
    public urlEnclosed?: string,
    public archivated?: boolean | null,
    public establishment?: IEstablishment
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getReportIdentifier(report: IReport): number | undefined {
  return report.id;
}
