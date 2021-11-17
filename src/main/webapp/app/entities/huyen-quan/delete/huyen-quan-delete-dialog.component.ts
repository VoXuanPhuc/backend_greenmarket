import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHuyenQuan } from '../huyen-quan.model';
import { HuyenQuanService } from '../service/huyen-quan.service';

@Component({
  templateUrl: './huyen-quan-delete-dialog.component.html',
})
export class HuyenQuanDeleteDialogComponent {
  huyenQuan?: IHuyenQuan;

  constructor(protected huyenQuanService: HuyenQuanService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.huyenQuanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
