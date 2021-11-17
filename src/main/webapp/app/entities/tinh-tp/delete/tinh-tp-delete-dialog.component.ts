import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITinhTP } from '../tinh-tp.model';
import { TinhTPService } from '../service/tinh-tp.service';

@Component({
  templateUrl: './tinh-tp-delete-dialog.component.html',
})
export class TinhTPDeleteDialogComponent {
  tinhTP?: ITinhTP;

  constructor(protected tinhTPService: TinhTPService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tinhTPService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
