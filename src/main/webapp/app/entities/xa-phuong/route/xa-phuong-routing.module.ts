import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { XaPhuongComponent } from '../list/xa-phuong.component';
import { XaPhuongDetailComponent } from '../detail/xa-phuong-detail.component';
import { XaPhuongUpdateComponent } from '../update/xa-phuong-update.component';
import { XaPhuongRoutingResolveService } from './xa-phuong-routing-resolve.service';

const xaPhuongRoute: Routes = [
  {
    path: '',
    component: XaPhuongComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: XaPhuongDetailComponent,
    resolve: {
      xaPhuong: XaPhuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: XaPhuongUpdateComponent,
    resolve: {
      xaPhuong: XaPhuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: XaPhuongUpdateComponent,
    resolve: {
      xaPhuong: XaPhuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(xaPhuongRoute)],
  exports: [RouterModule],
})
export class XaPhuongRoutingModule {}
