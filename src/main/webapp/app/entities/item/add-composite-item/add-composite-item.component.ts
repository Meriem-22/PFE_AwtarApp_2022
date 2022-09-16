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
import { IItemValue } from 'app/entities/item-value/item-value.model';

@Component({
  selector: 'jhi-add-composite-item',
  templateUrl: './add-composite-item.component.html',
  styleUrls: ['./add-composite-item.component.scss'],
})
export class AddCompositeItemComponent implements OnInit {
  naturesSharedCollection: INature[] = [];
  productDialog?: boolean;
  products!: IItem[];
  product!: IItem;
  item!: IItem;
  selectedProducts?: IItem[] | null;
  submitted?: boolean;
  statuses?: any[];
  isSaving = false;
  quantity = 1;
  CompositeItem!: IItem;
  price!: IItemValue;

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
    protected confirmationService: ConfirmationService
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

    this.loadRelationshipsOptions();
  }

  openNew(): void {
    this.product = {};
    this.submitted = false;
    this.productDialog = true;
  }

  deleteSelectedProducts(): void {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected products?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.products = this.products.filter(val => !this.selectedProducts!.includes(val));
        this.selectedProducts = null;
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
      },
    });
  }

  editProduct(product: IItem): void {
    console.log(product);
    this.product = { ...product };
    this.productDialog = true;
  }

  deleteProduct(product: IItem): void {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + 'product.name?.toString' + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.products = this.products.filter(val => val.id !== product.id);
        this.product = {};
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 });
      },
    });
  }

  hideDialog(): void {
    this.productDialog = false;
    this.submitted = false;
  }

  saveProduct(): void {
    this.submitted = true;

    if (this.product.name!.trim()) {
      if (this.product.id) {
        this.products[this.findIndexById(this.product.id)] = this.product;
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
      } else {
        /*this.product!.id! = this.createId();
          this.product.image = 'product-placeholder.svg';
          this.products.push(this.product);
          this.messageService.add({severity:'success', summary: 'Successful', detail: 'Product Created', life: 3000});
      */
      }

      this.products = [...this.products];
      this.productDialog = false;
      this.product = {};
    }
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
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const item = this.createFromForm();
    if (item.id !== undefined) {
      this.subscribeToSaveResponse(this.itemService.update(item));
    } else {
      this.subscribeToSaveResponse(this.itemService.create(item));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
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

  protected loadRelationshipsOptions(): void {
    this.natureService
      .query()
      .pipe(map((res: HttpResponse<INature[]>) => res.body ?? []))
      .pipe(map((natures: INature[]) => this.natureService.addNatureToCollectionIfMissing(natures, this.editForm.get('nature')!.value)))
      .subscribe((natures: INature[]) => (this.naturesSharedCollection = natures));
  }
  protected createFromForm(): IItem {
    return {
      ...new Item(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      urlPhotoContentType: this.editForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.editForm.get(['urlPhoto'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      composed: this.editForm.get(['composed'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      nature: this.editForm.get(['nature'])!.value,
    };
  }

  protected updateForm(item: IItem): void {
    this.editForm.patchValue({
      id: item.id,
      name: item.name,
      urlPhoto: item.urlPhoto,
      urlPhotoContentType: item.urlPhotoContentType,
      gender: item.gender,
      composed: item.composed,
      archivated: item.archivated,
      nature: item.nature,
      price: item.price,
      priceDate: item.priceDate,
    });

    this.naturesSharedCollection = this.natureService.addNatureToCollectionIfMissing(this.naturesSharedCollection, item.nature);
  }
}
