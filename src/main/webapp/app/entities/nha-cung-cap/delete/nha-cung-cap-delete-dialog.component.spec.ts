jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { NhaCungCapService } from '../service/nha-cung-cap.service';

import { NhaCungCapDeleteDialogComponent } from './nha-cung-cap-delete-dialog.component';

describe('Component Tests', () => {
  describe('NhaCungCap Management Delete Component', () => {
    let comp: NhaCungCapDeleteDialogComponent;
    let fixture: ComponentFixture<NhaCungCapDeleteDialogComponent>;
    let service: NhaCungCapService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhaCungCapDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(NhaCungCapDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NhaCungCapDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NhaCungCapService);
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
