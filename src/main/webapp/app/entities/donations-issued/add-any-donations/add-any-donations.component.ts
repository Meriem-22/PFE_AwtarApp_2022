import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'app/core/util/data-util.service';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
import { ChildService } from 'app/entities/child/service/child.service';
import { DonationDetails, IDonationDetails } from 'app/entities/donation-details/donation-details.model';
import { DonationDetailsService } from 'app/entities/donation-details/service/donation-details.service';
import { DonationItemDetails, IDonationItemDetails } from 'app/entities/donation-item-details/donation-item-details.model';
import { Beneficiaries } from 'app/entities/enumerations/beneficiaries.model';
import { Period } from 'app/entities/enumerations/period.model';
import { IEstablishment } from 'app/entities/establishment/establishment.model';
import { EstablishmentService } from 'app/entities/establishment/service/establishment.service';
import { IFamily, IFamilyAllDetails } from 'app/entities/family/family.model';
import { FamilyService } from 'app/entities/family/service/family.service';
import { IItem, Item } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';
import { INature } from 'app/entities/nature/nature.model';
import { NatureService } from 'app/entities/nature/service/nature.service';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { MenuItem } from 'primeng/api';
import { finalize, map, Observable } from 'rxjs';
import { DonationsIssued, IDonationsIssued } from '../donations-issued.model';
import { DonationsIssuedService } from '../service/donations-issued.service';

@Component({
  selector: 'jhi-add-any-donations',
  templateUrl: './add-any-donations.component.html',
  styleUrls: ['./add-any-donations.component.css'],
})
export class AddAnyDonationsComponent implements OnInit {
  isSaving = false;
  beneficiariesValues = Object.keys(Beneficiaries);
  periodValues = Object.keys(Period);
  recurring?: string;
  isSavingDetails = false;
  isLoading = false;
  display = false;
  displayc = false;
  displayE = false;
  nbDonCTot = 0;
  selectedFID?: string;
  selectedCID?: string;
  selectedEID?: string;
  targetProducts: IItem[] = [];

  donationsIssuedsSharedCollection: IDonationsIssued[] = [];
  naturesSharedCollection: INature[] = [];
  naturesSharedCollectionItem: INature[] = [];
  textParamNature!: string[];
  beneficiariesSharedCollection: IBeneficiary[] = [];
  itemsSharedCollection: IItem[] = [];
  selectedNature!: INature;
  natureNames!: string[];
  results!: string[];
  items: any[] = [];
  DonationsDetailsCollection: IDonationDetails[] = [];
  // ChildrenCollection: IChildAllDetails[] = [];
  textParam!: string;
  id1 = -1;
  step2 = 0;
  m = 0;
  n = 0;
  z = 0;
  k = 0;

  familys?: IFamily[] = [];
  childrenProfiles?: IProfile[] = [];
  establishments?: IEstablishment[] = [];

  selectedFamilys?: IFamily[] = [];
  selectedChildrenProfiles?: IProfile[] = [];
  selectedEstablishments?: IEstablishment[] = [];
  beneficiaries: number[] = [];
  AddedItems: IDonationItemDetails[] = [];
  itemdetails?: IDonationItemDetails;
  quantity?: number;

  DonationsIssued!: FormGroup;
  DonationsDetails!: FormGroup;
  DonationsDetailsItem!: FormGroup;
  beneficiaryType: string | undefined;
  donationsNature!: INature;

  donationList?: any[];
  donationListChild?: any[];
  donationChild = 0;
  Ordonnateur!: IProfile;
  Tuteur!: IProfile;
  parents?: any[] = [];
  children?: IProfile[] = [];
  childrenForDonation: IProfile[] = [];
  selectedchildrenofFamily: IProfile[] = [];
  childrenDetails: any[] = [];
  selectedChildren: any[] = [];
  childrenSchoolDetails: any[] = [];
  childrenWithoutFamilyDetails: any[] = [];
  allchildren: any[] = [];
  beneficiary!: IBeneficiary;
  t = 0;
  df = 0;
  o = 0;
  nbChild = 0;
  displayMaximizable!: boolean;

