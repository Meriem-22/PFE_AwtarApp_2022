import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { EventManager } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { DataUtils } from 'app/core/util/data-util.service';
import { ChildStatusItemService } from 'app/entities/child-status-item/service/child-status-item.service';
import { ItemGender } from 'app/entities/enumerations/item-gender.model';
import { IItemValue, ItemValue } from 'app/entities/item-value/item-value.model';
import { ItemValueService } from 'app/entities/item-value/service/item-value.service';
import { NatureService } from 'app/entities/nature/service/nature.service';
import { SchoolLevelItemService } from 'app/entities/school-level-item/service/school-level-item.service';
import { SchoolLevelService } from 'app/entities/school-level/service/school-level.service';
import dayjs from 'dayjs';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Observable, finalize } from 'rxjs';
import { IItem } from '../item.model';
import { ItemService } from '../service/item.service';

@Component({
  selector: 'jhi-add-simple-item',
  templateUrl: './add-simple-item.component.html',
  styleUrls: ['./add-simple-item.component.css'],
})
export class AddSimpleItemComponent implements OnInit {
  id!: number;
  item!: IItem;
  priceItem!: IItemValue;
  itemDialog?: boolean;
  submitted?: boolean;
  isSaving = false;
  quantity = 1;
  schoolItem?: boolean;
  SchoolLevelInfo?: any[];
  childStatusItem!: any[];
  account: Account | null = null;

  nPrice!: number;
  nPriceDate!: Date;
  nQtOnStock!: number;

  itemGenderValues = Object.keys(ItemGender);

  editForm = this.fb.group({
    id: [],
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
    private accountService: AccountService,
    protected childStatusItemService: ChildStatusItemService
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.id = history.state.itemId;
    this.schoolItem = history.state.schoolItem === 'yes';
    console.log(history.state.itemId);
    console.log(this.schoolItem);

    this.itemService.find(this.id).subscribe({
      next: (res: HttpResponse<IItem>) => {
        this.item = res.body!;
      },
      error: e => console.error(e),
    });

    if (this.schoolItem) {
      this.childStatusItemService.findAllStatusOfItem(this.id).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.childStatusItem = res.body ?? [];
          console.log(this.childStatusItem);
        },
        error: e => console.error(e),
      });

      this.schoolLevelItemService.findAllCompositeurSchoolItems(this.id).subscribe({
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
    this.showPrice();
  }

  hideDialog(): void {
    this.itemDialog = false;
    this.submitted = false;
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemValue = this.createFromForm2();
    if (itemValue.id !== undefined) {
      this.subscribeToSaveResponse(this.itemValueService.update(itemValue));
    } else {
      this.subscribeToSaveResponse(this.itemValueService.create(itemValue));
    }
  }

  editPrice(): void {
    this.isSaving = true;
    const itemValue = this.createFromForm();
    if (itemValue.id !== undefined) {
      this.subscribeToSaveResponse(this.itemValueService.update(itemValue));
    } else {
      this.subscribeToSaveResponse(this.itemValueService.create(itemValue));
    }
  }

  showPrice(): void {
    this.itemValueService.findItem(this.id).subscribe({
      next: (res: HttpResponse<IItemValue>) => {
        this.priceItem = res.body!;
        this.editForm.patchValue({
          price: this.priceItem.price,
          priceDate: this.priceItem.priceDate,
        });
      },
      error: e => console.error(e),
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemValue>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.showPrice(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.submitted = true;
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected createFromForm(): IItemValue {
    return {
      ...new ItemValue(),
      id: this.priceItem.id,
      price: this.editForm.get(['price'])!.value,
      priceDate: this.editForm.get(['priceDate'])!.value,
      availableStockQuantity: this.priceItem.availableStockQuantity,
      archivated: this.priceItem.archivated,
      item: this.priceItem.item,
    };
  }

  protected createFromForm2(): IItemValue {
    return {
      ...new ItemValue(),
      id: this.priceItem.id,
      price: this.priceItem.price,
      priceDate: this.priceItem.priceDate,
      availableStockQuantity: this.priceItem.availableStockQuantity! + this.quantity,
      archivated: this.priceItem.archivated,
      item: this.priceItem.item,
    };
  }
}
