import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChiTietHoaDonComponent } from './list/chi-tiet-hoa-don.component';
import { ChiTietHoaDonDetailComponent } from './detail/chi-tiet-hoa-don-detail.component';
import { ChiTietHoaDonUpdateComponent } from './update/chi-tiet-hoa-don-update.component';
import { ChiTietHoaDonDeleteDialogComponent } from './delete/chi-tiet-hoa-don-delete-dialog.component';
import { ChiTietHoaDonRoutingModule } from './route/chi-tiet-hoa-don-routing.module';

@NgModule({
  imports: [SharedModule, ChiTietHoaDonRoutingModule],
  declarations: [ChiTietHoaDonComponent, ChiTietHoaDonDetailComponent, ChiTietHoaDonUpdateComponent, ChiTietHoaDonDeleteDialogComponent],
  entryComponents: [ChiTietHoaDonDeleteDialogComponent],
})
export class ChiTietHoaDonModule {}
