import { IHoaDon } from 'app/entities/hoa-don/hoa-don.model';

export interface IPhuongThucTT {
  id?: number;
  tenPTTT?: string;
  hoaDons?: IHoaDon[] | null;
}

export class PhuongThucTT implements IPhuongThucTT {
  constructor(public id?: number, public tenPTTT?: string, public hoaDons?: IHoaDon[] | null) {}
}

export function getPhuongThucTTIdentifier(phuongThucTT: IPhuongThucTT): number | undefined {
  return phuongThucTT.id;
}
