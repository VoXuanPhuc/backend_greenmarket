import * as dayjs from 'dayjs';
import { IAnhNongSan } from 'app/entities/anh-nong-san/anh-nong-san.model';
import { IDanhGia } from 'app/entities/danh-gia/danh-gia.model';
import { IChiTietHoaDon } from 'app/entities/chi-tiet-hoa-don/chi-tiet-hoa-don.model';
import { IYeuThich } from 'app/entities/yeu-thich/yeu-thich.model';
import { IDanhMuc } from 'app/entities/danh-muc/danh-muc.model';
import { INhaCungCap } from 'app/entities/nha-cung-cap/nha-cung-cap.model';

export interface INongSan {
  id?: number;
  tenNS?: string;
  gia?: number;
  soluongNhap?: number;
  soluongCon?: number;
  noiSanXuat?: dayjs.Dayjs;
  moTaNS?: dayjs.Dayjs;
  anhNongSans?: IAnhNongSan[] | null;
  danhGias?: IDanhGia[] | null;
  chiTietHoaDons?: IChiTietHoaDon[] | null;
  yeuThiches?: IYeuThich[] | null;
  danhmuc?: IDanhMuc | null;
  nhacc?: INhaCungCap | null;
}

export class NongSan implements INongSan {
  constructor(
    public id?: number,
    public tenNS?: string,
    public gia?: number,
    public soluongNhap?: number,
    public soluongCon?: number,
    public noiSanXuat?: dayjs.Dayjs,
    public moTaNS?: dayjs.Dayjs,
    public anhNongSans?: IAnhNongSan[] | null,
    public danhGias?: IDanhGia[] | null,
    public chiTietHoaDons?: IChiTietHoaDon[] | null,
    public yeuThiches?: IYeuThich[] | null,
    public danhmuc?: IDanhMuc | null,
    public nhacc?: INhaCungCap | null
  ) {}
}

export function getNongSanIdentifier(nongSan: INongSan): number | undefined {
  return nongSan.id;
}
