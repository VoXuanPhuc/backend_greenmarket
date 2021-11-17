import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAnhNongSan, AnhNongSan } from '../anh-nong-san.model';

import { AnhNongSanService } from './anh-nong-san.service';

describe('Service Tests', () => {
  describe('AnhNongSan Service', () => {
    let service: AnhNongSanService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnhNongSan;
    let expectedResult: IAnhNongSan | IAnhNongSan[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AnhNongSanService);
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

      it('should create a AnhNongSan', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AnhNongSan()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AnhNongSan', () => {
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

      it('should partial update a AnhNongSan', () => {
        const patchObject = Object.assign({}, new AnhNongSan());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AnhNongSan', () => {
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

      it('should delete a AnhNongSan', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAnhNongSanToCollectionIfMissing', () => {
        it('should add a AnhNongSan to an empty array', () => {
          const anhNongSan: IAnhNongSan = { id: 123 };
          expectedResult = service.addAnhNongSanToCollectionIfMissing([], anhNongSan);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(anhNongSan);
        });

        it('should not add a AnhNongSan to an array that contains it', () => {
          const anhNongSan: IAnhNongSan = { id: 123 };
          const anhNongSanCollection: IAnhNongSan[] = [
            {
              ...anhNongSan,
            },
            { id: 456 },
          ];
          expectedResult = service.addAnhNongSanToCollectionIfMissing(anhNongSanCollection, anhNongSan);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AnhNongSan to an array that doesn't contain it", () => {
          const anhNongSan: IAnhNongSan = { id: 123 };
          const anhNongSanCollection: IAnhNongSan[] = [{ id: 456 }];
          expectedResult = service.addAnhNongSanToCollectionIfMissing(anhNongSanCollection, anhNongSan);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(anhNongSan);
        });

        it('should add only unique AnhNongSan to an array', () => {
          const anhNongSanArray: IAnhNongSan[] = [{ id: 123 }, { id: 456 }, { id: 72392 }];
          const anhNongSanCollection: IAnhNongSan[] = [{ id: 123 }];
          expectedResult = service.addAnhNongSanToCollectionIfMissing(anhNongSanCollection, ...anhNongSanArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const anhNongSan: IAnhNongSan = { id: 123 };
          const anhNongSan2: IAnhNongSan = { id: 456 };
          expectedResult = service.addAnhNongSanToCollectionIfMissing([], anhNongSan, anhNongSan2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(anhNongSan);
          expect(expectedResult).toContain(anhNongSan2);
        });

        it('should accept null and undefined values', () => {
          const anhNongSan: IAnhNongSan = { id: 123 };
          expectedResult = service.addAnhNongSanToCollectionIfMissing([], null, anhNongSan, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(anhNongSan);
        });

        it('should return initial array if no AnhNongSan is added', () => {
          const anhNongSanCollection: IAnhNongSan[] = [{ id: 123 }];
          expectedResult = service.addAnhNongSanToCollectionIfMissing(anhNongSanCollection, undefined, null);
          expect(expectedResult).toEqual(anhNongSanCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
