import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhuongThucTT } from '../phuong-thuc-tt.model';
import { PhuongThucTTService } from '../service/phuong-thuc-tt.service';
import { PhuongThucTTDeleteDialogComponent } from '../delete/phuong-thuc-tt-delete-dialog.component';

@Component({
  selector: 'jhi-phuong-thuc-tt',
  templateUrl: './phuong-thuc-tt.component.html',
})
export class PhuongThucTTComponent implements OnInit {
  phuongThucTTS?: IPhuongThucTT[];
  isLoading = false;

  constructor(protected phuongThucTTService: PhuongThucTTService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.phuongThucTTService.query().subscribe(
      (res: HttpResponse<IPhuongThucTT[]>) => {
        this.isLoading = false;
        this.phuongThucTTS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPhuongThucTT): number {
    return item.id!;
  }

  delete(phuongThucTT: IPhuongThucTT): void {
    const modalRef = this.modalService.open(PhuongThucTTDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.phuongThucTT = phuongThucTT;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
