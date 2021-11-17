jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TinhTPService } from '../service/tinh-tp.service';
import { ITinhTP, TinhTP } from '../tinh-tp.model';

import { TinhTPUpdateComponent } from './tinh-tp-update.component';

describe('Component Tests', () => {
  describe('TinhTP Management Update Component', () => {
    let comp: TinhTPUpdateComponent;
    let fixture: ComponentFixture<TinhTPUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let tinhTPService: TinhTPService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TinhTPUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TinhTPUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TinhTPUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      tinhTPService = TestBed.inject(TinhTPService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const tinhTP: ITinhTP = { id: 456 };

        activatedRoute.data = of({ tinhTP });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(tinhTP));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TinhTP>>();
        const tinhTP = { id: 123 };
        jest.spyOn(tinhTPService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ tinhTP });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: tinhTP }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(tinhTPService.update).toHaveBeenCalledWith(tinhTP);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TinhTP>>();
        const tinhTP = new TinhTP();
        jest.spyOn(tinhTPService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ tinhTP });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: tinhTP }));
        saveSubject.complete();

        // THEN
        expect(tinhTPService.create).toHaveBeenCalledWith(tinhTP);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TinhTP>>();
        const tinhTP = { id: 123 };
        jest.spyOn(tinhTPService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ tinhTP });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(tinhTPService.update).toHaveBeenCalledWith(tinhTP);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
