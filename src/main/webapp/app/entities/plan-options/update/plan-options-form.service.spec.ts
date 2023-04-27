import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plan-options.test-samples';

import { PlanOptionsFormService } from './plan-options-form.service';

describe('PlanOptions Form Service', () => {
  let service: PlanOptionsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanOptionsFormService);
  });

  describe('Service methods', () => {
    describe('createPlanOptionsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanOptionsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            aeTemplateLimit: expect.any(Object),
            videoSubmittionLimit: expect.any(Object),
            emojis: expect.any(Object),
            storage: expect.any(Object),
          })
        );
      });

      it('passing IPlanOptions should create a new form with FormGroup', () => {
        const formGroup = service.createPlanOptionsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            aeTemplateLimit: expect.any(Object),
            videoSubmittionLimit: expect.any(Object),
            emojis: expect.any(Object),
            storage: expect.any(Object),
          })
        );
      });
    });

    describe('getPlanOptions', () => {
      it('should return NewPlanOptions for default PlanOptions initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPlanOptionsFormGroup(sampleWithNewData);

        const planOptions = service.getPlanOptions(formGroup) as any;

        expect(planOptions).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanOptions for empty PlanOptions initial value', () => {
        const formGroup = service.createPlanOptionsFormGroup();

        const planOptions = service.getPlanOptions(formGroup) as any;

        expect(planOptions).toMatchObject({});
      });

      it('should return IPlanOptions', () => {
        const formGroup = service.createPlanOptionsFormGroup(sampleWithRequiredData);

        const planOptions = service.getPlanOptions(formGroup) as any;

        expect(planOptions).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanOptions should not enable id FormControl', () => {
        const formGroup = service.createPlanOptionsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanOptions should disable id FormControl', () => {
        const formGroup = service.createPlanOptionsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
