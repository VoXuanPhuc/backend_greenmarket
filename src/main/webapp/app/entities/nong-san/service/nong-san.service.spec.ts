import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INongSan, NongSan } from '../nong-san.model';

import { NongSanService } from './nong-san.service';

describe('Service Tests', () => {
  describe('NongSan Service', () => {
    let service: NongSanService;
    let httpMock: HttpTestingController;
    let elemDefault: INongSan;
    let expectedResult: INongSan | INongSan[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NongSanService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        tenNS: 'AAAAAAA',
        gia: 0,
        soluongNhap: 0,
        soluongCon: 0,
        noiSanXuat: currentDate,
        moTaNS: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            noiSanXuat: currentDate.format(DATE_TIME_FORMAT),
            moTaNS: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a NongSan', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            noiSanXuat: currentDate.format(DATE_TIME_FORMAT),
            moTaNS: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            noiSanXuat: currentDate,
            moTaNS: currentDate,
          },
          returnedFromService
        );

        service.create(new NongSan()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NongSan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenNS: 'BBBBBB',
            gia: 1,
            soluongNhap: 1,
            soluongCon: 1,
            noiSanXuat: currentDate.format(DATE_TIME_FORMAT),
            moTaNS: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            noiSanXuat: currentDate,
            moTaNS: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a NongSan', () => {
        const patchObject = Object.assign(
          {
            tenNS: 'BBBBBB',
            soluongCon: 1,
          },
          new NongSan()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            noiSanXuat: currentDate,
            moTaNS: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of NongSan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenNS: 'BBBBBB',
            gia: 1,
            soluongNhap: 1,
            soluongCon: 1,
            noiSanXuat: currentDate.format(DATE_TIME_FORMAT),
            moTaNS: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            noiSanXuat: currentDate,
            moTaNS: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a NongSan', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNongSanToCollectionIfMissing', () => {
        it('should add a NongSan to an empty array', () => {
          const nongSan: INongSan = { id: 123 };
          expectedResult = service.addNongSanToCollectionIfMissing([], nongSan);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nongSan);
        });

        it('should not add a NongSan to an array that contains it', () => {
          const nongSan: INongSan = { id: 123 };
          const nongSanCollection: INongSan[] = [
            {
              ...nongSan,
            },
            { id: 456 },
          ];
          expectedResult = service.addNongSanToCollectionIfMissing(nongSanCollection, nongSan);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a NongSan to an array that doesn't contain it", () => {
          const nongSan: INongSan = { id: 123 };
          const nongSanCollection: INongSan[] = [{ id: 456 }];
          expectedResult = service.addNongSanToCollectionIfMissing(nongSanCollection, nongSan);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nongSan);
        });

        it('should add only unique NongSan to an array', () => {
          const nongSanArray: INongSan[] = [{ id: 123 }, { id: 456 }, { id: 80882 }];
          const nongSanCollection: INongSan[] = [{ id: 123 }];
          expectedResult = service.addNongSanToCollectionIfMissing(nongSanCollection, ...nongSanArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const nongSan: INongSan = { id: 123 };
          const nongSan2: INongSan = { id: 456 };
          expectedResult = service.addNongSanToCollectionIfMissing([], nongSan, nongSan2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nongSan);
          expect(expectedResult).toContain(nongSan2);
        });

        it('should accept null and undefined values', () => {
          const nongSan: INongSan = { id: 123 };
          expectedResult = service.addNongSanToCollectionIfMissing([], null, nongSan, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nongSan);
        });

        it('should return initial array if no NongSan is added', () => {
          const nongSanCollection: INongSan[] = [{ id: 123 }];
          expectedResult = service.addNongSanToCollectionIfMissing(nongSanCollection, undefined, null);
          expect(expectedResult).toEqual(nongSanCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
