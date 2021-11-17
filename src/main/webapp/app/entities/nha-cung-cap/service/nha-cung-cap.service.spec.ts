import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INhaCungCap, NhaCungCap } from '../nha-cung-cap.model';

import { NhaCungCapService } from './nha-cung-cap.service';

describe('Service Tests', () => {
  describe('NhaCungCap Service', () => {
    let service: NhaCungCapService;
    let httpMock: HttpTestingController;
    let elemDefault: INhaCungCap;
    let expectedResult: INhaCungCap | INhaCungCap[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NhaCungCapService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenNCC: 'AAAAAAA',
        chitietdiachi: 'AAAAAAA',
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

      it('should create a NhaCungCap', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new NhaCungCap()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NhaCungCap', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenNCC: 'BBBBBB',
            chitietdiachi: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a NhaCungCap', () => {
        const patchObject = Object.assign(
          {
            tenNCC: 'BBBBBB',
            chitietdiachi: 'BBBBBB',
          },
          new NhaCungCap()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of NhaCungCap', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenNCC: 'BBBBBB',
            chitietdiachi: 'BBBBBB',
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

      it('should delete a NhaCungCap', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNhaCungCapToCollectionIfMissing', () => {
        it('should add a NhaCungCap to an empty array', () => {
          const nhaCungCap: INhaCungCap = { id: 123 };
          expectedResult = service.addNhaCungCapToCollectionIfMissing([], nhaCungCap);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhaCungCap);
        });

        it('should not add a NhaCungCap to an array that contains it', () => {
          const nhaCungCap: INhaCungCap = { id: 123 };
          const nhaCungCapCollection: INhaCungCap[] = [
            {
              ...nhaCungCap,
            },
            { id: 456 },
          ];
          expectedResult = service.addNhaCungCapToCollectionIfMissing(nhaCungCapCollection, nhaCungCap);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a NhaCungCap to an array that doesn't contain it", () => {
          const nhaCungCap: INhaCungCap = { id: 123 };
          const nhaCungCapCollection: INhaCungCap[] = [{ id: 456 }];
          expectedResult = service.addNhaCungCapToCollectionIfMissing(nhaCungCapCollection, nhaCungCap);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhaCungCap);
        });

        it('should add only unique NhaCungCap to an array', () => {
          const nhaCungCapArray: INhaCungCap[] = [{ id: 123 }, { id: 456 }, { id: 88793 }];
          const nhaCungCapCollection: INhaCungCap[] = [{ id: 123 }];
          expectedResult = service.addNhaCungCapToCollectionIfMissing(nhaCungCapCollection, ...nhaCungCapArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const nhaCungCap: INhaCungCap = { id: 123 };
          const nhaCungCap2: INhaCungCap = { id: 456 };
          expectedResult = service.addNhaCungCapToCollectionIfMissing([], nhaCungCap, nhaCungCap2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhaCungCap);
          expect(expectedResult).toContain(nhaCungCap2);
        });

        it('should accept null and undefined values', () => {
          const nhaCungCap: INhaCungCap = { id: 123 };
          expectedResult = service.addNhaCungCapToCollectionIfMissing([], null, nhaCungCap, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhaCungCap);
        });

        it('should return initial array if no NhaCungCap is added', () => {
          const nhaCungCapCollection: INhaCungCap[] = [{ id: 123 }];
          expectedResult = service.addNhaCungCapToCollectionIfMissing(nhaCungCapCollection, undefined, null);
          expect(expectedResult).toEqual(nhaCungCapCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
