import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHinhThucGiaoHang } from '../hinh-thuc-giao-hang.model';
import { HinhThucGiaoHangService } from '../service/hinh-thuc-giao-hang.service';
import { HinhThucGiaoHangDeleteDialogComponent } from '../delete/hinh-thuc-giao-hang-delete-dialog.component';

@Component({
  selector: 'jhi-hinh-thuc-giao-hang',
  templateUrl: './hinh-thuc-giao-hang.component.html',
})
export class HinhThucGiaoHangComponent implements OnInit {
  hinhThucGiaoHangs?: IHinhThucGiaoHang[];
  isLoading = false;

  constructor(protected hinhThucGiaoHangService: HinhThucGiaoHangService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.hinhThucGiaoHangService.query().subscribe(
      (res: HttpResponse<IHinhThucGiaoHang[]>) => {
        this.isLoading = false;
        this.hinhThucGiaoHangs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IHinhThucGiaoHang): number {
    return item.id!;
  }

  delete(hinhThucGiaoHang: IHinhThucGiaoHang): void {
    const modalRef = this.modalService.open(HinhThucGiaoHangDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hinhThucGiaoHang = hinhThucGiaoHang;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
