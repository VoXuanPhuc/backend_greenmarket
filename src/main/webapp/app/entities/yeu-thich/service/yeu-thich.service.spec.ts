import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IYeuThich, YeuThich } from '../yeu-thich.model';

import { YeuThichService } from './yeu-thich.service';

describe('Service Tests', () => {
  describe('YeuThich Service', () => {
    let service: YeuThichService;
    let httpMock: HttpTestingController;
    let elemDefault: IYeuThich;
    let expectedResult: IYeuThich | IYeuThich[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(YeuThichService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
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

      it('should create a YeuThich', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new YeuThich()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a YeuThich', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a YeuThich', () => {
        const patchObject = Object.assign({}, new YeuThich());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of YeuThich', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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

      it('should delete a YeuThich', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addYeuThichToCollectionIfMissing', () => {
        it('should add a YeuThich to an empty array', () => {
          const yeuThich: IYeuThich = { id: 123 };
          expectedResult = service.addYeuThichToCollectionIfMissing([], yeuThich);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(yeuThich);
        });

        it('should not add a YeuThich to an array that contains it', () => {
          const yeuThich: IYeuThich = { id: 123 };
          const yeuThichCollection: IYeuThich[] = [
            {
              ...yeuThich,
            },
            { id: 456 },
          ];
          expectedResult = service.addYeuThichToCollectionIfMissing(yeuThichCollection, yeuThich);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a YeuThich to an array that doesn't contain it", () => {
          const yeuThich: IYeuThich = { id: 123 };
          const yeuThichCollection: IYeuThich[] = [{ id: 456 }];
          expectedResult = service.addYeuThichToCollectionIfMissing(yeuThichCollection, yeuThich);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(yeuThich);
        });

        it('should add only unique YeuThich to an array', () => {
          const yeuThichArray: IYeuThich[] = [{ id: 123 }, { id: 456 }, { id: 91515 }];
          const yeuThichCollection: IYeuThich[] = [{ id: 123 }];
          expectedResult = service.addYeuThichToCollectionIfMissing(yeuThichCollection, ...yeuThichArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const yeuThich: IYeuThich = { id: 123 };
          const yeuThich2: IYeuThich = { id: 456 };
          expectedResult = service.addYeuThichToCollectionIfMissing([], yeuThich, yeuThich2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(yeuThich);
          expect(expectedResult).toContain(yeuThich2);
        });

        it('should accept null and undefined values', () => {
          const yeuThich: IYeuThich = { id: 123 };
          expectedResult = service.addYeuThichToCollectionIfMissing([], null, yeuThich, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(yeuThich);
        });

        it('should return initial array if no YeuThich is added', () => {
          const yeuThichCollection: IYeuThich[] = [{ id: 123 }];
          expectedResult = service.addYeuThichToCollectionIfMissing(yeuThichCollection, undefined, null);
          expect(expectedResult).toEqual(yeuThichCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
