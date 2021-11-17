import { IXaPhuong } from 'app/entities/xa-phuong/xa-phuong.model';
import { INongSan } from 'app/entities/nong-san/nong-san.model';

export interface INhaCungCap {
  id?: number;
  tenNCC?: string;
  chitietdiachi?: string | null;
  diaChi?: IXaPhuong | null;
  nongSans?: INongSan[] | null;
}

export class NhaCungCap implements INhaCungCap {
  constructor(
    public id?: number,
    public tenNCC?: string,
    public chitietdiachi?: string | null,
    public diaChi?: IXaPhuong | null,
    public nongSans?: INongSan[] | null
  ) {}
}

export function getNhaCungCapIdentifier(nhaCungCap: INhaCungCap): number | undefined {
  return nhaCungCap.id;
}
