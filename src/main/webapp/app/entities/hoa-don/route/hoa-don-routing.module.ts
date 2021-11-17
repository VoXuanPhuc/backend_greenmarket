import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HoaDonComponent } from '../list/hoa-don.component';
import { HoaDonDetailComponent } from '../detail/hoa-don-detail.component';
import { HoaDonUpdateComponent } from '../update/hoa-don-update.component';
import { HoaDonRoutingResolveService } from './hoa-don-routing-resolve.service';

const hoaDonRoute: Routes = [
  {
    path: '',
    component: HoaDonComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HoaDonDetailComponent,
    resolve: {
      hoaDon: HoaDonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HoaDonUpdateComponent,
    resolve: {
      hoaDon: HoaDonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HoaDonUpdateComponent,
    resolve: {
      hoaDon: HoaDonRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hoaDonRoute)],
  exports: [RouterModule],
})
export class HoaDonRoutingModule {}
