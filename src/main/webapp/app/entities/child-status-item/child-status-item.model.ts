import { IItem } from 'app/entities/item/item.model';
import { IChildStatus } from 'app/entities/child-status/child-status.model';

export interface IChildStatusItem {
  id?: number;
  affected?: boolean | null;
  archivated?: boolean | null;
  item?: IItem;
  childStatus?: IChildStatus;
}

export class ChildStatusItem implements IChildStatusItem {
  constructor(
    public id?: number,
    public affected?: boolean | null,
    public archivated?: boolean | null,
    public item?: IItem,
    public childStatus?: IChildStatus
  ) {
    this.affected = this.affected ?? false;
    this.archivated = this.archivated ?? false;
  }
}

export function getChildStatusItemIdentifier(childStatusItem: IChildStatusItem): number | undefined {
  return childStatusItem.id;
}
