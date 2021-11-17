import { INongSan } from 'app/entities/nong-san/nong-san.model';

export interface IDanhMuc {
  id?: number;
  tenDM?: string | null;
  nongSans?: INongSan[] | null;
}

export class DanhMuc implements IDanhMuc {
  constructor(public id?: number, public tenDM?: string | null, public nongSans?: INongSan[] | null) {}
}

export function getDanhMucIdentifier(danhMuc: IDanhMuc): number | undefined {
  return danhMuc.id;
}
