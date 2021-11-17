import { INongSan } from 'app/entities/nong-san/nong-san.model';

export interface IAnhNongSan {
  id?: number;
  ten?: string;
  anhnongsan?: INongSan | null;
}

export class AnhNongSan implements IAnhNongSan {
  constructor(public id?: number, public ten?: string, public anhnongsan?: INongSan | null) {}
}

export function getAnhNongSanIdentifier(anhNongSan: IAnhNongSan): number | undefined {
  return anhNongSan.id;
}
