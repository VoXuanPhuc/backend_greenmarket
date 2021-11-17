import { IHuyenQuan } from 'app/entities/huyen-quan/huyen-quan.model';

export interface ITinhTP {
  id?: number;
  ten?: string | null;
  huyenQuans?: IHuyenQuan[] | null;
}

export class TinhTP implements ITinhTP {
  constructor(public id?: number, public ten?: string | null, public huyenQuans?: IHuyenQuan[] | null) {}
}

export function getTinhTPIdentifier(tinhTP: ITinhTP): number | undefined {
  return tinhTP.id;
}
