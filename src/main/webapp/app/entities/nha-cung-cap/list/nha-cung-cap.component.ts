import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INhaCungCap } from '../nha-cung-cap.model';
import { NhaCungCapService } from '../service/nha-cung-cap.service';
import { NhaCungCapDeleteDialogComponent } from '../delete/nha-cung-cap-delete-dialog.component';

@Component({
  selector: 'jhi-nha-cung-cap',
  templateUrl: './nha-cung-cap.component.html',
})
export class NhaCungCapComponent implements OnInit {
  nhaCungCaps?: INhaCungCap[];
  isLoading = false;

  constructor(protected nhaCungCapService: NhaCungCapService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nhaCungCapService.query().subscribe(
      (res: HttpResponse<INhaCungCap[]>) => {
        this.isLoading = false;
        this.nhaCungCaps = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INhaCungCap): number {
    return item.id!;
  }

  delete(nhaCungCap: INhaCungCap): void {
    const modalRef = this.modalService.open(NhaCungCapDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nhaCungCap = nhaCungCap;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
