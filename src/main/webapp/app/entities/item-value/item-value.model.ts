import dayjs from 'dayjs/esm';
import { IItem } from 'app/entities/item/item.model';

export interface IItemValue {
  id?: number;
  price?: number;
  priceDate?: dayjs.Dayjs;
  availableStockQuantity?: number | null;
  archivated?: boolean | null;
  item?: IItem;
}

export class ItemValue implements IItemValue {
  constructor(
    public id?: number,
    public price?: number,
    public priceDate?: dayjs.Dayjs,
    public availableStockQuantity?: number | null,
    public archivated?: boolean | null,
    public item?: IItem
  ) {
    this.archivated = this.archivated ?? false;
  }
}

export function getItemValueIdentifier(itemValue: IItemValue): number | undefined {
  return itemValue.id;
}
export function getItemValueIdentifierFromItem(itemValue: IItem): number | undefined {
  return itemValue.idPrice;
}
