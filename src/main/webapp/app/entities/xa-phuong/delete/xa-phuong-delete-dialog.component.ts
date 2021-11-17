import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IXaPhuong } from '../xa-phuong.model';
import { XaPhuongService } from '../service/xa-phuong.service';

@Component({
  templateUrl: './xa-phuong-delete-dialog.component.html',
})
export class XaPhuongDeleteDialogComponent {
  xaPhuong?: IXaPhuong;

  constructor(protected xaPhuongService: XaPhuongService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.xaPhuongService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
