import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tinh-tp',
        data: { pageTitle: 'TinhTPS' },
        loadChildren: () => import('./tinh-tp/tinh-tp.module').then(m => m.TinhTPModule),
      },
      {
        path: 'huyen-quan',
        data: { pageTitle: 'HuyenQuans' },
        loadChildren: () => import('./huyen-quan/huyen-quan.module').then(m => m.HuyenQuanModule),
      },
      {
        path: 'xa-phuong',
        data: { pageTitle: 'XaPhuongs' },
        loadChildren: () => import('./xa-phuong/xa-phuong.module').then(m => m.XaPhuongModule),
      },
      {
        path: 'khach-hang',
        data: { pageTitle: 'KhachHangs' },
        loadChildren: () => import('./khach-hang/khach-hang.module').then(m => m.KhachHangModule),
      },
      {
        path: 'nha-cung-cap',
        data: { pageTitle: 'NhaCungCaps' },
        loadChildren: () => import('./nha-cung-cap/nha-cung-cap.module').then(m => m.NhaCungCapModule),
      },
      {
        path: 'danh-muc',
        data: { pageTitle: 'DanhMucs' },
        loadChildren: () => import('./danh-muc/danh-muc.module').then(m => m.DanhMucModule),
      },
      {
        path: 'nong-san',
        data: { pageTitle: 'NongSans' },
        loadChildren: () => import('./nong-san/nong-san.module').then(m => m.NongSanModule),
      },
      {
        path: 'anh-nong-san',
        data: { pageTitle: 'AnhNongSans' },
        loadChildren: () => import('./anh-nong-san/anh-nong-san.module').then(m => m.AnhNongSanModule),
      },
      {
        path: 'phuong-thuc-tt',
        data: { pageTitle: 'PhuongThucTTS' },
        loadChildren: () => import('./phuong-thuc-tt/phuong-thuc-tt.module').then(m => m.PhuongThucTTModule),
      },
      {
        path: 'chi-tiet-hoa-don',
        data: { pageTitle: 'ChiTietHoaDons' },
        loadChildren: () => import('./chi-tiet-hoa-don/chi-tiet-hoa-don.module').then(m => m.ChiTietHoaDonModule),
      },
      {
        path: 'hoa-don',
        data: { pageTitle: 'HoaDons' },
        loadChildren: () => import('./hoa-don/hoa-don.module').then(m => m.HoaDonModule),
      },
      {
        path: 'danh-gia',
        data: { pageTitle: 'DanhGias' },
        loadChildren: () => import('./danh-gia/danh-gia.module').then(m => m.DanhGiaModule),
      },
      {
        path: 'anh-danh-gia',
        data: { pageTitle: 'AnhDanhGias' },
        loadChildren: () => import('./anh-danh-gia/anh-danh-gia.module').then(m => m.AnhDanhGiaModule),
      },
      {
        path: 'hinh-thuc-giao-hang',
        data: { pageTitle: 'HinhThucGiaoHangs' },
        loadChildren: () => import('./hinh-thuc-giao-hang/hinh-thuc-giao-hang.module').then(m => m.HinhThucGiaoHangModule),
      },
      {
        path: 'yeu-thich',
        data: { pageTitle: 'YeuThiches' },
        loadChildren: () => import('./yeu-thich/yeu-thich.module').then(m => m.YeuThichModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
