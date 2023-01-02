import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITutor } from '../tutor.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { TutorService } from '../service/tutor.service';
import { TutorDeleteDialogComponent } from '../delete/tutor-delete-dialog.component';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-tutor',
  templateUrl: './tutor.component.html',
})
export class TutorComponent implements OnInit {
  tutors?: ITutor[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  account: Account | null = null;
  selected: any[] = [];

  constructor(
    protected tutorService: TutorService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private accountService: AccountService,
    protected modalService: NgbModal,
    protected profileService: ProfileService,
    protected dataUtils: DataUtils
  ) {}

  ngOnInit(): void {
    this.profileService.findAllTutors().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.tutors = res.body ?? [];
        console.log(this.tutors);
      },
      error: e => console.error(e),
    });
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  trackId(_index: number, item: ITutor): number {
    return item.id!;
  }

  delete(tutor: ITutor): void {
    const modalRef = this.modalService.open(TutorDeleteDialogComponent, { size: 'lg', backdrop: false, keyboard: false });
    modalRef.componentInstance.tutor = tutor;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  loadPage(): void {
    console.log(this.tutors);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }
}
