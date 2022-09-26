import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EventManager } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventWithContent } from 'app/core/util/event-manager.service';
import { IChildStatus } from 'app/entities/child-status/child-status.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { ItemGender } from 'app/entities/enumerations/item-gender.model';
import { Status } from 'app/entities/enumerations/status.model';
import { IItemValue, ItemValue } from 'app/entities/item-value/item-value.model';
import { ItemValueService } from 'app/entities/item-value/service/item-value.service';
import { INature } from 'app/entities/nature/nature.model';
import { NatureService } from 'app/entities/nature/service/nature.service';
import { ISchoolLevel } from 'app/entities/school-level/school-level.model';
import { SchoolLevelService } from 'app/entities/school-level/service/school-level.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { Message } from 'primeng/api';
import { Table } from 'primeng/table';
import { map, Observable, finalize } from 'rxjs';
import { CompositeItem, ICompositeItem, ICompositeSchoolItem, IItem, Item } from '../item.model';
import { ItemService } from '../service/item.service';
import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { ISchoolLevelItem } from 'app/entities/school-level-item/school-level-item.model';
import { ChildStatusService } from 'app/entities/child-status/service/child-status.service';

@Component({
  selector: 'jhi-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css'],
})
export class AddItemComponent implements OnInit {
  isSaving = false;
  itemGenderValues = Object.keys(ItemGender);
  itemDialog?: boolean;
  submitted?: boolean;
  quantityToAdd = 1;
  idToSend!: string;
  childStatusesSharedCollection: IChildStatus[] = [];

  naturesSharedCollection: INature[] = [];
  natures: INature[] = [];
  schoolLevels!: ISchoolLevel[];
  selectedSchoolLevels: ISchoolLevel[] = [];
  compositeurItemCollection: ICompositeItem[] = [];
  compositeurSchoolItemCollection: ICompositeSchoolItem[] = [];
  SelectedLevel: ISchoolLevel[] = [];
  quantity: { id: number; qt: number }[] = [];
  finalquantitytable: { id: number; qt: number }[] = [];
  values: string[] = [];
  nature!: INature[];
  selectedNature!: INature;
  natureNames!: string[];
  results!: string[];
  itemsWithNature: IItem[] = [];

  text!: string;
  textParamNature!: string[];
  itemType = '2';
  itemNature = '2';
  textParam!: string;
  msgs1!: Message[];
  invalide = false;
  invalideItemName = false;
  selecteditemGender?: Gender;
  items?: IItem[];
  item?: IItem | null;
  a!: string;
  b!: string;
  n!: string;
  itemValue!: IItemValue;
  SimpleItemSearch?: string;
  CompositeurItemSearch?: string;
  schoolLevelDetails: ISchoolLevelItem[] = [];
  level!: ISchoolLevel;
  quantitySchoolItemTab: number[] = [];
  schoolLevelIemTab: number[] = [];

  SI = 0;
  SIC = 0;

  step = 1;
  id1 = -1;
  exist = false;
  i = false;
  j = false;
  k = false;

  t?: number;
  x = 0;
  y = 0;

  s?: number;
  z?: number;
  f?: number;

  id = -1;

  message?: string;
  statusValues = Object.keys(Status);

  SchoolLevelForm = this.fb.group({
    schoolLevelcheckbox: [],
  });

  SchoolItemQuantityForm = this.fb.group({
    quantityNeeded: [],
  });

  editForm = this.fb.group({
    name: [null, [Validators.required]],
  });

  simpleItemForm = this.fb.group({
    name: [null, [Validators.required]],
    urlPhoto: [],
    urlPhotoContentType: [],
    gender: [null, [Validators.required]],
    price: [null, [Validators.required]],
    priceDate: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
  });

  compositeItemForm = this.fb.group({
    name: [null, [Validators.required]],
    urlPhoto: [],
    urlPhotoContentType: [],
    gender: [null, [Validators.required]],
  });
  compositeurItemForm = this.fb.group({
    quantity: [null, [Validators.required]],
    name: [null, [Validators.required]],
    urlPhoto: [],
    urlPhotoContentType: [],
    gender: [null, [Validators.required]],
    nature: [null, [Validators.required]],
    price: [null, [Validators.required]],
    priceDate: [null, [Validators.required]],
  });

