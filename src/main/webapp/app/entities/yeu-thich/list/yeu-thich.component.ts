import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IYeuThich } from '../yeu-thich.model';
import { YeuThichService } from '../service/yeu-thich.service';
import { YeuThichDeleteDialogComponent } from '../delete/yeu-thich-delete-dialog.component';

@Component({
  selector: 'jhi-yeu-thich',
  templateUrl: './yeu-thich.component.html',
})
export class YeuThichComponent implements OnInit {
  yeuThiches?: IYeuThich[];
  isLoading = false;

  constructor(protected yeuThichService: YeuThichService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.yeuThichService.query().subscribe(
      (res: HttpResponse<IYeuThich[]>) => {
        this.isLoading = false;
        this.yeuThiches = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IYeuThich): number {
    return item.id!;
  }

  delete(yeuThich: IYeuThich): void {
    const modalRef = this.modalService.open(YeuThichDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.yeuThich = yeuThich;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
