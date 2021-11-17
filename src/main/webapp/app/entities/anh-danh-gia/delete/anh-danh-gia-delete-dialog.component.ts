import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnhDanhGia } from '../anh-danh-gia.model';
import { AnhDanhGiaService } from '../service/anh-danh-gia.service';

@Component({
  templateUrl: './anh-danh-gia-delete-dialog.component.html',
})
export class AnhDanhGiaDeleteDialogComponent {
  anhDanhGia?: IAnhDanhGia;

  constructor(protected anhDanhGiaService: AnhDanhGiaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anhDanhGiaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
