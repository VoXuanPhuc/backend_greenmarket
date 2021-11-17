jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NongSanService } from '../service/nong-san.service';
import { INongSan, NongSan } from '../nong-san.model';
import { IDanhMuc } from 'app/entities/danh-muc/danh-muc.model';
import { DanhMucService } from 'app/entities/danh-muc/service/danh-muc.service';
import { INhaCungCap } from 'app/entities/nha-cung-cap/nha-cung-cap.model';
import { NhaCungCapService } from 'app/entities/nha-cung-cap/service/nha-cung-cap.service';

import { NongSanUpdateComponent } from './nong-san-update.component';

describe('Component Tests', () => {
  describe('NongSan Management Update Component', () => {
    let comp: NongSanUpdateComponent;
    let fixture: ComponentFixture<NongSanUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let nongSanService: NongSanService;
    let danhMucService: DanhMucService;
    let nhaCungCapService: NhaCungCapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NongSanUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NongSanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NongSanUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      nongSanService = TestBed.inject(NongSanService);
      danhMucService = TestBed.inject(DanhMucService);
      nhaCungCapService = TestBed.inject(NhaCungCapService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call DanhMuc query and add missing value', () => {
        const nongSan: INongSan = { id: 456 };
        const danhmuc: IDanhMuc = { id: 61361 };
        nongSan.danhmuc = danhmuc;

        const danhMucCollection: IDanhMuc[] = [{ id: 96265 }];
        jest.spyOn(danhMucService, 'query').mockReturnValue(of(new HttpResponse({ body: danhMucCollection })));
        const additionalDanhMucs = [danhmuc];
        const expectedCollection: IDanhMuc[] = [...additionalDanhMucs, ...danhMucCollection];
        jest.spyOn(danhMucService, 'addDanhMucToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ nongSan });
        comp.ngOnInit();

        expect(danhMucService.query).toHaveBeenCalled();
        expect(danhMucService.addDanhMucToCollectionIfMissing).toHaveBeenCalledWith(danhMucCollection, ...additionalDanhMucs);
        expect(comp.danhMucsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call NhaCungCap query and add missing value', () => {
        const nongSan: INongSan = { id: 456 };
        const nhacc: INhaCungCap = { id: 33127 };
        nongSan.nhacc = nhacc;

        const nhaCungCapCollection: INhaCungCap[] = [{ id: 73525 }];
        jest.spyOn(nhaCungCapService, 'query').mockReturnValue(of(new HttpResponse({ body: nhaCungCapCollection })));
        const additionalNhaCungCaps = [nhacc];
        const expectedCollection: INhaCungCap[] = [...additionalNhaCungCaps, ...nhaCungCapCollection];
        jest.spyOn(nhaCungCapService, 'addNhaCungCapToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ nongSan });
        comp.ngOnInit();

        expect(nhaCungCapService.query).toHaveBeenCalled();
        expect(nhaCungCapService.addNhaCungCapToCollectionIfMissing).toHaveBeenCalledWith(nhaCungCapCollection, ...additionalNhaCungCaps);
        expect(comp.nhaCungCapsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const nongSan: INongSan = { id: 456 };
        const danhmuc: IDanhMuc = { id: 27931 };
        nongSan.danhmuc = danhmuc;
        const nhacc: INhaCungCap = { id: 45157 };
        nongSan.nhacc = nhacc;

        activatedRoute.data = of({ nongSan });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(nongSan));
        expect(comp.danhMucsSharedCollection).toContain(danhmuc);
        expect(comp.nhaCungCapsSharedCollection).toContain(nhacc);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<NongSan>>();
        const nongSan = { id: 123 };
        jest.spyOn(nongSanService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ nongSan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nongSan }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(nongSanService.update).toHaveBeenCalledWith(nongSan);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<NongSan>>();
        const nongSan = new NongSan();
        jest.spyOn(nongSanService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ nongSan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nongSan }));
        saveSubject.complete();

        // THEN
        expect(nongSanService.create).toHaveBeenCalledWith(nongSan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<NongSan>>();
        const nongSan = { id: 123 };
        jest.spyOn(nongSanService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ nongSan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(nongSanService.update).toHaveBeenCalledWith(nongSan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDanhMucById', () => {
        it('Should return tracked DanhMuc primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDanhMucById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackNhaCungCapById', () => {
        it('Should return tracked NhaCungCap primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackNhaCungCapById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
