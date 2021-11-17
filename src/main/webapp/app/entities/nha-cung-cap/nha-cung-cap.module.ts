import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NhaCungCapComponent } from './list/nha-cung-cap.component';
import { NhaCungCapDetailComponent } from './detail/nha-cung-cap-detail.component';
import { NhaCungCapUpdateComponent } from './update/nha-cung-cap-update.component';
import { NhaCungCapDeleteDialogComponent } from './delete/nha-cung-cap-delete-dialog.component';
import { NhaCungCapRoutingModule } from './route/nha-cung-cap-routing.module';

@NgModule({
  imports: [SharedModule, NhaCungCapRoutingModule],
  declarations: [NhaCungCapComponent, NhaCungCapDetailComponent, NhaCungCapUpdateComponent, NhaCungCapDeleteDialogComponent],
  entryComponents: [NhaCungCapDeleteDialogComponent],
})
export class NhaCungCapModule {}
