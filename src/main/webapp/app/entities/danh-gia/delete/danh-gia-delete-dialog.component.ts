import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDanhGia } from '../danh-gia.model';
import { DanhGiaService } from '../service/danh-gia.service';

@Component({
  templateUrl: './danh-gia-delete-dialog.component.html',
})
export class DanhGiaDeleteDialogComponent {
  danhGia?: IDanhGia;

  constructor(protected danhGiaService: DanhGiaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.danhGiaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
