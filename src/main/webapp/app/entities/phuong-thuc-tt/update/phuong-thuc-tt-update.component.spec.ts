jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PhuongThucTTService } from '../service/phuong-thuc-tt.service';
import { IPhuongThucTT, PhuongThucTT } from '../phuong-thuc-tt.model';

import { PhuongThucTTUpdateComponent } from './phuong-thuc-tt-update.component';

describe('Component Tests', () => {
  describe('PhuongThucTT Management Update Component', () => {
    let comp: PhuongThucTTUpdateComponent;
    let fixture: ComponentFixture<PhuongThucTTUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let phuongThucTTService: PhuongThucTTService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhuongThucTTUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PhuongThucTTUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhuongThucTTUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      phuongThucTTService = TestBed.inject(PhuongThucTTService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const phuongThucTT: IPhuongThucTT = { id: 456 };

        activatedRoute.data = of({ phuongThucTT });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(phuongThucTT));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PhuongThucTT>>();
        const phuongThucTT = { id: 123 };
        jest.spyOn(phuongThucTTService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ phuongThucTT });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phuongThucTT }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(phuongThucTTService.update).toHaveBeenCalledWith(phuongThucTT);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PhuongThucTT>>();
        const phuongThucTT = new PhuongThucTT();
        jest.spyOn(phuongThucTTService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ phuongThucTT });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phuongThucTT }));
        saveSubject.complete();

        // THEN
        expect(phuongThucTTService.create).toHaveBeenCalledWith(phuongThucTT);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PhuongThucTT>>();
        const phuongThucTT = { id: 123 };
        jest.spyOn(phuongThucTTService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ phuongThucTT });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(phuongThucTTService.update).toHaveBeenCalledWith(phuongThucTT);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
