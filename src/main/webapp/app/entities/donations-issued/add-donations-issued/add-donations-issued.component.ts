import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'app/core/util/data-util.service';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
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
import { finalize, map, Observable } from 'rxjs';
import { DonationsIssued, IDonationsIssued } from '../donations-issued.model';
import { DonationsIssuedService } from '../service/donations-issued.service';

@Component({
  selector: 'jhi-add-donations-issued',
  templateUrl: './add-donations-issued.component.html',
  styleUrls: ['./add-donations-issued.component.css'],
})
export class AddDonationsIssuedComponent implements OnInit {
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
  textParamNature!: string[];
  beneficiariesSharedCollection: IBeneficiary[] = [];
  itemsSharedCollection: IItem[] = [];
  items: IItem[] = [];
  DonationsDetailsCollection: IDonationDetails[] = [];
  // ChildrenCollection: IChildAllDetails[] = [];
  textParam!: string;
  id1 = -1;
  step2 = 0;

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
  Ordonnateur!: HttpResponse<IProfile>;
  Tuteur!: HttpResponse<IProfile>;
  parents?: any[] = [];
  children?: IProfile[] = [];
  t = 0;
  df = 0;
  o = 0;
  nbChild = 0;

  DonationsIssued_step = false;
  DonationsDetails_step = false;
  step = 1;
  i = 0;
  j = 0;

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
    protected dataUtils: DataUtils
  ) {}

  ngOnInit(): void {
    this.DonationsIssued = this.formBuilder.group({
      model: [null, [Validators.required]],
      isValidated: [],
      validationDate: [],
      donationsDate: [],
      canceledDonations: [],
      canceledOn: [],
      cancellationReason: [],
      recurringDonations: [],
      periodicity: [],
      recurrence: [],
      beneficiaryType: [null, [Validators.required]],
      nature: [null, Validators.required],
      archivated: [],
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
  }

  trackItemById(_index: number, item: IItem): number {
    return item.id!;
  }

  next(): void {
    if (this.step === 1) {
      this.DonationsIssued_step = true;
      /*if (this.DonationsIssued.invalid) {
        return;
      }*/
    }

    this.beneficiaryType = this.DonationsIssued.get(['beneficiaryType'])!.value;
    console.log(this.beneficiaryType);

    if (this.beneficiaryType === 'FAMILY') {
      this.familyService.findFamily().subscribe({
        next: (res: HttpResponse<IFamily[]>) => {
          this.isLoading = true;
          this.familys = res.body ?? [];
          console.log(this.familys);
          this.step = 3;
          this.i = 1;
        },
        error: () => {
          this.isLoading = false;
        },
      });
    }

    if (this.beneficiaryType === 'CHILD') {
      this.profileService.findProfiles().subscribe({
        next: (res: HttpResponse<IProfile[]>) => {
          this.isLoading = true;
          this.childrenProfiles = res.body ?? [];

          console.log(this.childrenProfiles);
          this.i = 2;
          this.step = 2;
        },
        error: () => {
          this.isLoading = false;
        },
      });
    }

    if (this.beneficiaryType === 'ESTABLISHMENT') {
      this.establishmentService.findEstablishments().subscribe({
        next: (res: HttpResponse<IProfile[]>) => {
          this.isLoading = true;
          this.establishments = res.body ?? [];
          console.log(this.establishments);
          this.i = 3;
          this.step = 4;
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
        this.Ordonnateur = res;
      },
      error: e => console.error(e),
    });

    this.profileService.findProfile(family.tutor!.id!).subscribe({
      next: res => {
        this.Tuteur = res;
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

  previousState(): void {
    window.history.back();
  }

  previous(): void {
    this.step = 1;

    if (this.step === 1) {
      this.DonationsDetails_step = false;
    }
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

  addItem(item: Item): void {
    this.AddedItems.push(this.createFromFormItemDetails(item));
    console.log(this.AddedItems);
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
  goToItem(): void {
    this.step2 = 1;
  }
  getItemByNature(): void {
    this.getNatureID();

    this.itemService.findItemWithNature(this.id1).subscribe({
      next: (res: HttpResponse<IItem[]>) => {
        this.isLoading = true;
        this.items = res.body ?? [];
        console.log(this.items);
      },
      error: () => {
        this.isLoading = false;
      },
    });
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
      .pipe(
        map((natures: INature[]) => this.natureService.addNatureToCollectionIfMissing(natures, this.DonationsIssued.get('nature')!.value))
      )
      .subscribe((natures: INature[]) => (this.naturesSharedCollection = natures));
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

  protected createFromFormItemDetails(item: Item): IDonationItemDetails {
    return {
      ...new DonationItemDetails(),
      quantity: this.DonationsDetails.get(['quantity'])!.value,
      item,
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
      archivated: this.DonationsIssued.get(['archivated'])!.value,
      // donationsDetailsN: this.createFromFormDonationDetails(),
      //idsBeneficiary: this.beneficiaries.slice(),
    };
  }

  protected createFromFormDonationDetails(): IDonationDetails {
    return {
      ...new DonationDetails(),
      description: this.DonationsDetails.get(['description'])!.value,
      archivated: this.DonationsDetails.get(['archivated'])!.value,
      /*nature: this.DonationsIssued.get(['nature'])!.value,*/
      //donationItemDetails: this.AddedItems.slice(),
    };
  }
}
