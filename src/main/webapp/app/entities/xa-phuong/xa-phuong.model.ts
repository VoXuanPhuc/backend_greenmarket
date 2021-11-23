import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';
import { INhaCungCap } from 'app/entities/nha-cung-cap/nha-cung-cap.model';
import { IHuyenQuan } from 'app/entities/huyen-quan/huyen-quan.model';

export interface IXaPhuong {
  id?: number;
  ten?: string | null;
  khachHangs?: IKhachHang[] | null;
  nhaCungCaps?: INhaCungCap[] | null;
  huyen?: IHuyenQuan | null;
}

export class XaPhuong implements IXaPhuong {
  constructor(
    public id?: number,
    public ten?: string | null,
    public khachHangs?: IKhachHang[] | null,
    public nhaCungCaps?: INhaCungCap[] | null,
    public huyen?: IHuyenQuan | null
  ) {}
}

export function getXaPhuongIdentifier(xaPhuong: IXaPhuong): number | undefined {
  return xaPhuong.id;
}
