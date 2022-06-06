import { IChildStatusItem } from 'app/entities/child-status-item/child-status-item.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IChildStatus {
  id?: number;
  staus?: Status;
  archivated?: boolean | null;
  items?: IChildStatusItem[] | null;
}

export class ChildStatus implements IChildStatus {
  constructor(public id?: number, public staus?: Status, public archivated?: boolean | null, public items?: IChildStatusItem[] | null) {
    this.archivated = this.archivated ?? false;
  }
}

export function getChildStatusIdentifier(childStatus: IChildStatus): number | undefined {
  return childStatus.id;
}
