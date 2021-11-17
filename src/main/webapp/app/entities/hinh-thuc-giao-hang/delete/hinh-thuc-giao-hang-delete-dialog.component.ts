import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHinhThucGiaoHang } from '../hinh-thuc-giao-hang.model';
import { HinhThucGiaoHangService } from '../service/hinh-thuc-giao-hang.service';

@Component({
  templateUrl: './hinh-thuc-giao-hang-delete-dialog.component.html',
})
export class HinhThucGiaoHangDeleteDialogComponent {
  hinhThucGiaoHang?: IHinhThucGiaoHang;

  constructor(protected hinhThucGiaoHangService: HinhThucGiaoHangService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hinhThucGiaoHangService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
