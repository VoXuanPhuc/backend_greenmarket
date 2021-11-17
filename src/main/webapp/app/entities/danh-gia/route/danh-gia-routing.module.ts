import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DanhGiaComponent } from '../list/danh-gia.component';
import { DanhGiaDetailComponent } from '../detail/danh-gia-detail.component';
import { DanhGiaUpdateComponent } from '../update/danh-gia-update.component';
import { DanhGiaRoutingResolveService } from './danh-gia-routing-resolve.service';

const danhGiaRoute: Routes = [
  {
    path: '',
    component: DanhGiaComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DanhGiaDetailComponent,
    resolve: {
      danhGia: DanhGiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DanhGiaUpdateComponent,
    resolve: {
      danhGia: DanhGiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DanhGiaUpdateComponent,
    resolve: {
      danhGia: DanhGiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(danhGiaRoute)],
  exports: [RouterModule],
})
export class DanhGiaRoutingModule {}
