import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PhuongThucTTComponent } from '../list/phuong-thuc-tt.component';
import { PhuongThucTTDetailComponent } from '../detail/phuong-thuc-tt-detail.component';
import { PhuongThucTTUpdateComponent } from '../update/phuong-thuc-tt-update.component';
import { PhuongThucTTRoutingResolveService } from './phuong-thuc-tt-routing-resolve.service';

const phuongThucTTRoute: Routes = [
  {
    path: '',
    component: PhuongThucTTComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhuongThucTTDetailComponent,
    resolve: {
      phuongThucTT: PhuongThucTTRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhuongThucTTUpdateComponent,
    resolve: {
      phuongThucTT: PhuongThucTTRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhuongThucTTUpdateComponent,
    resolve: {
      phuongThucTT: PhuongThucTTRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(phuongThucTTRoute)],
  exports: [RouterModule],
})
export class PhuongThucTTRoutingModule {}
