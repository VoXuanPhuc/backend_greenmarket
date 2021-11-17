import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHuyenQuan, HuyenQuan } from '../huyen-quan.model';

import { HuyenQuanService } from './huyen-quan.service';

describe('Service Tests', () => {
  describe('HuyenQuan Service', () => {
    let service: HuyenQuanService;
    let httpMock: HttpTestingController;
    let elemDefault: IHuyenQuan;
    let expectedResult: IHuyenQuan | IHuyenQuan[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HuyenQuanService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        ten: 'AAAAAAA',
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

      it('should create a HuyenQuan', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new HuyenQuan()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HuyenQuan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ten: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a HuyenQuan', () => {
        const patchObject = Object.assign(
          {
            ten: 'BBBBBB',
          },
          new HuyenQuan()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HuyenQuan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ten: 'BBBBBB',
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

      it('should delete a HuyenQuan', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHuyenQuanToCollectionIfMissing', () => {
        it('should add a HuyenQuan to an empty array', () => {
          const huyenQuan: IHuyenQuan = { id: 123 };
          expectedResult = service.addHuyenQuanToCollectionIfMissing([], huyenQuan);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(huyenQuan);
        });

        it('should not add a HuyenQuan to an array that contains it', () => {
          const huyenQuan: IHuyenQuan = { id: 123 };
          const huyenQuanCollection: IHuyenQuan[] = [
            {
              ...huyenQuan,
            },
            { id: 456 },
          ];
          expectedResult = service.addHuyenQuanToCollectionIfMissing(huyenQuanCollection, huyenQuan);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a HuyenQuan to an array that doesn't contain it", () => {
          const huyenQuan: IHuyenQuan = { id: 123 };
          const huyenQuanCollection: IHuyenQuan[] = [{ id: 456 }];
          expectedResult = service.addHuyenQuanToCollectionIfMissing(huyenQuanCollection, huyenQuan);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(huyenQuan);
        });

        it('should add only unique HuyenQuan to an array', () => {
          const huyenQuanArray: IHuyenQuan[] = [{ id: 123 }, { id: 456 }, { id: 59855 }];
          const huyenQuanCollection: IHuyenQuan[] = [{ id: 123 }];
          expectedResult = service.addHuyenQuanToCollectionIfMissing(huyenQuanCollection, ...huyenQuanArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const huyenQuan: IHuyenQuan = { id: 123 };
          const huyenQuan2: IHuyenQuan = { id: 456 };
          expectedResult = service.addHuyenQuanToCollectionIfMissing([], huyenQuan, huyenQuan2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(huyenQuan);
          expect(expectedResult).toContain(huyenQuan2);
        });

        it('should accept null and undefined values', () => {
          const huyenQuan: IHuyenQuan = { id: 123 };
          expectedResult = service.addHuyenQuanToCollectionIfMissing([], null, huyenQuan, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(huyenQuan);
        });

        it('should return initial array if no HuyenQuan is added', () => {
          const huyenQuanCollection: IHuyenQuan[] = [{ id: 123 }];
          expectedResult = service.addHuyenQuanToCollectionIfMissing(huyenQuanCollection, undefined, null);
          expect(expectedResult).toEqual(huyenQuanCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
