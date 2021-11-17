import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INhaCungCap } from '../nha-cung-cap.model';
import { NhaCungCapService } from '../service/nha-cung-cap.service';

@Component({
  templateUrl: './nha-cung-cap-delete-dialog.component.html',
})
export class NhaCungCapDeleteDialogComponent {
  nhaCungCap?: INhaCungCap;

  constructor(protected nhaCungCapService: NhaCungCapService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nhaCungCapService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
