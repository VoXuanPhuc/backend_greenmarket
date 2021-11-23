import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { IXaPhuong } from 'app/entities/xa-phuong/xa-phuong.model';

export interface INhaCungCap {
  id?: number;
  tenNCC?: string;
  chitietdiachi?: string | null;
  nongSans?: INongSan[] | null;
  xa?: IXaPhuong | null;
}

export class NhaCungCap implements INhaCungCap {
  constructor(
    public id?: number,
    public tenNCC?: string,
    public chitietdiachi?: string | null,
    public nongSans?: INongSan[] | null,
    public xa?: IXaPhuong | null
  ) {}
}

export function getNhaCungCapIdentifier(nhaCungCap: INhaCungCap): number | undefined {
  return nhaCungCap.id;
}
