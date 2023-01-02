import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { DonationsReceivedService } from 'app/entities/donations-received/service/donations-received.service';
import { HttpResponse } from '@angular/common/http';
import { DonationsIssuedService } from 'app/entities/donations-issued/service/donations-issued.service';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
import { IDonationsIssued } from 'app/entities/donations-issued/donations-issued.model';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { FamilyService } from 'app/entities/family/service/family.service';
import { EstablishmentService } from 'app/entities/establishment/service/establishment.service';
import { ChildService } from 'app/entities/child/service/child.service';
import { CityService } from 'app/entities/city/service/city.service';
import { City } from 'app/entities/city/city.model';
import { IChild } from 'app/entities/child/child.model';

@Component({
  selector: 'jhi-dashboard-template',
  templateUrl: './dashboard-template.component.html',
  styleUrls: ['./dashboard-template.component.scss'],
})
export class DashboardTemplateComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  chartOptions: any;
  date!: Date;
  data: any;
  images!: any[];
  currentYear!: number;
  upcomingValidatedDonations?: IDonationsIssued[] = [];
  upcomingScheduledDonations?: IDonationsIssued[] = [];
  allBeneficiaries?: IBeneficiary[] = [];
  CurrentYearReceivedDonations?: any[] = [];
  e?: number;
  selectedCity = 0;
  numberOfEstablishments?: number;
  numberOfFamilies?: number;
  numberOfChildren?: number;
  citys?: City[] = [];
  display = false;
  months: string[] = [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
    'July',
    'August',
    'September',
    'October',
    'November',
    'December',
  ];

  issuedDonationsByMonth: any[] = [];
  canceledDonationsByMonth: any[] = [];
  receivedDonationsdByMonth: any[] = [];
  nb = 0;
  responsiveOptions: any[] = [
    {
      breakpoint: '1024px',
      numVisible: 5,
    },
    {
      breakpoint: '768px',
      numVisible: 3,
    },
    {
      breakpoint: '560px',
      numVisible: 1,
    },
  ];

  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    private donationsReceivedService: DonationsReceivedService,
    private donationsIssuedService: DonationsIssuedService,
    private beneficiaryService: BeneficiaryService,
    private familyService: FamilyService,
    private establishmentService: EstablishmentService,
    private childService: ChildService,
    private cityService: CityService
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    this.entitiesNavbarItems = EntityNavbarItems;
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.donationsIssuedService.DonationsIssuedOfCurrentYearByMonth().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.issuedDonationsByMonth = res.body!;
      },
      error: e => console.error(e),
    });

    this.donationsIssuedService.CanceledDonationsOfCurrentYearByMonth().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.canceledDonationsByMonth = res.body!;
      },
      error: e => console.error(e),
    });

    this.donationsReceivedService.ReceivedDonationsOfCurrentYearByMonth().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.receivedDonationsdByMonth = res.body!;
      },
      error: e => console.error(e),
    });

    this.donationsReceivedService.findRecentDonationsReceived().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.images = res.body!;
      },
      error: e => console.error(e),
    });

    this.currentYear = new Date().getFullYear();

    this.donationsReceivedService.getCurrentyeardonations().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.CurrentYearReceivedDonations = res.body!;
      },
      error: e => console.error(e),
    });

    this.donationsIssuedService.Upcomingscheduleddonations().subscribe({
      next: (res: HttpResponse<IDonationsIssued[]>) => {
        this.upcomingScheduledDonations = res.body!;
      },
      error: e => console.error(e),
    });

    this.donationsIssuedService.UpcomingDonationsValidated().subscribe({
      next: (res: HttpResponse<IDonationsIssued[]>) => {
        this.upcomingValidatedDonations = res.body!;
      },
      error: e => console.error(e),
    });

    this.beneficiaryService.getAllBeneficiaires().subscribe({
      next: (res: HttpResponse<IBeneficiary[]>) => {
        this.allBeneficiaries = res.body!;
      },
      error: e => console.error(e),
    });

    this.cityService.findall().subscribe({
      next: (res: HttpResponse<City[]>) => {
        this.citys = res.body!;
      },
      error: e => console.error(e),
    });
  }

  showStatics(): void {
    this.data = {
      labels: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
      datasets: [
        {
          type: 'line',
          label: 'Dons Emis',
          borderColor: '#42A5F5',
          borderWidth: 2,
          fill: false,
          data: [
            this.getDonationIValidated(this.months[0]),
            this.getDonationIValidated(this.months[1]),
            this.getDonationIValidated(this.months[2]),
            this.getDonationIValidated(this.months[3]),
            this.getDonationIValidated(this.months[4]),
            this.getDonationIValidated(this.months[5]),
            this.getDonationIValidated(this.months[6]),
            this.getDonationIValidated(this.months[7]),
            this.getDonationIValidated(this.months[8]),
            this.getDonationIValidated(this.months[9]),
            this.getDonationIValidated(this.months[10]),
            this.getDonationIValidated(this.months[11]),
          ],
        },
        {
          type: 'bar',
          label: 'Dons Annulé / non validé',
          backgroundColor: '#fc00ff',
          data: [
            this.getDonationICanceled(this.months[0]),
            this.getDonationICanceled(this.months[1]),
            this.getDonationICanceled(this.months[2]),
            this.getDonationICanceled(this.months[3]),
            this.getDonationICanceled(this.months[4]),
            this.getDonationICanceled(this.months[5]),
            this.getDonationICanceled(this.months[6]),
            this.getDonationICanceled(this.months[7]),
            this.getDonationICanceled(this.months[8]),
            this.getDonationICanceled(this.months[9]),
            this.getDonationICanceled(this.months[10]),
            this.getDonationICanceled(this.months[11]),
          ],
          borderColor: 'white',
          borderWidth: 2,
        },
        {
          type: 'bar',
          label: 'Dons Recus',
          backgroundColor: '#FFA726',
          data: [
            this.getDonationReceived(this.months[0]),
            this.getDonationReceived(this.months[1]),
            this.getDonationReceived(this.months[2]),
            this.getDonationReceived(this.months[3]),
            this.getDonationReceived(this.months[4]),
            this.getDonationReceived(this.months[5]),
            this.getDonationReceived(this.months[6]),
            this.getDonationReceived(this.months[7]),
            this.getDonationReceived(this.months[8]),
            this.getDonationReceived(this.months[9]),
            this.getDonationReceived(this.months[10]),
            this.getDonationReceived(this.months[11]),
          ],
        },
      ],
    };

    this.chartOptions = {
      plugins: {
        legend: {
          labels: {
            color: '#495057',
          },
        },
      },
      scales: {
        x: {
          ticks: {
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
        y: {
          ticks: {
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
      },
    };
  }

  showMaximizableDialog(): void {
    this.display = true;
    this.showStatics();
  }

  getDonationIValidated(keyword: string): any {
    console.log(this.issuedDonationsByMonth);
    for (let i = 0; i < this.issuedDonationsByMonth.length; i++) {
      if (this.issuedDonationsByMonth[i].month?.includes(keyword) && this.issuedDonationsByMonth[i].number!) {
        console.log(this.issuedDonationsByMonth[i].number);
        return this.issuedDonationsByMonth[i].number;
      }
    }

    return 0;
  }

  getDonationICanceled(keyword: string): any {
    console.log(this.canceledDonationsByMonth);
    for (let i = 0; i < this.canceledDonationsByMonth.length; i++) {
      if (this.canceledDonationsByMonth[i].month?.includes(keyword) && this.canceledDonationsByMonth[i].number!) {
        console.log(this.canceledDonationsByMonth[i].number);
        return this.canceledDonationsByMonth[i].number;
      }
    }

    return 0;
  }

  getDonationReceived(keyword: string): any {
    console.log(this.receivedDonationsdByMonth);
    for (let i = 0; i < this.receivedDonationsdByMonth.length; i++) {
      if (this.receivedDonationsdByMonth[i].month?.includes(keyword) && this.receivedDonationsdByMonth[i].number!) {
        console.log(this.receivedDonationsdByMonth[i].number);
        return this.receivedDonationsdByMonth[i].number;
      }
    }

    return 0;
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getBeneficiaries(city: string): void {
    this.selectedCity = 1;
    let id = -1;
    for (let i = 0; i < this.citys!.length; i++) {
      if (this.citys![i].name! === city) {
        id = this.citys![i].id!;
      }
    }

    if (id > 0) {
      this.establishmentService.findNumberOfEstablishmentsByCity(id).subscribe({
        next: (res: HttpResponse<any>) => {
          this.numberOfEstablishments = res.body.nbInCity;
        },
        error: e => console.error(e),
      });

      this.familyService.findNumberOfFamiliesByCity(id).subscribe({
        next: (res: HttpResponse<any>) => {
          this.numberOfFamilies = res.body.nbInCity;
        },
        error: e => console.error(e),
      });

      this.childService.findNumberOfChildrenByCity(id).subscribe({
        next: (res: HttpResponse<any>) => {
          this.numberOfChildren = res.body?.nbInCity;
        },
        error: e => console.error(e),
      });
    }
  }

  initBeneficiaries(): void {
    this.selectedCity = 0;
  }
}
