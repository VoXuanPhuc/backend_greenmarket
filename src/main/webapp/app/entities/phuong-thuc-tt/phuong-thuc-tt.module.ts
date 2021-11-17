import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PhuongThucTTComponent } from './list/phuong-thuc-tt.component';
import { PhuongThucTTDetailComponent } from './detail/phuong-thuc-tt-detail.component';
import { PhuongThucTTUpdateComponent } from './update/phuong-thuc-tt-update.component';
import { PhuongThucTTDeleteDialogComponent } from './delete/phuong-thuc-tt-delete-dialog.component';
import { PhuongThucTTRoutingModule } from './route/phuong-thuc-tt-routing.module';

@NgModule({
  imports: [SharedModule, PhuongThucTTRoutingModule],
  declarations: [PhuongThucTTComponent, PhuongThucTTDetailComponent, PhuongThucTTUpdateComponent, PhuongThucTTDeleteDialogComponent],
  entryComponents: [PhuongThucTTDeleteDialogComponent],
})
export class PhuongThucTTModule {}
