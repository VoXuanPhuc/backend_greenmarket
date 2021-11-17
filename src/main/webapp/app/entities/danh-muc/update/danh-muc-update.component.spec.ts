jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DanhMucService } from '../service/danh-muc.service';
import { IDanhMuc, DanhMuc } from '../danh-muc.model';

import { DanhMucUpdateComponent } from './danh-muc-update.component';

describe('Component Tests', () => {
  describe('DanhMuc Management Update Component', () => {
    let comp: DanhMucUpdateComponent;
    let fixture: ComponentFixture<DanhMucUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let danhMucService: DanhMucService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DanhMucUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DanhMucUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DanhMucUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      danhMucService = TestBed.inject(DanhMucService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const danhMuc: IDanhMuc = { id: 456 };

        activatedRoute.data = of({ danhMuc });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(danhMuc));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DanhMuc>>();
        const danhMuc = { id: 123 };
        jest.spyOn(danhMucService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ danhMuc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: danhMuc }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(danhMucService.update).toHaveBeenCalledWith(danhMuc);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DanhMuc>>();
        const danhMuc = new DanhMuc();
        jest.spyOn(danhMucService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ danhMuc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: danhMuc }));
        saveSubject.complete();

        // THEN
        expect(danhMucService.create).toHaveBeenCalledWith(danhMuc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DanhMuc>>();
        const danhMuc = { id: 123 };
        jest.spyOn(danhMucService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ danhMuc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(danhMucService.update).toHaveBeenCalledWith(danhMuc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
