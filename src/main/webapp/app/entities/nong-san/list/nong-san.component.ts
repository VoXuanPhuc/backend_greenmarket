import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INongSan } from '../nong-san.model';
import { NongSanService } from '../service/nong-san.service';
import { NongSanDeleteDialogComponent } from '../delete/nong-san-delete-dialog.component';

@Component({
  selector: 'jhi-nong-san',
  templateUrl: './nong-san.component.html',
})
export class NongSanComponent implements OnInit {
  nongSans?: INongSan[];
  isLoading = false;

  constructor(protected nongSanService: NongSanService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nongSanService.query().subscribe(
      (res: HttpResponse<INongSan[]>) => {
        this.isLoading = false;
        this.nongSans = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INongSan): number {
    return item.id!;
  }

  delete(nongSan: INongSan): void {
    const modalRef = this.modalService.open(NongSanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nongSan = nongSan;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