  DonationsIssued_step = false;
  DonationsDetails_step = false;
  step = 1;
  i = 0;
  j = 0;
  x = 0;
  y = 0;
  id = -1;

  myDate!: number;
  textParamNatureDon!: string[];
  natureDon!: string;

  childEducation: any;

  recDon = false;

  SelectedItem: IItem[] = [];
  quantityItemTab: number[] = [];
  IemTab: number[] = [];
  TabWithOneItemToAdd: number[] = [];
  quantityOfSelectedItem: { id: number; qt: number }[] = [];
  productDialog?: boolean;
  description?: string;
  natureDetailsDon!: INature;
  itemss!: MenuItem[];
  displayChildren = false;
  natureDescriptionDonation!: INature;
  DonationDetails?: IDonationDetails[] | null;

  QuantityForm = this.formBuilder.group({
    quantity: [null, [Validators.required]],
  });

  editForm = this.formBuilder.group({
    description: [null, [Validators.required]],
  });

  constructor(
    protected donationsIssuedService: DonationsIssuedService,
    protected activatedRoute: ActivatedRoute,
    protected formBuilder: FormBuilder,
    protected donationDetailsService: DonationDetailsService,
    protected natureService: NatureService,
    protected beneficiaryService: BeneficiaryService,
    protected itemService: ItemService,
    protected profileService: ProfileService,
    protected familyService: FamilyService,
    protected establishmentService: EstablishmentService,
    protected dataUtils: DataUtils,
    protected childService: ChildService
  ) {}

