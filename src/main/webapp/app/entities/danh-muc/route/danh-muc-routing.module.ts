import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DanhMucComponent } from '../list/danh-muc.component';
import { DanhMucDetailComponent } from '../detail/danh-muc-detail.component';
import { DanhMucUpdateComponent } from '../update/danh-muc-update.component';
import { DanhMucRoutingResolveService } from './danh-muc-routing-resolve.service';

const danhMucRoute: Routes = [
  {
    path: '',
    component: DanhMucComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DanhMucDetailComponent,
    resolve: {
      danhMuc: DanhMucRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DanhMucUpdateComponent,
    resolve: {
      danhMuc: DanhMucRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DanhMucUpdateComponent,
    resolve: {
      danhMuc: DanhMucRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(danhMucRoute)],
  exports: [RouterModule],
})
export class DanhMucRoutingModule {}
