jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NhaCungCapService } from '../service/nha-cung-cap.service';
import { INhaCungCap, NhaCungCap } from '../nha-cung-cap.model';
import { IXaPhuong } from 'app/entities/xa-phuong/xa-phuong.model';
import { XaPhuongService } from 'app/entities/xa-phuong/service/xa-phuong.service';

import { NhaCungCapUpdateComponent } from './nha-cung-cap-update.component';

describe('Component Tests', () => {
  describe('NhaCungCap Management Update Component', () => {
    let comp: NhaCungCapUpdateComponent;
    let fixture: ComponentFixture<NhaCungCapUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let nhaCungCapService: NhaCungCapService;
    let xaPhuongService: XaPhuongService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhaCungCapUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NhaCungCapUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NhaCungCapUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      nhaCungCapService = TestBed.inject(NhaCungCapService);
      xaPhuongService = TestBed.inject(XaPhuongService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call XaPhuong query and add missing value', () => {
        const nhaCungCap: INhaCungCap = { id: 456 };
        const xa: IXaPhuong = { id: 40939 };
        nhaCungCap.xa = xa;

        const xaPhuongCollection: IXaPhuong[] = [{ id: 32402 }];
        jest.spyOn(xaPhuongService, 'query').mockReturnValue(of(new HttpResponse({ body: xaPhuongCollection })));
        const additionalXaPhuongs = [xa];
        const expectedCollection: IXaPhuong[] = [...additionalXaPhuongs, ...xaPhuongCollection];
        jest.spyOn(xaPhuongService, 'addXaPhuongToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ nhaCungCap });
        comp.ngOnInit();

        expect(xaPhuongService.query).toHaveBeenCalled();
        expect(xaPhuongService.addXaPhuongToCollectionIfMissing).toHaveBeenCalledWith(xaPhuongCollection, ...additionalXaPhuongs);
        expect(comp.xaPhuongsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const nhaCungCap: INhaCungCap = { id: 456 };
        const xa: IXaPhuong = { id: 50102 };
        nhaCungCap.xa = xa;

        activatedRoute.data = of({ nhaCungCap });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(nhaCungCap));
        expect(comp.xaPhuongsSharedCollection).toContain(xa);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<NhaCungCap>>();
        const nhaCungCap = { id: 123 };
        jest.spyOn(nhaCungCapService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhaCungCap });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhaCungCap }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(nhaCungCapService.update).toHaveBeenCalledWith(nhaCungCap);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<NhaCungCap>>();
        const nhaCungCap = new NhaCungCap();
        jest.spyOn(nhaCungCapService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhaCungCap });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhaCungCap }));
        saveSubject.complete();

        // THEN
        expect(nhaCungCapService.create).toHaveBeenCalledWith(nhaCungCap);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<NhaCungCap>>();
        const nhaCungCap = { id: 123 };
        jest.spyOn(nhaCungCapService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhaCungCap });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(nhaCungCapService.update).toHaveBeenCalledWith(nhaCungCap);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackXaPhuongById', () => {
        it('Should return tracked XaPhuong primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackXaPhuongById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
