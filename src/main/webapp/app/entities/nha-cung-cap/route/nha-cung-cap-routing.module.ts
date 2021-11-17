import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NhaCungCapComponent } from '../list/nha-cung-cap.component';
import { NhaCungCapDetailComponent } from '../detail/nha-cung-cap-detail.component';
import { NhaCungCapUpdateComponent } from '../update/nha-cung-cap-update.component';
import { NhaCungCapRoutingResolveService } from './nha-cung-cap-routing-resolve.service';

const nhaCungCapRoute: Routes = [
  {
    path: '',
    component: NhaCungCapComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NhaCungCapDetailComponent,
    resolve: {
      nhaCungCap: NhaCungCapRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NhaCungCapUpdateComponent,
    resolve: {
      nhaCungCap: NhaCungCapRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NhaCungCapUpdateComponent,
    resolve: {
      nhaCungCap: NhaCungCapRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nhaCungCapRoute)],
  exports: [RouterModule],
})
export class NhaCungCapRoutingModule {}
