import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlanOptionsFormService } from './plan-options-form.service';
import { PlanOptionsService } from '../service/plan-options.service';
import { IPlanOptions } from '../plan-options.model';

import { PlanOptionsUpdateComponent } from './plan-options-update.component';

describe('PlanOptions Management Update Component', () => {
  let comp: PlanOptionsUpdateComponent;
  let fixture: ComponentFixture<PlanOptionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planOptionsFormService: PlanOptionsFormService;
  let planOptionsService: PlanOptionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlanOptionsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PlanOptionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanOptionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planOptionsFormService = TestBed.inject(PlanOptionsFormService);
    planOptionsService = TestBed.inject(PlanOptionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const planOptions: IPlanOptions = { id: 456 };

      activatedRoute.data = of({ planOptions });
      comp.ngOnInit();

      expect(comp.planOptions).toEqual(planOptions);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanOptions>>();
      const planOptions = { id: 123 };
      jest.spyOn(planOptionsFormService, 'getPlanOptions').mockReturnValue(planOptions);
      jest.spyOn(planOptionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planOptions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planOptions }));
      saveSubject.complete();

      // THEN
      expect(planOptionsFormService.getPlanOptions).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planOptionsService.update).toHaveBeenCalledWith(expect.objectContaining(planOptions));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanOptions>>();
      const planOptions = { id: 123 };
      jest.spyOn(planOptionsFormService, 'getPlanOptions').mockReturnValue({ id: null });
      jest.spyOn(planOptionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planOptions: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planOptions }));
      saveSubject.complete();

      // THEN
      expect(planOptionsFormService.getPlanOptions).toHaveBeenCalled();
      expect(planOptionsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanOptions>>();
      const planOptions = { id: 123 };
      jest.spyOn(planOptionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planOptions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planOptionsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
