import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPhuongThucTT, PhuongThucTT } from '../phuong-thuc-tt.model';

import { PhuongThucTTService } from './phuong-thuc-tt.service';

describe('Service Tests', () => {
  describe('PhuongThucTT Service', () => {
    let service: PhuongThucTTService;
    let httpMock: HttpTestingController;
    let elemDefault: IPhuongThucTT;
    let expectedResult: IPhuongThucTT | IPhuongThucTT[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PhuongThucTTService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenPTTT: 'AAAAAAA',
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

      it('should create a PhuongThucTT', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PhuongThucTT()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PhuongThucTT', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenPTTT: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PhuongThucTT', () => {
        const patchObject = Object.assign(
          {
            tenPTTT: 'BBBBBB',
          },
          new PhuongThucTT()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PhuongThucTT', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenPTTT: 'BBBBBB',
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

      it('should delete a PhuongThucTT', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPhuongThucTTToCollectionIfMissing', () => {
        it('should add a PhuongThucTT to an empty array', () => {
          const phuongThucTT: IPhuongThucTT = { id: 123 };
          expectedResult = service.addPhuongThucTTToCollectionIfMissing([], phuongThucTT);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phuongThucTT);
        });

        it('should not add a PhuongThucTT to an array that contains it', () => {
          const phuongThucTT: IPhuongThucTT = { id: 123 };
          const phuongThucTTCollection: IPhuongThucTT[] = [
            {
              ...phuongThucTT,
            },
            { id: 456 },
          ];
          expectedResult = service.addPhuongThucTTToCollectionIfMissing(phuongThucTTCollection, phuongThucTT);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PhuongThucTT to an array that doesn't contain it", () => {
          const phuongThucTT: IPhuongThucTT = { id: 123 };
          const phuongThucTTCollection: IPhuongThucTT[] = [{ id: 456 }];
          expectedResult = service.addPhuongThucTTToCollectionIfMissing(phuongThucTTCollection, phuongThucTT);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phuongThucTT);
        });

        it('should add only unique PhuongThucTT to an array', () => {
          const phuongThucTTArray: IPhuongThucTT[] = [{ id: 123 }, { id: 456 }, { id: 66561 }];
          const phuongThucTTCollection: IPhuongThucTT[] = [{ id: 123 }];
          expectedResult = service.addPhuongThucTTToCollectionIfMissing(phuongThucTTCollection, ...phuongThucTTArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const phuongThucTT: IPhuongThucTT = { id: 123 };
          const phuongThucTT2: IPhuongThucTT = { id: 456 };
          expectedResult = service.addPhuongThucTTToCollectionIfMissing([], phuongThucTT, phuongThucTT2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phuongThucTT);
          expect(expectedResult).toContain(phuongThucTT2);
        });

        it('should accept null and undefined values', () => {
          const phuongThucTT: IPhuongThucTT = { id: 123 };
          expectedResult = service.addPhuongThucTTToCollectionIfMissing([], null, phuongThucTT, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phuongThucTT);
        });

        it('should return initial array if no PhuongThucTT is added', () => {
          const phuongThucTTCollection: IPhuongThucTT[] = [{ id: 123 }];
          expectedResult = service.addPhuongThucTTToCollectionIfMissing(phuongThucTTCollection, undefined, null);
          expect(expectedResult).toEqual(phuongThucTTCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
