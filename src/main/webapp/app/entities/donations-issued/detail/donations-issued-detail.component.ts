import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { DataUtils } from 'app/core/util/data-util.service';
import { DonationDetailsService } from 'app/entities/donation-details/service/donation-details.service';
import { IDonationItemDetails } from 'app/entities/donation-item-details/donation-item-details.model';
import { DonationItemDetailsService } from 'app/entities/donation-item-details/service/donation-item-details.service';
import { IItem } from 'app/entities/item/item.model';
import { IDonationsIssued } from '../donations-issued.model';

@Component({
  selector: 'jhi-donations-issued-detail',
  templateUrl: './donations-issued-detail.component.html',
})
export class DonationsIssuedDetailComponent implements OnInit {
  donationsIssued: IDonationsIssued | null = null;
  donationItemsOfFamilies?: any[] = [];
  donationItemsOfChildren?: any[] = [];
  donationItemsOfEstablishments?: any[] = [];
  donationsOfFamilies?: any[] = [];
  donationsOfEstablishments?: any[] = [];
  donationsOfChildren?: any[] = [];
  itemsByDetailsOfFamilies: any[] = [];
  itemsByDetailsOfEstablishments: any[] = [];
  itemsByDetailsOfChildren: any[] = [];
  finalDonationItemsByDetailsFamilies: { details: any; items: any[]; beneficiary: any[] }[] = [];
  finalDonationItemsByDetailsEstablishments: { details: any; items: any[]; beneficiary: any[] }[] = [];
  finalDonationItemsByDetailsChildren: { details: any; items: any[]; beneficiary: any[] }[] = [];
  beneficiairy: { firstName: string; lastName: string; urlPhoto: string; urlPhotoContentType: string }[] = [];
  items: {
    urlPhoto: string;
    urlPhotoContentType: string;
    name: string;
    price: number;
    quantity: number;
    availableStockQuantity: number;
  }[] = [];
  donaionDetailsFamily: { details: any; ok: boolean }[] = [];
  donaionDetailsChild: { details: any; ok: boolean }[] = [];
  donaionDetailsEstablishment: { details: any; ok: boolean }[] = [];
  beneficiairiesF: { id: number; name: string }[] = [];
  beneficiairiesE: { id: number; name: string }[] = [];
  beneficiairiesC: { id: number; firstName: string; lastName: string; urlPhoto: string; urlPhotoContentType: string }[] = [];
  finalDonationFamilyDetails: { details: any; beneficiary: any[] }[] = [];
  finalDonationEstablishmentDetails: { details: any; beneficiary: any[] }[] = [];
  finalDonationChildDetails: { details: any; beneficiary: any[] }[] = [];
  selectedDetails?: any[];
  selectedItems?: any[];
  display = false;
  beneficiariesToShow?: any[];
  account: Account | null = null;

  xf = 0;
  yf = 0;
  if = 0;

  xc = 0;
  yc = 0;
  ic = 0;

  xe = 0;
  ye = 0;
  ie = 0;

