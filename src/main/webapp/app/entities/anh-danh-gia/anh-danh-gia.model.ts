import { IDanhGia } from 'app/entities/danh-gia/danh-gia.model';

export interface IAnhDanhGia {
  id?: number;
  ten?: string;
  danhgia?: IDanhGia | null;
}

export class AnhDanhGia implements IAnhDanhGia {
  constructor(public id?: number, public ten?: string, public danhgia?: IDanhGia | null) {}
}

export function getAnhDanhGiaIdentifier(anhDanhGia: IAnhDanhGia): number | undefined {
  return anhDanhGia.id;
}
