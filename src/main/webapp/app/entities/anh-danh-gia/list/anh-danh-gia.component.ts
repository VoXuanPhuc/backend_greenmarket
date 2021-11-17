import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnhDanhGia } from '../anh-danh-gia.model';
import { AnhDanhGiaService } from '../service/anh-danh-gia.service';
import { AnhDanhGiaDeleteDialogComponent } from '../delete/anh-danh-gia-delete-dialog.component';

@Component({
  selector: 'jhi-anh-danh-gia',
  templateUrl: './anh-danh-gia.component.html',
})
export class AnhDanhGiaComponent implements OnInit {
  anhDanhGias?: IAnhDanhGia[];
  isLoading = false;

  constructor(protected anhDanhGiaService: AnhDanhGiaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.anhDanhGiaService.query().subscribe(
      (res: HttpResponse<IAnhDanhGia[]>) => {
        this.isLoading = false;
        this.anhDanhGias = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAnhDanhGia): number {
    return item.id!;
  }

  delete(anhDanhGia: IAnhDanhGia): void {
    const modalRef = this.modalService.open(AnhDanhGiaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.anhDanhGia = anhDanhGia;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
