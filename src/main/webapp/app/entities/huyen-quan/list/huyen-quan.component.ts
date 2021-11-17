import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHuyenQuan } from '../huyen-quan.model';
import { HuyenQuanService } from '../service/huyen-quan.service';
import { HuyenQuanDeleteDialogComponent } from '../delete/huyen-quan-delete-dialog.component';

@Component({
  selector: 'jhi-huyen-quan',
  templateUrl: './huyen-quan.component.html',
})
export class HuyenQuanComponent implements OnInit {
  huyenQuans?: IHuyenQuan[];
  isLoading = false;

  constructor(protected huyenQuanService: HuyenQuanService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.huyenQuanService.query().subscribe(
      (res: HttpResponse<IHuyenQuan[]>) => {
        this.isLoading = false;
        this.huyenQuans = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IHuyenQuan): number {
    return item.id!;
  }

  delete(huyenQuan: IHuyenQuan): void {
    const modalRef = this.modalService.open(HuyenQuanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.huyenQuan = huyenQuan;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
