import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IXaPhuong } from '../xa-phuong.model';
import { XaPhuongService } from '../service/xa-phuong.service';
import { XaPhuongDeleteDialogComponent } from '../delete/xa-phuong-delete-dialog.component';

@Component({
  selector: 'jhi-xa-phuong',
  templateUrl: './xa-phuong.component.html',
})
export class XaPhuongComponent implements OnInit {
  xaPhuongs?: IXaPhuong[];
  isLoading = false;

  constructor(protected xaPhuongService: XaPhuongService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.xaPhuongService.query().subscribe(
      (res: HttpResponse<IXaPhuong[]>) => {
        this.isLoading = false;
        this.xaPhuongs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IXaPhuong): number {
    return item.id!;
  }

  delete(xaPhuong: IXaPhuong): void {
    const modalRef = this.modalService.open(XaPhuongDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.xaPhuong = xaPhuong;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
