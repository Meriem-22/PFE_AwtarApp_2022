import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IDonationsIssued } from 'app/entities/donations-issued/donations-issued.model';
import { DonationsIssuedService } from 'app/entities/donations-issued/service/donations-issued.service';
import { DonationsIssuedDeleteDialogComponent } from 'app/entities/donations-issued/delete/donations-issued-delete-dialog.component';
import { ASC, DESC, SORT } from 'app/config/pagination.constants';
import { combineLatest } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-dashbord',
  templateUrl: './dashbord.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  donations?: IDonationsIssued[];
  isLoading = false;

  constructor(private router: Router, protected activatedRoute: ActivatedRoute, protected donationsIssuedService: DonationsIssuedService) {}

  ngOnInit(): void {
    this.donationsIssuedService.getAllValidatedDonationsIssued().subscribe({
      next: (res: HttpResponse<IDonationsIssued[]>) => {
        this.isLoading = false;
        this.donations = res.body ?? [];
        console.log(this.donations);
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }
}
