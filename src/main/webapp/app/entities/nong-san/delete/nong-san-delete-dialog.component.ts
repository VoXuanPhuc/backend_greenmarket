import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INongSan } from '../nong-san.model';
import { NongSanService } from '../service/nong-san.service';

@Component({
  templateUrl: './nong-san-delete-dialog.component.html',
})
export class NongSanDeleteDialogComponent {
  nongSan?: INongSan;

  constructor(protected nongSanService: NongSanService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nongSanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
