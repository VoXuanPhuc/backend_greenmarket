import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDanhMuc } from '../danh-muc.model';
import { DanhMucService } from '../service/danh-muc.service';
import { DanhMucDeleteDialogComponent } from '../delete/danh-muc-delete-dialog.component';

@Component({
  selector: 'jhi-danh-muc',
  templateUrl: './danh-muc.component.html',
})
export class DanhMucComponent implements OnInit {
  danhMucs?: IDanhMuc[];
  isLoading = false;

  constructor(protected danhMucService: DanhMucService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.danhMucService.query().subscribe(
      (res: HttpResponse<IDanhMuc[]>) => {
        this.isLoading = false;
        this.danhMucs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDanhMuc): number {
    return item.id!;
  }

  delete(danhMuc: IDanhMuc): void {
    const modalRef = this.modalService.open(DanhMucDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.danhMuc = danhMuc;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
