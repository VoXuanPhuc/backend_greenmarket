import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDanhGia } from '../danh-gia.model';
import { DanhGiaService } from '../service/danh-gia.service';
import { DanhGiaDeleteDialogComponent } from '../delete/danh-gia-delete-dialog.component';

@Component({
  selector: 'jhi-danh-gia',
  templateUrl: './danh-gia.component.html',
})
export class DanhGiaComponent implements OnInit {
  danhGias?: IDanhGia[];
  isLoading = false;

  constructor(protected danhGiaService: DanhGiaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.danhGiaService.query().subscribe(
      (res: HttpResponse<IDanhGia[]>) => {
        this.isLoading = false;
        this.danhGias = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDanhGia): number {
    return item.id!;
  }

  delete(danhGia: IDanhGia): void {
    const modalRef = this.modalService.open(DanhGiaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.danhGia = danhGia;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
