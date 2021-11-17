import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IXaPhuong, XaPhuong } from '../xa-phuong.model';

import { XaPhuongService } from './xa-phuong.service';

describe('Service Tests', () => {
  describe('XaPhuong Service', () => {
    let service: XaPhuongService;
    let httpMock: HttpTestingController;
    let elemDefault: IXaPhuong;
    let expectedResult: IXaPhuong | IXaPhuong[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(XaPhuongService);
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

      it('should create a XaPhuong', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new XaPhuong()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a XaPhuong', () => {
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

      it('should partial update a XaPhuong', () => {
        const patchObject = Object.assign({}, new XaPhuong());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of XaPhuong', () => {
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

      it('should delete a XaPhuong', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addXaPhuongToCollectionIfMissing', () => {
        it('should add a XaPhuong to an empty array', () => {
          const xaPhuong: IXaPhuong = { id: 123 };
          expectedResult = service.addXaPhuongToCollectionIfMissing([], xaPhuong);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(xaPhuong);
        });

        it('should not add a XaPhuong to an array that contains it', () => {
          const xaPhuong: IXaPhuong = { id: 123 };
          const xaPhuongCollection: IXaPhuong[] = [
            {
              ...xaPhuong,
            },
            { id: 456 },
          ];
          expectedResult = service.addXaPhuongToCollectionIfMissing(xaPhuongCollection, xaPhuong);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a XaPhuong to an array that doesn't contain it", () => {
          const xaPhuong: IXaPhuong = { id: 123 };
          const xaPhuongCollection: IXaPhuong[] = [{ id: 456 }];
          expectedResult = service.addXaPhuongToCollectionIfMissing(xaPhuongCollection, xaPhuong);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(xaPhuong);
        });

        it('should add only unique XaPhuong to an array', () => {
          const xaPhuongArray: IXaPhuong[] = [{ id: 123 }, { id: 456 }, { id: 29564 }];
          const xaPhuongCollection: IXaPhuong[] = [{ id: 123 }];
          expectedResult = service.addXaPhuongToCollectionIfMissing(xaPhuongCollection, ...xaPhuongArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const xaPhuong: IXaPhuong = { id: 123 };
          const xaPhuong2: IXaPhuong = { id: 456 };
          expectedResult = service.addXaPhuongToCollectionIfMissing([], xaPhuong, xaPhuong2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(xaPhuong);
          expect(expectedResult).toContain(xaPhuong2);
        });

        it('should accept null and undefined values', () => {
          const xaPhuong: IXaPhuong = { id: 123 };
          expectedResult = service.addXaPhuongToCollectionIfMissing([], null, xaPhuong, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(xaPhuong);
        });

        it('should return initial array if no XaPhuong is added', () => {
          const xaPhuongCollection: IXaPhuong[] = [{ id: 123 }];
          expectedResult = service.addXaPhuongToCollectionIfMissing(xaPhuongCollection, undefined, null);
          expect(expectedResult).toEqual(xaPhuongCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