  simpleSchoolItemForm = this.fb.group({
    name: [null, [Validators.required]],
    urlPhoto: [],
    urlPhotoContentType: [],
    gender: [null, [Validators.required]],
    price: [null, [Validators.required]],
    priceDate: [null, [Validators.required]],
    schoolLevel: [null, [Validators.required]],
    nature: [],
    childStatus: [null, Validators.required],
  });

  compositeSchoolItemForm = this.fb.group({
    name: [null, [Validators.required]],
    urlPhoto: [],
    urlPhotoContentType: [],
    gender: [null, [Validators.required]],
  });

  compositeurSchoolItemForm = this.fb.group({
    name: [null, [Validators.required]],
    urlPhoto: [],
    urlPhotoContentType: [],
    gender: [null, [Validators.required]],
    price: [null, [Validators.required]],
    priceDate: [null, [Validators.required]],
    schoolLevel: [null, [Validators.required]],
    nature: [null, [Validators.required]],
    staus: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
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
    protected router: Router,
    protected messageService: MessageService,
    protected confirmationService: ConfirmationService,
    protected childStatusService: ChildStatusService
  ) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();
    this.schoolLevelService.findAllSchoolLevel().subscribe({
      next: (res: HttpResponse<ISchoolLevel[]>) => {
        this.schoolLevels = res.body ?? [];
        for (this.t = 0; this.t < this.schoolLevels.length; this.t++) {
          this.quantity[this.t] = { id: this.schoolLevels[this.t].id!, qt: -1 };
        }
      },
      error: e => console.error(e),
    });
    this.loadRelationships();
  }

  getSearch(event: any): void {
    this.results = this.search(event.query);
  }

  search(keyword: string): string[] {
    const names: string[] = [];
    this.getNatureID();

    this.itemService.findItemWithNature(this.id1).subscribe({
      next: (res: HttpResponse<IItem[]>) => {
        this.itemsWithNature = res.body ?? [];
        console.log(res);
      },
      error: e => console.error(e),
    });
    console.log(this.itemsWithNature);
    for (let i = 0; i < this.itemsWithNature.length; i++) {
      if (this.itemsWithNature[i].name?.includes(keyword)) {
        names.push(this.itemsWithNature[i].name!);
      }
    }
    return names;
  }

  SearchNature(event: any): void {
    this.textParamNature = this.searchN(event.query, this.naturesSharedCollection);
  }

  getNatureID(): void {
    console.log(this.naturesSharedCollection);

    for (let i = 0; i < this.naturesSharedCollection.length; i++) {
      if (
        this.naturesSharedCollection[i].name!.includes(this.textParam) &&
        this.naturesSharedCollection[i].name!.length === this.textParam.length
      ) {
        this.id1 = this.naturesSharedCollection[i].id!;
        this.selectedNature = this.naturesSharedCollection[i];
      }
      console.log(this.id1);
    }
  }

  searchN(keyword: string, tab: INature[]): string[] {
    const names: string[] = [];
    console.log(tab);
    for (let i = 0; i < tab.length; i++) {
      if (tab[i].name!.includes(keyword)) {
        names.push(tab[i].name!);
      }
    }
    return names;
  }

