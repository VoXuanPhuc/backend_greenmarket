import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChiTietHoaDon } from '../chi-tiet-hoa-don.model';
import { ChiTietHoaDonService } from '../service/chi-tiet-hoa-don.service';

@Component({
  templateUrl: './chi-tiet-hoa-don-delete-dialog.component.html',
})
export class ChiTietHoaDonDeleteDialogComponent {
  chiTietHoaDon?: IChiTietHoaDon;

  constructor(protected chiTietHoaDonService: ChiTietHoaDonService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chiTietHoaDonService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
