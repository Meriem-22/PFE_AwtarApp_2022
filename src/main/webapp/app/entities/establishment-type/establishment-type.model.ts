import { IEstablishment } from 'app/entities/establishment/establishment.model';

export interface IEstablishmentType {
  id?: number;
  typeName?: string;
  archivated?: boolean | null;
  establishments?: IEstablishment[] | null;
}

export class EstablishmentType implements IEstablishmentType {
  constructor(
    public id?: number,
    public typeName?: string,
    public archivated?: boolean | null,
    public establishments?: IEstablishment[] | null
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getEstablishmentTypeIdentifier(establishmentType: IEstablishmentType): number | undefined {
  return establishmentType.id;
}
