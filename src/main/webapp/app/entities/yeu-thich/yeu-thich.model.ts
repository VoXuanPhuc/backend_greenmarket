import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';

export interface IYeuThich {
  id?: number;
  nongsan?: INongSan | null;
  khachhang?: IKhachHang | null;
}

export class YeuThich implements IYeuThich {
  constructor(public id?: number, public nongsan?: INongSan | null, public khachhang?: IKhachHang | null) {}
}

export function getYeuThichIdentifier(yeuThich: IYeuThich): number | undefined {
  return yeuThich.id;
}
