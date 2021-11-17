import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAnhDanhGia, AnhDanhGia } from '../anh-danh-gia.model';

import { AnhDanhGiaService } from './anh-danh-gia.service';

describe('Service Tests', () => {
  describe('AnhDanhGia Service', () => {
    let service: AnhDanhGiaService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnhDanhGia;
    let expectedResult: IAnhDanhGia | IAnhDanhGia[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AnhDanhGiaService);
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

      it('should create a AnhDanhGia', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AnhDanhGia()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AnhDanhGia', () => {
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

      it('should partial update a AnhDanhGia', () => {
        const patchObject = Object.assign(
          {
            ten: 'BBBBBB',
          },
          new AnhDanhGia()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AnhDanhGia', () => {
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

      it('should delete a AnhDanhGia', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAnhDanhGiaToCollectionIfMissing', () => {
        it('should add a AnhDanhGia to an empty array', () => {
          const anhDanhGia: IAnhDanhGia = { id: 123 };
          expectedResult = service.addAnhDanhGiaToCollectionIfMissing([], anhDanhGia);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(anhDanhGia);
        });

        it('should not add a AnhDanhGia to an array that contains it', () => {
          const anhDanhGia: IAnhDanhGia = { id: 123 };
          const anhDanhGiaCollection: IAnhDanhGia[] = [
            {
              ...anhDanhGia,
            },
            { id: 456 },
          ];
          expectedResult = service.addAnhDanhGiaToCollectionIfMissing(anhDanhGiaCollection, anhDanhGia);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AnhDanhGia to an array that doesn't contain it", () => {
          const anhDanhGia: IAnhDanhGia = { id: 123 };
          const anhDanhGiaCollection: IAnhDanhGia[] = [{ id: 456 }];
          expectedResult = service.addAnhDanhGiaToCollectionIfMissing(anhDanhGiaCollection, anhDanhGia);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(anhDanhGia);
        });

        it('should add only unique AnhDanhGia to an array', () => {
          const anhDanhGiaArray: IAnhDanhGia[] = [{ id: 123 }, { id: 456 }, { id: 45875 }];
          const anhDanhGiaCollection: IAnhDanhGia[] = [{ id: 123 }];
          expectedResult = service.addAnhDanhGiaToCollectionIfMissing(anhDanhGiaCollection, ...anhDanhGiaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const anhDanhGia: IAnhDanhGia = { id: 123 };
          const anhDanhGia2: IAnhDanhGia = { id: 456 };
          expectedResult = service.addAnhDanhGiaToCollectionIfMissing([], anhDanhGia, anhDanhGia2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(anhDanhGia);
          expect(expectedResult).toContain(anhDanhGia2);
        });

        it('should accept null and undefined values', () => {
          const anhDanhGia: IAnhDanhGia = { id: 123 };
          expectedResult = service.addAnhDanhGiaToCollectionIfMissing([], null, anhDanhGia, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(anhDanhGia);
        });

        it('should return initial array if no AnhDanhGia is added', () => {
          const anhDanhGiaCollection: IAnhDanhGia[] = [{ id: 123 }];
          expectedResult = service.addAnhDanhGiaToCollectionIfMissing(anhDanhGiaCollection, undefined, null);
          expect(expectedResult).toEqual(anhDanhGiaCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
