import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDanhMuc } from '../danh-muc.model';
import { DanhMucService } from '../service/danh-muc.service';

@Component({
  templateUrl: './danh-muc-delete-dialog.component.html',
})
export class DanhMucDeleteDialogComponent {
  danhMuc?: IDanhMuc;

  constructor(protected danhMucService: DanhMucService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.danhMucService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
