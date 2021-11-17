import { IHuyenQuan } from 'app/entities/huyen-quan/huyen-quan.model';

export interface IXaPhuong {
  id?: number;
  ten?: string | null;
  huyen?: IHuyenQuan | null;
}

export class XaPhuong implements IXaPhuong {
  constructor(public id?: number, public ten?: string | null, public huyen?: IHuyenQuan | null) {}
}

export function getXaPhuongIdentifier(xaPhuong: IXaPhuong): number | undefined {
  return xaPhuong.id;
}
