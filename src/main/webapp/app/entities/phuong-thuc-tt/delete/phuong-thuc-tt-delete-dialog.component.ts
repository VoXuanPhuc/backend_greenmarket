import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhuongThucTT } from '../phuong-thuc-tt.model';
import { PhuongThucTTService } from '../service/phuong-thuc-tt.service';

@Component({
  templateUrl: './phuong-thuc-tt-delete-dialog.component.html',
})
export class PhuongThucTTDeleteDialogComponent {
  phuongThucTT?: IPhuongThucTT;

  constructor(protected phuongThucTTService: PhuongThucTTService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phuongThucTTService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
