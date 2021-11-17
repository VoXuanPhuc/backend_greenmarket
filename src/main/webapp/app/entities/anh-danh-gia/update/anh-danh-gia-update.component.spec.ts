jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AnhDanhGiaService } from '../service/anh-danh-gia.service';
import { IAnhDanhGia, AnhDanhGia } from '../anh-danh-gia.model';
import { IDanhGia } from 'app/entities/danh-gia/danh-gia.model';
import { DanhGiaService } from 'app/entities/danh-gia/service/danh-gia.service';

import { AnhDanhGiaUpdateComponent } from './anh-danh-gia-update.component';

describe('Component Tests', () => {
  describe('AnhDanhGia Management Update Component', () => {
    let comp: AnhDanhGiaUpdateComponent;
    let fixture: ComponentFixture<AnhDanhGiaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let anhDanhGiaService: AnhDanhGiaService;
    let danhGiaService: DanhGiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AnhDanhGiaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AnhDanhGiaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnhDanhGiaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      anhDanhGiaService = TestBed.inject(AnhDanhGiaService);
      danhGiaService = TestBed.inject(DanhGiaService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call DanhGia query and add missing value', () => {
        const anhDanhGia: IAnhDanhGia = { id: 456 };
        const danhgia: IDanhGia = { id: 56763 };
        anhDanhGia.danhgia = danhgia;

        const danhGiaCollection: IDanhGia[] = [{ id: 64561 }];
        jest.spyOn(danhGiaService, 'query').mockReturnValue(of(new HttpResponse({ body: danhGiaCollection })));
        const additionalDanhGias = [danhgia];
        const expectedCollection: IDanhGia[] = [...additionalDanhGias, ...danhGiaCollection];
        jest.spyOn(danhGiaService, 'addDanhGiaToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ anhDanhGia });
        comp.ngOnInit();

        expect(danhGiaService.query).toHaveBeenCalled();
        expect(danhGiaService.addDanhGiaToCollectionIfMissing).toHaveBeenCalledWith(danhGiaCollection, ...additionalDanhGias);
        expect(comp.danhGiasSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const anhDanhGia: IAnhDanhGia = { id: 456 };
        const danhgia: IDanhGia = { id: 30528 };
        anhDanhGia.danhgia = danhgia;

        activatedRoute.data = of({ anhDanhGia });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(anhDanhGia));
        expect(comp.danhGiasSharedCollection).toContain(danhgia);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnhDanhGia>>();
        const anhDanhGia = { id: 123 };
        jest.spyOn(anhDanhGiaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anhDanhGia });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: anhDanhGia }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(anhDanhGiaService.update).toHaveBeenCalledWith(anhDanhGia);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnhDanhGia>>();
        const anhDanhGia = new AnhDanhGia();
        jest.spyOn(anhDanhGiaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anhDanhGia });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: anhDanhGia }));
        saveSubject.complete();

        // THEN
        expect(anhDanhGiaService.create).toHaveBeenCalledWith(anhDanhGia);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnhDanhGia>>();
        const anhDanhGia = { id: 123 };
        jest.spyOn(anhDanhGiaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anhDanhGia });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(anhDanhGiaService.update).toHaveBeenCalledWith(anhDanhGia);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDanhGiaById', () => {
        it('Should return tracked DanhGia primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDanhGiaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