  ngOnInit(): void {
    this.DonationsIssued = this.formBuilder.group({
      model: [null, [Validators.required]],
      isValidated: [],
      donationsDate: [null, [Validators.required]],
      recurringDonations: [],
      periodicity: [],
      recurrence: [],
      beneficiaryType: [null, [Validators.required]],
    });

    this.DonationsDetails = this.formBuilder.group({
      description: [null, [Validators.required]],
      archivated: [],
      beneficiary: [null, Validators.required],
      quantity: [null, [Validators.required]],
      date: [null, [Validators.required]],
      item: [null, Validators.required],
    });
    this.loadRelationshipsOptions();
    this.loadRelationshipsOptionsItemDetails();
    this.childDon();

    this.childService.getChildrenDetails().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = true;
        this.childrenDetails = res.body ?? [];

        console.log(this.childrenDetails);
      },
      error: () => {
        this.isLoading = false;
      },
    });
    this.childService.getChildrenWithoutFamilyDetails().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.isLoading = true;
        this.childrenWithoutFamilyDetails = res.body ?? [];

        console.log(this.childrenWithoutFamilyDetails);
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  trackItemById(_index: number, item: IItem): number {
    return item.id!;
  }

  changeSelection(enent: any): void {
    if (this.recDon === false) {
      this.recDon = true;
    } else {
      this.recDon = false;
    }

    console.log(this.recDon);
  }

  next(): void {
    this.step++;
    this.beneficiaryType = this.DonationsIssued.get(['beneficiaryType'])!.value;
    console.log(this.beneficiaryType);

    if (this.beneficiaryType === 'FAMILY') {
      this.familyService.findFamily().subscribe({
        next: (res: HttpResponse<IFamily[]>) => {
          this.isLoading = true;
          this.familys = res.body ?? [];
          console.log(this.familys);
          this.step2 = 2;
        },
        error: () => {
          this.isLoading = false;
        },
      });
    }

    if (this.beneficiaryType === 'CHILD') {
      this.step2 = 1;
      this.myDate = new Date().getFullYear();

      for (
        this.m = 0, this.n = 0, this.z = 0;
        this.m < this.childrenWithoutFamilyDetails.length + this.childrenDetails.length - 1;
        this.m++
      ) {
        if (this.n < this.childrenDetails.length) {
          this.allchildren[this.m] = this.childrenDetails[this.n];
          this.n++;
        }
        if (this.n === this.childrenDetails.length) {
          this.allchildren[this.m + 1] = this.childrenWithoutFamilyDetails[this.z];
          this.allchildren[this.m + 1].familyName = 'sans famille';
          this.z++;
        }
      }
      console.log(this.allchildren);

      this.childService.getSchoolChildrenDetails(this.myDate.toString()).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.isLoading = true;
          this.childrenSchoolDetails = res.body ?? [];

          console.log(this.childrenSchoolDetails);
        },
        error: () => {
          this.isLoading = false;
        },
      });
    }

    if (this.beneficiaryType === 'ESTABLISHMENT') {
      this.establishmentService.findEstablishments().subscribe({
        next: (res: HttpResponse<IEstablishment[]>) => {
          this.isLoading = true;
          this.establishments = res.body ?? [];
          console.log(this.establishments);
          this.step2 = 3;
        },
        error: () => {
          this.isLoading = false;
        },
      });
    }

    if (this.beneficiaryType === 'COMMMON') {
      this.step2 = 4;
      this.familyService.findFamily().subscribe({
        next: (res: HttpResponse<IFamily[]>) => {
          this.isLoading = true;
          this.familys = res.body ?? [];
          console.log(this.familys);
        },
        error: () => {
          this.isLoading = false;
        },
      });

      this.myDate = new Date().getFullYear();

      for (
        this.m = 0, this.n = 0, this.z = 0;
        this.m < this.childrenWithoutFamilyDetails.length + this.childrenDetails.length - 1;
        this.m++
      ) {
        if (this.n < this.childrenDetails.length) {
          this.allchildren[this.m] = this.childrenDetails[this.n];
          this.n++;
        }
        if (this.n === this.childrenDetails.length) {
          this.allchildren[this.m + 1] = this.childrenWithoutFamilyDetails[this.z];
          this.allchildren[this.m + 1].familyName = 'sans famille';
          this.z++;
        }
      }
      console.log(this.allchildren);

      this.childService.getSchoolChildrenDetails(this.myDate.toString()).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.isLoading = true;
          this.childrenSchoolDetails = res.body ?? [];

          console.log(this.childrenSchoolDetails);
        },
        error: () => {
          this.isLoading = false;
        },
      });

      this.establishmentService.findEstablishments().subscribe({
        next: (res: HttpResponse<IEstablishment[]>) => {
          this.isLoading = true;
          this.establishments = res.body ?? [];
          console.log(this.establishments);
        },
        error: () => {
          this.isLoading = false;
        },
      });
    }
  }

  showDialog(family: IFamilyAllDetails): void {
    this.selectedFID = family.familyName!;
    this.display = true;
    this.profileService.getAllParentsProfile(family.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.parents = res.body ?? [];
        for (this.t = 0; this.t < this.parents.length; this.t++) {
          this.o = this.o + +this.parents[this.t].annualRevenue;
        }
      },
      error: e => console.error(e),
    });

    this.profileService.getAllChildrenProfile(family.id!).subscribe({
      next: (res: HttpResponse<IProfile[]>) => {
        this.children = res.body ?? [];
        this.nbChild = this.children.length;
        for (this.t = 0; this.t < this.children.length; this.t++) {
          this.donationDetailsService.getAllDonationsIssuedOfFamily(this.children[this.t].child!.id!).subscribe({
            next: (resc: HttpResponse<any[]>) => {
              this.nbDonCTot = this.nbDonCTot + resc.body!.length;
            },
            error: e => console.error(e),
          });
        }
      },
      error: e => console.error(e),
    });

    this.donationDetailsService.getAllDonationsIssuedOfFamily(family.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.donationList = res.body ?? [];
        for (this.t = 0; this.t < this.donationList.length; this.t++) {
          if (this.donationList[this.t].isValidated === true) {
            this.df = this.df + 1;
          }
        }
      },
      error: e => console.error(e),
    });

    this.profileService.findProfile(family.authorizingOfficer!.id!).subscribe({
      next: res => {
        this.Ordonnateur = res.body!;
      },
      error: e => console.error(e),
    });

    this.profileService.findProfile(family.tutor!.id!).subscribe({
      next: res => {
        this.Tuteur = res.body!;
      },
      error: e => console.error(e),
    });
  }

  showDialogChild(child: any): void {
    this.display = true;
    this.selectedCID = child.firstName.toString();
    this.selectedFID = child.lastName.toString();
    this.selectedCID = this.selectedCID! + ' ' + this.selectedFID!;
    this.childEducation = {};
    this.df = 0;
    for (let s = 0; s < this.childrenSchoolDetails.length; s++) {
      if (this.childrenSchoolDetails[s].id === child.id) {
        this.childEducation = { ...this.childrenSchoolDetails[s] };
      }
    }
    console.log(this.childEducation);

    this.donationDetailsService.getAllDonationsIssuedOfFamily(child.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.donationList = res.body ?? [];
        for (this.t = 0; this.t < this.donationList.length; this.t++) {
          if (this.donationList[this.t].isValidated === true) {
            this.df = this.df + 1;
          }
        }
      },
      error: e => console.error(e),
    });

    /* this.beneficiaryService.find(child.id!).subscribe({
      next: (res: HttpResponse<IBeneficiary>) =>{
       this.beneficiary = res.body!
       console.log(this.beneficiary);

        this.profileService.findProfile(this.beneficiary.authorizingOfficer!.id!).subscribe({
          next: (res2: HttpResponse<IProfile>) => {
            this.Ordonnateur = res2.body!;
            console.log(this.Ordonnateur);
          },
          error: e => console.error(e),
        });

   this.profileService.findProfile(this.beneficiary.tutor!.id!).subscribe({
      next: (res3: HttpResponse<IProfile>) => {
        this.Tuteur = res3.body!;
       // console.log(this.Tuteur)
      },
      error: e => console.error(e),
    });
   
  },
    
    error: e => console.error(e),
  });*/
  }

  showDialogEstablishment(establishment: IEstablishment): void {
    this.display = true;
    this.selectedEID = establishment.name!;
    this.donationDetailsService.getAllDonationsIssuedOfFamily(establishment.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.donationList = res.body ?? [];
        for (this.t = 0; this.t < this.donationList.length; this.t++) {
          if (this.donationList[this.t].isValidated === true) {
            this.df = this.df + 1;
          }
        }
      },
      error: e => console.error(e),
    });
  }

  childNbDon(child: IProfile): number {
    this.donationChild = 0;

    /*this.donationDetailsService.getAllDonationsIssuedOfFamily(child.child!.id!).subscribe({
    next: (res: HttpResponse<any[]>) => {
      this.donationListChild = res.body ?? [];
      this.donationChild= this.donationListChild.length
      console.log(this.donationListChild);
    },
    error: e => console.error(e),
  });*/

    return this.donationChild;
  }

  childDon(): void {
    for (this.t = 0; this.t < this.children!.length; this.t++) {
      this.donationDetailsService.getAllDonationsIssuedOfFamily(this.children![this.t].child!.id!).subscribe({
        next: (resc: HttpResponse<any[]>) => {
          this.nbDonCTot = this.nbDonCTot + resc.body!.length;
        },
        error: e => console.error(e),
      });
    }
  }

  add(): void {
    if (this.step === 2) {
      this.DonationsDetails_step = true;
      if (this.DonationsDetails.invalid) {
        return;
      }
      this.DonationsDetailsCollection.push(this.createFromFormDonationDetails());
      //  console.log(this.DonationsDetailsCollection)

      this.DonationsDetails = this.formBuilder.group({
        description: [null, [Validators.required]],
        beneficiaryType: [null, [Validators.required]],
        archivated: [],
        nature: [null, Validators.required],
        beneficiary: [null, Validators.required],
        quantity: [null, [Validators.required]],
        date: [null, [Validators.required]],
        item: [null, Validators.required],
      });
    }
  }

  donationDescription(): void {
    this.productDialog = true;
  }

  hideDialog(): void {
    this.productDialog = false;

    this.editForm.reset();
    this.natureDon = '';
  }

  updateTable($event: any): any {
    this.id = $event.target.id;
    const q = $event.target.value;
    let exist = false;
    for (this.x = 0; this.x < this.quantityOfSelectedItem.length; this.x++) {
      if (this.quantityOfSelectedItem[this.x].id - this.id === 0) {
        this.quantityOfSelectedItem[this.x] = { id: this.id, qt: q };
        exist = true;
      }
      console.log(this.quantityOfSelectedItem);
      break;
    }
    if (!exist) {
      this.quantityOfSelectedItem[this.quantityOfSelectedItem.length] = { id: this.id, qt: q };
    }
    return this.id;
  }

  finaleTable(): void {
    for (this.t = 0; this.t < this.targetProducts.length; this.t++) {
      for (this.x = 0; this.x < this.quantityOfSelectedItem.length; this.x++) {
        if (this.quantityOfSelectedItem[this.x].id - this.targetProducts[this.t].id! === 0) {
          this.quantityItemTab[this.y] = this.quantityOfSelectedItem[this.x].qt;
          this.IemTab[this.y] = this.quantityOfSelectedItem[this.x].id;

          this.y++;
        }
      }
    }

    if (this.targetProducts.length > this.quantityOfSelectedItem.length) {
      for (this.t = 0; this.t < this.targetProducts.length; this.t++) {
        let s = false;
        for (this.x = 0; this.x < this.quantityOfSelectedItem.length; this.x++) {
          if (this.quantityOfSelectedItem[this.x].id - this.targetProducts[this.t].id! === 0) {
            s = true;
          }
        }
        if (!s) {
          this.TabWithOneItemToAdd[this.k] = this.targetProducts[this.t].id!;
          this.k++;
        }
      }
    }
  }

  previousState(): void {
    window.history.back();
  }

  previous(): void {
    if (this.step > 1) {
      this.step = this.step - 1;
    }
  }

  goToItem(): void {
    this.step = 3;
    this.description = this.editForm.get(['description'])!.value;

    this.textParam = this.natureDon;
    this.search();

    this.editForm.reset();
    this.natureDon = '';
    this.natureDescriptionDonation = this.selectedNature;

    this.hideDialog();
  }

  showMaximizableDialog(): void {
    this.displayMaximizable = true;
  }

  target(item: IItem): boolean {
    for (let p = 0; p < this.targetProducts.length; p++) {
      if (this.targetProducts[p].id === item.id) {
        console.log(this.targetProducts[p].id);
        console.log(item.id);

        return true;
        break;
      }
    }
    return false;
  }

  trackNatureById(_index: number, item: INature): number {
    return item.id!;
  }

  trackBeneficiaryById(_index: number, item: IBeneficiary): number {
    return item.id!;
  }

  submit(): void {
    this.isSaving = true;
    const dons = this.createFromFormDonationsIssued();
    this.subscribeToSaveResponse(this.donationsIssuedService.createDons(dons));
    console.log(dons);
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  addBeneficiary(identity: number): void {
    if (this.j === 0) {
      this.beneficiaries.push(this.i);
      this.j++;
    }
    this.beneficiaries.push(identity);
    console.log(this.beneficiaries);
  }

  addDonationDetails(): void {
    this.DonationDetails!.push(this.createFromFormDonationDetails());
    console.log(this.DonationDetails);
  }

  search(): void {
    this.getNatureID();

    this.itemService.findItemsDetailsWithNature(this.id1).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.items = res.body ?? [];
        console.log(this.items);
      },
      error: e => console.error(e),
    });
  }

  SearchNature(event: any, x: string): void {
    if (x === 'searchNatureDon') {
      this.textParamNatureDon = this.searchNDon(event.query, this.naturesSharedCollection);
    }
    if (x === 'searchNatureItem') {
      this.textParamNature = this.searchN(event.query, this.naturesSharedCollection);
    }
  }

  getNatureID(): void {
    for (let i = 0; i < this.naturesSharedCollection.length; i++) {
      if (
        this.naturesSharedCollection[i].name!.includes(this.textParam) &&
        this.naturesSharedCollection[i].name!.length === this.textParam.length
      ) {
        this.id1 = this.naturesSharedCollection[i].id!;
        this.selectedNature = this.naturesSharedCollection[i];
      }
      console.log(this.id1);
      console.log(this.textParam);
    }
  }

  searchN(keyword: string, tab: INature[]): string[] {
    const names: string[] = [];
    console.log(this.textParam);
    console.log(tab);
    for (let i = 0; i < tab.length; i++) {
      if (tab[i].name!.includes(keyword)) {
        names.push(tab[i].name!);
      }
    }
    return names;
  }

  searchNDon(keyword: string, tab: INature[]): string[] {
    const names: string[] = [];
    console.log(this.natureDon);
    console.log(tab);
    for (let i = 0; i < tab.length; i++) {
      if (tab[i].name!.includes(keyword)) {
        names.push(tab[i].name!);
      }
    }
    return names;
  }

  getChildren(id: number): void {
    console.log(id);

    this.profileService.getAllChildrenProfile(id).subscribe({
      next: (res: HttpResponse<IProfile[]>) => {
        this.childrenForDonation = res.body ?? [];
        this.nbChild = this.childrenForDonation.length;
      },
      error: e => console.error(e),
    });

    this.displayChildren = true;
  }

  getBeneficiaryID(benef: any[]): void {
    for (let i = 0; i < benef.length; i++) {
      this.beneficiaries[i] = benef[i].id;
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonationsIssued>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected loadRelationshipsOptions(): void {
    this.natureService
      .query()
      .pipe(map((res: HttpResponse<INature[]>) => res.body ?? []))
      .subscribe((natures: INature[]) => (this.naturesSharedCollection = natures));
    // this.natures = this.naturesSharedCollection;
  }

  protected onSaveSuccess(): void {
    this.previousState();
    // this.router.navigate(['family',  , 'view']
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected loadRelationshipsOptionsItemDetails(): void {
    this.itemService
      .query()
      .pipe(map((res: HttpResponse<IItem[]>) => res.body ?? []))
      .pipe(map((items: IItem[]) => this.itemService.addItemToCollectionIfMissing(items, this.DonationsDetails.get('item')!.value)))
      .subscribe((items: IItem[]) => (this.itemsSharedCollection = items));
  }

  protected createFromFormItemDetails(): IDonationItemDetails {
    return {
      ...new DonationItemDetails(),
      itemsWithQuantitys: this.IemTab,
      quantityOfItems: this.quantityItemTab,
      itemsWithoutQuantitys: this.TabWithOneItemToAdd,
    };
  }

  protected createFromFormDonationsIssued(): IDonationsIssued {
    return {
      ...new DonationsIssued(),
      model: this.DonationsIssued.get(['model'])!.value,
      isValidated: this.DonationsIssued.get(['isValidated'])!.value,
      donationsDate: this.DonationsIssued.get(['donationsDate'])!.value,
      recurringDonations: this.DonationsIssued.get(['recurringDonations'])!.value,
      periodicity: this.DonationsIssued.get(['periodicity'])!.value,
      recurrence: this.DonationsIssued.get(['recurrence'])!.value,
      donationsDetailsN: this.DonationDetails,
    };
  }

  protected createFromFormDonationDetails(): IDonationDetails {
    if (this.beneficiaryType === 'FAMILY') {
      this.getBeneficiaryID(this.selectedFamilys!);
    }
    if (this.beneficiaryType === 'CHILD') {
      this.getBeneficiaryID(this.selectedChildren);
    }
    if (this.beneficiaryType === 'ESTABLISHMENT') {
      this.getBeneficiaryID(this.selectedEstablishments!);
    }

    return {
      ...new DonationDetails(),
      description: this.description,
      nature: this.natureDescriptionDonation,
      donationItemDetails: this.createFromFormItemDetails(),
      idsBeneficiary: this.beneficiaries,
    };
  }
}
