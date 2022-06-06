import { IProfile } from 'app/entities/profile/profile.model';
import { IEstablishment } from 'app/entities/establishment/establishment.model';
import { IEducationalInstitution } from 'app/entities/educational-institution/educational-institution.model';

export interface ICity {
  id?: number;
  name?: string;
  governorate?: string | null;
  isGovernorate?: boolean | null;
  archivated?: boolean | null;
  personbirthPlaces?: IProfile[] | null;
  personplaceOfResidences?: IProfile[] | null;
  establishments?: IEstablishment[] | null;
  educationalInstitutions?: IEducationalInstitution[] | null;
}

export class City implements ICity {
  constructor(
    public id?: number,
    public name?: string,
    public governorate?: string | null,
    public isGovernorate?: boolean | null,
    public archivated?: boolean | null,
    public personbirthPlaces?: IProfile[] | null,
    public personplaceOfResidences?: IProfile[] | null,
    public establishments?: IEstablishment[] | null,
    public educationalInstitutions?: IEducationalInstitution[] | null
  ) {
    this.isGovernorate = this.isGovernorate ?? false;
    this.archivated = this.archivated ?? false;
  }
}

export function getCityIdentifier(city: ICity): number | undefined {
  return city.id;
}
