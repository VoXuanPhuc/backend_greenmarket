import * as dayjs from 'dayjs';
import { IChiTietHoaDon } from 'app/entities/chi-tiet-hoa-don/chi-tiet-hoa-don.model';
import { IPhuongThucTT } from 'app/entities/phuong-thuc-tt/phuong-thuc-tt.model';
import { IHinhThucGiaoHang } from 'app/entities/hinh-thuc-giao-hang/hinh-thuc-giao-hang.model';
import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';

export interface IHoaDon {
  id?: number;
  tongthanhtoan?: number;
  chiphivanchuyen?: number;
  ngaythanhtoan?: dayjs.Dayjs;
  ngaytao?: dayjs.Dayjs;
  trangthai?: string | null;
  chiTietHoaDons?: IChiTietHoaDon[] | null;
  phuongthucTT?: IPhuongThucTT | null;
  phuongthucGH?: IHinhThucGiaoHang | null;
  khachhang?: IKhachHang | null;
}

export class HoaDon implements IHoaDon {
  constructor(
    public id?: number,
    public tongthanhtoan?: number,
    public chiphivanchuyen?: number,
    public ngaythanhtoan?: dayjs.Dayjs,
    public ngaytao?: dayjs.Dayjs,
    public trangthai?: string | null,
    public chiTietHoaDons?: IChiTietHoaDon[] | null,
    public phuongthucTT?: IPhuongThucTT | null,
    public phuongthucGH?: IHinhThucGiaoHang | null,
    public khachhang?: IKhachHang | null
  ) {}
}

export function getHoaDonIdentifier(hoaDon: IHoaDon): number | undefined {
  return hoaDon.id;
}
