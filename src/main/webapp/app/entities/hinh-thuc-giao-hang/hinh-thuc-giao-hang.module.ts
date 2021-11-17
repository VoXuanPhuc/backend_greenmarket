import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HinhThucGiaoHangComponent } from './list/hinh-thuc-giao-hang.component';
import { HinhThucGiaoHangDetailComponent } from './detail/hinh-thuc-giao-hang-detail.component';
import { HinhThucGiaoHangUpdateComponent } from './update/hinh-thuc-giao-hang-update.component';
import { HinhThucGiaoHangDeleteDialogComponent } from './delete/hinh-thuc-giao-hang-delete-dialog.component';
import { HinhThucGiaoHangRoutingModule } from './route/hinh-thuc-giao-hang-routing.module';

@NgModule({
  imports: [SharedModule, HinhThucGiaoHangRoutingModule],
  declarations: [
    HinhThucGiaoHangComponent,
    HinhThucGiaoHangDetailComponent,
    HinhThucGiaoHangUpdateComponent,
    HinhThucGiaoHangDeleteDialogComponent,
  ],
  entryComponents: [HinhThucGiaoHangDeleteDialogComponent],
})
export class HinhThucGiaoHangModule {}