  step = 1;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected donationItemDetailsService: DonationItemDetailsService,
    protected dataUtils: DataUtils,
    protected donationDetailsService: DonationDetailsService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ donationsIssued }) => {
      this.donationsIssued = donationsIssued;

      this.donationItemDetailsService.getAllDonationItemDetailsOfFamilies(donationsIssued.id).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.donationItemsOfFamilies = res.body ?? [];
          console.log(this.donationItemsOfFamilies);
        },
        error: e => console.error(e),
      });

      this.donationItemDetailsService.getAllDonationItemDetailsOfChildren(donationsIssued.id).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.donationItemsOfChildren = res.body ?? [];
          console.log(this.donationItemsOfChildren);
        },
        error: e => console.error(e),
      });

      this.donationItemDetailsService.getAllDonationItemDetailsOfEstablishments(donationsIssued.id).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.donationItemsOfEstablishments = res.body ?? [];
          console.log(this.donationItemsOfEstablishments);
        },
        error: e => console.error(e),
      });

      this.donationDetailsService.getFamiliesDonationDetails(donationsIssued.id).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.donationsOfFamilies = res.body ?? [];

          for (let i = 0; i < this.donationsOfFamilies.length; i++) {
            this.donaionDetailsFamily[i] = { details: this.donationsOfFamilies[i], ok: false };
          }
          console.log(this.donaionDetailsFamily);
        },
        error: e => console.error(e),
      });
      this.donationDetailsService.getEstablishmentsDonationDetails(donationsIssued.id).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.donationsOfEstablishments = res.body ?? [];
          for (let i = 0; i < this.donationsOfEstablishments.length; i++) {
            this.donaionDetailsEstablishment[i] = { details: this.donationsOfEstablishments[i], ok: false };
          }
          console.log(this.donaionDetailsEstablishment);
        },
        error: e => console.error(e),
      });
      this.donationDetailsService.getChildrenDonationDetails(donationsIssued.id).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.donationsOfChildren = res.body ?? [];
          for (let i = 0; i < this.donationsOfChildren.length; i++) {
            this.donaionDetailsChild[i] = { details: this.donationsOfChildren[i], ok: false };
          }
          console.log(this.donaionDetailsChild);
        },
        error: e => console.error(e),
      });
    });
  }

  next(): void {
    this.finalTableDetails();
    this.step++;
  }

  previousStep(): void {
    this.step--;
  }

  showDialog(tab: any[]): void {
    this.beneficiariesToShow = tab;
    this.display = true;
  }

  finalTableDetails(): void {
    /*Families Details */
    for (let i = 0; i < this.donaionDetailsFamily.length; i++) {
      if (!this.donaionDetailsFamily[i].ok) {
        this.beneficiairiesF[this.yf] = { id: this.donaionDetailsFamily[i].details.bId, name: this.donaionDetailsFamily[i].details.name };
        this.yf++;
        this.finalDonationFamilyDetails[this.xf] = { details: this.donaionDetailsFamily[i].details, beneficiary: this.beneficiairiesF };
      }
      this.donaionDetailsFamily[i].ok = true;
      for (let j = 0; j < this.donaionDetailsFamily.length; j++) {
        if (
          this.donaionDetailsFamily[j].details.description === this.donaionDetailsFamily[i].details.description &&
          this.donaionDetailsFamily[j].ok === false
        ) {
          this.finalDonationFamilyDetails[this.xf] = { details: this.donaionDetailsFamily[i].details, beneficiary: this.beneficiairiesF };
          this.beneficiairiesF[this.yf] = this.beneficiairiesF[this.yf] = {
            id: this.donaionDetailsFamily[j].details.bId,
            name: this.donaionDetailsFamily[j].details.name,
          };
          this.yf++;
          this.donaionDetailsFamily[j].ok = true;
        }
      }

      this.xf++;
      this.beneficiairiesF = [];
      this.yf = 0;
    }

    this.finalDonationFamilyDetails = this.finalDonationFamilyDetails.filter(element => {
      if (Object.keys(element).length !== 0) {
        return true;
      }

      return false;
    });

    console.log(this.finalDonationFamilyDetails);
    /*Families Details */

    /*Establishments Details */
    for (let i = 0; i < this.donaionDetailsEstablishment.length; i++) {
      if (!this.donaionDetailsEstablishment[i].ok) {
        this.beneficiairiesE[this.ye] = {
          id: this.donaionDetailsEstablishment[i].details.bId,
          name: this.donaionDetailsEstablishment[i].details.name,
        };
        this.ye++;
        this.finalDonationEstablishmentDetails[this.xe] = {
          details: this.donaionDetailsEstablishment[i].details,
          beneficiary: this.beneficiairiesE,
        };
      }
      this.donaionDetailsEstablishment[i].ok = true;
      for (let j = 0; j < this.donaionDetailsEstablishment.length; j++) {
        if (
          this.donaionDetailsEstablishment[j].details.description === this.donaionDetailsEstablishment[i].details.description &&
          this.donaionDetailsEstablishment[j].ok === false
        ) {
          this.finalDonationEstablishmentDetails[this.xe] = {
            details: this.donaionDetailsEstablishment[i].details,
            beneficiary: this.beneficiairiesE,
          };
          this.beneficiairiesF[this.ye] = this.beneficiairiesE[this.ye] = {
            id: this.donaionDetailsEstablishment[j].details.bId,
            name: this.donaionDetailsEstablishment[j].details.name,
          };
          this.ye++;
          this.donaionDetailsEstablishment[j].ok = true;
        }
      }

      this.xe++;
      this.beneficiairiesE = [];
      this.ye = 0;
    }

    this.finalDonationEstablishmentDetails = this.finalDonationEstablishmentDetails.filter(element => {
      if (Object.keys(element).length !== 0) {
        return true;
      }

      return false;
    });

    console.log(this.finalDonationEstablishmentDetails);
    /*Establishments Details */

    /*Children Details */
    for (let i = 0; i < this.donaionDetailsChild.length; i++) {
      if (!this.donaionDetailsChild[i].ok) {
        this.beneficiairiesC[this.yc] = {
          id: this.donaionDetailsChild[i].details.bId,
          firstName: this.donaionDetailsChild[i].details.firstName,
          lastName: this.donaionDetailsChild[i].details.lastName,
          urlPhoto: this.donaionDetailsChild[i].details.urlPhoto,
          urlPhotoContentType: this.donaionDetailsChild[i].details.urlPhotoContentType,
        };
        this.yc++;
        this.finalDonationChildDetails[this.xc] = { details: this.donaionDetailsChild[i].details, beneficiary: this.beneficiairiesC };
      }
      this.donaionDetailsChild[i].ok = true;
      for (let j = 0; j < this.donaionDetailsChild.length; j++) {
        if (
          this.donaionDetailsChild[j].details.description === this.donaionDetailsChild[i].details.description &&
          this.donaionDetailsChild[j].ok === false
        ) {
          this.finalDonationChildDetails[this.xc] = { details: this.donaionDetailsChild[i].details, beneficiary: this.beneficiairiesC };
          this.beneficiairiesC[this.yc] = this.beneficiairiesC[this.yc] = {
            id: this.donaionDetailsChild[j].details.bId,
            firstName: this.donaionDetailsChild[j].details.firstName,
            lastName: this.donaionDetailsChild[j].details.lastName,
            urlPhoto: this.donaionDetailsChild[j].details.urlPhoto,
            urlPhotoContentType: this.donaionDetailsChild[j].details.urlPhotoContentType,
          };
          this.yc++;
          this.donaionDetailsChild[j].ok = true;
        }
      }

      this.xc++;
      this.beneficiairiesC = [];
      this.yc = 0;
    }

    this.finalDonationChildDetails = this.finalDonationChildDetails.filter(element => {
      if (Object.keys(element).length !== 0) {
        return true;
      }

      return false;
    });

    console.log(this.finalDonationChildDetails);
    /*Children Details */

    this.getItems();
  }

  getItems(): void {
    /* Family items donation*/

    for (let x = 0; x < this.finalDonationFamilyDetails.length; x++) {
      for (let f = 0; f < this.donationItemsOfFamilies!.length; f++) {
        if (
          this.donationItemsOfFamilies![f].description === this.finalDonationFamilyDetails[x].details.description &&
          this.donationItemsOfFamilies![f].bId === this.finalDonationFamilyDetails[x].beneficiary[0].id
        ) {
          this.itemsByDetailsOfFamilies[this.if] = this.donationItemsOfFamilies![f];
          this.if++;
        }
      }
      this.finalDonationItemsByDetailsFamilies[x] = {
        details: this.finalDonationFamilyDetails[x].details,
        items: this.itemsByDetailsOfFamilies,
        beneficiary: this.finalDonationFamilyDetails[x].beneficiary,
      };
      this.itemsByDetailsOfFamilies = [];
      this.if = 0;
    }
    console.log(this.finalDonationItemsByDetailsFamilies);

    /* Family items donation*/

    /* Establishments items donation */

    for (let x = 0; x < this.finalDonationEstablishmentDetails.length; x++) {
      for (let f = 0; f < this.donationItemsOfEstablishments!.length; f++) {
        if (
          this.donationItemsOfEstablishments![f].description === this.finalDonationEstablishmentDetails[x].details.description &&
          this.donationItemsOfEstablishments![f].bId === this.finalDonationEstablishmentDetails[x].beneficiary[0].id
        ) {
          this.itemsByDetailsOfEstablishments[this.ie] = this.donationItemsOfEstablishments![f];
          this.ie++;
        }
      }
      this.finalDonationItemsByDetailsEstablishments[x] = {
        details: this.finalDonationEstablishmentDetails[x].details,
        items: this.itemsByDetailsOfEstablishments,
        beneficiary: this.finalDonationEstablishmentDetails[x].beneficiary,
      };
      this.itemsByDetailsOfEstablishments = [];
      this.ie = 0;
    }
    console.log(this.finalDonationItemsByDetailsEstablishments);
    /* Establishments items donation */

    /* Children items donation */

    for (let x = 0; x < this.finalDonationChildDetails.length; x++) {
      for (let f = 0; f < this.donationItemsOfChildren!.length; f++) {
        if (
          this.donationItemsOfChildren![f].description === this.finalDonationChildDetails[x].details.description &&
          this.donationItemsOfChildren![f].bId === this.finalDonationChildDetails[x].beneficiary[0].id
        ) {
          this.itemsByDetailsOfChildren[this.ic] = this.donationItemsOfChildren![f];
          this.ic++;
        }
      }
      this.finalDonationItemsByDetailsChildren[x] = {
        details: this.finalDonationChildDetails[x].details,
        items: this.itemsByDetailsOfChildren,
        beneficiary: this.finalDonationChildDetails[x].beneficiary,
      };
      this.itemsByDetailsOfChildren = [];
      this.ic = 0;
    }
    console.log(this.finalDonationItemsByDetailsChildren);
    /* Children items donation */
  }

  previousState(): void {
    window.history.back();
  }

  trackId(_index: number, item: IItem): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }
}
