import { IHoaDon } from 'app/entities/hoa-don/hoa-don.model';

export interface IHinhThucGiaoHang {
  id?: number;
  mota?: string;
  hoaDons?: IHoaDon[] | null;
}

export class HinhThucGiaoHang implements IHinhThucGiaoHang {
  constructor(public id?: number, public mota?: string, public hoaDons?: IHoaDon[] | null) {}
}

export function getHinhThucGiaoHangIdentifier(hinhThucGiaoHang: IHinhThucGiaoHang): number | undefined {
  return hinhThucGiaoHang.id;
}
