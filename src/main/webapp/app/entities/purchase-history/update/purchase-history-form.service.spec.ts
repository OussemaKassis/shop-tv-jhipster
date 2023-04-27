import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../purchase-history.test-samples';

import { PurchaseHistoryFormService } from './purchase-history-form.service';

describe('PurchaseHistory Form Service', () => {
  let service: PurchaseHistoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PurchaseHistoryFormService);
  });

  describe('Service methods', () => {
    describe('createPurchaseHistoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPurchaseHistoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            plan: expect.any(Object),
            purchaseDate: expect.any(Object),
            client: expect.any(Object),
          })
        );
      });

      it('passing IPurchaseHistory should create a new form with FormGroup', () => {
        const formGroup = service.createPurchaseHistoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            plan: expect.any(Object),
            purchaseDate: expect.any(Object),
            client: expect.any(Object),
          })
        );
      });
    });

    describe('getPurchaseHistory', () => {
      it('should return NewPurchaseHistory for default PurchaseHistory initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPurchaseHistoryFormGroup(sampleWithNewData);

        const purchaseHistory = service.getPurchaseHistory(formGroup) as any;

        expect(purchaseHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewPurchaseHistory for empty PurchaseHistory initial value', () => {
        const formGroup = service.createPurchaseHistoryFormGroup();

        const purchaseHistory = service.getPurchaseHistory(formGroup) as any;

        expect(purchaseHistory).toMatchObject({});
      });

      it('should return IPurchaseHistory', () => {
        const formGroup = service.createPurchaseHistoryFormGroup(sampleWithRequiredData);

        const purchaseHistory = service.getPurchaseHistory(formGroup) as any;

        expect(purchaseHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPurchaseHistory should not enable id FormControl', () => {
        const formGroup = service.createPurchaseHistoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPurchaseHistory should disable id FormControl', () => {
        const formGroup = service.createPurchaseHistoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
