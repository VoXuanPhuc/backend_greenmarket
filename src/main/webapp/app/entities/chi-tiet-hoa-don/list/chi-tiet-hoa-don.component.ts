import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChiTietHoaDon } from '../chi-tiet-hoa-don.model';
import { ChiTietHoaDonService } from '../service/chi-tiet-hoa-don.service';
import { ChiTietHoaDonDeleteDialogComponent } from '../delete/chi-tiet-hoa-don-delete-dialog.component';

@Component({
  selector: 'jhi-chi-tiet-hoa-don',
  templateUrl: './chi-tiet-hoa-don.component.html',
})
export class ChiTietHoaDonComponent implements OnInit {
  chiTietHoaDons?: IChiTietHoaDon[];
  isLoading = false;

  constructor(protected chiTietHoaDonService: ChiTietHoaDonService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.chiTietHoaDonService.query().subscribe(
      (res: HttpResponse<IChiTietHoaDon[]>) => {
        this.isLoading = false;
        this.chiTietHoaDons = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IChiTietHoaDon): number {
    return item.id!;
  }

  delete(chiTietHoaDon: IChiTietHoaDon): void {
    const modalRef = this.modalService.open(ChiTietHoaDonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chiTietHoaDon = chiTietHoaDon;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
