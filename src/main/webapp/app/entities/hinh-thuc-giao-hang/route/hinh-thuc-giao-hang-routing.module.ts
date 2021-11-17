import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HinhThucGiaoHangComponent } from '../list/hinh-thuc-giao-hang.component';
import { HinhThucGiaoHangDetailComponent } from '../detail/hinh-thuc-giao-hang-detail.component';
import { HinhThucGiaoHangUpdateComponent } from '../update/hinh-thuc-giao-hang-update.component';
import { HinhThucGiaoHangRoutingResolveService } from './hinh-thuc-giao-hang-routing-resolve.service';

const hinhThucGiaoHangRoute: Routes = [
  {
    path: '',
    component: HinhThucGiaoHangComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HinhThucGiaoHangDetailComponent,
    resolve: {
      hinhThucGiaoHang: HinhThucGiaoHangRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HinhThucGiaoHangUpdateComponent,
    resolve: {
      hinhThucGiaoHang: HinhThucGiaoHangRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HinhThucGiaoHangUpdateComponent,
    resolve: {
      hinhThucGiaoHang: HinhThucGiaoHangRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hinhThucGiaoHangRoute)],
  exports: [RouterModule],
})
export class HinhThucGiaoHangRoutingModule {}
