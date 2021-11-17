import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { IHoaDon } from 'app/entities/hoa-don/hoa-don.model';

export interface IChiTietHoaDon {
  id?: number;
  gia?: number;
  soluong?: number;
  nongsan?: INongSan | null;
  hoadon?: IHoaDon | null;
}

export class ChiTietHoaDon implements IChiTietHoaDon {
  constructor(
    public id?: number,
    public gia?: number,
    public soluong?: number,
    public nongsan?: INongSan | null,
    public hoadon?: IHoaDon | null
  ) {}
}

export function getChiTietHoaDonIdentifier(chiTietHoaDon: IChiTietHoaDon): number | undefined {
  return chiTietHoaDon.id;
}
