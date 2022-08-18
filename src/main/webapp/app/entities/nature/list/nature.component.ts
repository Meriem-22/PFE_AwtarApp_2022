import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INature } from '../nature.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { NatureService } from '../service/nature.service';
import { NatureDeleteDialogComponent } from '../delete/nature-delete-dialog.component';
import { faCarSide } from '@fortawesome/free-solid-svg-icons';
import { Table } from 'primeng/table';

@Component({
  selector: 'jhi-nature',
  templateUrl: './nature.component.html',
})
export class NatureComponent implements OnInit {
  natures!: INature[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  columns: { field: string; header: string; width: string }[] = [];

  carsData!: INature[];
  selectedColumns: { field: string }[] = [];
  selectedNature!: INature[];
  loading = true;

  constructor(
    protected natureService: NatureService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.natureService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<INature[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.columns[0] = { field: 'Name', header: 'Name', width: '15%' };
    this.columns[1] = { field: 'Destined To', header: 'Destined To', width: '15%' };
    this.columns[2] = { field: 'Necessity Value', header: 'Necessity Value', width: '15%' };
    this.columns[3] = { field: 'Archivated', header: 'Archivated', width: '15%' };

    if (!localStorage.getItem('selectedColumns')) {
      this.setColumnsDefaultValue();
    } else {
      this.selectedColumns = JSON.parse(localStorage.getItem('selectedColumns')!);
    }
  }

  getColumnsField(): any {
    return this.selectedColumns.map(c => c.field).join(',');
  }

  setColumnsDefaultValue(): void {
    this.selectedColumns = this.columns;
    this.save();
  }

  clear(table: Table): void {
    table.clear();
  }

  save(): void {
    localStorage.setItem('selectedColumns', JSON.stringify(this.selectedColumns));
  }

  trackId(_index: number, item: INature): number {
    return item.id!;
  }

  delete(nature: INature): void {
    const modalRef = this.modalService.open(NatureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nature = nature;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = +(page ?? 1);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === ASC;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: INature[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/nature'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.natures = data ?? [];
    this.carsData = data ?? [];
    console.log(this.carsData);
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
