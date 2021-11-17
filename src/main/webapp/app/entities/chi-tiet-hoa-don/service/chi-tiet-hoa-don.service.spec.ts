import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IChiTietHoaDon, ChiTietHoaDon } from '../chi-tiet-hoa-don.model';

import { ChiTietHoaDonService } from './chi-tiet-hoa-don.service';

describe('Service Tests', () => {
  describe('ChiTietHoaDon Service', () => {
    let service: ChiTietHoaDonService;
    let httpMock: HttpTestingController;
    let elemDefault: IChiTietHoaDon;
    let expectedResult: IChiTietHoaDon | IChiTietHoaDon[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ChiTietHoaDonService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        gia: 0,
        soluong: 0,
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

      it('should create a ChiTietHoaDon', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ChiTietHoaDon()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ChiTietHoaDon', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            gia: 1,
            soluong: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ChiTietHoaDon', () => {
        const patchObject = Object.assign(
          {
            soluong: 1,
          },
          new ChiTietHoaDon()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ChiTietHoaDon', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            gia: 1,
            soluong: 1,
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

      it('should delete a ChiTietHoaDon', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addChiTietHoaDonToCollectionIfMissing', () => {
        it('should add a ChiTietHoaDon to an empty array', () => {
          const chiTietHoaDon: IChiTietHoaDon = { id: 123 };
          expectedResult = service.addChiTietHoaDonToCollectionIfMissing([], chiTietHoaDon);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(chiTietHoaDon);
        });

        it('should not add a ChiTietHoaDon to an array that contains it', () => {
          const chiTietHoaDon: IChiTietHoaDon = { id: 123 };
          const chiTietHoaDonCollection: IChiTietHoaDon[] = [
            {
              ...chiTietHoaDon,
            },
            { id: 456 },
          ];
          expectedResult = service.addChiTietHoaDonToCollectionIfMissing(chiTietHoaDonCollection, chiTietHoaDon);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ChiTietHoaDon to an array that doesn't contain it", () => {
          const chiTietHoaDon: IChiTietHoaDon = { id: 123 };
          const chiTietHoaDonCollection: IChiTietHoaDon[] = [{ id: 456 }];
          expectedResult = service.addChiTietHoaDonToCollectionIfMissing(chiTietHoaDonCollection, chiTietHoaDon);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(chiTietHoaDon);
        });

        it('should add only unique ChiTietHoaDon to an array', () => {
          const chiTietHoaDonArray: IChiTietHoaDon[] = [{ id: 123 }, { id: 456 }, { id: 93256 }];
          const chiTietHoaDonCollection: IChiTietHoaDon[] = [{ id: 123 }];
          expectedResult = service.addChiTietHoaDonToCollectionIfMissing(chiTietHoaDonCollection, ...chiTietHoaDonArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const chiTietHoaDon: IChiTietHoaDon = { id: 123 };
          const chiTietHoaDon2: IChiTietHoaDon = { id: 456 };
          expectedResult = service.addChiTietHoaDonToCollectionIfMissing([], chiTietHoaDon, chiTietHoaDon2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(chiTietHoaDon);
          expect(expectedResult).toContain(chiTietHoaDon2);
        });

        it('should accept null and undefined values', () => {
          const chiTietHoaDon: IChiTietHoaDon = { id: 123 };
          expectedResult = service.addChiTietHoaDonToCollectionIfMissing([], null, chiTietHoaDon, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(chiTietHoaDon);
        });

        it('should return initial array if no ChiTietHoaDon is added', () => {
          const chiTietHoaDonCollection: IChiTietHoaDon[] = [{ id: 123 }];
          expectedResult = service.addChiTietHoaDonToCollectionIfMissing(chiTietHoaDonCollection, undefined, null);
          expect(expectedResult).toEqual(chiTietHoaDonCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
