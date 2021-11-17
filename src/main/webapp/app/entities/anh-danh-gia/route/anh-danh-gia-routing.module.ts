import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnhDanhGiaComponent } from '../list/anh-danh-gia.component';
import { AnhDanhGiaDetailComponent } from '../detail/anh-danh-gia-detail.component';
import { AnhDanhGiaUpdateComponent } from '../update/anh-danh-gia-update.component';
import { AnhDanhGiaRoutingResolveService } from './anh-danh-gia-routing-resolve.service';

const anhDanhGiaRoute: Routes = [
  {
    path: '',
    component: AnhDanhGiaComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnhDanhGiaDetailComponent,
    resolve: {
      anhDanhGia: AnhDanhGiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnhDanhGiaUpdateComponent,
    resolve: {
      anhDanhGia: AnhDanhGiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnhDanhGiaUpdateComponent,
    resolve: {
      anhDanhGia: AnhDanhGiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(anhDanhGiaRoute)],
  exports: [RouterModule],
})
export class AnhDanhGiaRoutingModule {}
