import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDanhMuc, DanhMuc } from '../danh-muc.model';

import { DanhMucService } from './danh-muc.service';

describe('Service Tests', () => {
  describe('DanhMuc Service', () => {
    let service: DanhMucService;
    let httpMock: HttpTestingController;
    let elemDefault: IDanhMuc;
    let expectedResult: IDanhMuc | IDanhMuc[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DanhMucService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenDM: 'AAAAAAA',
        anhDanhMuc: 'AAAAAAA',
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

      it('should create a DanhMuc', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DanhMuc()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DanhMuc', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenDM: 'BBBBBB',
            anhDanhMuc: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DanhMuc', () => {
        const patchObject = Object.assign(
          {
            tenDM: 'BBBBBB',
            anhDanhMuc: 'BBBBBB',
          },
          new DanhMuc()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DanhMuc', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenDM: 'BBBBBB',
            anhDanhMuc: 'BBBBBB',
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

      it('should delete a DanhMuc', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDanhMucToCollectionIfMissing', () => {
        it('should add a DanhMuc to an empty array', () => {
          const danhMuc: IDanhMuc = { id: 123 };
          expectedResult = service.addDanhMucToCollectionIfMissing([], danhMuc);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(danhMuc);
        });

        it('should not add a DanhMuc to an array that contains it', () => {
          const danhMuc: IDanhMuc = { id: 123 };
          const danhMucCollection: IDanhMuc[] = [
            {
              ...danhMuc,
            },
            { id: 456 },
          ];
          expectedResult = service.addDanhMucToCollectionIfMissing(danhMucCollection, danhMuc);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DanhMuc to an array that doesn't contain it", () => {
          const danhMuc: IDanhMuc = { id: 123 };
          const danhMucCollection: IDanhMuc[] = [{ id: 456 }];
          expectedResult = service.addDanhMucToCollectionIfMissing(danhMucCollection, danhMuc);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(danhMuc);
        });

        it('should add only unique DanhMuc to an array', () => {
          const danhMucArray: IDanhMuc[] = [{ id: 123 }, { id: 456 }, { id: 56110 }];
          const danhMucCollection: IDanhMuc[] = [{ id: 123 }];
          expectedResult = service.addDanhMucToCollectionIfMissing(danhMucCollection, ...danhMucArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const danhMuc: IDanhMuc = { id: 123 };
          const danhMuc2: IDanhMuc = { id: 456 };
          expectedResult = service.addDanhMucToCollectionIfMissing([], danhMuc, danhMuc2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(danhMuc);
          expect(expectedResult).toContain(danhMuc2);
        });

        it('should accept null and undefined values', () => {
          const danhMuc: IDanhMuc = { id: 123 };
          expectedResult = service.addDanhMucToCollectionIfMissing([], null, danhMuc, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(danhMuc);
        });

        it('should return initial array if no DanhMuc is added', () => {
          const danhMucCollection: IDanhMuc[] = [{ id: 123 }];
          expectedResult = service.addDanhMucToCollectionIfMissing(danhMucCollection, undefined, null);
          expect(expectedResult).toEqual(danhMucCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
