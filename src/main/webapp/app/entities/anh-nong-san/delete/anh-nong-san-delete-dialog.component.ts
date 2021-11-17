import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnhNongSan } from '../anh-nong-san.model';
import { AnhNongSanService } from '../service/anh-nong-san.service';

@Component({
  templateUrl: './anh-nong-san-delete-dialog.component.html',
})
export class AnhNongSanDeleteDialogComponent {
  anhNongSan?: IAnhNongSan;

  constructor(protected anhNongSanService: AnhNongSanService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anhNongSanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
