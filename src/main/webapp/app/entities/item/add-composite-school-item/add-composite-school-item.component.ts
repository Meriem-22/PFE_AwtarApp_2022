import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { EventManager } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { DATE_FORMAT } from 'app/config/input.constants';
import { DataUtils } from 'app/core/util/data-util.service';
import { IChildStatusItem } from 'app/entities/child-status-item/child-status-item.model';
import { ChildStatusItemService } from 'app/entities/child-status-item/service/child-status-item.service';
import { ChildStatus } from 'app/entities/child-status/child-status.model';
import { ChildStatusService } from 'app/entities/child-status/service/child-status.service';
import { ItemGender } from 'app/entities/enumerations/item-gender.model';
import { IItemValue, ItemValue } from 'app/entities/item-value/item-value.model';
import { ItemValueService } from 'app/entities/item-value/service/item-value.service';
import { INature } from 'app/entities/nature/nature.model';
import { NatureService } from 'app/entities/nature/service/nature.service';
import { SchoolLevelItem } from 'app/entities/school-level-item/school-level-item.model';
import { SchoolLevelItemService } from 'app/entities/school-level-item/service/school-level-item.service';
import { SchoolLevelService } from 'app/entities/school-level/service/school-level.service';
import dayjs from 'dayjs/esm';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Observable, finalize, map } from 'rxjs';
import { IItem, Item } from '../item.model';
import { ItemService } from '../service/item.service';

@Component({
  selector: 'jhi-add-composite-school-item',
  templateUrl: './add-composite-school-item.component.html',
  styleUrls: ['./add-composite-school-item.component.scss'],
})
export class AddCompositeSchoolItemComponent implements OnInit {
  naturesSharedCollection: INature[] = [];
  products!: IItem[];
  product!: IItem;
  item!: IItem;
  selectedProducts?: IItem[] | null;
  productDialog?: boolean;
  submitted?: boolean;
  statuses?: any[];
  isSaving = false;
  quantity = 1;
  CompositeItem!: IItem;
  price!: IItemValue;
  SchoolLevelInfo?: any[];
  childStatusItem!: any[];

  itemGenderValues = Object.keys(ItemGender);

