import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITinhTP } from '../tinh-tp.model';
import { TinhTPService } from '../service/tinh-tp.service';
import { TinhTPDeleteDialogComponent } from '../delete/tinh-tp-delete-dialog.component';

@Component({
  selector: 'jhi-tinh-tp',
  templateUrl: './tinh-tp.component.html',
})
export class TinhTPComponent implements OnInit {
  tinhTPS?: ITinhTP[];
  isLoading = false;

  constructor(protected tinhTPService: TinhTPService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tinhTPService.query().subscribe(
      (res: HttpResponse<ITinhTP[]>) => {
        this.isLoading = false;
        this.tinhTPS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITinhTP): number {
    return item.id!;
  }

  delete(tinhTP: ITinhTP): void {
    const modalRef = this.modalService.open(TinhTPDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tinhTP = tinhTP;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
