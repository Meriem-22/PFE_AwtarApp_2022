import { Component, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { EventManager } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ItemValueService } from 'app/entities/item-value/service/item-value.service';
import { NatureService } from 'app/entities/nature/service/nature.service';
import { SchoolLevelService } from 'app/entities/school-level/service/school-level.service';
import { IItem, Item } from '../item.model';
import { ItemService } from '../service/item.service';
import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { HttpResponse } from '@angular/common/http';
import { ItemGender } from 'app/entities/enumerations/item-gender.model';
import { INature } from 'app/entities/nature/nature.model';
import { map, Observable, finalize } from 'rxjs';
import { IItemValue, ItemValue } from 'app/entities/item-value/item-value.model';
import dayjs from 'dayjs/esm';
import { DATE_FORMAT } from 'app/config/input.constants';
import { DatePipe } from '@angular/common';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-add-composite-item',
  templateUrl: './add-composite-item.component.html',
  styleUrls: ['./add-composite-item.component.scss'],
})
export class AddCompositeItemComponent implements OnInit {
  naturesSharedCollection: INature[] = [];
  productDialog?: boolean;
  submitted?: boolean;
  products!: IItem[];
  product!: IItem;
  item!: IItem;
  account: Account | null = null;
  selectedProducts?: IItem[] | null;
  statuses?: any[];
  isSaving = false;
  quantity = 1;
  CompositeItem!: IItem;
  price!: IItemValue;
  Compositeprice!: IItemValue;
  Compositeurprice!: IItemValue;

  nprice!: IItemValue;

  itemGenderValues = Object.keys(ItemGender);

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
    private accountService: AccountService,
    protected confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

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

  openNew(): void {
    this.product = {};
    this.submitted = false;
    this.productDialog = true;
  }

  editProduct(product: IItem): void {
    this.product = { ...product };
    this.productDialog = true;
  }

  hideDialog(): void {
    this.productDialog = false;
    this.submitted = false;
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