  getItemName(form: FormGroup, x: string): void {
    if (x === 'composite') {
      this.SI = 0;
      this.SIC = 0;
      this.n = this.SimpleItemSearch!;
      this.CompositeurItemSearch = '';
    }
    if (x === 'compositeur') {
      this.SIC = 0;
      this.n = this.CompositeurItemSearch!;
    }
    for (let i = 0; i < this.itemsWithNature.length; i++) {
      if (this.itemsWithNature[i].name!.includes(this.n) && this.itemsWithNature[i].name!.length === this.n.length) {
        this.item = this.itemsWithNature[i];
        if (x === 'composite') {
          this.SI = -1;
        }
        if (x === 'compositeur') {
          this.SIC = -1;
        }
      }
    }
    console.log(this.item);
    if (this.SI === -1 || this.SIC === -1) {
      this.itemValueService.findItem(this.item!.id!).subscribe({
        next: (res: HttpResponse<IItemValue>) => {
          this.itemValue = res.body!;
          console.log(this.itemValue);
          if (form === this.simpleItemForm || form === this.compositeurItemForm) {
            form.patchValue({
              name: this.item!.name,
              urlPhoto: this.item!.urlPhoto!,
              urlPhotoContentType: this.item!.urlPhotoContentType!,
              gender: this.item!.gender,
              price: this.itemValue.price!,
              priceDate: this.itemValue.priceDate!,
              quantity: 1,
            });
          }
          if (form === this.compositeItemForm) {
            this.compositeItemForm.patchValue({
              name: this.item!.name,
              urlPhoto: this.item!.urlPhoto!,
              urlPhotoContentType: this.item!.urlPhotoContentType!,
              gender: this.item!.gender,
            });
          }
        },
        error: e => console.error(e),
      });

      if (x === 'composite') {
        this.SI = 1;
        if (this.itemNature === '2' && this.itemType === '1') {
          this.router.navigateByUrl('item/add/composite', { state: { item: this.item, price: this.itemValue } });
        }

        if (this.itemNature === '2' && this.itemType === '2') {
          this.idToSend = this.item!.id!.toString();
          this.router.navigateByUrl('item/add/simple-item/' + this.idToSend, { state: { itemId: this.item!.id!, schoolItem: 'false' } });
        }
      }
      if (x === 'compositeur') {
        this.SIC = 1;
      }
      console.log('SI', this.SI);
      console.log('SIC', this.SIC);
    }

    if (x === 'composite') {
      if (this.SI === 0) {
        this.SI = 2;
        if (form === this.simpleItemForm) {
          this.initForm();
        }
        if (form === this.compositeItemForm) {
          this.initForm1();
        }

        console.log('X', x);
        console.log(this.SI);
      }
    }

    if (x === 'compositeur') {
      if (this.SIC === 0) {
        this.SIC = 2;
        if (form === this.compositeurItemForm) {
          this.initForm2();
        }

        console.log('SI', this.SI);
        console.log('SIC', this.SIC);
      }
    }
  }

  getSchoolItemName(form: FormGroup, x: string): void {
    if (x === 'composite') {
      this.SI = 0;
      this.SIC = 0;
      this.n = this.SimpleItemSearch!;
      this.CompositeurItemSearch = '';
    }
    if (x === 'compositeur') {
      this.SIC = 0;
      this.n = this.CompositeurItemSearch!;
    }
    for (let i = 0; i < this.itemsWithNature.length; i++) {
      if (this.itemsWithNature[i].name!.includes(this.n) && this.itemsWithNature[i].name!.length === this.n.length) {
        this.item = this.itemsWithNature[i];
        if (x === 'composite') {
          this.SI = -1;
        }
        if (x === 'compositeur') {
          this.SIC = -1;
        }
      }
    }

    console.log(this.item);
    if (this.SI === -1 || this.SIC === -1) {
      this.itemValueService.findItem(this.item!.id!).subscribe({
        next: (res: HttpResponse<IItemValue>) => {
          this.itemValue = res.body!;
          console.log(this.itemValue);
          if (form === this.simpleSchoolItemForm || form === this.compositeurSchoolItemForm) {
            form.patchValue({
              name: this.item!.name,
              urlPhoto: this.item!.urlPhoto!,
              urlPhotoContentType: this.item!.urlPhotoContentType!,
              gender: this.item!.gender,
              price: this.itemValue.price!,
              priceDate: this.itemValue.priceDate!,
              quantity: 1,
            });
          }
          if (form === this.compositeSchoolItemForm) {
            this.compositeSchoolItemForm.patchValue({
              name: this.item!.name,
              urlPhoto: this.item!.urlPhoto!,
              urlPhotoContentType: this.item!.urlPhotoContentType!,
              gender: this.item!.gender,
            });
          }
        },
        error: e => console.error(e),
      });

      if (x === 'composite') {
        this.SI = 1;
        if (this.itemNature === '1' && this.itemType === '1') {
          this.router.navigateByUrl('item/add/composite-school-item', { state: { item: this.item, price: this.itemValue } });
        }

        if (this.itemNature === '1' && this.itemType === '2') {
          this.idToSend = this.item!.id!.toString();
          this.router.navigateByUrl('item/add/simple-item/' + this.idToSend, { state: { itemId: this.item!.id!, schoolItem: 'yes' } });
        }
      }
      if (x === 'compositeur') {
        this.SIC = 1;
      }
      console.log('SI', this.SI);
      console.log('SIC', this.SIC);
    }

    if (x === 'composite') {
      if (this.SI === 0) {
        this.SI = 2;
        if (form === this.simpleSchoolItemForm) {
          this.initForm3();
        }
        if (form === this.compositeSchoolItemForm) {
          this.initForm5();
        }

        console.log('X', x);
        console.log(this.SI);
      }
    }

    if (x === 'compositeur') {
      if (this.SIC === 0) {
        this.SIC = 2;
        if (form === this.compositeurSchoolItemForm) {
          this.initForm4();
        }

        console.log('SI', this.SI);
        console.log('SIC', this.SIC);
      }
    }
  }

