import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITinhTP, TinhTP } from '../tinh-tp.model';

import { TinhTPService } from './tinh-tp.service';

describe('Service Tests', () => {
  describe('TinhTP Service', () => {
    let service: TinhTPService;
    let httpMock: HttpTestingController;
    let elemDefault: ITinhTP;
    let expectedResult: ITinhTP | ITinhTP[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TinhTPService);
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

      it('should create a TinhTP', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TinhTP()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TinhTP', () => {
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

      it('should partial update a TinhTP', () => {
        const patchObject = Object.assign({}, new TinhTP());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TinhTP', () => {
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

      it('should delete a TinhTP', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTinhTPToCollectionIfMissing', () => {
        it('should add a TinhTP to an empty array', () => {
          const tinhTP: ITinhTP = { id: 123 };
          expectedResult = service.addTinhTPToCollectionIfMissing([], tinhTP);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(tinhTP);
        });

        it('should not add a TinhTP to an array that contains it', () => {
          const tinhTP: ITinhTP = { id: 123 };
          const tinhTPCollection: ITinhTP[] = [
            {
              ...tinhTP,
            },
            { id: 456 },
          ];
          expectedResult = service.addTinhTPToCollectionIfMissing(tinhTPCollection, tinhTP);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TinhTP to an array that doesn't contain it", () => {
          const tinhTP: ITinhTP = { id: 123 };
          const tinhTPCollection: ITinhTP[] = [{ id: 456 }];
          expectedResult = service.addTinhTPToCollectionIfMissing(tinhTPCollection, tinhTP);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(tinhTP);
        });

        it('should add only unique TinhTP to an array', () => {
          const tinhTPArray: ITinhTP[] = [{ id: 123 }, { id: 456 }, { id: 10093 }];
          const tinhTPCollection: ITinhTP[] = [{ id: 123 }];
          expectedResult = service.addTinhTPToCollectionIfMissing(tinhTPCollection, ...tinhTPArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const tinhTP: ITinhTP = { id: 123 };
          const tinhTP2: ITinhTP = { id: 456 };
          expectedResult = service.addTinhTPToCollectionIfMissing([], tinhTP, tinhTP2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(tinhTP);
          expect(expectedResult).toContain(tinhTP2);
        });

        it('should accept null and undefined values', () => {
          const tinhTP: ITinhTP = { id: 123 };
          expectedResult = service.addTinhTPToCollectionIfMissing([], null, tinhTP, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(tinhTP);
        });

        it('should return initial array if no TinhTP is added', () => {
          const tinhTPCollection: ITinhTP[] = [{ id: 123 }];
          expectedResult = service.addTinhTPToCollectionIfMissing(tinhTPCollection, undefined, null);
          expect(expectedResult).toEqual(tinhTPCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
