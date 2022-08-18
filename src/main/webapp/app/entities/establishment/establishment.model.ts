import { IReport } from 'app/entities/report/report.model';
import { IEstablishmentType } from 'app/entities/establishment-type/establishment-type.model';
import { ICity } from 'app/entities/city/city.model';
import { IAuthorizingOfficer } from '../authorizing-officer/authorizing-officer.model';
import { ITutor } from '../tutor/tutor.model';

export interface IEstablishment {
  id?: number;
  name?: string;
  activity?: string | null;
  manager?: string | null;
  managerCin?: string | null;
  contact?: string | null;
  adress?: string | null;
  cp?: string | null;
  region?: string | null;
  phone?: string | null;
  fax?: string | null;
  email?: string | null;
  site?: string | null;
  remark?: string | null;
  reports?: IReport[] | null;
  establishmentType?: IEstablishmentType;
  city?: ICity;
  authorizingOfficer?: IAuthorizingOfficer | null;
  tutor?: ITutor | null;
}

export class Establishment implements IEstablishment {
  constructor(
    public id?: number,
    public name?: string,
    public activity?: string | null,
    public manager?: string | null,
    public managerCin?: string | null,
    public contact?: string | null,
    public adress?: string | null,
    public cp?: string | null,
    public region?: string | null,
    public phone?: string | null,
    public fax?: string | null,
    public email?: string | null,
    public site?: string | null,
    public remark?: string | null,
    public reports?: IReport[] | null,
    public establishmentType?: IEstablishmentType,
    public city?: ICity,
    public authorizingOfficer?: IAuthorizingOfficer | null,
    public tutor?: ITutor | null
  ) {}
}

export function getEstablishmentIdentifier(establishment: IEstablishment): number | undefined {
  return establishment.id;
}
