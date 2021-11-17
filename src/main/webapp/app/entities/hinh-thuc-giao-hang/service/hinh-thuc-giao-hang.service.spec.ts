import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHinhThucGiaoHang, HinhThucGiaoHang } from '../hinh-thuc-giao-hang.model';

import { HinhThucGiaoHangService } from './hinh-thuc-giao-hang.service';

describe('Service Tests', () => {
  describe('HinhThucGiaoHang Service', () => {
    let service: HinhThucGiaoHangService;
    let httpMock: HttpTestingController;
    let elemDefault: IHinhThucGiaoHang;
    let expectedResult: IHinhThucGiaoHang | IHinhThucGiaoHang[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HinhThucGiaoHangService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        mota: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a HinhThucGiaoHang', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new HinhThucGiaoHang()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HinhThucGiaoHang', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            mota: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a HinhThucGiaoHang', () => {
        const patchObject = Object.assign({}, new HinhThucGiaoHang());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HinhThucGiaoHang', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            mota: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a HinhThucGiaoHang', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHinhThucGiaoHangToCollectionIfMissing', () => {
        it('should add a HinhThucGiaoHang to an empty array', () => {
          const hinhThucGiaoHang: IHinhThucGiaoHang = { id: 123 };
          expectedResult = service.addHinhThucGiaoHangToCollectionIfMissing([], hinhThucGiaoHang);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hinhThucGiaoHang);
        });

        it('should not add a HinhThucGiaoHang to an array that contains it', () => {
          const hinhThucGiaoHang: IHinhThucGiaoHang = { id: 123 };
          const hinhThucGiaoHangCollection: IHinhThucGiaoHang[] = [
            {
              ...hinhThucGiaoHang,
            },
            { id: 456 },
          ];
          expectedResult = service.addHinhThucGiaoHangToCollectionIfMissing(hinhThucGiaoHangCollection, hinhThucGiaoHang);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a HinhThucGiaoHang to an array that doesn't contain it", () => {
          const hinhThucGiaoHang: IHinhThucGiaoHang = { id: 123 };
          const hinhThucGiaoHangCollection: IHinhThucGiaoHang[] = [{ id: 456 }];
          expectedResult = service.addHinhThucGiaoHangToCollectionIfMissing(hinhThucGiaoHangCollection, hinhThucGiaoHang);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hinhThucGiaoHang);
        });

        it('should add only unique HinhThucGiaoHang to an array', () => {
          const hinhThucGiaoHangArray: IHinhThucGiaoHang[] = [{ id: 123 }, { id: 456 }, { id: 42627 }];
          const hinhThucGiaoHangCollection: IHinhThucGiaoHang[] = [{ id: 123 }];
          expectedResult = service.addHinhThucGiaoHangToCollectionIfMissing(hinhThucGiaoHangCollection, ...hinhThucGiaoHangArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const hinhThucGiaoHang: IHinhThucGiaoHang = { id: 123 };
          const hinhThucGiaoHang2: IHinhThucGiaoHang = { id: 456 };
          expectedResult = service.addHinhThucGiaoHangToCollectionIfMissing([], hinhThucGiaoHang, hinhThucGiaoHang2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hinhThucGiaoHang);
          expect(expectedResult).toContain(hinhThucGiaoHang2);
        });

        it('should accept null and undefined values', () => {
          const hinhThucGiaoHang: IHinhThucGiaoHang = { id: 123 };
          expectedResult = service.addHinhThucGiaoHangToCollectionIfMissing([], null, hinhThucGiaoHang, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hinhThucGiaoHang);
        });

        it('should return initial array if no HinhThucGiaoHang is added', () => {
          const hinhThucGiaoHangCollection: IHinhThucGiaoHang[] = [{ id: 123 }];
          expectedResult = service.addHinhThucGiaoHangToCollectionIfMissing(hinhThucGiaoHangCollection, undefined, null);
          expect(expectedResult).toEqual(hinhThucGiaoHangCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
