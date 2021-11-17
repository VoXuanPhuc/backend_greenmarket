import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChiTietHoaDonComponent } from '../list/chi-tiet-hoa-don.component';
import { ChiTietHoaDonDetailComponent } from '../detail/chi-tiet-hoa-don-detail.component';
import { ChiTietHoaDonUpdateComponent } from '../update/chi-tiet-hoa-don-update.component';
import { ChiTietHoaDonRoutingResolveService } from './chi-tiet-hoa-don-routing-resolve.service';

const chiTietHoaDonRoute: Routes = [
  {
    path: '',
    component: ChiTietHoaDonComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChiTietHoaDonDetailComponent,
    resolve: {
      chiTietHoaDon: ChiTietHoaDonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChiTietHoaDonUpdateComponent,
    resolve: {
      chiTietHoaDon: ChiTietHoaDonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChiTietHoaDonUpdateComponent,
    resolve: {
      chiTietHoaDon: ChiTietHoaDonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(chiTietHoaDonRoute)],
  exports: [RouterModule],
})
export class ChiTietHoaDonRoutingModule {}
