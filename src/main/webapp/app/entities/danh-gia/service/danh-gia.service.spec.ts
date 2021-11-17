import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDanhGia, DanhGia } from '../danh-gia.model';

import { DanhGiaService } from './danh-gia.service';

describe('Service Tests', () => {
  describe('DanhGia Service', () => {
    let service: DanhGiaService;
    let httpMock: HttpTestingController;
    let elemDefault: IDanhGia;
    let expectedResult: IDanhGia | IDanhGia[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DanhGiaService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        sao: 0,
        chitiet: 'AAAAAAA',
        image: 'AAAAAAA',
        ngaydanhgia: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            ngaydanhgia: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DanhGia', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ngaydanhgia: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngaydanhgia: currentDate,
          },
          returnedFromService
        );

        service.create(new DanhGia()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DanhGia', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            sao: 1,
            chitiet: 'BBBBBB',
            image: 'BBBBBB',
            ngaydanhgia: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngaydanhgia: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DanhGia', () => {
        const patchObject = Object.assign(
          {
            image: 'BBBBBB',
            ngaydanhgia: currentDate.format(DATE_TIME_FORMAT),
          },
          new DanhGia()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            ngaydanhgia: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DanhGia', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            sao: 1,
            chitiet: 'BBBBBB',
            image: 'BBBBBB',
            ngaydanhgia: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngaydanhgia: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DanhGia', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDanhGiaToCollectionIfMissing', () => {
        it('should add a DanhGia to an empty array', () => {
          const danhGia: IDanhGia = { id: 123 };
          expectedResult = service.addDanhGiaToCollectionIfMissing([], danhGia);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(danhGia);
        });

        it('should not add a DanhGia to an array that contains it', () => {
          const danhGia: IDanhGia = { id: 123 };
          const danhGiaCollection: IDanhGia[] = [
            {
              ...danhGia,
            },
            { id: 456 },
          ];
          expectedResult = service.addDanhGiaToCollectionIfMissing(danhGiaCollection, danhGia);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DanhGia to an array that doesn't contain it", () => {
          const danhGia: IDanhGia = { id: 123 };
          const danhGiaCollection: IDanhGia[] = [{ id: 456 }];
          expectedResult = service.addDanhGiaToCollectionIfMissing(danhGiaCollection, danhGia);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(danhGia);
        });

        it('should add only unique DanhGia to an array', () => {
          const danhGiaArray: IDanhGia[] = [{ id: 123 }, { id: 456 }, { id: 31741 }];
          const danhGiaCollection: IDanhGia[] = [{ id: 123 }];
          expectedResult = service.addDanhGiaToCollectionIfMissing(danhGiaCollection, ...danhGiaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const danhGia: IDanhGia = { id: 123 };
          const danhGia2: IDanhGia = { id: 456 };
          expectedResult = service.addDanhGiaToCollectionIfMissing([], danhGia, danhGia2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(danhGia);
          expect(expectedResult).toContain(danhGia2);
        });

        it('should accept null and undefined values', () => {
          const danhGia: IDanhGia = { id: 123 };
          expectedResult = service.addDanhGiaToCollectionIfMissing([], null, danhGia, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(danhGia);
        });

        it('should return initial array if no DanhGia is added', () => {
          const danhGiaCollection: IDanhGia[] = [{ id: 123 }];
          expectedResult = service.addDanhGiaToCollectionIfMissing(danhGiaCollection, undefined, null);
          expect(expectedResult).toEqual(danhGiaCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
