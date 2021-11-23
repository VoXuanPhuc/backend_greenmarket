import * as dayjs from 'dayjs';
import { IYeuThich } from 'app/entities/yeu-thich/yeu-thich.model';
import { IDanhGia } from 'app/entities/danh-gia/danh-gia.model';
import { IHoaDon } from 'app/entities/hoa-don/hoa-don.model';
import { IXaPhuong } from 'app/entities/xa-phuong/xa-phuong.model';

export interface IKhachHang {
  id?: number;
  hoTenKH?: string | null;
  tenDangNhap?: string | null;
  matkhau?: string;
  email?: string;
  sdt?: string;
  ngaySinh?: dayjs.Dayjs;
  gioitinh?: string | null;
  chitietdiachi?: string | null;
  yeuThiches?: IYeuThich[] | null;
  danhGias?: IDanhGia[] | null;
  hoaDons?: IHoaDon[] | null;
  xa?: IXaPhuong | null;
}

export class KhachHang implements IKhachHang {
  constructor(
    public id?: number,
    public hoTenKH?: string | null,
    public tenDangNhap?: string | null,
    public matkhau?: string,
    public email?: string,
    public sdt?: string,
    public ngaySinh?: dayjs.Dayjs,
    public gioitinh?: string | null,
    public chitietdiachi?: string | null,
    public yeuThiches?: IYeuThich[] | null,
    public danhGias?: IDanhGia[] | null,
    public hoaDons?: IHoaDon[] | null,
    public xa?: IXaPhuong | null
  ) {}
}

export function getKhachHangIdentifier(khachHang: IKhachHang): number | undefined {
  return khachHang.id;
}