  displayPosition!: boolean;
  position!: string;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    urlPhoto: [],
    urlPhotoContentType: [],
    gender: [null, [Validators.required]],
    composed: [],
    archivated: [],
    nature: [null, Validators.required],
    price: [null, [Validators.required]],
    priceDate: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected itemService: ItemService,
    protected natureService: NatureService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected schoolLevelService: SchoolLevelService,
    protected itemValueService: ItemValueService,
    protected messageService: MessageService,
    protected confirmationService: ConfirmationService,
    protected schoolLevelItemService: SchoolLevelItemService,
    protected childStatusItemService: ChildStatusItemService
  ) {}

  ngOnInit(): void {
    this.item = history.state.item;
    this.price = history.state.price;
    console.log(this.item);
    console.log(this.price);

    this.itemValueService.findItem(this.item.id!).subscribe({
      next: (res: HttpResponse<IItemValue>) => {
        this.price = res.body!;
      },
      error: e => console.error(e),
    });

    this.childStatusItemService.findAllStatusOfItem(this.item.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.childStatusItem = res.body ?? [];
        console.log(this.childStatusItem);
      },
      error: e => console.error(e),
    });

    this.itemService.findAllCompositeurItems(this.item.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.products = res.body ?? [];
        console.log(this.products);
        /*for (this.t = 0; this.t < this.schoolLevels.length; this.t++) {
          this.quantity[this.t] = { id: this.schoolLevels[this.t].id!, qt: -1 };
        }*/
      },
      error: e => console.error(e),
    });
  }

  findSchoolLevelInfo(): void {
    this.schoolLevelItemService.findAllCompositeurSchoolItems(this.item.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.SchoolLevelInfo = res.body ?? [];
        console.log(this.SchoolLevelInfo);
        /*for (this.t = 0; this.t < this.schoolLevels.length; this.t++) {
        this.quantity[this.t] = { id: this.schoolLevels[this.t].id!, qt: -1 };
      }*/
      },
      error: e => console.error(e),
    });
  }

  showPositionDialog(position: string): void {
    this.findSchoolLevelInfo();
    this.position = position;
    this.displayPosition = true;
  }

  openNew(): void {
    this.product = {};
    this.submitted = false;
    this.productDialog = true;
  }

  editProduct(product: IItem): void {
    console.log(product);
    this.product = { ...product };
    this.productDialog = true;
  }

  updatePrice = (): void => {
    const Compositeur = this.createFromFormPrice2();
    this.updateCompositeurValue(Compositeur);

    let s = 0;
    for (let i = 0; i < this.products.length; i++) {
      s = s + this.products[i].price!;
    }

    const Composite = this.createFromFormPrice(this.price, s, dayjs());
    this.save(Composite);
  };

  saveProduct(): void {
    this.submitted = true;
    if (this.product.name!.trim()) {
      if (this.product.id) {
        this.products[this.findIndexById(this.product.id)] = this.convertDateFromClient(this.product);

        console.log(this.product);
        this.updatePrice();
      }

      this.products = [...this.products];
      this.productDialog = false;
      this.product = {};
    }
  }

  hideDialog(): void {
    this.productDialog = false;
    this.submitted = false;
  }

  showPrice(): void {
    this.itemValueService.findItem(this.item.id!).subscribe({
      next: (res: HttpResponse<IItemValue>) => {
        this.price = res.body!;
        this.editForm.patchValue({
          price: this.price.price,
          priceDate: this.price.priceDate,
          availableStockQuantity: this.price.availableStockQuantity,
        });
      },
      error: e => console.error(e),
    });

    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
  }

  trackNatureById(_index: number, item: INature): number {
    return item.id!;
  }

  findIndexById(id: number): number {
    let index = -1;
    for (let i = 0; i < this.products.length; i++) {
      if (this.products[i].id === id) {
        index = i;
        break;
      }
    }

    return index;
  }

  createId(): string {
    let id = '';
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 5; i++) {
      id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return id;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({});
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    this.isSaving = true;
  }

  AddItem(): void {
    this.isSaving = true;
    const itemValue = this.createFromToAddItem();
    if (itemValue.id !== undefined) {
      this.subscribeToSaveResponse(this.itemValueService.update(itemValue));
    } else {
      this.subscribeToSaveResponse(this.itemValueService.create(itemValue));
    }
  }

  save(item: IItemValue): void {
    this.isSaving = true;
    if (item.id !== undefined) {
      this.subscribeToSaveResponseAdd(this.itemValueService.update(item));
    } else {
      this.subscribeToSaveResponseAdd(this.itemValueService.create(item));
    }
  }

  updateCompositeurValue(item: IItemValue): void {
    this.isSaving = true;
    this.subscribeToSaveResponseUpdate(this.itemValueService.updatePrice(item));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemValue>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.showPrice(),
      error: () => this.onSaveError(),
    });
  }

  protected subscribeToSaveResponseAdd(result: Observable<HttpResponse<IItem>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected subscribeToSaveResponseUpdate(result: Observable<HttpResponse<IItem>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.showPrice(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected createFromFormPrice(priceItem: IItemValue, nPrice: number, nDate: dayjs.Dayjs): IItemValue {
    return {
      ...new ItemValue(),

      id: priceItem.id,
      price: nPrice,
      priceDate: nDate,
      availableStockQuantity: priceItem.availableStockQuantity,
      archivated: priceItem.archivated,
      item: priceItem.item,
    };
  }

  protected createFromFormPrice2(): IItemValue {
    return {
      ...new ItemValue(),
      id: this.product.idPrice,
      price: this.product.price,
      priceDate: this.product.priceDate,
    };
  }

  protected convertDateFromClient(itemValue: IItem): IItem {
    return Object.assign({}, itemValue, {
      priceDate: itemValue.priceDate?.isValid() ? itemValue.priceDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected createFromToAddItem(): IItemValue {
    return {
      ...new ItemValue(),
      id: this.price.id,
      price: this.price.price,
      priceDate: this.price.priceDate,
      availableStockQuantity: this.price.availableStockQuantity! + this.quantity,
      archivated: this.price.archivated,
      item: this.price.item,
    };
  }
}
