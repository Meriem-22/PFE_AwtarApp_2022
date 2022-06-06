import { IItem } from 'app/entities/item/item.model';

export interface ICompositeItem {
  id?: number;
  quantity?: number;
  archivated?: boolean | null;
  composant?: IItem;
  composer?: IItem;
}

export class CompositeItem implements ICompositeItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public archivated?: boolean | null,
    public composant?: IItem,
    public composer?: IItem
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getCompositeItemIdentifier(compositeItem: ICompositeItem): number | undefined {
  return compositeItem.id;
}
