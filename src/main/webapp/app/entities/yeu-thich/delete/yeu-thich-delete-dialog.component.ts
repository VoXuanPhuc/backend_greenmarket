import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IYeuThich } from '../yeu-thich.model';
import { YeuThichService } from '../service/yeu-thich.service';

@Component({
  templateUrl: './yeu-thich-delete-dialog.component.html',
})
export class YeuThichDeleteDialogComponent {
  yeuThich?: IYeuThich;

  constructor(protected yeuThichService: YeuThichService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.yeuThichService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
