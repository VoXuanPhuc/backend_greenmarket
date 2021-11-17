import * as dayjs from 'dayjs';
import { IAnhDanhGia } from 'app/entities/anh-danh-gia/anh-danh-gia.model';
import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';

export interface IDanhGia {
  id?: number;
  sao?: number;
  chitiet?: string;
  image?: string;
  ngaydanhgia?: dayjs.Dayjs;
  anhDanhGias?: IAnhDanhGia[] | null;
  nongsan?: INongSan | null;
  khachhang?: IKhachHang | null;
}

export class DanhGia implements IDanhGia {
  constructor(
    public id?: number,
    public sao?: number,
    public chitiet?: string,
    public image?: string,
    public ngaydanhgia?: dayjs.Dayjs,
    public anhDanhGias?: IAnhDanhGia[] | null,
    public nongsan?: INongSan | null,
    public khachhang?: IKhachHang | null
  ) {}
}

export function getDanhGiaIdentifier(danhGia: IDanhGia): number | undefined {
  return danhGia.id;
}
