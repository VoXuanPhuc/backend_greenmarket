import { IXaPhuong } from 'app/entities/xa-phuong/xa-phuong.model';
import { ITinhTP } from 'app/entities/tinh-tp/tinh-tp.model';

export interface IHuyenQuan {
  id?: number;
  ten?: string | null;
  xaPhuongs?: IXaPhuong[] | null;
  tinh?: ITinhTP | null;
}

export class HuyenQuan implements IHuyenQuan {
  constructor(public id?: number, public ten?: string | null, public xaPhuongs?: IXaPhuong[] | null, public tinh?: ITinhTP | null) {}
}

export function getHuyenQuanIdentifier(huyenQuan: IHuyenQuan): number | undefined {
  return huyenQuan.id;
}
