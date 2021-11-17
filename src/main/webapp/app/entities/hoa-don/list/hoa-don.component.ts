import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHoaDon } from '../hoa-don.model';
import { HoaDonService } from '../service/hoa-don.service';
import { HoaDonDeleteDialogComponent } from '../delete/hoa-don-delete-dialog.component';

@Component({
  selector: 'jhi-hoa-don',
  templateUrl: './hoa-don.component.html',
})
export class HoaDonComponent implements OnInit {
  hoaDons?: IHoaDon[];
  isLoading = false;

  constructor(protected hoaDonService: HoaDonService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.hoaDonService.query().subscribe(
      (res: HttpResponse<IHoaDon[]>) => {
        this.isLoading = false;
        this.hoaDons = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IHoaDon): number {
    return item.id!;
  }

  delete(hoaDon: IHoaDon): void {
    const modalRef = this.modalService.open(HoaDonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hoaDon = hoaDon;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
