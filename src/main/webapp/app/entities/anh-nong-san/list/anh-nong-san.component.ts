import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnhNongSan } from '../anh-nong-san.model';
import { AnhNongSanService } from '../service/anh-nong-san.service';
import { AnhNongSanDeleteDialogComponent } from '../delete/anh-nong-san-delete-dialog.component';

@Component({
  selector: 'jhi-anh-nong-san',
  templateUrl: './anh-nong-san.component.html',
})
export class AnhNongSanComponent implements OnInit {
  anhNongSans?: IAnhNongSan[];
  isLoading = false;

  constructor(protected anhNongSanService: AnhNongSanService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.anhNongSanService.query().subscribe(
      (res: HttpResponse<IAnhNongSan[]>) => {
        this.isLoading = false;
        this.anhNongSans = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAnhNongSan): number {
    return item.id!;
  }

  delete(anhNongSan: IAnhNongSan): void {
    const modalRef = this.modalService.open(AnhNongSanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.anhNongSan = anhNongSan;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