  openNew(): void {
    this.item = {};
    this.submitted = false;
    this.itemDialog = true;
  }

  updateTable($event: any): any {
    this.id = $event.target.id;
    const q = $event.target.value;
    for (this.x = 0; this.x < this.quantity.length; this.x++) {
      if (this.quantity[this.x].id - this.id === 0) {
        this.quantity[this.x] = { id: this.id, qt: q };
      }
    }

    return this.id;
  }

  finaleTable(): void {
    for (this.t = 0; this.t < this.SelectedLevel.length; this.t++) {
      for (this.x = 0; this.x < this.quantity.length; this.x++) {
        if (this.quantity[this.x].id - this.SelectedLevel[this.t].id! === 0) {
          //this.finalquantitytable[this.y] = { id: this.quantity[this.x].id, qt: this.quantity[this.x].qt };
          this.quantitySchoolItemTab[this.y] = this.quantity[this.x].qt;
          this.schoolLevelIemTab[this.y] = this.quantity[this.x].id;

          this.y++;
        }
      }
    }
    console.log(this.quantitySchoolItemTab);
    console.log(this.schoolLevelIemTab);

    this.add();
  }

  removevalue(i: any): void {
    this.values.splice(i, 1);
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData1(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.simpleItemForm, field, isImage).subscribe({});
  }
  setFileData2(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.compositeItemForm, field, isImage).subscribe({});
  }
  setFileData3(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.compositeurItemForm, field, isImage).subscribe({});
  }
  setFileData4(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.simpleSchoolItemForm, field, isImage).subscribe({});
  }
  setFileData5(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.compositeSchoolItemForm, field, isImage).subscribe({});
  }
  setFileData6(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.compositeurSchoolItemForm, field, isImage).subscribe({});
  }

  onchange($event: any): any {
    const id = $event.target.value;

    console.log(id);

    return id;
  }

  next(): void {
    console.log(this.itemNature);
    console.log(this.itemType);

    if (!this.textParam) {
      this.invalide = true;
    }

    if (this.textParam && this.itemNature === '1' && this.itemType === '2') {
      this.step = 4;
    }
    if (this.textParam && this.itemNature === '1' && this.itemType === '1') {
      this.step = 5;
    }
    if (this.textParam && this.itemNature === '2' && this.itemType === '2') {
      this.step = 2;
    }
    if (this.textParam && this.itemNature === '2' && this.itemType === '1') {
      this.step = 3;
    }
  }

  previous(): void {
    if (this.SI === 0) {
      this.step = 1;
    } else {
      this.SI = 0;
    }
  }

  previous2(): void {
    this.SIC = 0;
  }

  nextStep(): void {
    this.step++;
  }

  clear(table: Table): void {
    table.clear();
  }

  clearInputImage1(field: string, fieldContentType: string, idInput: string): void {
    this.simpleItemForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }
  clearInputImage2(field: string, fieldContentType: string, idInput: string): void {
    this.compositeItemForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }
  clearInputImage3(field: string, fieldContentType: string, idInput: string): void {
    this.compositeurItemForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }
  clearInputImage4(field: string, fieldContentType: string, idInput: string): void {
    this.simpleSchoolItemForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }
  clearInputImage5(field: string, fieldContentType: string, idInput: string): void {
    this.compositeSchoolItemForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }
  clearInputImage6(field: string, fieldContentType: string, idInput: string): void {
    this.compositeurSchoolItemForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  add(): void {
    if (this.step === 4) {
      const schoolItem = this.createFromSimpleSchoolItemForm();
      console.log(schoolItem);
      this.save();
    }

    if (this.step === 5) {
      if (this.compositeurSchoolItemForm.invalid) {
        return;
      }
      this.finaleTable();
      this.compositeurSchoolItemCollection.push(this.createFromCompositeurSchoolItemForm());
      console.log(this.compositeurSchoolItemCollection);
    }
  }

  previousState(): void {
    window.history.back();
  }

  trackChildStatusById(_index: number, item: IChildStatus): number {
    return item.id!;
  }

  save(): void {
    if (this.step === 2) {
      const item = this.createFromSimpleItemForm();
      this.subscribeToSaveResponse(this.itemService.addSimpleItem(item));
    }
    if (this.step === 3) {
      const item = this.createFromCompositeItemForm();
    }
    if (this.step === 4) {
      const item = this.createFromSimpleSchoolItemForm();
      this.subscribeToSaveResponse(this.itemService.addSimpleSchoolItem(item));
    }
    if (this.step === 5) {
      const item = this.createFromCompositeSchoolItemForm();
    }
  }

  trackNatureById(_index: number, item: INature): number {
    return item.id!;
  }

  protected loadRelationships(): void {
    this.childStatusService
      .query()
      .pipe(map((res: HttpResponse<IChildStatus[]>) => res.body ?? []))
      .pipe(
        map((childStatuses: IChildStatus[]) =>
          this.childStatusService.addChildStatusToCollectionIfMissing(childStatuses, this.simpleSchoolItemForm.get('childStatus')!.value)
        )
      )
      .subscribe((childStatuses: IChildStatus[]) => (this.childStatusesSharedCollection = childStatuses));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.isSaving = true;
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected initForm(): void {
    this.simpleItemForm.patchValue({
      name: this.SimpleItemSearch,
      urlPhoto: '',
      urlPhotoContentType: '',
      gender: '',
      price: '',
      priceDate: '',
      quantity: 1,
    });
    console.log(this.SI);
  }
  protected initForm1(): void {
    this.compositeItemForm.patchValue({
      name: this.SimpleItemSearch,
      urlPhoto: '',
      urlPhotoContentType: '',
      gender: '',
    });
  }

  protected initForm2(): void {
    this.compositeurItemForm.patchValue({
      name: this.CompositeurItemSearch,
      urlPhoto: '',
      urlPhotoContentType: '',
      gender: '',
      price: '',
      priceDate: '',
      quantity: 1,
      nature: this.textParam,
    });
  }

  protected initForm3(): void {
    this.simpleSchoolItemForm.patchValue({
      name: this.SimpleItemSearch,
      urlPhoto: '',
      urlPhotoContentType: '',
      gender: '',
      price: '',
      priceDate: '',
      quantity: 1,
      nature: this.textParam,
    });
  }

  protected initForm4(): void {
    this.compositeurSchoolItemForm.patchValue({
      name: this.CompositeurItemSearch,
      urlPhoto: '',
      urlPhotoContentType: '',
      gender: '',
      price: '',
      priceDate: '',
      quantity: 1,
      nature: this.textParam,
    });
  }

  protected initForm5(): void {
    this.compositeSchoolItemForm.patchValue({
      name: this.SimpleItemSearch,
      urlPhoto: '',
      urlPhotoContentType: '',
      gender: '',
    });
  }

  protected loadRelationshipsOptions(): void {
    this.natureService
      .query()
      .pipe(map((res: HttpResponse<INature[]>) => res.body ?? []))
      .subscribe((natures: INature[]) => (this.naturesSharedCollection = natures));
    this.natures = this.naturesSharedCollection;
  }

  protected createFromSimpleItemForm(): IItem {
    return {
      ...new Item(),
      name: this.simpleItemForm.get(['name'])!.value,
      urlPhotoContentType: this.simpleItemForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.simpleItemForm.get(['urlPhoto'])!.value,
      gender: this.simpleItemForm.get(['gender'])!.value,
      price: this.simpleItemForm.get(['price'])!.value,
      nature: this.selectedNature,
      priceDate: this.simpleItemForm.get(['priceDate'])!.value,
      quantityToAdd: this.quantityToAdd,
    };
  }
  protected createFromCompositeItemForm(): IItem {
    return {
      ...new Item(),
      name: this.compositeItemForm.get(['name'])!.value,
      urlPhotoContentType: this.compositeItemForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.compositeItemForm.get(['urlPhoto'])!.value,
      gender: this.compositeItemForm.get(['gender'])!.value,
      nature: this.selectedNature,
      compositeurSimpleItems: this.compositeurItemCollection.slice(),
      quantityToAdd: this.quantityToAdd,
    };
  }

  protected createFromCompositeurItemForm(): ICompositeItem {
    return {
      ...new CompositeItem(),
      name: this.compositeurItemForm.get(['name'])!.value,
      urlPhotoContentType: this.compositeurItemForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.compositeurItemForm.get(['urlPhoto'])!.value,
      gender: this.compositeurItemForm.get(['gender'])!.value,
      nature: this.compositeurItemForm.get(['nature'])!.value,
      price: this.compositeurItemForm.get(['price'])!.value,
      priceDate: this.compositeurItemForm.get(['priceDate'])!.value,
    };
  }
  protected createFromSimpleSchoolItemForm(): IItem {
    return {
      ...new Item(),
      name: this.simpleSchoolItemForm.get(['name'])!.value,
      urlPhotoContentType: this.simpleSchoolItemForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.simpleSchoolItemForm.get(['urlPhoto'])!.value,
      gender: this.simpleSchoolItemForm.get(['gender'])!.value,
      price: this.simpleSchoolItemForm.get(['price'])!.value,
      priceDate: this.simpleSchoolItemForm.get(['priceDate'])!.value,
      nature: this.selectedNature,
      childStatus: this.simpleSchoolItemForm.get(['childStatus'])!.value,
      quantitySchoolItemTab: this.quantitySchoolItemTab.slice(),
      schoolLevelIemTab: this.schoolLevelIemTab.slice(),
      quantityToAdd: this.quantityToAdd,
    };
  }
  protected createFromCompositeSchoolItemForm(): IItem {
    return {
      ...new Item(),
      name: this.compositeSchoolItemForm.get(['name'])!.value,
      urlPhotoContentType: this.compositeSchoolItemForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.compositeSchoolItemForm.get(['urlPhoto'])!.value,
      gender: this.compositeSchoolItemForm.get(['gender'])!.value,
      nature: this.selectedNature,
      compositeurSchoolItems: this.compositeurSchoolItemCollection.slice(),
      quantityToAdd: this.quantityToAdd,
    };
  }
  protected createFromCompositeurSchoolItemForm(): IItem {
    return {
      ...new Item(),
      name: this.compositeurSchoolItemForm.get(['name'])!.value,
      urlPhotoContentType: this.compositeurSchoolItemForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.compositeurSchoolItemForm.get(['urlPhoto'])!.value,
      gender: this.compositeurSchoolItemForm.get(['gender'])!.value,
      price: this.compositeurSchoolItemForm.get(['price'])!.value,
      priceDate: this.compositeurSchoolItemForm.get(['priceDate'])!.value,
      nature: this.compositeurSchoolItemForm.get(['nature'])!.value,
      schoolLevelDetails: this.finalquantitytable.slice(),
      staus: this.compositeurSchoolItemForm.get(['staus'])!.value,
    };
  }
}
