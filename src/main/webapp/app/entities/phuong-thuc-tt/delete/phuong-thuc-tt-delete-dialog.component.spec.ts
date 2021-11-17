jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PhuongThucTTService } from '../service/phuong-thuc-tt.service';

import { PhuongThucTTDeleteDialogComponent } from './phuong-thuc-tt-delete-dialog.component';

describe('Component Tests', () => {
  describe('PhuongThucTT Management Delete Component', () => {
    let comp: PhuongThucTTDeleteDialogComponent;
    let fixture: ComponentFixture<PhuongThucTTDeleteDialogComponent>;
    let service: PhuongThucTTService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhuongThucTTDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(PhuongThucTTDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhuongThucTTDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PhuongThucTTService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
