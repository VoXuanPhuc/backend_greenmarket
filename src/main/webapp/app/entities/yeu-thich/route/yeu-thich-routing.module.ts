import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { YeuThichComponent } from '../list/yeu-thich.component';
import { YeuThichDetailComponent } from '../detail/yeu-thich-detail.component';
import { YeuThichUpdateComponent } from '../update/yeu-thich-update.component';
import { YeuThichRoutingResolveService } from './yeu-thich-routing-resolve.service';

const yeuThichRoute: Routes = [
  {
    path: '',
    component: YeuThichComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: YeuThichDetailComponent,
    resolve: {
      yeuThich: YeuThichRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: YeuThichUpdateComponent,
    resolve: {
      yeuThich: YeuThichRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: YeuThichUpdateComponent,
    resolve: {
      yeuThich: YeuThichRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(yeuThichRoute)],
  exports: [RouterModule],
})
export class YeuThichRoutingModule {}
